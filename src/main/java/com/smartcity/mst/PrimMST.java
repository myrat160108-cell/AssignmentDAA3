package com.smartcity.mst;

import com.smartcity.model.Edge;
import com.smartcity.model.Graph;

import java.util.*;

public class PrimMST {
    public static class Result {
        public int totalWeight;
        public boolean isConnected;
        public long operationCount = 0;
    }

    public static Result run(Graph graph) {
        Result result = new Result();
        int v = graph.getVertices();
        if (v == 0) return result;

        boolean[] visited = new boolean[v];
        PriorityQueue<Edge> pq = new PriorityQueue<>();

        visited[0] = true;
        for (Edge e : graph.getAdj().get(0)) {
            pq.add(e);
            result.operationCount++;
        }

        int edgesInMST = 0;
        while (!pq.isEmpty() && edgesInMST < v - 1) {
            Edge edge = pq.poll();
            result.operationCount++;

            if (visited[edge.dest]) continue;

            visited[edge.dest] = true;
            result.totalWeight += edge.weight;
            edgesInMST++;

            for (Edge nextEdge : graph.getAdj().get(edge.dest)) {
                result.operationCount++;
                if (!visited[nextEdge.dest]) {
                    pq.add(nextEdge);
                }
            }
        }

        result.isConnected = (edgesInMST == v - 1) || (v == 1);
        return result;
    }
}