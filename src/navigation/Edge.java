package navigation;

/**
 * Represents an edge in a graph.
 */
public class Edge {

    /**
     * The first vertex of this edge.
     */
    private Vertex first;
    /**
     * The second vertex of this edge.
     */
    private Vertex second;

    /**
     * Initializes an edge given two vertices
     * @param first The first vertex.
     * @param second The second vertex.
     */
    public Edge(Vertex first, Vertex second){
        this.first = first;
        this.second = second;
    }

    /**
     * Returns the first vertex of this edge.
     * @return The first vertex of this edge.
     */
    public Vertex getFirst() {
        return first;
    }


    /**
     * Returns the second vertex of this edge.
     * @return The second vertex of this edge.
     */
    public Vertex getSecond() {
        return second;
    }
}
