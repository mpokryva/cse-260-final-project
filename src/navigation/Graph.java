package navigation;

import parsing.Map;
import parsing.Node;
import parsing.Way;

import java.util.List;

/**
 * Created by mpokr on 11/20/2016.
 */
public interface Graph {


    Edge constructEdge(Way way);

    Vertex constructVertex(Node node);




}
