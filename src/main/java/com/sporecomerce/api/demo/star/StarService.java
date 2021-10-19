package com.sporecomerce.api.demo.star;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StarService {
    @Autowired
    StarRepository starRepository;
    private ArrayList<Star> stars = new ArrayList<>();
    private HashMap<Integer, Double> nearStars = new HashMap<>();

    public StarService(ArrayList<Star> stars) {
        Iterable<Star> starsTemp = starRepository.findAll();
        starsTemp.forEach(this.stars::add);
    }

    // ------------------------------------------------------------------------
    // TODO: Change
    public void findNearStars(Star star) {

        if (!star.getNearStars().isEmpty())
            return;

    }

    public void printMap(HashMap<Integer, Double> map) {
        map.entrySet().forEach(entry -> {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        });

    }

    // TODO:change
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

    public StarService() {
    }

}