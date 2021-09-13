package com.sporecomerce.api.demo.planet;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/planet")
public class PlanetController {
    @Autowired
    PlanetRepository planetRepository;

    @GetMapping("/planets")
    public ResponseEntity<ArrayList<Planet>> getPlanets() {
        ArrayList<Planet> response = new ArrayList<>();
        Iterable<Planet> planetsDB = planetRepository.findAll();
        planetsDB.forEach(response::add);
        return new ResponseEntity<>(response, null, HttpStatus.OK);
    }
}
