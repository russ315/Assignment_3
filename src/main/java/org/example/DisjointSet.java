package org.example;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

class OpCounter {
    public long count = 0;
    public void increment() { count++; }
    public void add(long val) { count += val; }
}

class DisjointSet {
    private Map<String, String> parent = new HashMap<>();
    private OpCounter opCounter;

    public DisjointSet(Set<String> nodes, OpCounter counter) {
        this.opCounter = counter;
        for (String node : nodes) {
            parent.put(node, node);
        }
    }

    public String find(String node) {
        opCounter.increment(); // Count each find call
        if (parent.get(node).equals(node)) {
            return node;
        }
        String root = find(parent.get(node));
        parent.put(node, root); // Path compression
        return root;
    }

    public void union(String a, String b) {
        opCounter.increment(); // Count each union call
        String rootA = find(a);
        String rootB = find(b);
        if (!rootA.equals(rootB)) {
            parent.put(rootA, rootB);
        }
    }
}