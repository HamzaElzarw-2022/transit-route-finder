package com.mycompany.app;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CSVReader {

    public static Map placeReader(String path) {

        String line= "";
        Map<String, String[]> output = new HashMap<String, String[]>();

        try {
            BufferedReader br= new BufferedReader(new FileReader(path));

            String head= br.readLine();

            while((line = br.readLine()) != null)
            {
                String[] values= line.split(",");
                output.put(values[0], new String[]{values[1], values[2]});
            }


        } catch (FileNotFoundException e) {
            System.out.println("map was not found");
            //throw new RuntimeException(e);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    return output;
    }

    public static Graph mapReader(String path) {

        int numberOfVertices = 0;
        String line= "";
        ArrayList<String[]> lines = new ArrayList<String[]>();
        ArrayList<String> vertices = new ArrayList<String>();

        try {
            BufferedReader br= new BufferedReader(new FileReader(path));

            String head= br.readLine();

            while((line = br.readLine()) != null) {
                String[] values= line.split(",");
                lines.add(values);

                if(!vertices.contains(values[0]))
                    vertices.add(values[0]);
                else if(!vertices.contains(values[1]))
                    vertices.add(values[1]);
            }
            numberOfVertices = vertices.size();
            System.out.println("numberofvertices: " + numberOfVertices);

        } catch (FileNotFoundException e) {
            System.out.println("map was not found");
            //throw new RuntimeException(e);
            System.exit(0);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        Graph output = new Graph(numberOfVertices, vertices);

        System.out.println("numberOfLines: " + lines.size());

        for (int i=0; i<lines.size(); i++) {

            String[] values = lines.get(i);

            output.addEdge(
                    values[0],                    //src
                    values[1],                    //dest
                    Integer.parseInt(values[2]),  //time
                    values[3]                     //line
            );
        }
        return output;
    }
}

