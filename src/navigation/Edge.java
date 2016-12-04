package navigation;

/**
 * Represents an edge in a graph.
 */
public class Edge {

    /**
     * The wayId to of the way this edge represents.
     */
    private String wayId;
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
    public Edge(String id, Vertex first, Vertex second, double weight){
        this.wayId = id;
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

    /**
     * Returns the vertex of the edge that is not the vertex specified.
     * Specified vertex must exist.
     * @param vertex The vertex to not return/
     * @return The complement of the specified vertex, if the edge has the specified vertex.
     * Otherwise, throws an IllegalArgumentException.
     * @throws IllegalArgumentException Thrown if edge does not have the specified vertex.
     */
    public Vertex getComplementVertex(Vertex vertex){
        if (first.equals(vertex))
            return second;
        else if (second.equals(vertex))
            return first;
        else
            throw new IllegalArgumentException();
    }

    /**
     * Return's the wayId of the way this edge represents.
     * @return The wayId of the way this edge represents.
     */
    public String getWayId() {
        return wayId;
    }

    /**
     * Returns this edge's hashcode.
     *
     * @return This edge's hashcode.
     */
    @Override
    public int hashCode() {
        return wayId.hashCode();
    }

    /**
     * Only equal if the two edge's wayId's are equal.
     *
     * @param other The edge to compare to.
     * @return True if the two edge's wayId's are equal.
     */
    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Edge)) {
            return false;
        }
        return this.getWayId().equals(((Edge) other).getWayId());
    }

    public void setFirst(Vertex first) {
        this.first = first;
    }

    public void setSecond(Vertex second) {
        this.second = second;
    }
}
