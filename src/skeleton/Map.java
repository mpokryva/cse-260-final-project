package skeleton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Represents a data-only (no GUI) OSM map.
 * Has collections of Nodes, Way, and Relation.
 */
public class Map {
    List<Node> nodeList;     // List of nodes
    HashMap<String, Node> idToNodeMap;  // Map of id's of Nodes to Nodes.
    HashMap<String, Node> nameToNodeMap; // Map of names of Nodes to Nodes.
    HashMap<String, Way> nameToWayMap;  // Map of names of Ways to Ways.
    List<Way> wayList;  // List of ways.
    List<Relation> relationList;    // List of relations
    private double minLat;  // Minimum latitude of this map.
    private double maxLat;  // Maximum latitude of this map.
    private double minLon;  // Minimum longitude of this map.
    private double maxLon;  // Maximum longitude of this map.

    /**
     * Default map constructor. Initializes fields, and not much else.
     */
    public Map() {

    }

    /**
     * Adds an OSMElement to this Map.
     * Handles different types of OSMElements (Nodes, Ways, Relations).
     * @param osmElement Element to add to this map.
     */
    public void addElement(OSMElement osmElement) {
        if (osmElement instanceof Node) {

        } else if (osmElement instanceof Way) {

        } else {

        }
    }

    /**
     * Finds and returns a node according to its ID.
     *
     * @param id The ID of the node to look for.
     * @return Matching node, if exists. Null otherwise.
     */
    public Node findNodeById(String id) {

    }

    /**
     * Finds and returns a node according to its name.
     *
     * @param nodeName The name of the node to look for.
     * @return Matching node, if exists. Null otherwise.
     */
    public Node findNodeByName(String nodeName) {

    }

    /**
     * Finds and returns a way according to its name.
     *
     * @param wayName The name of the way to look for.
     * @return Matching way, if exists. Null otherwise.
     */
    public Way findWayByName(String wayName) {

    }


    /**
     * Finds node objects that a way contains.
     *
     * @param way The way to find the nodes of.
     * @return A list of nodes objects that the way contains.
     */
    public List<Node> findNodesInWay(Way way) {

    }

    /**
     * Gets the names of all the ways in this map.
     * @return String array of way names.
     */
    public String[] getWayNames() {

    }

    /**
     * Returns the center longitude of this map.
     * @return This map's center longitude.
     */
    public double getCenterLon(){

    }

    /**
     * Returns the center latitude of this map.
     * @return This map's center latitude.
     */
    public double getCenterLat(){

    }




}
