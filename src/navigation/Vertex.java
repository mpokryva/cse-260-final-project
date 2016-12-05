package navigation;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a vertex in a graph.
 */
public class Vertex {
    /**
     * The id of this vertex.
     */
    private String id;
    /**
     * All the adjacentEdges connected to this vertex
     */
    private ArrayList<Edge> adjacentEdges;
    /**
     * Initializes a vertex based on the given id.
     * @param id The id of the vertex.
     */
    public Vertex(String id){
        this.id = id;
        adjacentEdges = new ArrayList<>();
    }

    /**
     * Adds an edge to this vertex's list of adjacent edges.
     * @param edge The edge to add.
     */
    public void addAdjacentEdge(Edge edge){
        adjacentEdges.add(edge);
    }

    /**
     * Convenience method to add an edge list to this vertex's list of adjacent edges.
     * @param edgeList The list of edges to add.
     */
    public void addAdjacentEdgeList(List<Edge> edgeList){
        adjacentEdges.addAll(edgeList);
    }

    /**
     * Returns this vertex's id.
     * @return This vertex's id.
     */
    public String getId() {
        return id;
    }

    /**
     * Returns this vertex's list of adjacent edges.
     * @return This vertex's list of adjacent edges.
     */
    public ArrayList<Edge> getAdjacentEdges() {
        return adjacentEdges;
    }

    public ArrayList<Vertex> getNeighborVertices(){
        ArrayList<Vertex> neighbors = new ArrayList<>();
        for (Edge adjacentEdge : adjacentEdges){
            neighbors.add(adjacentEdge.getComplementVertex(this));
        }
        return neighbors;
    }

    /**
     * Returns this vertex's hashcode.
     *
     * @return This vertex's hashcode.
     */
    @Override
    public int hashCode() {
        return id.hashCode();
    }

    /**
     * Only equal if the two vertex's id's are equal.
     *
     * @param other The vertex to compare to.
     * @return True if the two vertex's id's are equal.
     */
    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Vertex)) {
            return false;
        }
        return this.getId().equals(((Vertex) other).getId());
    }

    /**
     * Returns an edge that is connected to this vertex and the specified vertex, if it exists.
     * @param other The specified vertex.
     * @return The edge that is connected to this vertex and the specified vertex, if it exists. Returns null if it doesn't.
     */
    public Edge getEdgeConnectedToOther(Vertex other){
        for (Edge adjacentEdge : adjacentEdges){
            if (adjacentEdge.getComplementVertex(this).equals(other)){
                return adjacentEdge;
            }
        }
        return null;
    }
    
    
    
}
