package com.mycompany.app;

import java.util.ArrayList;
import java.util.Map;
import java.util.Stack;
import java.util.concurrent.TimeUnit;

public class routeFinder {

    private Stack<step> steps;
    private int totalTime = 0;

    private String destination;
    private String source;

    private String startPlace;
    private String endPlace;
    private Map<String, String[]> places; //key for the map is a place and value is [nearestVertex, timeFromVertexToPlace]

    private Map<String, Integer> duration;
    private Map<String, String> previous;
    private Map<String, String> linkingLines;
    private ArrayList<String> vertices;

    public routeFinder(Graph myGraph, Map<String, String[]> places, String source, String destination) {


        if(places.get(source) != null) {
            this.source = places.get(source)[0];
            startPlace = source;
            this.places = places;
            totalTime += Integer.parseInt(places.get(startPlace)[1]);
        }
        else {
            this.source = source;
            startPlace = "none";
        }

        if(places.get(destination) != null) {
            this.destination = places.get(destination)[0];
            endPlace = destination;
            this.places = places;
            totalTime += Integer.parseInt(places.get(endPlace)[1]);
        }
        else {
            this.destination = destination;
            endPlace = "none";
        }

        DijkstraAlgorithm algo = new DijkstraAlgorithm(myGraph, this.source);

        this.duration = algo.getShortestDistances();
        this.previous = algo.getPreviousVertices();
        this.linkingLines = algo.getLinkingLines();
        this.vertices = algo.getVertices();

        this.steps = new Stack<step>();

    }

    public void printStartPlace()
    {
        if(!startPlace.equals("none"))
            System.out.println(
                    " ===>\n" +
                    " | walk from " + startPlace + " to " + places.get(startPlace)[0]  + " station" +
                    "\n | time it will take: " + places.get(startPlace)[1] + " minutes" +
                    "\n ===>\n"
            );
    }

    public void printEndPlace()
    {
        if(!endPlace.equals("none"))
            System.out.println(
                    " ===>\n" +
                    " | walk from " + places.get(endPlace)[0] + " station to " + endPlace +
                    "\n | time it will take: " + places.get(endPlace)[1] + " minutes" +
                    "\n ===>"
            );
    }

    public void printTotalTime() {

        if (totalTime < 60) {
                System.out.println(" | total journey time: " + totalTime + " minutes");
        }
        else {
            long hours = TimeUnit.MINUTES.toHours(totalTime);  // Convert minutes to hours
            long remainingMinutes = totalTime % 60;
            System.out.println(" | total journey time: " + hours + " hour and " + remainingMinutes + " minutes");
        }
    }

    public Stack<step> createSteps() {

        String edgeIndex = this.destination;

        try {
            linkingLines.get(edgeIndex).equals("none");

        }
        catch (NullPointerException e) {
            System.out.println("\nyour destination does not exist !!!!!!!!!!");
            System.exit(0);
        }

        while(!linkingLines.get(edgeIndex).equals("none")) {

            step bus = new step(linkingLines.get(edgeIndex), edgeIndex); //first add line and destination


            //iterate through edges until the line changes
            while(linkingLines.get(edgeIndex).equals(linkingLines.get(previous.get(edgeIndex))))
            {
                edgeIndex = previous.get(edgeIndex);
                bus.addStop(edgeIndex);
            }

            edgeIndex = previous.get(edgeIndex);
            bus.setStartVertex(edgeIndex);  //enter the start vertex of the bus

            //enter the time spent in this bus
            if(duration.get(bus.getStartVertex()) != null) {
                bus.setTime( duration.get(bus.getEndVertex()) - duration.get(bus.getStartVertex()) );
                totalTime += duration.get(bus.getEndVertex()) - duration.get(bus.getStartVertex());
            }

            steps.push(bus);

        }

        return steps;
    }

    //methods made for GUI

    public int getTotalTime() {
        return totalTime;
    }
    public String[] getStartPlace() {

        String[] data = new String[] {"none","none","none"};

        if(startPlace.equals("none"))
            return data;

        data[0] = startPlace;
        data[1] = places.get(startPlace)[0]; //nearestVertexToStart
        data[2] = places.get(startPlace)[1]; //timeToIt
        return data;
    }
    public String[] getEndPlace() {

        String[] data = new String[] {"none","none","none"};

        if(endPlace.equals("none"))
            return data;

        data[0] = endPlace;
        data[1] = places.get(endPlace)[0]; //nearestVertexToEnd
        data[2] = places.get(endPlace)[1]; //timeToIt
        return data;
    }

}
