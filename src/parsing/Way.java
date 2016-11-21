package parsing;

import java.util.ArrayList;
import java.util.List;

/**
 * An object of this class represents a way in an OSM file.
 */
public class Way extends OSMElement {

    /**
     * A list of this ways's node references.
     */
    private List<String> nodeRefs;

    /**
     * Initializes a way with the given ID.
     *
     * @param id The ID of this way.
     */
    public Way(String id) {
        super(id);
        nodeRefs = new ArrayList<>();
    }

    /**
     * Adds a node reference ID to the node reference list.
     *
     * @param nodeRefID The node ref ID to add.
     */
    public void addNodeRef(String nodeRefID) {
        nodeRefs.add(nodeRefID);
    }

    /**
     * Returns this way's node references as a list.
     *
     * @return A list of this way's node references.
     */
    public List<String> getNodeRefList() {
        return nodeRefs;
    }

    /**
     * Checks if this way has the specified node.
     * @param node The node to check for.
     * @return True if way contains this node. False otherwise.
     */
    public boolean hasNode(Node node){
        return nodeRefs.contains(node.getId());
    }


}
