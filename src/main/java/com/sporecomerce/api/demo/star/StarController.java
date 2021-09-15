package com.sporecomerce.api.demo.star;

import java.util.ArrayList;

import com.sporecomerce.api.demo.planet.Planet;
import com.sporecomerce.api.demo.planet.PlanetRepository;
import com.sporecomerce.api.demo.spaceship.Spaceship;
import com.sporecomerce.api.demo.spaceship.SpaceshipRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import antlr.collections.List;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Controller
@RequestMapping(value = "/star")
public class StarController {
    @Autowired
    StarRepository starRepository;

    @Autowired
    PlanetRepository planetRepository;

    @Autowired
    SpaceshipRepository spaceshipRepository;

    Logger logger = LoggerFactory.getLogger(StarController.class);

    @GetMapping("/view")
    public String getMainPage(Model model) {
        Iterable<Star> stars = starRepository.findAll();
        Iterable<Planet> planets = planetRepository.findAll();
        Iterable<Spaceship> spaceships = spaceshipRepository.findAll();
        model.addAttribute("stars", stars);
        model.addAttribute("planets", planets);
        model.addAttribute("spaceships", spaceships);
        return "star";
    }

    // http://localhost:8080/star/stars
    @GetMapping(value = "/stars")
    public ResponseEntity<ArrayList<Star>> getStars() {
        ArrayList<Star> response = new ArrayList<>();
        Iterable<Star> starsI = starRepository.findAll();
        starsI.forEach(response::add);
        return new ResponseEntity<>(response, null, HttpStatus.OK);
    }

    // http://localhost:8080/star?id=...
    @GetMapping("")
    public ResponseEntity<Star> getStar(@RequestParam Long id) {
        try {
            Star star = starRepository.findById(id).get();
            if (star == null)
                return new ResponseEntity<>(null, null, HttpStatus.NOT_FOUND);
            return new ResponseEntity<>(star, null, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // localhost:8080/star
    /*
     * { "x": 12, "y": 12, "z": 15, "name": "new Starx", "isInHabited": false }
     */
    @PostMapping("")
    public ResponseEntity<Star> createStar(@RequestBody Star star) {
        try {
            // TODO:Check if posistions are valid
            if (star.getName() == "" || star.getIsInHabited() == null)
                throw new Exception("Invalid data");
            Star newStar = starRepository.save(star);
            return new ResponseEntity<>(newStar, null, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, null, HttpStatus.NOT_ACCEPTABLE);
        }

    }

    // http://localhost:8080/star/planet?star_id=37&planet_id=15
    @PutMapping("/planet")
    public ResponseEntity<Star> addPlanet(@RequestParam Long star_id, @RequestParam Long planet_id) {
        try {
            Planet planet = planetRepository.findById(planet_id).get();
            Star star = starRepository.findById(star_id).get();
            if (star.addPlanet(planet))
                starRepository.save(star);
            return new ResponseEntity<>(star, null, HttpStatus.OK);
        } catch (Exception e) {
            logger.info(e.toString());
            return new ResponseEntity<>(null, null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // http://localhost:8080/star/remove_planet?star_id=..&planet_id=..
    @PutMapping("/remove_planet")
    public ResponseEntity<Star> removePlanet(@RequestParam Long star_id, @RequestParam Long planet_id) {
        try {
            Star star = starRepository.findById(star_id).get();
            Planet planet = planetRepository.findById(planet_id).get();
            if (star.removePlanet(planet))
                starRepository.save(star);
            return new ResponseEntity<>(star, null, HttpStatus.OK);
        } catch (Exception e) {
            logger.info(e.toString());
            return new ResponseEntity<>(null, null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // http://localhost:8080/star/spaceship?star_id=37&spaceship_id=2
    @PutMapping("/spaceship")
    public ResponseEntity<Star> addSpaceShip(@RequestParam Long star_id, @RequestParam Long spaceship_id) {
        try {
            Spaceship spaceship = spaceshipRepository.findById(spaceship_id).get();
            Star star = starRepository.findById(star_id).get();
            star.addSpaceShip(spaceship);
            starRepository.save(star);
            return new ResponseEntity<>(star, null, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // http://localhost:8080/star/remove_spaceship?star_id=..&spaceship_id=..
    @PutMapping("/remove_spaceship")
    public ResponseEntity<Star> removeSpaceShip(@RequestParam Long star_id, @RequestParam Long spaceship_id) {
        try {
            Spaceship spaceship = spaceshipRepository.findById(spaceship_id).get();
            Star star = starRepository.findById(star_id).get();

            star.getSpaceLobby().remove(spaceship);
            spaceship.setStar(null);
            starRepository.save(star);
            return new ResponseEntity<>(star, null, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // localhost:8080/star?star_id=1
    @PutMapping("")
    public ResponseEntity<Star> updateStar(@RequestParam Long star_id, @RequestBody Star star) {
        try {
            Star oldStar = starRepository.findById(star_id).get();
            star.setId(star_id);
            star.setPlanetList(oldStar.getPlanetList());
            star.setSpaceLobby(oldStar.getSpaceLobby());
            if (star.getName() == "")
                star.setName(oldStar.getName());
            if (star.getIsInHabited() == null)
                star.setIsInHabited(oldStar.getIsInHabited());
            if (star.getX() == -1)
                star.setX(oldStar.getX());
            if (star.getY() == -1)
                star.setY(oldStar.getY());
            if (star.getZ() == -1)
                star.setZ(oldStar.getZ());
            // TODO: check positions if its valid
            starRepository.save(star);
            return new ResponseEntity<>(star, null, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // http://localhost:8080/star?star_id=5
    @DeleteMapping("")
    public ResponseEntity<Star> deleteStar(@RequestParam Long star_id) {
        try {
            Star star = starRepository.findById(star_id).get();
            for (Planet p : star.getPlanetList()) {
                p.setStar(null);
            }
            for (Spaceship s : star.getSpaceLobby()) {
                s.setStar(null);
            }
            star.getPlanetList().clear();
            star.getSpaceLobby().clear();
            starRepository.delete(star);
            return new ResponseEntity<>(star, null, HttpStatus.OK);
        } catch (Exception e) {
            logger.info(e.toString());
            return new ResponseEntity<>(null, null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
