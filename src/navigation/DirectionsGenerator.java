package navigation;

import parsing.Map;
import parsing.Node;
import parsing.Way;

import java.util.*;

/**
 * Reponsible for generating directions.
 */
public class DirectionsGenerator {

    /**
     * This generator's associated map.
     */
    private Map map;
    /**
     * This generator's associated map graph.
     */
    private Graph mapGraph;

    /**
     * The current (latest & relevant) path this generator has generated.
     */
    private List<Way> currentPath;
    /**
     * Initializes a DirectionsGenerator based on the specified Map.
     */
    public DirectionsGenerator(Map map){
        map.createGraph();
    }

    /**
     * Computes a path from a specified starting to an ending point.
     * Uses Dijkstra's algorithm.
     * @param startingNode The stating point.
     * @param endingNode The ending point.
     * @return A list of ways representing a path from the starting to ending point.
     */
    public List<Way> generatePath(Node startingNode, Node endingNode){
        VertexEdgeCollection vertexEdgeCollection = map.getGraph();
        HashMap<String, Edge> idToEdgeMap = vertexEdgeCollection.getIdToEdgeMap();
        HashMap<String, Vertex> idToVertexMap = vertexEdgeCollection.getIdToVertexMap();
        Vertex startingVertex = idToVertexMap.get(startingNode.getId());
        Vertex endingVertex = idToVertexMap.get(endingNode.getId());

        LinkedHashMap<String, Double> shortestPathMap = new LinkedHashMap<>();
        HashSet<Vertex> visitedSet = new HashSet<>();
        HashSet<Vertex> unvisitedSet = new HashSet<>();
        Set<String> vertexIdSet = idToVertexMap.keySet();
        // Stores the id of the vertex and its distance to the initial vertex.
        HashMap<String, Double> idToDistanceMap = new HashMap<>();
        // Set distances to infinity, except for initial vertex.
        for (String id : vertexIdSet){
            idToDistanceMap.put(id, Double.POSITIVE_INFINITY); // filling up distance hashMap.
            unvisitedSet.add(idToVertexMap.get(id)); // filling up unvisited set.
        }
        idToDistanceMap.put(startingVertex.getId(), 0d); // setting distance for initial vertex.
        visitedSet.add(startingVertex); // adding initial vertex to visited set.
        unvisitedSet.remove(startingVertex); // remove initial vertex from unvisited set.

        Vertex currentVertex = startingVertex;
        // Unvisited vertex with smallest tentative distance.
        Vertex minDistUnvisitedVertex = getMinDistanceVertex(unvisitedSet, idToDistanceMap);
             /*
        The algorithm.
         */
        while (!visitedSet.contains(endingVertex)){
            Vertex minDistanceNeighbor = updateDistances(currentVertex, visitedSet, unvisitedSet, idToDistanceMap);
            visitedSet.add(currentVertex);
            unvisitedSet.remove(currentVertex);
            if (idToDistanceMap.get(minDistanceNeighbor.getId()) < idToDistanceMap.get(minDistUnvisitedVertex.getId()))
                minDistUnvisitedVertex = minDistanceNeighbor;
            currentVertex = minDistUnvisitedVertex;
            shortestPathMap.put(currentVertex.getId(), idToDistanceMap.get(currentVertex.getId()));
        }

        return new ArrayList<Way>();
    }

    /**
     * Updates the minimum distances of the unvisited neighbors of the specified vertex, and returns
     * the vertex with the minimum distance among them.
     * @param currentVertex The specified vertex.
     * @param visitedSet The set of visited vertices.
     * @param unvisitedSet The set of unvisited vertices.
     * @param idToDistanceMap A hash map of vertex id's to their tentative distances.
     * @return The unvisited, neighboring node with the smallest tentative distance.
     */
    private Vertex updateDistances(Vertex currentVertex, HashSet<Vertex> visitedSet,
                                   HashSet<Vertex> unvisitedSet, HashMap<String, Double> idToDistanceMap){
        Vertex minDistanceVertex = null; // The neighboring unvisited vertex with the smallest tentative distance.
        double minTentativeDistance = Double.POSITIVE_INFINITY; // the tentative distance of minDistanceVertex.
        for (Edge adjacentEdge : currentVertex.getAdjacentEdges()){
            Vertex complementVertex = adjacentEdge.getComplementVertex(currentVertex);
            if (unvisitedSet.contains(complementVertex)){
                double tentativeDistance = idToDistanceMap.get(complementVertex.getId());
                double distanceToCurrent = idToDistanceMap.get(currentVertex.getId());
                double distanceToNeighbor = adjacentEdge.getWeight();
                double totalDistance = distanceToCurrent + distanceToNeighbor;
                if (totalDistance < tentativeDistance){
                    idToDistanceMap.put(complementVertex.getId(), totalDistance);
                    if (totalDistance < minTentativeDistance)
                        minTentativeDistance = totalDistance;
                        minDistanceVertex = complementVertex;
                }
                else {
                    if (tentativeDistance < minTentativeDistance)
                        minTentativeDistance = tentativeDistance;
                        minDistanceVertex = complementVertex;
                }
            }
        }
        return minDistanceVertex;
    }

    /**
     * Returns the vertex with the minimum distance from the specified set.
     * @param vertexSet The specified set of vertices.
     * @param idToDistanceMap A hashmap of vertex id's to their distances.
     * @return The vertex in vertexSet with the smallest distance.
     */
    private Vertex getMinDistanceVertex(HashSet<Vertex> vertexSet, HashMap<String, Double> idToDistanceMap){
        Vertex minDistanceVertex = null;
        double minDistance = Double.POSITIVE_INFINITY;
        for (Vertex vertex : vertexSet){
            double currentVertexDistance = idToDistanceMap.get(vertex.getId());
            if (currentVertexDistance < minDistance){
                minDistanceVertex = vertex;
                minDistance = currentVertexDistance;
            }
        }
        return minDistanceVertex;
    }


    /**
     * Sets the current path of the generator to the specified path.
     * @param currentPath The new current path.
     */
    public void setCurrentPath(List<Way> currentPath){
        this.currentPath = currentPath;
    }

    /**
     * Checks if way is in the current path of the generator.
     * @return True if way in is the path. False otherwise.
     */
    public boolean isWayInCurrentPath(Way way){
        return true; //FOR NOW.
    }

    /**
     * Returns the starting point of this generator's current path.
     * @return The starting point of this generator's current path.
     */
    private Node getCurrentStartingPoint(){
        return new Node(); // FOR NOW
    }

    /**
     * Returns the ending point of this generator's current path.
     * @return The ending point of this generator's current path.
     */
    private Node getCurrentEndingPoint(){
        return new Node(); // FOR NOW
    }



}
