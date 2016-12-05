package navigation;

import java.util.HashMap;
import java.util.List;

/**
 * Created by mpokr on 12/3/2016.
 */
public class VertexEdgeCollection {

    /**
     * This collection's list of vertices.
     */
    private HashMap<String, Vertex> idToVertexMap;
    /**
     * This collections list of edges.
     */
    private List<Edge> edgeList;

    /**
     * Configures this VertexEdgeCollection with the supplied lists.
     * @param vertexList The collection's vertex list.
     * @param edgeList The collection's edge list.
     */
    public VertexEdgeCollection(HashMap<String, Vertex> vertexList, List<Edge> edgeList){
        this.idToVertexMap = vertexList;
        this.edgeList = edgeList;
    }

    /**
     * Return this collection's map of id's to vertices.
     * @return this collection's map of id's to vertices.
     */
    public HashMap<String, Vertex> getIdToVertexMap() {
        return idToVertexMap;
    }

    /**
     * Return this collection's map of id's to edges.
     * @return this collection's list of id's to edges.
     */
    public List<Edge> getEdgeList() {
        return edgeList;
    }
}
