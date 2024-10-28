package com.transitroute.springserver;

public class RouteRequest {
    private String start;
    private String end;

    public RouteRequest() {
    }

    public RouteRequest(String start, String end) {
        this.start = start;
        this.end = end;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }
}
