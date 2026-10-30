package com.smartcity.model;

import java.util.ArrayList;
import java.util.List;

public class Graph {
    private final int vertices;
    private final boolean isDirected;
    private final List<Edge> edges;
    private final List<List<Edge>> adj;

    public Graph(int vertices, boolean isDirected) {
        this.vertices = vertices;
        this.isDirected = isDirected;
        this.edges = new ArrayList<>();
        this.adj = new ArrayList<>();
        for (int i = 0; i < vertices; i++) {
            adj.add(new ArrayList<>());
        }
    }

    public void addEdge(int u, int v, int weight) {
        Edge edge = new Edge(u, v, weight);
        edges.add(edge);
        adj.get(u).add(edge);
        if (!isDirected) {
            adj.get(v).add(new Edge(v, u, weight));
        }
    }

    public int getVertices() { return vertices; }
    public boolean isDirected() { return isDirected; }
    public List<Edge> getEdges() { return edges; }
    public List<List<Edge>> getAdj() { return adj; }
}