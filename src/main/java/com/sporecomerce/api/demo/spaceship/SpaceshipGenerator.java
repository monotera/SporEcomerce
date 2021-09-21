package com.sporecomerce.api.demo.spaceship;

import java.util.Random;

public class SpaceshipGenerator {
    //Initial values
    public Boolean initial(Spaceship ship, SpaceshipRole role, int i){
        Random random = new Random();
        Integer load = 60;
        Integer velocity = 1600;
        Integer bound = 100;
        Boolean sentry = false;

        ship.setShip_load(random.nextInt(bound)+load);
        ship.setVelocity(random.nextInt(bound)*velocity);
        ship.setShip_name(role);
        ship.setId(Long.parseLong("190809"+String.valueOf(i)));
        
        sentry = true;
        return sentry;
    }
}
