package com.sporecomerce.star;

import java.util.ArrayList;
import java.util.Random;

public class StarMain {

    public static void main(String[] args) {
        ArrayList<Star> stars = new ArrayList<>();
        StarController starController;
        Random random = new Random();
        for (int i = 0; i < 40000; i++) {
            stars.add(new Star(random.nextInt(300), random.nextInt(300), random.nextInt(300), "Estrella " + i,
                    Boolean.TRUE));
        }
        starController = new StarController(stars);

        starController.findNearStars(stars.get(0));

    }
}
