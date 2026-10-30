package com.smartcity.connectivity;

import com.smartcity.model.Edge;
import com.smartcity.model.Graph;

import java.util.*;

public class TopologicalSort {
    public static class Result {
        public List<Integer> order = new ArrayList<>();
        public boolean hasCycle = false;
        public long operationCount = 0;
    }

    public static Result run(Graph graph) {
        Result result = new Result();
        int v = graph.getVertices();
        int[] inDegree = new int[v];

        for (int i = 0; i < v; i++) {
            for (Edge e : graph.getAdj().get(i)) {
                inDegree[e.dest]++;
                result.operationCount++;
            }
        }

        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < v; i++) {
            if (inDegree[i] == 0) {
                queue.add(i);
            }
        }

        while (!queue.isEmpty()) {
            int current = queue.poll();
            result.order.add(current);
            result.operationCount++;

            for (Edge e : graph.getAdj().get(current)) {
                result.operationCount++;
                inDegree[e.dest]--;
                if (inDegree[e.dest] == 0) {
                    queue.add(e.dest);
                }
            }
        }

        if (result.order.size() != v) {
            result.hasCycle = true;
            result.order.clear();
        }

        return result;
    }
}