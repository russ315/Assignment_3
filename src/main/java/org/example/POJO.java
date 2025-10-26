package org.example;

import java.util.List;
import java.util.Map;

// --- Input POJOs (for ass_3_input.json) ---

class GraphInput {
    List<Graph> graphs;
}

class Graph {
    int id;
    List<String> nodes;
    List<EdgeDef> edges;
}

class EdgeDef {
    String from;
    String to;
    int weight;
    public EdgeDef(String from, String to, int weight) {
        this.from = from;
        this.to = to;
        this.weight = weight;
    }
}

// --- Output POJOs (for ass_3_output.json) ---

class Output {
    List<Result> results;
}

class Result {
    int graph_id;
    InputStats input_stats;
    AlgorithmStats prim;
    AlgorithmStats kruskal;

    Result(int id, int vertices, int edges, AlgorithmStats prim, AlgorithmStats kruskal) {
        this.graph_id = id;
        this.input_stats = new InputStats(vertices, edges);
        this.prim = prim;
        this.kruskal = kruskal;
    }
}

class InputStats {
    int vertices;
    int edges;
    InputStats(int v, int e) { this.vertices = v; this.edges = e; }
}

class AlgorithmStats {
    List<EdgeDef> mst_edges;
    int total_cost;
    long operations_count;
    double execution_time_ms;

    AlgorithmStats(List<EdgeDef> mst, int cost, long ops, double time) {
        this.mst_edges = mst;
        this.total_cost = cost;
        this.operations_count = ops;
        this.execution_time_ms = time;
    }
}