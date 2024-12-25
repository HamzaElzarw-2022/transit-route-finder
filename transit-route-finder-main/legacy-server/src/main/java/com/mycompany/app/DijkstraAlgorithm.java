package com.mycompany.app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class DijkstraAlgorithm {

    //input variables
    private Graph graph; //graph to be used by the algorithm
    private String source; //start vertex

    //output variables
    private Map<String, Integer> distances; //list of shortest distances from source to each vertex
    private Map<String, String> previous; //list of the previous vertices in the shortest path to each vertex
    private Map<String, String> linkingLines; //line that connects between previous and current node.
    private boolean[] visited; //list to check if vertex was visited before.
    private ArrayList<String> vertices;

    //main constructor
    public DijkstraAlgorithm(Graph graph, String source) {

        this.graph = graph;
        this.source = source;

        //initializing lists according to number of vertices in the given graph
        distances = new HashMap<String, Integer>();
        previous = new HashMap<String, String>();
        visited = new boolean[graph.getNumVertices()];
        linkingLines = new HashMap<String, String>();
        vertices = graph.getVertices();

        for (int i = 0; i < vertices.size(); i++)
        {
            distances.put(vertices.get(i), Integer.MAX_VALUE);
            previous.put(vertices.get(i), "none");
            linkingLines.put(vertices.get(i), "none");
        }

        distances.put(source, 0);
    }

    public ArrayList<String> getVertices()
    {
        return vertices;
    }

    //return shortest paths from source to all vertices in graph
    public Map<String, Integer> getShortestDistances() {

        //create a queue and insert source vertex in it as Node object
        PriorityQueue<Node> queue = new PriorityQueue<>();
        queue.offer(new Node(source, 0, "none"));

        while (!queue.isEmpty())
        {
            //mark the node with the next turn to variable node
            Node node = queue.poll();
            String u = node.vertex;

            //if u is visited skip iteration if not mark it as visited
            try {
                if (visited[graph.getIndexOfVertex(u)]) {
                    continue;
                }
                visited[graph.getIndexOfVertex(u)] = true;
            }
            catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("\nyour source does not exist !!!!!!!!!!");
                System.exit(0);
            }

            //iterate through all vertices connected with u and check if there could be shorter path to them through u.

            for (Edge edge : graph.getEdges(u))
            {
                String v = edge.getVertex();
                int weight = edge.getTime();
                String linkingLine = edge.getLine();
                int extra = 0;

                //if passenger will need to change line at this stop add 5 minutes to the weight of this path
                //which guarantees that the passenger will stay at the same line if the duration difference is not worth it
                if(!node.linkingLine.equals("none") && !edge.getLine().equals(node.linkingLine) ) {
                    extra += 5;
                }

                //check if v is visited and if new distance is less than current distance
                if (!visited[graph.getIndexOfVertex(v)] && distances.get(u) + weight + extra < distances.get(v))
                {
                    distances.put(v, (distances.get(u) + weight + extra));  //updates distance to v
                    previous.put(v, u);
                    linkingLines.put(v, linkingLine);
                    queue.offer(new Node(v, distances.get(v), linkingLine));
                }
            }
        }
        return distances;
    }

    public Map<String, String> getPreviousVertices()
    {
        return previous;
    }
    public Map<String, String> getLinkingLines()
    {
        return linkingLines;
    }

    //can we remove node object ?
    private static class Node  implements Comparable<Node>{
        private String vertex;
        private int distance;
        private String linkingLine;

        private Node(String vertex, int distance, String linkingLine) {
            this.vertex = vertex;
            this.distance = distance;
            this.linkingLine = linkingLine;
        }

        @Override
        public int compareTo(Node other) {
            return Integer.compare(distance, other.distance);
        }
    }
}
