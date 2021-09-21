package com.sporecomerce.api.demo.star;

import java.util.ArrayList;
import java.util.Random;

import com.sporecomerce.api.demo.planet.Planet;
import com.sporecomerce.api.demo.planet.PlanetGenerator;
import com.sporecomerce.api.demo.spaceship.Spaceship;

public class StarGenerator {
    //Initial values
    public Boolean initial(Star star, ArrayList<String> productNames, String star_id, Boolean habited, ArrayList<Star> star_list) {
        PlanetGenerator gP = new PlanetGenerator();
        Random random = new Random();
        int numPlanets = 0;
        Boolean sentry = false;

        star.validateStar(star_list);

        star.setName(star_id);
        star.setIsInHabited(habited);

        if(star.getIsInHabited()){
            numPlanets = random.nextInt(3)+1; //1 - 3 planets per inhabited star
             for (int i = 0; i < numPlanets; i++) {
                 Planet p = new Planet();
                 //idSet = 161201 for PLAnet + star_id + numPlanet
                 String id = "KL-161"+String.valueOf(star.getId())+String.valueOf(i);
                 p.setPlanet_name(id);
                 gP.initial(p, productNames);
                 star.addPlanet(p);
             }
             sentry = true;
        }

        return sentry;
    }

    public Boolean shipEntering(Star star, Spaceship spaceship){
        star.getSpaceLobby().add(spaceship);
        return true;
    }

    public Boolean shipLeaving(Star star, Spaceship spaceship){
        star.getSpaceLobby().remove(spaceship);
        return true;
    }
}
