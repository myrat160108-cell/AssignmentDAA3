package com.smartcity;

import com.smartcity.benchmark.BenchmarkRunner;
import com.smartcity.connectivity.KosarajuSCC;
import com.smartcity.connectivity.TopologicalSort;
import com.smartcity.generator.GraphGenerator;
import com.smartcity.model.Graph;
import com.smartcity.mst.KruskalMST;
import com.smartcity.mst.PrimMST;
import com.smartcity.shortestpath.Dijkstra;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Smart City Infrastructure & Route Planning ===");

        runDemo();

        System.out.println("\n------------------------------------------------");

        BenchmarkRunner.runBenchmarks();
    }

    private static void runDemo() {
        System.out.println("\n--- Quick Algorithm Demonstration ---");

        Graph undirGraph = GraphGenerator.generateConnectedUndirectedGraph(10, 30, 42);

        // MST: Kruskal
        KruskalMST.Result kruskalRes = KruskalMST.run(undirGraph);
        System.out.println("Kruskal MST Total Weight: " + kruskalRes.totalWeight);

        // MST: Prim
        PrimMST.Result primRes = PrimMST.run(undirGraph);
        System.out.println("Prim MST Total Weight:    " + primRes.totalWeight);

        // Shortest Path: Dijkstra
        Dijkstra.Result dijkstraRes = Dijkstra.run(undirGraph, 0);
        System.out.println("Dijkstra Shortest Distance from 0 to 5: " + dijkstraRes.dist[5]);

        // Connectivity: DAG & Topological Sort
        Graph dag = GraphGenerator.generateDAG(10, 20, 42);
        TopologicalSort.Result topoRes = TopologicalSort.run(dag);
        System.out.println("DAG Topological Order: " + topoRes.order);

        // Connectivity: Strongly Connected Components (Kosaraju)
        Graph dirGraph = new Graph(5, true);
        dirGraph.addEdge(1, 0, 1);
        dirGraph.addEdge(0, 2, 1);
        dirGraph.addEdge(2, 1, 1);
        dirGraph.addEdge(0, 3, 1);
        dirGraph.addEdge(3, 4, 1);

        KosarajuSCC.Result sccRes = KosarajuSCC.run(dirGraph);
        System.out.println("Strongly Connected Components Count: " + sccRes.sccList.size());
    }
}