package com.smartcity.shortestpath;

import com.smartcity.model.Edge;
import com.smartcity.model.Graph;

import java.util.*;

public class Dijkstra {
    public static class Result {
        public int[] dist;
        public int[] parent;
        public long operationCount = 0;

        public Result(int v) {
            dist = new int[v];
            parent = new int[v];
            Arrays.fill(dist, Integer.MAX_VALUE);
            Arrays.fill(parent, -1);
        }
    }

    public static Result run(Graph graph, int src) {
        int v = graph.getVertices();
        Result result = new Result(v);
        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a[1]));

        result.dist[src] = 0;
        pq.add(new int[]{src, 0});

        while (!pq.isEmpty()) {
            int[] curr = pq.poll();
            int u = curr[0];
            int d = curr[1];
            result.operationCount++;

            if (d > result.dist[u]) continue;

            for (Edge edge : graph.getAdj().get(u)) {
                result.operationCount++;
                if (edge.weight < 0) {
                    throw new IllegalArgumentException("Dijkstra doesn't support negative weights");
                }
                if (result.dist[u] != Integer.MAX_VALUE && result.dist[u] + edge.weight < result.dist[edge.dest]) {
                    result.dist[edge.dest] = result.dist[u] + edge.weight;
                    result.parent[edge.dest] = u;
                    pq.add(new int[]{edge.dest, result.dist[edge.dest]});
                }
            }
        }

        return result;
    }
}