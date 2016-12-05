package navigation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

import java.util.Set;


public class ShortestPathGenerator {

    /**
     * The map of id's of vertices to vertices.
     */
    private final HashMap<String, Vertex>  idToVertexMap;
    /**
     * The list of edges.
     */
    private final List<Edge> edges;
    /**
     * The set of visited vertices.
     */
    private HashSet<Vertex> visitedVertexSet;
    /**
     * The set of unvisited vertices.
     */
    private HashSet<Vertex> unvisitedVertexSet;
    /**
     * The map of vertices to their predecessors.
     */
    private HashMap<Vertex, Vertex> predecessorMap;
    /**
     * The map of vertices to their minimum distances to a specified vertex.
     */
    private HashMap<Vertex, Double> distanceMap;

    public ShortestPathGenerator(VertexEdgeCollection vertexEdgeCollection) {
        this.idToVertexMap = vertexEdgeCollection.getIdToVertexMap();
        this.edges = new ArrayList<>(vertexEdgeCollection.getEdgeList());
    }

    /**
     *  Executes Dijkstra's algorithm from the vertex with the specified initial id.
     * @param initialId The specified initial id of the vertex.
     */
    public void execute(String initialId) {
        Vertex initial = idToVertexMap.get(initialId);
        visitedVertexSet = new HashSet<>();
        unvisitedVertexSet = new HashSet<>();
        distanceMap = new HashMap<>();
        predecessorMap = new HashMap<>();
        distanceMap.put(initial, 0d);
        unvisitedVertexSet.add(initial);
        while (unvisitedVertexSet.size() > 0) {
            Vertex currentVertex = getMinimum(unvisitedVertexSet);
            visitedVertexSet.add(currentVertex);
            unvisitedVertexSet.remove(currentVertex);
            updateMinimumDistances(currentVertex);
        }
    }

    /**
     * Updates the minimum distances from the specified vertex.
     * @param vertex The specified vertex.
     */
    private void updateMinimumDistances(Vertex vertex) {
        List<Vertex> adjacentNodes = vertex.getNeighborVertices();
        for (Vertex target : adjacentNodes) {
            if (getShortestDistance(target) > getShortestDistance(vertex)
                    + getDistance(vertex, target)) {
                distanceMap.put(target, getShortestDistance(vertex)
                        + getDistance(vertex, target));
                predecessorMap.put(target, vertex);
                unvisitedVertexSet.add(target);
            }
        }

    }

    /**
     * Returns distance between two specified vertices.
     * @param vertex First vertex.
     * @param target Target vertex.
     * @return The distance between the first and target vertices.
     */
    private double getDistance(Vertex vertex, Vertex target) {
        return vertex.getEdgeConnectedToOther(target).getWeight();
    }


    /**
     * Gets the minimum distance from the set of vertices.
     * @param vertexSet The set of vertices.
     * @return The vertex with the minimum distance.
     */
    private Vertex getMinimum(Set<Vertex> vertexSet) {
        Vertex minimum = null;
        for (Vertex vertex : vertexSet) {
            if (minimum == null) {
                minimum = vertex;
            } else {
                if (getShortestDistance(vertex) < getShortestDistance(minimum)) {
                    minimum = vertex;
                }
            }
        }
        return minimum;
    }

    /**
     * Convenience method for retrieving the distance of the specified vertex from the shortest distance hash map.
     * @param destination The specified vertex.
     * @return The current shortest distance of the specified vertex.
     */
    private Double getShortestDistance(Vertex destination) {
        Double distance = distanceMap.get(destination);
        if (distance == null) {
            return Double.MAX_VALUE;
        } else {
            return distance;
        }
    }


    /**
     * Reconstructs the path to a vertex with the specified id and returns it as a linked list of vertices.
     * @param targetId The id of the vertex to make the path to.
     * @return The shortest path to this vertex. Null if the path does not exists.
     */
    public LinkedList<Vertex> getPath(String targetId) {
        Vertex target = idToVertexMap.get(targetId);
        LinkedList<Vertex> path = new LinkedList<>();
        Vertex nextInPath = target;
        // check if a path exists
        if (predecessorMap.get(nextInPath) == null) {
            return null;
        }
        path.add(nextInPath);
        while (predecessorMap.get(nextInPath) != null) {
            nextInPath = predecessorMap.get(nextInPath);
            path.add(nextInPath);
        }
        // Put path into the correct order.
        Collections.reverse(path);
        return path;
    }



}
