package com.transitroute.springserver;

import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;
import java.util.Stack;

@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api")
@RestController
public class MainController {

    private ResourceLoader resourceLoader;

    Resource mapResource;
    Resource placesResource;

    final Graph myGraph;
    final Map<String, String[]> places;

    public MainController(ResourceLoader resourceLoader) throws IOException {
        this.resourceLoader = resourceLoader;

        mapResource = resourceLoader.getResource("classpath:maps/londonMap.csv");
        placesResource = resourceLoader.getResource("classpath:maps/londonPlaces.csv");

        myGraph = CSVReader.mapReader(mapResource.getFile().getPath());
        places = CSVReader.placeReader(placesResource.getFile().getPath());
    }

    @PostMapping
    public RouteResponse route(@RequestBody RouteRequest routeRequest) {

        RouteResponse routeResponse = new RouteResponse();

        if(!myGraph.hasVertex(routeRequest.getStart()) && !places.containsKey(routeRequest.getStart())) {
            routeResponse.setStatus("noStart");
            return routeResponse;
        }
        if(!myGraph.hasVertex(routeRequest.getEnd()) && !places.containsKey(routeRequest.getEnd())) {
            routeResponse.setStatus("noEnd");
            return routeResponse;
        }

        routeFinder finder = new routeFinder(myGraph, places, routeRequest.getStart(), routeRequest.getEnd());
        Stack<Step> mySteps = finder.createSteps();
        int time = finder.getTotalTime();
        String[] endPlace = finder.getEndPlace();
        String[] startPlace = finder.getStartPlace();

        routeResponse.setStatus("success");
        routeResponse.setTotalTime(time);
        routeResponse.setStartPlace(startPlace[0]);
        routeResponse.setStartPlaceVertex(startPlace[1]);
        routeResponse.setStartWalkTime(startPlace[2]);
        routeResponse.setEndPlace(endPlace[0]);
        routeResponse.setEndPlaceVertex(endPlace[1]);
        routeResponse.setEndWalkTime(endPlace[2]);
        routeResponse.setSteps(mySteps);

        return routeResponse;
    }
}
