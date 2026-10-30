package com.smartcity.mst;

import com.smartcity.dsu.DSU;
import com.smartcity.model.Edge;
import com.smartcity.model.Graph;

import java.util.*;

public class KruskalMST {
    public static class Result {
        public int totalWeight;
        public List<Edge> mstEdges = new ArrayList<>();
        public boolean isConnected;
        public long operationCount = 0;
    }

    public static Result run(Graph graph) {
        Result result = new Result();
        DSU dsu = new DSU(graph.getVertices());

        List<Edge> sortedEdges = new ArrayList<>(graph.getEdges());
        Collections.sort(sortedEdges);
        result.operationCount += (long) sortedEdges.size() * Math.log(sortedEdges.size());

        for (Edge edge : sortedEdges) {
            result.operationCount++;
            if (dsu.union(edge.src, edge.dest)) {
                result.mstEdges.add(edge);
                result.totalWeight += edge.weight;
            }
        }

        result.operationCount += dsu.dsuOpsCount;
        result.isConnected = (result.mstEdges.size() == graph.getVertices() - 1) || (graph.getVertices() == 1);
        return result;
    }
}