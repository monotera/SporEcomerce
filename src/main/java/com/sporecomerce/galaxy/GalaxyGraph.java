package com.sporecomerce.galaxy;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.sporecomerce.star.Star;

public class GalaxyGraph {

    private long galaxy_id;
    private ArrayList<Star> galaxyContent;

    private int vertices;
    private int edges;

    // Aumentar el numero si se desea mas caminos usando la ecuacion
    // MAX_EDGES * ((MAX_EDGES-1)/2)
    // entre mas grande MAX_EDGES mas facil el juego
    private final int MAX_EDGES = 20;
    private final int MAX_VERTICES = 20;

    private Random random = new Random();

    private List<List<Integer>> adjacencyList;

    public List<List<Integer>> getAdjacencyList() {
        return adjacencyList;
    }

    public int getVertices() {
        return vertices;
    }

    public void setVertices(int vertices) {
        this.vertices = vertices;
    }

    public int getEdges() {
        return edges;
    }

    public void setEdges(int edges) {
        this.edges = edges;
    }

    public int getMAX_EDGES() {
        return MAX_EDGES;
    }

    public int getMAX_VERTICES() {
        return MAX_VERTICES;
    }

    public Random getRandom() {
        return random;
    }

    public void setRandom(Random random) {
        this.random = random;
    }

    public void setAdjacencyList(List<List<Integer>> adjacencyList) {
        this.adjacencyList = adjacencyList;
    }

    public int getVerticesSize() {
        return adjacencyList.size();
    }

    public int getEdgeSize(int pos) {
        return adjacencyList.get(pos).size();
    }

    public GalaxyGraph() {
        this.vertices = MAX_VERTICES;
        this.edges = random.nextInt(computeMaxEdges(MAX_EDGES)) + 1;
        adjacencyList = new ArrayList<>(vertices);
        for (int i = 0; i < vertices; i++)
            adjacencyList.add(new ArrayList<>());
    }

    public GalaxyGraph(long galaxy_id, ArrayList<Star> galaxyContent, int vertices, int edges, Random random, List<List<Integer>> adjacencyList) {
        this.galaxy_id = galaxy_id;
        this.galaxyContent = galaxyContent;
        this.vertices = vertices;
        this.edges = edges;
        this.random = random;
        this.adjacencyList = adjacencyList;
    }

    int computeMaxEdges(int numOfVertices) {
        return numOfVertices * ((numOfVertices - 1) / 2);
    }


    public long getGalaxy_id() {
        return this.galaxy_id;
    }

    public void setGalaxy_id(long galaxy_id) {
        this.galaxy_id = galaxy_id;
    }

    public ArrayList<Star> getGalaxyContent() {
        return this.galaxyContent;
    }

    public void setGalaxyContent(ArrayList<Star> galaxyContent) {
        this.galaxyContent = galaxyContent;
    }

    @Override
    public String toString() {
        return "{" +
            " galaxy_id='" + getGalaxy_id() + "'" +
            ", galaxyContent='" + getGalaxyContent() + "'" +
            ", vertices='" + getVertices() + "'" +
            ", edges='" + getEdges() + "'" +
            ", MAX_EDGES='" + getMAX_EDGES() + "'" +
            ", MAX_VERTICES='" + getMAX_VERTICES() + "'" +
            ", random='" + getRandom() + "'" +
            ", adjacencyList='" + getAdjacencyList() + "'" +
            "}";
    }

}