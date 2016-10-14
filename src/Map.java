import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by mpokr on 10/13/2016.
 */
public class Map {
    List<Node> nodeList;
    HashMap<String,Node> idToNodeMap;
    HashMap<String,Node> nameToNodeMap;
    List<Way> wayList;
    List<Relation> relationList;

    public Map(){
        nodeList = new ArrayList<>();
        wayList = new ArrayList<>();
        relationList = new ArrayList<>();
        idToNodeMap = new HashMap<>();
    }





    public void addElement(OSMElement osmElement) {
        if (osmElement instanceof Node) {
            Node nodeToAdd = ((Node) osmElement);
            nodeList.add(nodeToAdd);
            idToNodeMap.put(nodeToAdd.getId(), nodeToAdd);
        } else if (osmElement instanceof Way) {
            wayList.add((Way) osmElement);
        } else {
            relationList.add((Relation)osmElement);
        }
    }

    /**
     * Finds and returns a node according to its ID.
     * @param id The ID of the node to look for.
     * @return Matching node, if exists. Null otherwise.
     */
    public Node findNodeById(String id){
        if (idToNodeMap.containsKey(id)){
            return idToNodeMap.get(id);
        }
        return null;
    }

    /**
     * Finds and returns a node according to its name.
     * @param name The name of the node to look for.
     * @return Matching node, if exists. Null otherwise.
     */
    public Node findNodeByName(String name){
        if (nameToNodeMap.containsKey(name)){
            return nameToNodeMap.get(name);
        }
        return null;
    }

    /**
     * Finds node objects that a way contains.
     * @param way The way to find the nodes of.
     * @return A list of nodes objects that the way contains.
     */
    public List<Node> findNodesInWay(Way way){
        List<Node> nodesInWay = new ArrayList<>();
        for (String nodeRef : way.getNodeRefList()){
            Node foundNode = findNodeById(nodeRef);
            // Node exists (node ref is not bogus).
            if (foundNode != null){
                nodesInWay.add(foundNode);
            }
        }
        return nodesInWay;
    }

    public List<Way> getWayList(){
        return wayList;
    }


}
