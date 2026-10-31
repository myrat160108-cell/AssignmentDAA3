package com.smartcity.benchmark;

import com.smartcity.generator.GraphGenerator;
import com.smartcity.connectivity.*;
import com.smartcity.mst.*;
import com.smartcity.model.Graph;
import com.smartcity.shortestpath.Dijkstra;

public class BenchmarkRunner {
    public static void runBenchmarks() {
        int[][] configs = {
                {10, 30},     // Small
                {100, 500},   // Medium
                {1000, 5000}  // Large
        };

        System.out.println("=== BENCHMARK RESULTS ===");
        for (int[] cfg : configs) {
            int V = cfg[0];
            int E = cfg[1];
            System.out.println("\n--- Graph V=" + V + ", E=" + E + " ---");

            Graph undirGraph = GraphGenerator.generateConnectedUndirectedGraph(V, E, 42);

            long timeK = 0, opsK = 0;
            for (int i = 0; i < 5; i++) {
                long start = System.nanoTime();
                KruskalMST.Result r = KruskalMST.run(undirGraph);
                timeK += (System.nanoTime() - start);
                opsK = r.operationCount;
            }

            long timeP = 0, opsP = 0;
            for (int i = 0; i < 5; i++) {
                long start = System.nanoTime();
                PrimMST.Result r = PrimMST.run(undirGraph);
                timeP += (System.nanoTime() - start);
                opsP = r.operationCount;
            }

            System.out.printf("Kruskal: Avg Time = %.2f us, Ops = %d\n", (timeK / 5.0) / 1000.0, opsK);
            System.out.printf("Prim:    Avg Time = %.2f us, Ops = %d\n", (timeP / 5.0) / 1000.0, opsP);

            long timeD = 0, opsD = 0;
            for (int i = 0; i < 5; i++) {
                long start = System.nanoTime();
                Dijkstra.Result r = Dijkstra.run(undirGraph, 0);
                timeD += (System.nanoTime() - start);
                opsD = r.operationCount;
            }
            System.out.printf("Dijkstra: Avg Time = %.2f us, Ops = %d\n", (timeD / 5.0) / 1000.0, opsD);
        }
    }
}