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
     * The weight of this edge
     */
    private double weight;

    /**
     * Initializes an edge given two vertices
     * @param first The first vertex.
     * @param second The second vertex.
     */
    public Edge(Vertex first, Vertex second, double weight){
        this.first = first;
        this.second = second;
        this.weight = weight;
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

    /**
     * Returns the edge's weight.
     * @return The edge's weight.
     */
    public double getWeight(){
        return weight;
    }
}
