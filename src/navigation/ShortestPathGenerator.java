package navigation;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import parsing.Map;
import parsing.OSMParser;

import java.util.Set;


public class ShortestPathGenerator {

    private final HashMap<String, Vertex>  idToVertexMap;
    private final List<Edge> edges;
    private HashSet<Vertex> visitedVertexSet;
    private HashSet<Vertex> unvisitedVertexSet;
    private HashMap<Vertex, Vertex> predecessorMap;
    private HashMap<Vertex, Double> distanceMap;

    public ShortestPathGenerator(VertexEdgeCollection vertexEdgeCollection) {
        this.idToVertexMap = vertexEdgeCollection.getIdToVertexMap();
        this.edges = new ArrayList<>(vertexEdgeCollection.getEdgeList());
    }

    public void execute(String initialId) {
        Vertex initial = idToVertexMap.get(initialId);
        visitedVertexSet = new HashSet<>();
        unvisitedVertexSet = new HashSet<>();
        distanceMap = new HashMap<>();
        predecessorMap = new HashMap<>();
        distanceMap.put(initial, 0d);
        unvisitedVertexSet.add(initial);
        while (unvisitedVertexSet.size() > 0) {
            Vertex node = getMinimum(unvisitedVertexSet);
            visitedVertexSet.add(node);
            unvisitedVertexSet.remove(node);
            findMinimalDistances(node);
        }
    }

    private void findMinimalDistances(Vertex node) {
        List<Vertex> adjacentNodes = getNeighbors(node);
        for (Vertex target : adjacentNodes) {
            if (getShortestDistance(target) > getShortestDistance(node)
                    + getDistance(node, target)) {
                distanceMap.put(target, getShortestDistance(node)
                        + getDistance(node, target));
                predecessorMap.put(target, node);
                unvisitedVertexSet.add(target);
            }
        }

    }

    private double getDistance(Vertex node, Vertex target) {
        return node.getEdgeConnectedToOther(target).getWeight();
    }

    private List<Vertex> getNeighbors(Vertex node) {
        return node.getNeighborVertices();
    }

    private Vertex getMinimum(Set<Vertex> vertexes) {
        Vertex minimum = null;
        for (Vertex vertex : vertexes) {
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

    private boolean isSettled(Vertex vertex) {
        return visitedVertexSet.contains(vertex);
    }

    private Double getShortestDistance(Vertex destination) {
        Double d = distanceMap.get(destination);
        if (d == null) {
            return Double.MAX_VALUE;
        } else {
            return d;
        }
    }


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

    public static void main(String[] args) throws Exception {
        OSMParser parser = new OSMParser(new File(args[0]));
        parser.parse();
        Map map = parser.getMap();
        //ShortestPathGenerator test = new ShortestPathGenerator(map);
        //test.execute(map.getGraph().getIdToVertexMap().get("1924650362"));
        //LinkedList<Vertex> path = test.getPath(map.getGraph().getIdToVertexMap().get("213560856"));
    }

}
