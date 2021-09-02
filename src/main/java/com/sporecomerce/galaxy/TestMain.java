package com.sporecomerce.galaxy;

public class TestMain {
    public static void main(String[] args) {
        GalaxyGraphController graphController = new GalaxyGraphController();
        System.out.println("The generated random graph :");
        graphController.generateGalaxy();
        graphController.printGraph();
        System.out.println("------caso1---------");
        graphController.printShortestDistance(0, 1);
        System.out.println("------caso2---------");
        graphController.printShortestDistance(10, 4);
        System.out.println("------caso3---------");
        graphController.printShortestDistance(1, 5);
        System.out.println("------caso4---------");
        graphController.printShortestDistance(5, 0);
        System.out.println("------caso5---------");
        graphController.printShortestDistance(1, 0);
        System.out.println("------caso6---------");
        graphController.printShortestDistance(4, 15);

    }
}
