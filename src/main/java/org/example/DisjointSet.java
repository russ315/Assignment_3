package org.example;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

class DisjointSet {
    private Map<String, String> parent = new HashMap<>();

    /**
     * Initializes the DSU structure, with each node as its own parent.
     * @param nodes The set of all nodes (vertices) in the graph.
     */
    public DisjointSet(Set<String> nodes) {
        for (String node : nodes) {
            parent.put(node, node);
        }
    }

    /**
     * Finds the representative (root) of the set that 'node' belongs to.
     * Implements path compression for efficiency.
     * @param node The node to find.
     * @return The representative of the set.
     */
    public String find(String node) {
        if (parent.get(node).equals(node)) {
            return node;
        }
        // Path compression: set parent directly to the root
        String root = find(parent.get(node));
        parent.put(node, root);
        return root;
    }

    /**
     * Merges the sets containing node 'a' and node 'b'.
     * @param a A node in the first set.
     * @param b A node in the second set.
     */
    public void union(String a, String b) {
        String rootA = find(a);
        String rootB = find(b);
        if (!rootA.equals(rootB)) {
            // Simple union: make one root the parent of the other
            parent.put(rootA, rootB);
        }
    }
}