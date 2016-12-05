package navigation;

import parsing.Map;
import parsing.Node;
import parsing.OSMParser;
import parsing.Way;

import java.io.File;
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
     * The current (latest & relevant) path this generator has generated.
     */
    private LinkedList<Vertex> currentPath;
    private ShortestPathGenerator pathGenerator;

    /**
     * Initializes a DirectionsGenerator based on the specified Map.
     */
    public DirectionsGenerator(Map map) {
        this.map = map;
        this.map.createGraph();
        pathGenerator = new ShortestPathGenerator(map.getGraph());
    }

    /**
     * Computes a path from a specified starting to an ending point.
     * Uses Dijkstra's algorithm.
     *
     * @param startingNode The stating point.
     * @param endingNode   The ending point.
     * @return A list of ways representing a path from the starting to ending point.
     */
    public LinkedList<Vertex> findShortestPath(Node startingNode, Node endingNode) {
        LinkedList<Vertex> path = null;
        if (validateNodes(startingNode, endingNode)) {
            pathGenerator.execute(startingNode.getId());
            path = pathGenerator.getPath(endingNode.getId());
        }
        currentPath = path;
        return path;
    }

    public boolean validateNodes(Node startingNode, Node endingNode) {
        return !(map.findWaysByNode(startingNode).size() == 0 || map.findWaysByNode(endingNode).size() == 0);
    }


    /**
     * Sets the current path of the generator to the specified path.
     *
     * @param currentPath The new current path.
     */
    public void setCurrentPath(LinkedList<Vertex> currentPath) {
        this.currentPath = currentPath;
    }

    /**
     * Checks if coordinates are on the path.
     *
     * @return True if coordinates are part of the is the path. False otherwise.
     */
    public boolean areCoordinatesOnPath(double lon, double lat){
        for (Vertex vertex: currentPath){
            String vertexId = vertex.getId();
            Node correspondingNode = map.findNodeById(vertexId);
            if (correspondingNode.getLon() == lon && correspondingNode.getLat() == lat){
                return true;
            }
        }
        return false;
    }

    /**
     * Returns the starting point of this generator's current path.
     *
     * @return The starting point of this generator's current path.
     */
    private Node getCurrentStartingPoint() {
        return new Node(); // FOR NOW
    }

    /**
     * Returns the ending point of this generator's current path.
     *
     * @return The ending point of this generator's current path.
     */
    private Node getCurrentEndingPoint() {
        return new Node(); // FOR NOW
    }

    public static void main(String[] args) throws Exception {
        OSMParser parser = new OSMParser(new File(args[0]));
        parser.parse();
        Map map = parser.getMap();
        DirectionsGenerator dirGen = new DirectionsGenerator(map);
        dirGen.findShortestPath(map.findNodeById("700355297"), map.findNodeById("213527379"));
        int i = 3;
    }

}
