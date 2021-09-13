package com.sporecomerce.api.demo.crewmembers;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/crew")
public class CrewmembersController {

    @Autowired
    CrewmembersRepository crewmembersRepository;

    @GetMapping("/crews")
    public ResponseEntity<ArrayList<Crewmembers>> getCrew() {
        ArrayList<Crewmembers> response = new ArrayList<>();
        Iterable<Crewmembers> crewDB = crewmembersRepository.findAll();
        crewDB.forEach(response::add);
        return new ResponseEntity<>(response, null, HttpStatus.OK);
    }
}
