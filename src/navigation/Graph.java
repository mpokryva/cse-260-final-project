package navigation;

import parsing.Map;
import parsing.Node;
import parsing.Way;

import java.util.HashMap;
import java.util.List;

/**
 * Created by mpokr on 11/20/2016.
 */
public interface Graph {


    Edge constructEdge(Way way, Node startingNode, Node endingNode, HashMap<String, Vertex> idToVertexMap);
    void createGraph();





}
