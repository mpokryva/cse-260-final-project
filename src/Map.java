import java.util.ArrayList;
import java.util.List;

/**
 * Created by mpokr on 10/13/2016.
 */
public class Map {
    List<Node> nodes;
    List<Way> ways;
    List<Relation> relations;

    public Map(){
        nodes = new ArrayList<>();
        ways = new ArrayList<>();
        relations = new ArrayList<>();
    }

    public void addNode(Node node) {
        nodes.add(node);
    }

    public void addWay(Way way) {
        ways.add(way);
    }

    public void addRelation(Relation relation) {
        relations.add(relation);
    }

    public void addElement(OSMElement osmElement) {
        if (osmElement instanceof Node) {
            addNode((Node) osmElement);
        } else if (osmElement instanceof Way) {
            addWay((Way) osmElement);
        } else {
            addRelation((Relation) osmElement);
        }
    }


}
