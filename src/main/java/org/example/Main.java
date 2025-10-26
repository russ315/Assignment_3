package org.example;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set; // Added this import

class Main {

    public static GraphInput readInputFromFile(String filename) throws IOException {
        String jsonContent = new String(Files.readAllBytes(Paths.get(filename)));

        Gson gson = new Gson();

        GraphInput input = gson.fromJson(jsonContent, GraphInput.class);

        return input;
    }
    public static void main(String[] args) {

        GraphInput input = null;
        String inputFilename = "ass_3_input.json";
        String outputFilename = "ass_3_output.json";

        try {
            input = readInputFromFile(inputFilename);
            System.out.println("Successfully read " + inputFilename);
        } catch (IOException e) {
            System.err.println("Error reading input file: " + inputFilename);
            e.printStackTrace();
            return;
        }

        Output output = new Output();
        output.results = new ArrayList<>();

        for (Graph g : input.graphs) {
            Set<String> nodes = new HashSet<>(g.nodes);

            AlgorithmStats primStats = MSTAlgorithms.primAlgorithm(nodes, g.edges);
            AlgorithmStats kruskalStats = MSTAlgorithms.kruskalAlgorithm(nodes, g.edges);

            Result result = new Result(
                    g.id,
                    g.nodes.size(),
                    g.edges.size(),
                    primStats,
                    kruskalStats
            );
            output.results.add(result);
        }

        try {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String jsonOutput = gson.toJson(output);
            Files.write(Paths.get(outputFilename), jsonOutput.getBytes());

            System.out.println("Successfully generated " + outputFilename);

            System.out.println("\n--- " + outputFilename + " Content ---");
            System.out.println(jsonOutput);

        } catch (IOException e) {
            System.err.println("Error writing output file: " + outputFilename);
            e.printStackTrace();
        }
    }
}