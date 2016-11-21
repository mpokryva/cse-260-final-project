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



}
