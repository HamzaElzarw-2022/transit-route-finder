package com.transitroute.springserver;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;


public class RouteResponse {

    String status;
    int totalTime;

    String startPlace;
    String startPlaceVertex;
    String startWalkTime;

    String endPlace;
    String endPlaceVertex;
    String endWalkTime;

    List<Step> steps;

    public void setSteps(Stack<Step> steps) {
        this.steps = new ArrayList<>();
        while (!steps.isEmpty()) {
            this.steps.add(steps.pop());
        }
    }

    public List<Step> getSteps() {
        return steps;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(int totalTime) {
        this.totalTime = totalTime;
    }

    public String getStartPlace() {
        return startPlace;
    }

    public void setStartPlace(String startPlace) {
        this.startPlace = startPlace;
    }

    public String getStartPlaceVertex() {
        return startPlaceVertex;
    }

    public void setStartPlaceVertex(String startPlaceVertex) {
        this.startPlaceVertex = startPlaceVertex;
    }

    public String getStartWalkTime() {
        return startWalkTime;
    }

    public void setStartWalkTime(String startWalkTime) {
        this.startWalkTime = startWalkTime;
    }

    public String getEndPlace() {
        return endPlace;
    }

    public void setEndPlace(String endPlace) {
        this.endPlace = endPlace;
    }

    public String getEndPlaceVertex() {
        return endPlaceVertex;
    }

    public void setEndPlaceVertex(String endPlaceVertex) {
        this.endPlaceVertex = endPlaceVertex;
    }

    public String getEndWalkTime() {
        return endWalkTime;
    }

    public void setEndWalkTime(String endWalkTime) {
        this.endWalkTime = endWalkTime;
    }
}
