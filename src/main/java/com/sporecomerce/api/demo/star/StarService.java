package com.sporecomerce.api.demo.star;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.sporecomerce.api.demo.galaxy.GalaxyGraphService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StarService {
    @Autowired
    StarRepository starRepository;
    private HashMap<Integer, Double> nearStars = new HashMap<>();
    private GalaxyGraphService galaxyGraphService = new GalaxyGraphService();
    public Logger logger = LoggerFactory.getLogger(StarService.class);

    // ------------------------------------------------------------------------
    // TODO: Change
    public String findNearStars(Star star, ArrayList<Star> stars) {
        galaxyGraphService.uploadGalaxy();
        List<List<Integer>> graph = galaxyGraphService.getGraph();
        if (!star.getNearStars().isEmpty())
            return star.getNearStars();

        int index = 0;

        for (Star s : stars) {
            if (s.getId() == star.getId())
                break;
            index++;
        }

        List<Integer> conections = graph.get(index);
        logger.info(String.valueOf(conections.size()));
        String nearStars = "";

        for (int i = 0; i < 10 && i < conections.size(); i++) {
            Long tempStar = stars.get(conections.get(i)).getId();
            nearStars = nearStars + "," + String.valueOf(tempStar);
        }

        return nearStars;
    }

    public ArrayList<Star> transformIdToStar(Star star, ArrayList<Star> stars) {
        try {
            ArrayList<Star> Nearstars = new ArrayList<>();
            String ids = star.getNearStars().substring(1);
            List<String> items = new ArrayList<String>(Arrays.asList(ids.split(",")));

            ArrayList<Long> idsL = new ArrayList<>();
            items.forEach(id -> {
                Long temp = Long.parseLong(id);
                logger.info(String.valueOf(temp));
                idsL.add(temp);
            });
            logger.info("****************");
            for (Long id : idsL) {
                for (Star s : stars) {
                    if (s.getId() == id) {
                        Nearstars.add(s);
                        break;
                    }
                }
            }

            return Nearstars;

        } catch (Exception e) {
            // TODO: handle exception
            logger.info(e.toString());
            return null;
        }

    }

    public void printMap(HashMap<Integer, Double> map) {
        map.entrySet().forEach(entry -> {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        });

    }

    public StarService() {
    }

}