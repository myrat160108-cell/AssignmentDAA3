package com.smartcity.generator;

import com.smartcity.model.Graph;
import java.util.Random;

public class GraphGenerator {
    public static Graph generateConnectedUndirectedGraph(int vertices, int edges, long seed) {
        Random rand = new Random(seed);
        Graph graph = new Graph(vertices, false);

        for (int i = 1; i < vertices; i++) {
            int p = rand.nextInt(i);
            int w = rand.nextInt(100) + 1;
            graph.addEdge(i, p, w);
        }

        int remaining = edges - (vertices - 1);
        while (remaining > 0) {
            int u = rand.nextInt(vertices);
            int v = rand.nextInt(vertices);
            if (u != v) {
                int w = rand.nextInt(100) + 1;
                graph.addEdge(u, v, w);
                remaining--;
            }
        }
        return graph;
    }

    public static Graph generateDAG(int vertices, int edges, long seed) {
        Random rand = new Random(seed);
        Graph graph = new Graph(vertices, true);
        int added = 0;
        while (added < edges) {
            int u = rand.nextInt(vertices);
            int v = rand.nextInt(vertices);
            if (u < v) { // Гарантирует отсутствие циклов
                graph.addEdge(u, v, rand.nextInt(100) + 1);
                added++;
            }
        }
        return graph;
    }
}