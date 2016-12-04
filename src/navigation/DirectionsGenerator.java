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

        HashMap<String, Double> shortestPathSet = new HashMap<>();
        // Set distances to infinity, except for initial vertex.
        Set<String> vertexIdSet = idToVertexMap.keySet();
        for (String id : vertexIdSet){
            shortestPathSet.put(id, -1d);
            if (id.equals(startingVertex.getId())){
                shortestPathSet.put(id, 0d);
            }
        }
        HashSet<Vertex> visitedVertexSet = new HashSet<>();
        visitedVertexSet.add(startingVertex);
        HashSet<Vertex> unvisitedVertexSet = new HashSet<>();


        //HashMap<String, Boolean> shortestPathSet = new HashMap<>();
        while (!visitedVertexSet.contains(endingVertex)){
            Vertex currentVertex;
            for (Edge adjacentEdge : currentVertex.getAdjacentEdges()){

            }
        }
        return new ArrayList<Way>();
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
