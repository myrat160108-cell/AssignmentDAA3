package com.smartcity.test;

import com.smartcity.connectivity.KosarajuSCC;
import com.smartcity.connectivity.TopologicalSort;
import com.smartcity.generator.GraphGenerator;
import com.smartcity.model.Graph;
import com.smartcity.mst.KruskalMST;
import com.smartcity.mst.PrimMST;
import com.smartcity.shortestpath.Dijkstra;

import java.util.HashSet;
import java.util.Set;

public class CorrectnessTest {

    public static void main(String[] args) {
        System.out.println("=== RUNNING CORRECTNESS TESTS ===\n");

        testMSTEquivalence();
        testTopologicalSortDAG();
        testTopologicalSortCycle();
        testSCCCoverage();
        testDijkstraShortestPath();
        testSingleVertexGraph();

        System.out.println("\nALL TESTS PASSED SUCCESSFULLY!");
    }

    private static void testMSTEquivalence() {
        Graph graph = GraphGenerator.generateConnectedUndirectedGraph(10, 25, 123);
        KruskalMST.Result kruskal = KruskalMST.run(graph);
        PrimMST.Result prim = PrimMST.run(graph);

        assert kruskal.totalWeight == prim.totalWeight : "MST weights do not match!";
        assert kruskal.isConnected && prim.isConnected : "Graph should be connected!";
        System.out.println("[PASS] MST Equivalence (Kruskal Weight == Prim Weight)");
    }

    private static void testTopologicalSortDAG() {
        Graph dag = GraphGenerator.generateDAG(8, 15, 456);
        TopologicalSort.Result result = TopologicalSort.run(dag);

        assert !result.hasCycle : "DAG should not contain cycles!";
        assert result.order.size() == 8 : "Topological order size must equal vertex count!";
        System.out.println("[PASS] Topological Sort for DAG");
    }

    private static void testTopologicalSortCycle() {
        Graph cyclicGraph = new Graph(3, true);
        cyclicGraph.addEdge(0, 1, 1);
        cyclicGraph.addEdge(1, 2, 1);
        cyclicGraph.addEdge(2, 0, 1); // Образуем цикл

        TopologicalSort.Result result = TopologicalSort.run(cyclicGraph);

        assert result.hasCycle : "Cycle was not detected!";
        assert result.order.isEmpty() : "Order should be empty when a cycle exists!";
        System.out.println("[PASS] Cycle Detection in Topological Sort");
    }

    private static void testSCCCoverage() {
        Graph graph = new Graph(5, true);
        graph.addEdge(1, 0, 1);
        graph.addEdge(0, 2, 1);
        graph.addEdge(2, 1, 1);
        graph.addEdge(0, 3, 1);
        graph.addEdge(3, 4, 1);

        KosarajuSCC.Result sccResult = KosarajuSCC.run(graph);
        Set<Integer> visitedVertices = new HashSet<>();
        int totalVerticesInSCCs = 0;

        for (var component : sccResult.sccList) {
            totalVerticesInSCCs += component.size();
            for (int v : component) {
                visitedVertices.add(v);
            }
        }

        assert totalVerticesInSCCs == 5 : "Total vertices across SCCs must match graph size!";
        assert visitedVertices.size() == 5 : "Vertices must belong to exactly one SCC!";
        System.out.println("[PASS] Kosaraju SCC Vertices Coverage");
    }

    private static void testDijkstraShortestPath() {
        Graph graph = new Graph(4, true);
        graph.addEdge(0, 1, 4);
        graph.addEdge(1, 2, 2);
        graph.addEdge(0, 2, 10);

        Dijkstra.Result res = Dijkstra.run(graph, 0);

        assert res.dist[0] == 0 : "Distance to source must be 0";
        assert res.dist[1] == 4 : "Shortest path to 1 should be 4";
        assert res.dist[2] == 6 : "Shortest path to 2 should be 6 (0->1->2)";
        assert res.dist[3] == Integer.MAX_VALUE : "Unreachable vertex distance must be Integer.MAX_VALUE";
        System.out.println("[PASS] Dijkstra Shortest Path & Unreachable Vertices Handling");
    }

    private static void testSingleVertexGraph() {
        Graph singleNodeGraph = new Graph(1, false);

        KruskalMST.Result kruskal = KruskalMST.run(singleNodeGraph);
        PrimMST.Result prim = PrimMST.run(singleNodeGraph);
        Dijkstra.Result dijkstra = Dijkstra.run(singleNodeGraph, 0);

        assert kruskal.totalWeight == 0 && prim.totalWeight == 0 : "Single node MST weight must be 0";
        assert dijkstra.dist[0] == 0 : "Distance to self must be 0";
        System.out.println("[PASS] Single Vertex Edge Case Handling");
    }
}