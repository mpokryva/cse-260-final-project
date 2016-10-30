package handin1;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dpokryvailo on 10/11/2016.
 */
public class Way extends OSMElement {

    private List<String> nodeRefs;

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

    public List<String> getNodeRefList(){
        return nodeRefs;
    }


}
