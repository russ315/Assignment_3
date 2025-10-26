package org.example;

class Edge implements Comparable<Edge> {
    String from;
    String to;
    int weight;

    Edge(String from, String to, int weight) {
        this.from = from;
        this.to = to;
        this.weight = weight;
    }

    @Override
    public int compareTo(Edge other) {
        // Compares edges based on their weight
        return Integer.compare(this.weight, other.weight);
    }

    @Override
    public String toString() {
        return "{" + from + " -- " + to + ", w:" + weight + "}";
    }
}