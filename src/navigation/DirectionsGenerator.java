package navigation;

import parsing.Map;
import parsing.Node;
import parsing.Way;

import java.util.ArrayList;
import java.util.List;

/**
 * Reponsible for generating directions.
 */
public class DirectionsGenerator {

    /**
     * Initializes a DirectionsGenerator based on the specified Map.
     */
    public DirectionsGenerator(Map map){
        // Creates a Graph of the map, etc.
    }

    /**
     * Computes a path from a specified starting to an ending point.
     * Uses Dijkstra's algorithm.
     * @param startingPoint The stating point.
     * @param endingPoint The ending point.
     * @return A list of ways representing a path from the starting to ending point.
     */
    public List<Way> generatePath(Node startingPoint, Node endingPoint){
        return new ArrayList<Way>();
    }



}
