package com.mycompany.app;

import java.util.Map;
import java.util.Scanner;
import java.util.Stack;

public class Console {

    public static void main(String[] args) {

        String path= "maps/londonMap.csv";
        String placesPath= "maps/londonPlaces.csv";

        Scanner sc = new Scanner(System.in);
        Graph myGraph = CSVReader.mapReader(path);
        Map<String, String[]> places = CSVReader.placeReader(placesPath);

        System.out.println("\n----------------------------------------------->\n");

        System.out.println("NOTE: you can see londonMap picture in project folder.\n");
        System.out.println("choose starting point: ");
        String source = sc.nextLine();
        System.out.println("choose destination: ");
        String destination = sc.nextLine();

        while(true)
        {
            routeFinder finder = new routeFinder(myGraph, places, source, destination);
            Stack<step> mySteps = finder.createSteps();

            finder.printTotalTime();
            finder.printStartPlace();
            while(!mySteps.empty()) {
                mySteps.pop().printStep();
            }
            finder.printEndPlace();

            System.out.println("choose starting point (type 'exit' to close program): ");
            source = sc.nextLine();
            if(source.equals("exit"))
                break;
            System.out.println("choose destination: ");
            destination = sc.nextLine();

        }
    }
}