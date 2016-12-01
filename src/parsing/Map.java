package parsing;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by mpokr on 10/13/2016.
 */
public class Map {
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
        } else {
            relationList.add((Relation) osmElement);
        }
        // Set the lon and lat ranges after the minimum lon and lat have been calculated.
        lonRange = maxLon - minLon;
        latRange = maxLat - minLat;
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
     * @param node The node to look for.
     * @return A list of nodes containing the specified node.
     */
    public List<Way> findWaysByNode(Node node){
        ArrayList<Way> waysContainingNode = new ArrayList<>();
        for (Way way : wayList){
            if (way.hasNode(node)){
                waysContainingNode.add(way);
            }
        }
        return waysContainingNode;
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
     * @return The distance from the maximum latitude to the minimum latitude.
     */
    public double getLatRange() {
        return latRange;
    }

    /**
     * Returns the distance from the maximum longitude to the minimum longitude.
     * @return The distance from the maximum longitude to the minimum longitude.
     */
    public double getLonRange() {
        return lonRange;
    }
}
