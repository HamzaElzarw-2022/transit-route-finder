package com.mycompany.app;

import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;
import java.util.Stack;

public class Server
{

    public static void main(String[] args) throws Exception {

        //create Graph and add places
        String path= "maps/londonMap.csv";
        String placesPath= "maps/londonPlaces.csv";
        final Graph myGraph = CSVReader.mapReader(path);
        final Map<String, String[]> places = CSVReader.placeReader(placesPath);
        System.out.println("Graph created");

        // Create an HTTP server instance
        final org.eclipse.jetty.server.Server server = new org.eclipse.jetty.server.Server(8080); // Change the port number if needed
        ServletContextHandler handler = new ServletContextHandler();
        handler.setContextPath("/");
        server.setHandler(handler);
        System.out.println("New Server started on port 8080");

        handler.addServlet(new ServletHolder(new HttpServlet() {

            @Override
            protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
                super.doOptions(req, resp);
                System.out.println("its do options bro");

                // Set CORS headers
                resp.setHeader("Access-Control-Allow-Origin", "*");
                resp.setHeader("Access-Control-Allow-Methods", "*");
                resp.setHeader("Access-Control-Allow-Headers", "*");
                resp.setStatus(HttpServletResponse.SC_OK);
            }

            @Override
            protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

                System.out.println("its do post finally");

                // Set CORS headers
                resp.setHeader("Access-Control-Allow-Origin", "*");
                resp.setHeader("Access-Control-Allow-Methods", "*");
                resp.setHeader("Access-Control-Allow-Headers", "*");

                StringBuilder requestBody = new StringBuilder();
                BufferedReader reader = req.getReader();
                String line;
                while ((line = reader.readLine()) != null) {
                    requestBody.append(line);
                }
                // Process the request body as JSON
                JSONParser jsonParser = new JSONParser();
                JSONObject requestJson;
                try {
                    requestJson = (JSONObject) jsonParser.parse(requestBody.toString());
                } catch (ParseException e) {
                    System.out.println("parser here");
                    throw new RuntimeException(e);
                }

                // Get the values of "startingPoint" and "destination" properties
                String startingPoint = (String) requestJson.get("startingPoint");
                String destination = (String) requestJson.get("destination");

                System.out.println("request received, start is "+startingPoint+" end is "+ destination);

                // Create a JSON response
                String jsonResponse;

                if(!myGraph.hasVertex(startingPoint) && !places.containsKey(startingPoint)){
                    JSONObject noStart = new JSONObject();
                    noStart.put("status", "noStart");
                    jsonResponse = noStart.toJSONString();
                }
                else if(!myGraph.hasVertex(destination) && !places.containsKey(destination)) {
                    JSONObject noEnd = new JSONObject();
                    noEnd.put("status", "noEnd");
                    jsonResponse = noEnd.toJSONString();
                }
                else {
                    jsonResponse = createJsonString(myGraph, places, startingPoint, destination);
                }


                resp.setContentType("application/json");
                resp.getWriter().println(jsonResponse);
            }
        }), "/api");

        server.start();
        server.join();
    }

    public static String createJsonString(Graph myGraph, Map<String, String[]> places, String startingPoint, String destination) {

        routeFinder finder = new routeFinder(myGraph, places, startingPoint, destination);

        Stack<step> mySteps = finder.createSteps();
        int time = finder.getTotalTime();
        String[] endPlace = finder.getEndPlace();
        String[] startPlace = finder.getStartPlace();

        // Create a JSON response
        JSONObject responseJson = new JSONObject();

        responseJson.put("status", "success");

        responseJson.put("totalTime", time);



        responseJson.put("startPlace", startPlace[0]);
        responseJson.put("startPlaceVertex", startPlace[1]);
        responseJson.put("startWalkTime", startPlace[2]);

        responseJson.put("endPlace", endPlace[0]);
        responseJson.put("endPlaceVertex", endPlace[1]);
        responseJson.put("endWalkTime", endPlace[2]);

        JSONArray JSONSteps = new JSONArray();
        while (!mySteps.isEmpty()) {

            step thisStep = mySteps.pop();
            JSONObject JSONStep = new JSONObject();

            JSONStep.put("startVertex", thisStep.getStartVertex());
            JSONStep.put("endVertex", thisStep.getEndVertex());
            JSONStep.put("time", thisStep.getTime());
            JSONStep.put("line", thisStep.getLine());

            JSONArray JSONStops = new JSONArray();
            while (!thisStep.getStops().isEmpty()) {
                JSONObject stop = new JSONObject();
                stop.put("getStop", thisStep.popStop());
                JSONStops.add(stop);
            }

            JSONStep.put("stops", JSONStops);

            JSONSteps.add(JSONStep);

        }
        responseJson.put("steps", JSONSteps);

        return responseJson.toJSONString();
    }
}
