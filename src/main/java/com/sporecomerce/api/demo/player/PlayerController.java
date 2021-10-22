package com.sporecomerce.api.demo.player;

import java.util.ArrayList;

import com.sporecomerce.api.demo.crewmembers.Crewmembers;
import com.sporecomerce.api.demo.crewmembers.CrewmembersRepository;
import com.sporecomerce.api.demo.star.Star;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/player")
public class PlayerController {

    @Autowired
    PlayerRepository playerRepository;

    @Autowired
    CrewmembersRepository crewmembersRepository;

    @GetMapping("/players")
    @CrossOrigin(origins = "http://localhost:4200")

    public ResponseEntity<ArrayList<Player>> getPLayers() {
        ArrayList<Player> response = new ArrayList<>();
        Iterable<Player> db = playerRepository.findAll();
        db.forEach(response::add);
        return new ResponseEntity<>(response, null, HttpStatus.OK);
    }

    @GetMapping("")
    @CrossOrigin(origins = "http://localhost:4200")

    public ResponseEntity<Player> getPlayer(@RequestParam Long player_id) {
        try {
            Player player = playerRepository.findById(player_id).get();
            if (player == null)
                return new ResponseEntity<>(null, null, HttpStatus.NOT_FOUND);
            return new ResponseEntity<>(player, null, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/theplayer")
    @CrossOrigin(origins = "http://localhost:4200")

    public ResponseEntity<Player> getPlayer() {
        Iterable<Player> players = playerRepository.findAll();
        ArrayList<Player> listPlayers = new ArrayList<>();
        players.forEach(listPlayers::add);
        return new ResponseEntity<>(listPlayers.get(0), null, HttpStatus.OK);
    }

    @PutMapping("")
    @CrossOrigin(origins = "http://localhost:4200")

    public ResponseEntity<Player> modPlayer(@RequestParam Long player_id, @RequestBody Player newPlayer) {
        try {
            Player player = playerRepository.findById(player_id).get();
            if (player == null)
                return new ResponseEntity<>(null, null, HttpStatus.NOT_FOUND);
            newPlayer.setId(player.getId());
            newPlayer.setCrewmembers(player.getCrewmembers());
            if (newPlayer.getPlayer_name() == null || newPlayer.getPlayer_name() == "")
                newPlayer.setPlayer_name(player.getPlayer_name());
            if (newPlayer.getPlayer_role() == null)
                newPlayer.setPlayer_role(player.getPlayer_role());
            playerRepository.save(newPlayer);
            return new ResponseEntity<>(newPlayer, null, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/mod_name")
    @CrossOrigin(origins = "http://localhost:4200")

    public ResponseEntity<Boolean> modPlayerName(@RequestParam Long player_id, @RequestParam String newName) {
        try {
            Player player = playerRepository.findById(player_id).get();
            if (player == null)
                return new ResponseEntity<>(false, null, HttpStatus.NOT_FOUND);

            Player newPlayer = new Player();
            newPlayer.setId(player.getId());
            newPlayer.setCrewmembers(player.getCrewmembers());
            newPlayer.setPlayer_name(newName);
            newPlayer.setPlayer_role(player.getPlayer_role());
            playerRepository.save(newPlayer);
            return new ResponseEntity<>(true, null, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(false, null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/mod_role")
    @CrossOrigin(origins = "http://localhost:4200")

    public ResponseEntity<Boolean> modPlayerRole(@RequestParam Long player_id, @RequestParam String newRole) {
        try {
            Player player = playerRepository.findById(player_id).get();
            if (player == null)
                return new ResponseEntity<>(false, null, HttpStatus.NOT_FOUND);
            Player newPlayer = new Player();
            newPlayer.setId(player.getId());
            newPlayer.setCrewmembers(player.getCrewmembers());
            newPlayer.setPlayer_name(player.getPlayer_name());
            newPlayer.setPlayer_role(Role.valueOf(newRole));
            playerRepository.save(newPlayer);
            return new ResponseEntity<>(true, null, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(false, null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/change_crew")
    @CrossOrigin(origins = "http://localhost:4200")

    public ResponseEntity<Player> changeCrew(@RequestParam Long player_id, @RequestParam Long crew_id) {
        try {
            Player player = playerRepository.findById(player_id).get();
            Crewmembers crewmembers = crewmembersRepository.findById(crew_id).get();
            if (player == null || crewmembers == null)
                return new ResponseEntity<>(null, null, HttpStatus.NOT_FOUND);
            if (player.getCrewmembers() != null) {
                player.getCrewmembers().getPlayer_list().remove(player);
            }
            crewmembers.addPlayer(player);
            playerRepository.save(player);
            return new ResponseEntity<>(player, null, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("")
    @CrossOrigin(origins = "http://localhost:4200")

    public ResponseEntity<Player> createPlayer(@RequestBody Player player) {
        try {
            if (player.getPlayer_name() == null || player.getPlayer_name() == "" || player.getPlayer_role() == null)
                return new ResponseEntity<>(null, null, HttpStatus.INTERNAL_SERVER_ERROR);
            playerRepository.save(player);
            return new ResponseEntity<>(player, null, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("")
    @CrossOrigin(origins = "http://localhost:4200")

    public ResponseEntity<Player> deletePlayer(@RequestParam Long player_id) {
        try {
            Player player = playerRepository.findById(player_id).get();
            if (player == null)
                return new ResponseEntity<>(null, null, HttpStatus.NOT_FOUND);
            playerRepository.delete(player);
            return new ResponseEntity<>(player, null, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
