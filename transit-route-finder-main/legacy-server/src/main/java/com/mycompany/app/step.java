package com.mycompany.app;

import java.util.Stack;

public class step {

    private String startVertex;
    private Stack<String> stops;  //we used stack here in order to reverse the stops when we are calling it
    private String endVertex;
    private String line;
    private int time;

    public step(String line, String endVertex) {
        this.line= line;
        this.endVertex = endVertex;
        this.stops = new Stack<String>();
    }

    public String getEndVertex()
    {
        return endVertex;
    }
    public void addStop(String vertex)
    {
        stops.push(vertex);
    }
    public String getStartVertex() {
        return startVertex;
    }
    public void setStartVertex(String startVertex) {
        this.startVertex = startVertex;
    }
    public void setTime(int time) {
        this.time = time;
    }

    public void printStep()
    {
        System.out.println(
                " ===> Start Stop: { " + startVertex + " }" +
                        "\n | line name:" + line +
                        "\n | Time in the bus: " + time + " minutes" +
                        "\n | Stops: "
        );
        while(!stops.empty())
        {
            System.out.println(" |    - " + stops.pop());
        }
        System.out.println(
                " ===> end Stop: { " +endVertex + " }\n"
        );
    }

    public String popStop() {
        return stops.pop();
    }

    public Stack<String> getStops() {
        return stops;
    }

    public String getLine() {
        return line;
    }

    public int getTime() {
        return time;
    }
}
