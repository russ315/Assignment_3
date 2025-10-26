package org.example;

import java.util.*;

import static org.example.MSTAlgorithms.kruskalAlgorithm;
import static org.example.MSTAlgorithms.primAlgorithm;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    // This method simulates reading the JSON file, since we can't do file I/O.
    // This data is copied from your 'ass_3_input.json'.
    public static GraphInput getSimulatedInput() {
        GraphInput input = new GraphInput();
        input.graphs = new ArrayList<>();

        // Graph 1
        Graph g1 = new Graph();
        g1.id = 1;
        g1.nodes = Arrays.asList("A", "B", "C", "D", "E");
        g1.edges = new ArrayList<>();
        g1.edges.add(new EdgeDef("A", "B", 4));
        g1.edges.add(new EdgeDef("A", "C", 3));
        g1.edges.add(new EdgeDef("B", "C", 2));
        g1.edges.add(new EdgeDef("B", "D", 5));
        g1.edges.add(new EdgeDef("C", "D", 7));
        g1.edges.add(new EdgeDef("C", "E", 8));
        g1.edges.add(new EdgeDef("D", "E", 6));
        input.graphs.add(g1);

        // Graph 2
        Graph g2 = new Graph();
        g2.id = 2;
        g2.nodes = Arrays.asList("A", "B", "C", "D");
        g2.edges = new ArrayList<>();
        g2.edges.add(new EdgeDef("A", "B", 1));
        g2.edges.add(new EdgeDef("A", "C", 4));
        g2.edges.add(new EdgeDef("B", "C", 2));
        g2.edges.add(new EdgeDef("C", "D", 3));
        g2.edges.add(new EdgeDef("B", "D", 5));
        input.graphs.add(g2);

        return input;
    }

    public static void main(String[] args) {
        GraphInput input = getSimulatedInput();

        Output output = new Output();
        output.results = new ArrayList<>();

        for (Graph g : input.graphs) {
            Set<String> nodes = new HashSet<>(g.nodes);

            //  Run algorithms and get stats
            AlgorithmStats primStats = MSTAlgorithms.primAlgorithm(nodes, g.edges);
            AlgorithmStats kruskalStats = MSTAlgorithms.kruskalAlgorithm(nodes, g.edges);

            // Create result object
            Result result = new Result(
                    g.id,
                    g.nodes.size(),
                    g.edges.size(),
                    primStats,
                    kruskalStats
            );
            output.results.add(result);
        }


        System.out.println(generateJsonManually(output));
    }

    private static String generateJsonManually(Output output) {
        StringBuilder sb = new StringBuilder();
        sb.append("{\n  \"results\": [\n");
        for (int i = 0; i < output.results.size(); i++) {
            Result r = output.results.get(i);
            sb.append("    {\n");
            sb.append("      \"graph_id\": ").append(r.graph_id).append(",\n");
            sb.append("      \"input_stats\": {\n");
            sb.append("        \"vertices\": ").append(r.input_stats.vertices).append(",\n");
            sb.append("        \"edges\": ").append(r.input_stats.edges).append("\n");
            sb.append("      },\n");
            sb.append("      \"prim\": ").append(formatStats(r.prim)).append(",\n");
            sb.append("      \"kruskal\": ").append(formatStats(r.kruskal)).append("\n");
            sb.append("    }");
            if (i < output.results.size() - 1) sb.append(",");
            sb.append("\n");
        }
        sb.append("  ]\n}");
        return sb.toString();
    }

    private static String formatStats(AlgorithmStats stats) {
        StringBuilder sb = new StringBuilder();
        sb.append("{\n");
        sb.append("        \"mst_edges\": [\n");
        for (int i = 0; i < stats.mst_edges.size(); i++) {
            EdgeDef e = stats.mst_edges.get(i);
            sb.append("          {\"from\": \"").append(e.from).append("\", \"to\": \"")
                    .append(e.to).append("\", \"weight\": ").append(e.weight).append("}");
            if (i < stats.mst_edges.size() - 1) sb.append(",");
            sb.append("\n");
        }
        sb.append("        ],\n");
        sb.append("        \"total_cost\": ").append(stats.total_cost).append(",\n");
        sb.append("        \"operations_count\": ").append(stats.operations_count).append(",\n");
        sb.append("        \"execution_time_ms\": ").append(stats.execution_time_ms).append("\n");
        sb.append("      }");
        return sb.toString();
    }
}