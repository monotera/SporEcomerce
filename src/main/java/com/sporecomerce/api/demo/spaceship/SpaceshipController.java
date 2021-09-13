package com.sporecomerce.api.demo.spaceship;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/spaceship")
public class SpaceshipController {

    @Autowired
    SpaceshipRepository spaceshipRepository;

    @GetMapping("/spaceships")
    public ResponseEntity<ArrayList<Spaceship>> getSpaceShips() {
        ArrayList<Spaceship> response = new ArrayList<>();
        Iterable<Spaceship> db = spaceshipRepository.findAll();
        db.forEach(response::add);
        return new ResponseEntity<>(response, null, HttpStatus.OK);
    }
}
