package org.example;

class Edge implements Comparable<Edge> {
    String from;
    String to;
    int weight;
    OpCounter opCounter; // Reference to a counter

    Edge(String from, String to, int weight, OpCounter counter) {
        this.from = from;
        this.to = to;
        this.weight = weight;
        this.opCounter = counter;
    }

    // This is a key operation for sorting (Kruskal) and PriorityQueue (Prim)
    @Override
    public int compareTo(Edge other) {
        if (opCounter != null) {
            opCounter.increment(); // Count each comparison
        }
        return Integer.compare(this.weight, other.weight);
    }

    // Helper to convert to POJO
    public EdgeDef toEdgeDef() {
        return new EdgeDef(from, to, weight);
    }

    @Override
    public String toString() {
        return "{" + from + "--" + to + ", w:" + weight + "}";
    }
}