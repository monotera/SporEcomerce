package com.sporecomerce.api.demo.player;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/player")
public class PlayerController {

    @Autowired
    PlayerRepository playerRepository;

    @GetMapping("/players")
    public ResponseEntity<ArrayList<Player>> getPLayers() {
        ArrayList<Player> response = new ArrayList<>();
        Iterable<Player> db = playerRepository.findAll();
        db.forEach(response::add);
        return new ResponseEntity<>(response, null, HttpStatus.OK);
    }
}
