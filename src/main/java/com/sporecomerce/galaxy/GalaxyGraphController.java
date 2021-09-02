package com.sporecomerce.galaxy;

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

public class GalaxyGraphController {

    private GalaxyGraph galaxy = new GalaxyGraph();
    private Random random = new Random();

    public void generateGalaxy() {
        generateGraph();
        while (!isConnected()) {
            generateGraph();
        }
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

    // TODO:Check if clear graph works
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
        // TODO: Store the values
        for (int i = path.size() - 1; i >= 0; i--) {
            System.out.print(path.get(i) + " ");
        }
        System.out.println("---------------");
    }

    public void printGraph() {
        try {
            FileWriter myWriter = new FileWriter("graph.txt");
            System.out.println("Successfully wrote to the file.");
            for (int i = 0; i < galaxy.getAdjacencyList().size(); i++) {
                String aux = Integer.toString(i) + "-> {";
                myWriter.write(aux);

                List<Integer> list = galaxy.getAdjacencyList().get(i);

                if (list.isEmpty())
                    myWriter.write("No adjacent vertices");

                else {
                    int size = list.size();
                    for (int j = 0; j < size; j++) {
                        aux = Integer.toString(list.get(j));
                        myWriter.write(aux);
                        if (j < size - 1)
                            myWriter.write(" , ");
                    }
                }
                myWriter.write("}\n");
            }
            myWriter.close();
        } catch (IOException e) {
            System.err.println(e);
        }
    }
}
