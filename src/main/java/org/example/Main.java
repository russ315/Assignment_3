package org.example;

import java.util.*;

import static org.example.MSTAlgorithms.kruskalAlgorithm;
import static org.example.MSTAlgorithms.primAlgorithm;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Set<String> nodes = new HashSet<>(Arrays.asList("A", "B", "C", "D", "E"));
        List<Edge> edges = new ArrayList<>();
        edges.add(new Edge("A", "B", 4));
        edges.add(new Edge("A", "C", 3));
        edges.add(new Edge("B", "C", 2));
        edges.add(new Edge("B", "D", 5));
        edges.add(new Edge("C", "D", 7));
        edges.add(new Edge("C", "E", 8));
        edges.add(new Edge("D", "E", 6));

        // Create adjacency list for Prim's
        Map<String, List<Edge>> adj = new HashMap<>();
        for (String node : nodes) {
            adj.put(node, new ArrayList<>());
        }
        for (Edge edge : edges) {
            adj.get(edge.from).add(edge);
            adj.get(edge.to).add(edge);
        }

        // --- Run Kruskal's Algorithm ---
        System.out.println("Running Kruskal's Algorithm...");
        Map<String, Object> kruskalResult = kruskalAlgorithm(nodes, edges);
        System.out.println("Total Cost: " + kruskalResult.get("cost"));
        System.out.println("MST Edges: " + kruskalResult.get("mst"));
        System.out.println("---------------------------------");

        // --- Run Prim's Algorithm ---
        System.out.println("Running Prim's Algorithm...");
        Map<String, Object> primResult = primAlgorithm(nodes, adj);
        System.out.println("Total Cost: " + primResult.get("cost"));
        System.out.println("MST Edges: " + primResult.get("mst"));
        System.out.println("---------------------------------");
    }
}