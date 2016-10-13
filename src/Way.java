import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.StringJoiner;

/**
 * Created by dpokryvailo on 10/11/2016.
 */
public class Way extends OSMElement {
    private List<String> nodeRefs;


    /**
     * Adds a node reference ID to the node reference list.
     *
     * @param nodeRefID The node ref ID to add.
     */
    public void addNodeRef(String nodeRefID) {
        nodeRefs.add(nodeRefID);
    }

    public Way(String id) {
        super(id);
        nodeRefs = new ArrayList<>();
    }
}
