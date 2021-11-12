package com.sporecomerce.api.demo.galaxy;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Stack;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.lang.reflect.Type;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

public class GalaxyGraphService {

    private GalaxyGraph galaxy = new GalaxyGraph();
    private Random random = new Random();

    public void generateGalaxy() {
        generateGraph();
        while (!isConnected()) {
            generateGraph();
        }
    }

    public void uploadGalaxy() {
        this.galaxy.fillGraph(getGraph());
    }

    public GalaxyGraph getGalaxy() {
        return galaxy;
    }

    public List<Integer> getConnections(Integer starIndex) {
        return galaxy.getAdjacencyList().get(starIndex);
    }

    public void generateGraph() {
        for (int i = 0; i < galaxy.getEdges(); i++) {

            int v = random.nextInt(galaxy.getVertices());
            int w = random.nextInt(galaxy.getVertices());
            if ((v == w) || galaxy.getAdjacencyList().get(v).contains(w)) {
                i = i - 1;
                continue;
            }
            addEdge(v, w);
        }
    }

    public void addEdge(int v, int w) {
        galaxy.getAdjacencyList().get(v).add(w);
        if (v != w)
            galaxy.getAdjacencyList().get(w).add(v);
    }

    public Boolean isConnected() {
        ArrayList<Boolean> v = DFS(0);
        if (v.contains(Boolean.FALSE)) {
            System.out.println("No es conexo");
            return false;
        }

        System.out.println("Es conexo");
        return true;

    }

    public ArrayList<Boolean> DFS(int s) {

        ArrayList<Boolean> visited = new ArrayList<>(Arrays.asList(new Boolean[galaxy.getVertices()]));
        Collections.fill(visited, Boolean.FALSE);

        Stack<Integer> stack = new Stack<>();

        stack.push(s);

        while (stack.empty() == false) {
            s = stack.peek();
            stack.pop();
            if (visited.get(s) == false) {
                visited.set(s, true);
            }
            Iterator<Integer> itr = galaxy.getAdjacencyList().get(s).iterator();
            while (itr.hasNext()) {
                int v = itr.next();
                if (!visited.get(v))
                    stack.push(v);
            }

        }
        return visited;
    }

    public void clearGraph() {
        for (int i = 0; i < galaxy.getVertices(); i++)
            galaxy.getAdjacencyList().get(i).clear();
        galaxy.getAdjacencyList().clear();
        galaxy.setAdjacencyList(new ArrayList<>(galaxy.getVertices()));
        for (int i = 0; i < galaxy.getVertices(); i++)
            galaxy.getAdjacencyList().add(new ArrayList<>());
    }

    public boolean BFS(int src, int dest, ArrayList<Integer> pred, ArrayList<Integer> dist) {

        LinkedList<Integer> queue = new LinkedList<Integer>();

        ArrayList<Boolean> visited = new ArrayList<>(Arrays.asList(new Boolean[galaxy.getVertices()]));
        Collections.fill(visited, Boolean.FALSE);

        visited.set(src, Boolean.TRUE);
        dist.set(src, 0);
        queue.add(src);

        while (!queue.isEmpty()) {
            int u = queue.remove();
            for (int i = 0; i < galaxy.getAdjacencyList().get(u).size(); i++) {
                if (visited.get(galaxy.getAdjacencyList().get(u).get(i)) == false) {
                    visited.set(galaxy.getAdjacencyList().get(u).get(i), Boolean.TRUE);
                    dist.set(galaxy.getAdjacencyList().get(u).get(i), dist.get(u) + 1);
                    pred.set(galaxy.getAdjacencyList().get(u).get(i), u);
                    queue.add(galaxy.getAdjacencyList().get(u).get(i));

                    if (galaxy.getAdjacencyList().get(u).get(i) == dest)
                        return true;
                }
            }

        }
        return false;
    }

    // function to print the shortest distance and path
    // between source vertex and destination vertex
    public void printShortestDistance(int s, int dest) {
        ArrayList<Integer> pred = new ArrayList<>(Collections.nCopies(galaxy.getVertices(), -1));
        ArrayList<Integer> dist = new ArrayList<>(Collections.nCopies(galaxy.getVertices(), Integer.MAX_VALUE));

        if (BFS(s, dest, pred, dist) == false) {
            System.out.println("No hay camino");
            return;
        }

        LinkedList<Integer> path = new LinkedList<Integer>();
        int crawl = dest;
        path.add(crawl);
        while (pred.get(crawl) != -1) {
            path.add(pred.get(crawl));
            crawl = pred.get(crawl);
        }
        for (int i = path.size() - 1; i >= 0; i--) {
            System.out.print(path.get(i) + " ");
        }
        System.out.println("---------------");
    }

    public void printGraph() {
        Gson prettyGson = new GsonBuilder().setPrettyPrinting().create();
        String prettyJson = prettyGson.toJson(galaxy.getAdjacencyList());
        try {
            FileWriter fr = new FileWriter("graph.json");
            fr.write(prettyJson);
            fr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<List<Integer>> getGraph() {
        Type graphListType = new TypeToken<List<List<Integer>>>() {
        }.getType();

        JsonReader graphData;
        Gson gson = new Gson();
        try {
            graphData = new JsonReader(new FileReader("graph.json"));
            List<List<Integer>> graph = gson.fromJson(graphData, graphListType);
            return graph;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

}
