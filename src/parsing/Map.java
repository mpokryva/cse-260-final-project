package parsing;

import navigation.Edge;
import navigation.Graph;
import navigation.Vertex;
import navigation.VertexEdgeCollection;

import java.util.*;

/**
 * Created by mpokr on 10/13/2016.
 */
public class Map implements Graph {
    List<Node> nodeList;     // List of nodes
    HashMap<String, Node> idToNodeMap;  // Map of id's of Nodes to Nodes.
    HashMap<String, Node> nameToNodeMap; // Map of names of Nodes to Nodes.
    HashMap<String, Way> idToWayMap;     // Map of id's of Ways to Ways.
    HashMap<String, Way> nameToWayMap;  // Map of names of Ways to Ways.
    List<Way> wayList;  // List of ways.
    List<Relation> relationList;    // List of relations
    private double minLat;  // Minimum latitude of this map.
    private double maxLat;  // Maximum latitude of this map.
    private double minLon;  // Minimum longitude of this map.
    private double maxLon;  // Maximum longitude of this map.
    private VertexEdgeCollection graph;
    /**
     * Distance from minimum latitude to maximum latitude.
     */
    private double latRange;
    /**
     * Distance from minimum longitude to maximum longitude.
     */
    private double lonRange;


    /**
     * Default map constructor. Initializes fields, and not much else.
     */
    public Map() {
        nodeList = new ArrayList<>();
        wayList = new ArrayList<>();
        relationList = new ArrayList<>();
        idToNodeMap = new HashMap<>();
        nameToWayMap = new HashMap<>();
        idToWayMap = new HashMap<>();
        minLat = Integer.MAX_VALUE;
        maxLat = Integer.MIN_VALUE;
        minLon = Integer.MAX_VALUE;
        maxLon = Integer.MIN_VALUE;
    }


    /**
     * Adds an OSMElement to this Map.
     * Handles different types of OSMElements (Nodes, Ways, Relations).
     *
     * @param osmElement Element to add to this map.
     */
    public void addElement(OSMElement osmElement) {
        if (osmElement instanceof Node) {
            Node nodeToAdd = ((Node) osmElement);
            nodeList.add(nodeToAdd);
            idToNodeMap.put(nodeToAdd.getId(), nodeToAdd);
            //Temporary min lat and lon check. Should be placed elsewhere for more efficiency.
            double lat = nodeToAdd.getLat();
            double lon = nodeToAdd.getLon();
            if (lat < minLat) {
                minLat = lat;
            }
            if (lon < minLon) {
                minLon = lon;
            }
            if (lat > maxLat) {
                maxLat = lat;
            }
            if (lon > maxLon) {
                maxLon = lon;
            }
        } else if (osmElement instanceof Way) {
            Way wayToAdd = (Way) osmElement;
            wayList.add(wayToAdd);
            nameToWayMap.put(wayToAdd.getName(), wayToAdd);
            idToWayMap.put(wayToAdd.getId(), wayToAdd);
        } else {
            relationList.add((Relation) osmElement);
        }
        // Set the lon and lat ranges after the minimum lon and lat have been calculated.
        lonRange = maxLon - minLon;
        latRange = maxLat - minLat;
    }

    /**
     * Returns a weighted graph edge. Its weight corresponds to the specified way's length.
     *
     * @param way The way to be represented by the edge.
     * @return The weighted edge representing the specified way. Returns null if a faulty way is supplied
     * (a way with less than 2 nodes).
     */
    @Override
    public Edge constructEdge(Way way, Node startingNode, Node endingNode, HashMap<String, Vertex> idToVertexMap) {
        //List<Node> nodesInWay = findNodeSubListInWay(way, startingNode, endingNode);
        List<Node> nodesInWay = new ArrayList<>();
        nodesInWay.add(startingNode);
        nodesInWay.add(endingNode);
        Iterator<Node> it = nodesInWay.iterator();
        double weight = 0;
        Node previous;
        // check if way is non-faulty.
        if (nodesInWay.size() > 1)
            previous = it.next();
        else
            return null;
        while (it.hasNext()) {
            Node current = it.next();
            weight += getDistanceBetweenNodes(previous, current);
            previous = current;
        }
        Vertex startingVertex = new Vertex(startingNode.getId());
        Vertex endingVertex = new Vertex(endingNode.getId());
        if (idToVertexMap.containsKey(startingVertex.getId())){
            idToVertexMap.get(startingVertex.getId())
        }
        return new Edge(way.getId(), startingVertex, endingVertex, weight);
    }

    private double getDistanceBetweenNodes(Node startNode, Node endNode){
        double radianConversionFactor = Math.PI / 180;
        double lon1 = startNode.getLon() * radianConversionFactor;
        double lat1 = startNode.getLat() * radianConversionFactor;
        double lon2 = endNode.getLon() * radianConversionFactor;
        double lat2 = endNode.getLat() * radianConversionFactor;
            /*
            Haversine formula
            a = sin²(Δφ/2) + cos φ1 ⋅ cos φ2 ⋅ sin²(Δλ/2)
            c = 2 ⋅ atan2( √a, √(1−a) )
            d = R ⋅ c
            where	φ is latitude, λ is longitude, R is earth’s radius (mean radius = 6,371km);
             */
        double a0 = (Math.sin((lat2 - lat1) / 2) * Math.sin((lat2 - lat1)) / 2);
        double a1 = Math.cos(lat1) * Math.cos(lat2);
        double a2 = (Math.sin((lon2 - lon1) / 2) * Math.sin((lon2 - lon1)) / 2);
        double a = a0 + a1 * a2;
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double earthRadius = 6.371e6;
        return earthRadius * c;
    }


    /**
     * Creates a graph representation out of this map.
     *
     * @return The graph representation of this map.
     */
    @Override
    public void createGraph() {
        HashMap<String, Vertex> idVertexMap = new HashMap<>();
        List<Edge> edgeList = new ArrayList<>();
        for (Way way : wayList) {
            if (way.getId().equals("20240595")){
                int i =3;
            }
            List<Node> nodesInWay = this.findNodesInWay(way);
            Iterator<Node> it = nodesInWay.iterator();
            Node first = null;
            if (nodesInWay.size() > 1){
                first = it.next();
            }
            while (it.hasNext()){
                Node second = it.next();
                Edge edge = constructEdge(way, first, second);
                edgeList.add(edge);
                first = second;
            }
            /*
            //if (way.hasTag("highway")){
                List<Node> nodesInWay = this.findNodesInWay(way);

                Node firstNode = nodesInWay.get(0);
                Node lastNode = nodesInWay.get(nodesInWay.size() - 1);
                int nodesWithMultipleWays = 0;
                for (int i = 1; i < nodesInWay.size()-1; i++) {
                    Node testNode = nodesInWay.get(i);
                    List<Way> wayWithNode = findWaysByNode(testNode);
                    if (wayWithNode.size() > 1) {
                        nodesWithMultipleWays++;
                        Edge edge = constructEdge(way, firstNode, testNode);
                        idToEdgeMap.put(edge.getWayId(), edge);
                        firstNode = testNode;
                    }
                }
                if (nodesWithMultipleWays == 0){
                    Edge edge = constructEdge(way, firstNode, lastNode);
                    idToEdgeMap.put(edge.getWayId(), edge);
                }
            //}
            */
        }
        HashMap<String, Vertex> idToVertexMap = new HashMap<>();
        for (Edge edge : edgeList) {
            Vertex firstVertex = edge.getFirst();
            Vertex secondVertex = edge.getSecond();
            if (firstVertex.getId().equals("213726335") || secondVertex.getId().equals("213726335")){
                int i =3;
            }
            // Check if vertex map already contains vertex. If so, update it, and update the edge.
            if (idToVertexMap.containsKey(firstVertex.getId())){
                Vertex firstInMap = idToVertexMap.get(firstVertex.getId());
                firstInMap.addAdjacentEdge(edge);
                idToVertexMap.put(firstInMap.getId(), firstInMap);
                edge.setFirst(firstInMap);
            }
            else {
                idToVertexMap.put(firstVertex.getId(), firstVertex);
            }
            if (idToVertexMap.containsKey(secondVertex.getId())){
                Vertex secondInMap = idToVertexMap.get(secondVertex.getId());
                secondInMap.addAdjacentEdge(edge);
                idToVertexMap.put(secondInMap.getId(), secondInMap);
                edge.setSecond(secondInMap);
            }
            else {
                idToVertexMap.put(secondVertex.getId(), secondVertex);
            }


        }
        graph = new VertexEdgeCollection(idToVertexMap, edgeList);
        //int lone = countLoneNodes(nodeList);
        int i =3;
    }

    private int countLoneNodes(List<Node> nodes){
        int loneNodes = 0;
        for (Node node : nodes){
            if (findWaysByNode(node).size() == 0)
                loneNodes++;
        }
        return loneNodes;
    }


    /**
     * Finds and returns a node according to its ID.
     *
     * @param id The ID of the node to look for.
     * @return Matching node, if exists. Null otherwise.
     */
    public Node findNodeById(String id) {
        if (idToNodeMap.containsKey(id)) {
            return idToNodeMap.get(id);
        }
        return null;
    }

    /**
     * Finds and returns a node according to its name.
     *
     * @param nodeName The name of the node to look for.
     * @return Matching node, if exists. Null otherwise.
     */
    public Node findNodeByName(String nodeName) {
        if (nameToNodeMap.containsKey(nodeName)) {
            return nameToNodeMap.get(nodeName);
        }
        return null;
    }

    /**
     * Finds and returns a way according to its name.
     *
     * @param wayName The name of the way to look for.
     * @return Matching way, if exists. Null otherwise.
     */
    public Way findWayByName(String wayName) {
        if (wayName != null && nameToWayMap.containsKey(wayName)) {
            return nameToWayMap.get(wayName);
        }
        return null;
    }

    /**
     * Returns the way associated with the specified id.
     *
     * @param id The id of the way to find.
     * @return The way, if it can be found. Null otherwise.
     */
    public Way findWayById(String id) {
        return idToWayMap.get(id);
    }


    /**
     * Finds node objects that a way contains.
     *
     * @param way The way to find the nodes of.
     * @return A list of nodes objects that the way contains.
     */
    public List<Node> findNodesInWay(Way way) {
        List<Node> nodesInWay = new ArrayList<>();
        for (String nodeRef : way.getNodeRefList()) {
            Node foundNode = findNodeById(nodeRef);
            // Checks if node exists (node ref is not bogus).
            if (foundNode != null) {
                nodesInWay.add(foundNode);
            }
        }
        return nodesInWay;
    }

    /**
     * Returns a sub list of nodes in way, from a specified to node to another.
     *
     * @param way       The way to find the nodes of.
     * @param firstNode The beginning node of the sub list.
     * @param lastNode  The ending node of the sub list.
     * @return The sublist of nodes in the way.
     */
    public List<Node> findNodeSubListInWay(Way way, Node firstNode, Node lastNode) {
        String firstRef = firstNode.getId();
        String lastRef = lastNode.getId();
        Integer firstIndex = null;
        Integer lastIndex = null;
        List<Node> nodesInWay = new ArrayList<>();
        List<String> nodeRefList = way.getNodeRefList();
        // Check if firstNode and lastNode are actually the first and last nodes of the specified way.
        if (firstNode.equals(findNodeById(nodeRefList.get(0))) && lastNode.equals(findNodeById(nodeRefList.get(nodeRefList.size()-1)))){
            return findNodesInWay(way);
        }
        for (int i = 0; i < nodeRefList.size(); i++) {
            Node foundNode = findNodeById(nodeRefList.get(i));
            // Checks if node exists (node ref is not bogus).
            if (foundNode != null) {
                nodesInWay.add(foundNode);
                if (lastIndex != null && firstIndex != null)
                    return nodesInWay.subList(firstIndex, lastIndex + 1);

                if (foundNode.getId().equals(firstRef)) {
                    firstIndex = i;
                } else if (foundNode.getId().equals(lastRef)) {
                    lastIndex = i;
                }

            }

        }
        return nodesInWay.subList(firstIndex, lastIndex);


    }


    /**
     * Returns an array of way names.
     *
     * @return A array of way names.
     */
    public String[] getWayNames() {
        String[] wayNames = new String[wayList.size()];
        for (int i = 0; i < wayList.size(); i++) {
            wayNames[i] = wayList.get(i).getName();
        }
        return wayNames;
    }

    /**
     * Returns a list of ways that contain a node that is nearest to a specified lon and lat.
     *
     * @param lon The longitude to look for.
     * @param lat The latitude to look for.
     * @return A list of ways containing a node at this latitude and longitude.
     */
    public List<Way> findWaysByLonLat(double lon, double lat) {
        Node nearestNode = findNearestNode(lon, lat);
        return findWaysByNode(nearestNode);
    }

    /**
     * Returns a list of ways that contains the specified node.
     *
     * @param node The node to look for.
     * @return A list of nodes containing the specified node.
     */
    public List<Way> findWaysByNode(Node node) {
        ArrayList<Way> waysContainingNode = new ArrayList<>();
        for (Way way : wayList) {
            if (way.hasNode(node)) {
                waysContainingNode.add(way);
            }
        }
        return waysContainingNode;
    }

    /**
     * Returns the node nearest to the specified node.
     * @param node The specified node.
     * @return The node nearest to the specified node.
     */
    public Node findNearestNode(Node node){
        double minDistance = Double.POSITIVE_INFINITY;
        Node nearestNode = null;
        for (Node nodeToCompare : nodeList){
            if (!nodeToCompare.equals(node)){
                double distanceBetween = getDistanceBetweenNodes(node, nodeToCompare);
                if (distanceBetween < minDistance){
                    minDistance = distanceBetween;
                    nearestNode = nodeToCompare;
                }
            }
        }
        return nearestNode;
    }

    /**
     * Given a longitude and latitude, returns the nearest node.
     *
     * @param coordLon The longitude, in map coordinate units.
     * @param coordLat The latitude, in map coordinate units.
     * @return The nearest node to the given coordinates.
     */
    public Node findNearestNode(double coordLon, double coordLat) {
        double minDistance = Double.MAX_VALUE;
        Node nearestNode = null;
        for (Way way : wayList) {
            List<Node> nodesInWay = findNodesInWay(way);
            for (Node node : nodesInWay) {
                double xDiff = node.getLon() - coordLon;
                double yDiff = node.getLat() - coordLat;
                double distance = Math.sqrt((xDiff * xDiff) + (yDiff * yDiff));
                if (distance < minDistance) {
                    minDistance = distance;
                    nearestNode = node;
                }
            }
        }
        return nearestNode;
    }

    /**
     * Returns the center longitude of this map.
     *
     * @return This map's center longitude.
     */
    public double getCenterLon() {
        return (getMaxLon() - getMinLon()) / 2 + getMinLon();
    }

    /**
     * Returns the center latitude of this map.
     *
     * @return This map's center latitude.
     */
    public double getCenterLat() {
        return (getMaxLat() - getMinLat()) / 2 + getMinLat();
    }

    /**
     * Returns this map's ways as a list.
     *
     * @return A list of this map's ways
     */
    public List<Way> getWayList() {
        return wayList;
    }

    /**
     * Returns the minimum latitude of this map.
     *
     * @return This map's minimum latitude.
     */
    public double getMinLat() {
        return minLat;
    }

    /**
     * Set the minimum latitude of this map.
     *
     * @param minLat The new minimum latitude
     */
    public void setMinLat(double minLat) {
        this.minLat = minLat;
    }

    /**
     * Set the maximum latitude of this map.
     *
     * @return maxLat The new maximum latitude
     */
    public double getMaxLat() {
        return maxLat;
    }

    /**
     * Set the maximum latitude of this map.
     *
     * @param maxLat The new maximum latitude
     */
    public void setMaxLat(double maxLat) {
        this.maxLat = maxLat;
    }

    /**
     * Returns the minimum longitude of this map.
     *
     * @return This map's minimum longitude.
     */
    public double getMinLon() {
        return minLon;
    }

    /**
     * Set the minimum longitude of this map.
     *
     * @param minLon The new minimum longitude
     */
    public void setMinLon(double minLon) {
        this.minLon = minLon;
    }

    /**
     * Set the maximum longitude of this map.
     *
     * @return maxLon The new minimum longitude
     */
    public double getMaxLon() {
        return maxLon;
    }

    /**
     * Set the maximum longitude of this map.
     *
     * @param maxLon The new maximum longitude
     */
    public void setMaxLon(double maxLon) {
        this.maxLon = maxLon;
    }

    /**
     * Returns the distance from the maximum latitude to the minimum latitude.
     *
     * @return The distance from the maximum latitude to the minimum latitude.
     */
    public double getLatRange() {
        return latRange;
    }

    /**
     * Returns the distance from the maximum longitude to the minimum longitude.
     *
     * @return The distance from the maximum longitude to the minimum longitude.
     */
    public double getLonRange() {
        return lonRange;
    }

    /**
     * Returns this map's graph representation.
     *
     * @return This map's graph representation.
     */
    public VertexEdgeCollection getGraph() {
        return graph;
    }
}
