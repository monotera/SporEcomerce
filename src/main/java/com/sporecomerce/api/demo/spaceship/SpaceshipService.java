package com.sporecomerce.api.demo.spaceship;

import java.util.ArrayList;

import com.sporecomerce.api.demo.star.Star;
import com.sporecomerce.api.demo.star.StarRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SpaceshipService {

    @Autowired
    StarRepository starRepository;

    @Autowired
    SpaceshipRepository spaceshipRepository;
    Logger logger = LoggerFactory.getLogger(SpaceshipService.class);

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

    public Boolean moveShip(Star origin, Star des, Spaceship ship) {
        if (!isMyStar(origin, ship)) {
            logger.info("F");
            return false;
        }
        logger.info("arg");
        try {
            origin.getSpaceLobby().remove(ship);
            logger.info("1");
            ship.setStar(null);
            logger.info("2");

            starRepository.save(origin);
            logger.info("3");

            des.addSpaceShip(ship);
            logger.info("4");

            starRepository.save(des);
            logger.info("5");

            spaceshipRepository.save(ship);
            logger.info("6");

            return true;
        } catch (Exception e) {
            logger.info(e.toString());
            return false;
        }
    }

}
