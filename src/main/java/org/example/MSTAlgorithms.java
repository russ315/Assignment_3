package org.example;

import java.util.*;
import java.util.stream.Collectors;

public class MSTAlgorithms {

    public static AlgorithmStats kruskalAlgorithm(Set<String> nodes, List<EdgeDef> edgeDefs) {
        OpCounter counter = new OpCounter();
        long startTime = System.nanoTime();

        List<Edge> mst = new ArrayList<>();
        int totalCost = 0;

        // Convert EdgeDefs to Edges with counters
        List<Edge> edges = edgeDefs.stream()
                .map(ed -> new Edge(ed.from, ed.to, ed.weight, counter))
                .collect(Collectors.toList());

        // 1. Sort edges. Comparisons are counted by Edge.compareTo()
        Collections.sort(edges);

        // 2. Initialize DSU
        DisjointSet ds = new DisjointSet(nodes, counter);

        // 3. Iterate
        for (Edge edge : edges) {
            counter.increment(); // Count loop iteration/edge consideration
            String rootFrom = ds.find(edge.from);
            String rootTo = ds.find(edge.to);

            if (!rootFrom.equals(rootTo)) {
                mst.add(edge);
                totalCost += edge.weight;
                ds.union(edge.from, edge.to);
            }
        }

        long endTime = System.nanoTime();
        double execTimeMs = (endTime - startTime) / 1_000_000.0;

        List<EdgeDef> mstDefs = mst.stream().map(Edge::toEdgeDef).collect(Collectors.toList());
        return new AlgorithmStats(mstDefs, totalCost, counter.count, execTimeMs);
    }

    public static AlgorithmStats primAlgorithm(Set<String> nodes, List<EdgeDef> edgeDefs) {
        OpCounter counter = new OpCounter();
        long startTime = System.nanoTime();

        List<Edge> mst = new ArrayList<>();
        int totalCost = 0;

        // Build adjacency list
        Map<String, List<Edge>> adj = new HashMap<>();
        for (String node : nodes) {
            adj.put(node, new ArrayList<>());
        }
        for (EdgeDef ed : edgeDefs) {
            // Give counter to edges for PriorityQueue comparisons
            Edge edge = new Edge(ed.from, ed.to, ed.weight, counter);
            adj.get(ed.from).add(edge);
            adj.get(ed.to).add(edge);
        }

        PriorityQueue<Edge> pq = new PriorityQueue<>();
        Set<String> visited = new HashSet<>();

        if (nodes.isEmpty()) {
            return new AlgorithmStats(Collections.emptyList(), 0, 0, 0.0);
        }

        String startNode = nodes.iterator().next();
        visited.add(startNode);
        counter.increment(); // visited.add()

        for (Edge edge : adj.getOrDefault(startNode, Collections.emptyList())) {
            pq.add(edge);
            counter.increment(); // pq.add()
        }

        while (!pq.isEmpty() && mst.size() < nodes.size() - 1) {
            Edge minEdge = pq.poll();
            counter.increment(); // pq.poll()

            String unvisitedNode = null;
            counter.increment(); // visited.contains()
            if (visited.contains(minEdge.from) && !visited.contains(minEdge.to)) {
                counter.increment(); // visited.contains()
                unvisitedNode = minEdge.to;
            } else if (visited.contains(minEdge.to) && !visited.contains(minEdge.from)) {
                counter.increment(); // visited.contains()
                unvisitedNode = minEdge.from;
            }

            if (unvisitedNode == null) {
                continue; // Both visited or both unvisited (shouldn't happen)
            }

            visited.add(unvisitedNode);
            counter.increment(); // visited.add()
            mst.add(minEdge);
            totalCost += minEdge.weight;

            for (Edge neighborEdge : adj.getOrDefault(unvisitedNode, Collections.emptyList())) {
                String adjacentNode = neighborEdge.to.equals(unvisitedNode) ? neighborEdge.from : neighborEdge.to;

                counter.increment(); // visited.contains()
                if (!visited.contains(adjacentNode)) {
                    pq.add(neighborEdge);
                    counter.increment(); // pq.add()
                }
            }
        }

        long endTime = System.nanoTime();
        double execTimeMs = (endTime - startTime) / 1_000_000.0;

        List<EdgeDef> mstDefs = mst.stream().map(Edge::toEdgeDef).collect(Collectors.toList());
        return new AlgorithmStats(mstDefs, totalCost, counter.count, execTimeMs);
    }
}