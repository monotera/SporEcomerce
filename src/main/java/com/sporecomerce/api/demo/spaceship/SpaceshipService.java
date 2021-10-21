package com.sporecomerce.api.demo.spaceship;

import java.util.ArrayList;

import com.sporecomerce.api.demo.star.Star;

import org.springframework.stereotype.Service;

@Service
public class SpaceshipService {

    public Star findShipStar(Spaceship ship, ArrayList<Star> stars) {
        for (Star star : stars) {
            for (Spaceship spaceship : star.getSpaceLobby()) {
                if (ship.getId() == spaceship.getId())
                    return star;
            }
        }
        return null;
    }

    public Boolean isMyStar(Star star, Spaceship ship) {
        for (Spaceship spaceship : star.getSpaceLobby()) {
            if (spaceship.getId() == ship.getId())
                return true;
        }
        return false;
    }

}
