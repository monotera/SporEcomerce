package com.sporecomerce.api.demo.spaceship;

import java.util.ArrayList;

import com.sporecomerce.api.demo.crewmembers.Crewmembers;
import com.sporecomerce.api.demo.star.Star;
import com.sporecomerce.api.demo.star.StarRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/spaceship")
public class SpaceshipController {

    @Autowired
    SpaceshipRepository spaceshipRepository;

    @Autowired
    StarRepository starRepository;
    Logger logger = LoggerFactory.getLogger(SpaceshipController.class);

    @GetMapping("/spaceships")
    public ResponseEntity<ArrayList<Spaceship>> getSpaceShips() {
        ArrayList<Spaceship> response = new ArrayList<>();
        Iterable<Spaceship> db = spaceshipRepository.findAll();
        db.forEach(response::add);
        return new ResponseEntity<>(response, null, HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<Spaceship> getSpaceship(@RequestParam Long spaceship_id) {
        try {
            Spaceship spaceship = spaceshipRepository.findById(spaceship_id).get();
            if (spaceship.equals(null))
                return new ResponseEntity<>(null, null, HttpStatus.NOT_FOUND);
            return new ResponseEntity<>(spaceship, null, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("")
    public ResponseEntity<Spaceship> modSpaceship(@RequestParam Long spaceship_id, @RequestBody Spaceship spaceship) {
        try {
            Spaceship oldSpaceship = spaceshipRepository.findById(spaceship_id).get();
            if (spaceship.equals(null))
                return new ResponseEntity<>(null, null, HttpStatus.NOT_FOUND);

            spaceship.setId(oldSpaceship.getId());
            spaceship.setStar(oldSpaceship.getStar());
            if (spaceship.getShip_name() == null)
                spaceship.setShip_name(oldSpaceship.getShip_name());

            if (spaceship.getShip_load() == 0.0)
                spaceship.setShip_load(oldSpaceship.getShip_load());
            if (spaceship.getVelocity() == 0.0)
                spaceship.setVelocity(oldSpaceship.getShip_load());
            spaceshipRepository.save(spaceship);
            return new ResponseEntity<>(spaceship, null, HttpStatus.OK);
        } catch (Exception e) {

            return new ResponseEntity<>(null, null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("")
    public ResponseEntity<Spaceship> createSpaceship(@RequestBody Spaceship spaceship) {
        try {
            if (spaceship.getShip_name() == null || spaceship.getShip_load() == 0.0 || spaceship.getVelocity() == 0.0)
                return new ResponseEntity<>(null, null, HttpStatus.INTERNAL_SERVER_ERROR);

            spaceshipRepository.save(spaceship);

            return new ResponseEntity<>(spaceship, null, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/move_ship")
    public ResponseEntity<Spaceship> moveSpaceship(@RequestParam Long spaceship_id, @RequestParam Long star_id) {
        try {
            Star star = starRepository.findById(star_id).get();
            Spaceship spaceship = spaceshipRepository.findById(spaceship_id).get();
            if (spaceship.equals(null) || star.equals(null))
                return new ResponseEntity<>(null, null, HttpStatus.NOT_FOUND);

            spaceship.changeStar(star, spaceship.getStar());
            spaceshipRepository.save(spaceship);
            return new ResponseEntity<>(spaceship, null, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("")
    public ResponseEntity<Spaceship> deleteSpaceship(@RequestParam Long spaceship_id) {
        try {
            Spaceship spaceship = spaceshipRepository.findById(spaceship_id).get();
            if (spaceship.equals(null))
                return new ResponseEntity<>(null, null, HttpStatus.NOT_FOUND);
            spaceship.getCrew().setSpace_crew(null);
            spaceship.setCrew(null);
            spaceshipRepository.delete(spaceship);
            return new ResponseEntity<>(spaceship, null, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Ee", e);
            return new ResponseEntity<>(null, null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

/**
 * public ResponseEntity<Spaceship> getSpaceship(@RequestParam Long
 * spaceship_id) { try { return new ResponseEntity<>(spaceship, null,
 * HttpStatus.OK); } catch (Exception e) { return new ResponseEntity<>(null,
 * null, HttpStatus.INTERNAL_SERVER_ERROR); } }
 */