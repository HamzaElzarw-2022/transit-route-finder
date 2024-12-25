package com.mycompany.app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Graph {

    private Map<String, List<Edge>> adjacencyList; //hash Map used to retrieve edges of vertex
    private int numVertices; //total number of vertices in graph
    private ArrayList<String> vertices;

    public Graph(int numVertices, ArrayList<String> vertices) {

        this.numVertices = numVertices;
        this.adjacencyList = new HashMap<>();
        this.vertices = vertices;
        for (int i = 0; i < numVertices; i++) {
            adjacencyList.put(vertices.get(i), new ArrayList<Edge>());
        }
    }
    public int getIndexOfVertex(String vertex) {
        return vertices.indexOf(vertex);
    }

    public ArrayList<String> getVertices() {
        return vertices;
    }

    //adds a two-way edge to the graph src->dest, dest->src
    public void addEdge(String src, String dest, int time, String line) {

        adjacencyList.get(src).add(new Edge(dest, time,  line));
        adjacencyList.get(dest).add(new Edge(src, time,  line));

    }

    //return all edges connected with src ID
    public List<Edge> getEdges(String src) {

        List<Edge> edges = new ArrayList<>();

        for (Edge edge : adjacencyList.get(src)) {
            edges.add(edge);
        }

        return edges;
    }

    public int getNumVertices() {
        return numVertices;
    }

    public boolean hasVertex(String vertex) {
        return vertices.contains(vertex);
    }
}

