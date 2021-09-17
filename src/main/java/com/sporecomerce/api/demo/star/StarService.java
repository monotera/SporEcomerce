package com.sporecomerce.api.demo.star;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public class StarService {
    private ArrayList<Star> stars = new ArrayList<>();
    private HashMap<Integer, Double> nearStars = new HashMap<>();

    public StarService(ArrayList<Star> stars) {
        this.stars = stars;
    }

    // ------------------------------------------------------------------------
    public void findNearStars(Star star) {

        if (!star.getNearStars().isEmpty())
            return;
        double distancia;
        Star starAux;
        for (int i = 0; i < stars.size(); i++) {
            starAux = stars.get(i);
            distancia = distFunction(star.getX(), star.getY(), star.getZ(), starAux.getX(), starAux.getY(),
                    starAux.getZ());
            nearStars.put(i, distancia);
        }
        nearStars = sortByValue(nearStars);
        star.setNearStars(set10Nearest());
        star.setNearStars(sortByValue(star.getNearStars()));
        printMap(star.getNearStars());

    }

    public void printMap(HashMap<Integer, Double> map) {
        map.entrySet().forEach(entry -> {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        });

    }

    public HashMap<Integer, Double> set10Nearest() {
        HashMap<Integer, Double> auxMap = new HashMap<>();
        int i = 0;
        for (Map.Entry<Integer, Double> e : nearStars.entrySet()) {
            if (i != 0) {
                auxMap.put(e.getKey(), e.getValue());
            }
            if (i >= 10)
                break;
            i++;
        }
        return auxMap;
    }

    public double distFunction(int x1, int y1, int z1, int x2, int y2, int z2) {
        int eq1 = (int) Math.pow(x1 - x2, 2);
        int eq2 = (int) Math.pow(y1 - y2, 2);
        int eq3 = (int) Math.pow(z1 - z2, 2);
        return Math.sqrt(eq1 + eq2 + eq3);
    }

    public static HashMap<Integer, Double> sortByValue(HashMap<Integer, Double> hm) {
        List<Map.Entry<Integer, Double>> list = new LinkedList<Map.Entry<Integer, Double>>(hm.entrySet());

        Collections.sort(list, new Comparator<Map.Entry<Integer, Double>>() {
            public int compare(Map.Entry<Integer, Double> o1, Map.Entry<Integer, Double> o2) {
                return (o1.getValue()).compareTo(o2.getValue());
            }
        });

        // put data from sorted list to hashmap
        HashMap<Integer, Double> temp = new LinkedHashMap<Integer, Double>();
        for (Map.Entry<Integer, Double> aa : list) {
            temp.put(aa.getKey(), aa.getValue());
        }
        return temp;
    }

    public StarService() {
    }

}