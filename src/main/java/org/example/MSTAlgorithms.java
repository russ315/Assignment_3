package org.example;

import java.util.*;

public class MSTAlgorithms {

    /**
     * Finds the Minimum Spanning Tree (MST) using Kruskal's algorithm.
     *
     * @param nodes The set of all vertex names.
     * @param edges The list of all edges in the graph.
     * @return A Map containing the MST edge list ("mst") and total cost ("cost").
     */
    public static Map<String, Object> kruskalAlgorithm(Set<String> nodes, List<Edge> edges) {
        List<Edge> mst = new ArrayList<>();
        int totalCost = 0;

        // 1. Sort all edges in non-decreasing order of their weight.
        Collections.sort(edges);

        // 2. Initialize a DisjointSet structure for all vertices.
        DisjointSet ds = new DisjointSet(nodes);

        // 3. Iterate through all sorted edges.
        for (Edge edge : edges) {
            String rootFrom = ds.find(edge.from);
            String rootTo = ds.find(edge.to);

            // 4. If including this edge does not form a cycle, include it.
            if (!rootFrom.equals(rootTo)) {
                mst.add(edge);
                totalCost += edge.weight;
                ds.union(edge.from, edge.to); // 5. Union the two sets.
            }
        }

        Map<String, Object> result = new HashMap<>();
        result.put("mst", mst);
        result.put("cost", totalCost);
        return result;
    }
    /**
     * Finds the Minimum Spanning Tree (MST) using Prim's algorithm.
     *
     * @param nodes The set of all vertex names.
     * @param adj An adjacency list representation of the graph.
     * @return A Map containing the MST edge list ("mst") and total cost ("cost").
     */
    public static Map<String, Object> primAlgorithm(Set<String> nodes, Map<String, List<Edge>> adj) {
        List<Edge> mst = new ArrayList<>();
        int totalCost = 0;

        // 1. Initialize a PriorityQueue to store edges, ordered by min weight.
        PriorityQueue<Edge> pq = new PriorityQueue<>();

        // 2. Keep track of vertices already included in the MST.
        Set<String> visited = new HashSet<>();

        // 3. Start from an arbitrary vertex (e.g., the first in the set).
        if (nodes.isEmpty()) {
            return Collections.emptyMap(); // Handle empty graph
        }
        String startNode = nodes.iterator().next();
        visited.add(startNode);

        // 4. Add all edges from the starting node to the priority queue.
        for (Edge edge : adj.getOrDefault(startNode, Collections.emptyList())) {
            pq.add(edge);
        }

        // 5. Loop until the MST is complete (V-1 edges) or PQ is empty.
        while (!pq.isEmpty() && mst.size() < nodes.size() - 1) {
            // 6. Get the smallest-weight edge from the PQ.
            Edge minEdge = pq.poll();

            // 7. Find the node that is not yet in the visited set.
            String unvisitedNode = null;
            if (visited.contains(minEdge.from) && !visited.contains(minEdge.to)) {
                unvisitedNode = minEdge.to;
            } else if (visited.contains(minEdge.to) && !visited.contains(minEdge.from)) {
                unvisitedNode = minEdge.from;
            }

            // 8. If both are visited, this edge forms a cycle, so skip it.
            if (unvisitedNode == null) {
                continue;
            }

            // 9. Add the edge to the MST and the new node to visited.
            visited.add(unvisitedNode);
            mst.add(minEdge);
            totalCost += minEdge.weight;

            // 10. Add all new adjacent edges (to unvisited nodes) to the PQ.
            for (Edge neighborEdge : adj.getOrDefault(unvisitedNode, Collections.emptyList())) {
                String adjacentNode = neighborEdge.to.equals(unvisitedNode) ? neighborEdge.from : neighborEdge.to;
                if (!visited.contains(adjacentNode)) {
                    pq.add(neighborEdge);
                }
            }
        }

        Map<String, Object> result = new HashMap<>();
        result.put("mst", mst);
        result.put("cost", totalCost);
        return result;
    }
}