package com.sporecomerce.api.demo.star;

import java.util.ArrayList;
import java.util.Random;

import com.sporecomerce.api.demo.planet.Planet;
import com.sporecomerce.api.demo.planet.PlanetGenerator;
import com.sporecomerce.api.demo.spaceship.Spaceship;

public class StarGenerator {
    //Initial values
    public Boolean initial(Star star, ArrayList<String> productNames, Long star_id, Boolean habited) {
        PlanetGenerator gP = new PlanetGenerator();
        Random random = new Random();
        int numPlanets = 0;
        long id = 0;
        Boolean sentry = false;

        star.setId(star_id);
        star.setIsInHabited(habited);

        if(star.getIsInHabited()){
            numPlanets = random.nextInt(3)+1; //1 - 3 planets per inhabited star
             for (int i = 0; i < numPlanets; i++) {
                 Planet p = new Planet();
                 //idSet = 161201 for PLAnet + star_id + numPlanet
                 id = Long.parseLong("1612010"+String.valueOf(star.getId())+String.valueOf(i));
                 p.setId(id);
                 gP.initial(p, productNames);
                 star.getPlanetList().add(p);
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
