package com.mycompany.app;

public class Edge {

    private String vertex;  //destination name
    private int time;  //time needed to travel from src to dest
    private String line; //30M, 36L


    public Edge(String vertex, int time, String line) {
        this.vertex = vertex;
        this.time = time;
        this.line = line;
    }

    public String getVertex() {
        return vertex;
    }

    public int getTime() {
        return time;
    }

    public String getLine() {
        return line;
    }

}
