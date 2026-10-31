package com.smartcity.connectivity;

import com.smartcity.model.Edge;
import com.smartcity.model.Graph;

import java.util.*;

public class KosarajuSCC {
    public static class Result {
        public List<List<Integer>> sccList = new ArrayList<>();
        public long operationCount = 0;
    }

    public static Result run(Graph graph) {
        Result result = new Result();
        int v = graph.getVertices();
        boolean[] visited = new boolean[v];
        Stack<Integer> stack = new Stack<>();

        // 1. Pass 1: Fill order
        for (int i = 0; i < v; i++) {
            if (!visited[i]) {
                dfs1(graph, i, visited, stack, result);
            }
        }

        // 2. Transpose Graph
        Graph transpose = new Graph(v, true);
        for (int i = 0; i < v; i++) {
            for (Edge e : graph.getAdj().get(i)) {
                transpose.addEdge(e.dest, e.src, e.weight);
                result.operationCount++;
            }
        }

        // 3. Pass 2: DFS on Transposed Graph
        Arrays.fill(visited, false);
        while (!stack.isEmpty()) {
            int node = stack.pop();
            result.operationCount++;
            if (!visited[node]) {
                List<Integer> component = new ArrayList<>();
                dfs2(transpose, node, visited, component, result);
                result.sccList.add(component);
            }
        }

        return result;
    }

    private static void dfs1(Graph g, int u, boolean[] visited, Stack<Integer> stack, Result res) {
        visited[u] = true;
        res.operationCount++;
        for (Edge e : g.getAdj().get(u)) {
            res.operationCount++;
            if (!visited[e.dest]) {
                dfs1(g, e.dest, visited, stack, res);
            }
        }
        stack.push(u);
    }

    private static void dfs2(Graph g, int u, boolean[] visited, List<Integer> component, Result res) {
        visited[u] = true;
        component.add(u);
        res.operationCount++;
        for (Edge e : g.getAdj().get(u)) {
            res.operationCount++;
            if (!visited[e.dest]) {
                dfs2(g, e.dest, visited, component, res);
            }
        }
    }
}