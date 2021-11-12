package com.sporecomerce.api.demo.crewmembers;

import java.util.ArrayList;
import java.util.Iterator;

import com.sporecomerce.api.demo.player.Player;
import com.sporecomerce.api.demo.player.PlayerRepository;
import com.sporecomerce.api.demo.player.Role;
import com.sporecomerce.api.demo.product.Product;
import com.sporecomerce.api.demo.product.ProductRepository;
import com.sporecomerce.api.demo.productxcrew.Productxcrew;
import com.sporecomerce.api.demo.productxcrew.ProductxcrewRepository;
import com.sporecomerce.api.demo.spaceship.Spaceship;
import com.sporecomerce.api.demo.spaceship.SpaceshipRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
@RequestMapping(value = "/crew")
public class CrewmembersController {

    @Autowired
    CrewmembersRepository crewmembersRepository;

    @Autowired
    SpaceshipRepository spaceshipRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ProductxcrewRepository productxcrewRepository;

    @Autowired
    PlayerRepository playerRepository;
    Logger logger = LoggerFactory.getLogger(CrewmembersController.class);

    @GetMapping("/crews")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<ArrayList<Crewmembers>> getCrew() {
        ArrayList<Crewmembers> response = new ArrayList<>();
        Iterable<Crewmembers> crewDB = crewmembersRepository.findAll();
        crewDB.forEach(response::add);
        return new ResponseEntity<>(response, null, HttpStatus.OK);
    }

    @GetMapping("/load-capacity")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<Double> getLoad(@RequestParam Long crew_id) {
        try {
            Crewmembers crew = crewmembersRepository.findById(crew_id).orElseThrow();
            return new ResponseEntity<>(crew.calculateAvaliableCapacity(), null, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // http://localhost:8080/crew?crew_id=9
    @GetMapping("")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<Crewmembers> getCrew(@RequestParam Long crew_id) {
        try {
            Crewmembers crew = crewmembersRepository.findById(crew_id).get();
            if (crew == null)
                return new ResponseEntity<>(null, null, HttpStatus.NOT_FOUND);
            return new ResponseEntity<>(crew, null, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/captain")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<Boolean> isCaptain(@RequestParam Long player_id) {
        Boolean cent = false;
        try {
            Player player = playerRepository.findById(player_id).get();
            Crewmembers crew = player.getCrewmembers();
            if (crew == null)
                return new ResponseEntity<>(false, null, HttpStatus.NOT_FOUND);
            for (Player p : crew.getPlayer_list()) {
                if (p.getPlayer_role().equals(Role.ROLE_CAPTAIN) && p.getId() != player_id) {
                    cent = true;
                }
            }
            return new ResponseEntity<>(cent, null, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(false, null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<Crewmembers> modCrew(@RequestParam Long crew_id, @RequestBody Crewmembers crew) {
        try {
            Crewmembers oldCrew = crewmembersRepository.findById(crew_id).get();
            if (crew == null)
                return new ResponseEntity<>(null, null, HttpStatus.NOT_FOUND);
            crew.setId(oldCrew.getId());
            crew.setSpace_crew(oldCrew.getSpace_crew());
            crew.setProducts(oldCrew.getProducts());
            if (crew.getCrew_name() == null || crew.getCrew_name() == "")
                crew.setCrew_name(oldCrew.getCrew_name());
            if (crew.getAccTime() == null)
                crew.setAccTime(oldCrew.getAccTime());
            if (crew.getCredits() == 0.0)
                crew.setCredits(oldCrew.getCredits());
            crewmembersRepository.save(crew);
            return new ResponseEntity<>(crew, null, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/cap")
    public Player findCaptain(@RequestParam Long crew_id) {
        Crewmembers crew = crewmembersRepository.findById(crew_id).orElseThrow();
        for (Player player : crew.getPlayer_list()) {
            if (player.getPlayer_role() == Role.ROLE_CAPTAIN)
                return player;
        }
        return null;
    }

    @PutMapping("/change_crewName")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<Boolean> modCrewName(@RequestParam Long crew_id, @RequestParam String newName) {
        try {
            Crewmembers oldCrew = crewmembersRepository.findById(crew_id).get();
            Crewmembers crew = new Crewmembers();
            crew.setId(oldCrew.getId());
            crew.setSpace_crew(oldCrew.getSpace_crew());
            crew.setProducts(oldCrew.getProducts());
            crew.setCrew_name(newName);
            if (crew.getAccTime() == null)
                crew.setAccTime(oldCrew.getAccTime());
            if (crew.getCredits() == 0.0)
                crew.setCredits(oldCrew.getCredits());
            crewmembersRepository.save(crew);
            return new ResponseEntity<>(true, null, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(false, null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/change-spaceship")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<Crewmembers> changeSpaceship(@RequestParam Long crew_id, @RequestParam Long spaceship_id) {
        try {
            Crewmembers crew = crewmembersRepository.findById(crew_id).get();
            Spaceship spaceship = spaceshipRepository.findById(spaceship_id).get();
            if (crew == null || spaceship == null)
                return new ResponseEntity<>(null, null, HttpStatus.NOT_FOUND);
            boolean isChanged = crew.changeSpace_crew(spaceship);
            if (!isChanged)
                return new ResponseEntity<>(null, null, HttpStatus.NOT_IMPLEMENTED);
            crewmembersRepository.save(crew);
            return new ResponseEntity<>(crew, null, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("add-product")
    @CrossOrigin(origins = "http://localhost:4200")
    @PreAuthorize(value = "hasRole('ROLE_PILOT') || hasRole('ROLE_CAPTAIN')")
    public ResponseEntity<Crewmembers> addProduct(@RequestParam Long crew_id, @RequestParam Long product_id,
            @RequestBody Productxcrew pxcNew) {
        try {
            Boolean validation = true;
            Crewmembers crew = crewmembersRepository.findById(crew_id).get();
            Product product = productRepository.findById(product_id).get();
            logger.info(product.getProduct_name());
            if (crew == null || product == null)
                return new ResponseEntity<>(null, null, HttpStatus.NOT_FOUND);
            Iterable<Productxcrew> pxc = productxcrewRepository.findAll();
            for (Productxcrew productxcrew : pxc) {
                if (productxcrew.obtainCrew() != null && productxcrew.getProduct() != null) {
                    if (productxcrew.obtainCrew().getId() == crew_id && productxcrew.getProduct().getId() == product_id)
                        validation = false;
                }
            }
            if (!validation)
                return new ResponseEntity<>(null, null, HttpStatus.CONFLICT);

            crew.addProduct(product, pxcNew.getStock(), pxcNew.getDemand(), pxcNew.isSP_(), pxcNew.getOffer(),
                    pxcNew.isPP_());
            crewmembersRepository.save(crew);
            return new ResponseEntity<>(crew, null, HttpStatus.OK);
        } catch (Exception e) {
            logger.info(e.toString());
            return new ResponseEntity<>(null, null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("remove-product")
    @CrossOrigin(origins = "http://localhost:4200")
    @PreAuthorize(value = "hasRole('ROLE_PILOT') || hasRole('ROLE_CAPTAIN')")
    public ResponseEntity<Crewmembers> removeProduct(@RequestParam Long crew_id, @RequestParam Long product_id) {
        try {
            Crewmembers crew = crewmembersRepository.findById(crew_id).get();
            Product product = productRepository.findById(product_id).get();
            logger.info(product.getProduct_name());
            if (crew == null || product == null)
                return new ResponseEntity<>(null, null, HttpStatus.NOT_FOUND);
            crew.removeProduct(product, -1);
            crewmembersRepository.save(crew);
            return new ResponseEntity<>(crew, null, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/game-over")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<Boolean> gameOver(@RequestParam Long player_id) {
        try {
            Player player = playerRepository.findById(player_id).get();
            player.getCrewmembers().setAccTime(0);
            player.getCrewmembers().setCredits(1000000);
            player.getCrewmembers().removeallProduct();
            playerRepository.save(player);
            crewmembersRepository.save(player.getCrewmembers());
            return new ResponseEntity<>(true, null, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(false, null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<Crewmembers> createCrew(@RequestBody Crewmembers crew) {
        try {
            if (crew.getCrew_name() == null || crew.getAccTime() == null || crew.getCredits() < 0.0
                    || crew.getCrew_name() == "")
                return new ResponseEntity<>(null, null, HttpStatus.INTERNAL_SERVER_ERROR);
            crew.setSpace_crew(null);
            crewmembersRepository.save(crew);
            return new ResponseEntity<>(crew, null, HttpStatus.OK);
        } catch (Exception e) {
            logger.error(e.toString(), e.toString());
            return new ResponseEntity<>(null, null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<Crewmembers> deleteCrew(@RequestParam Long crew_id) {
        try {

            Crewmembers crew = crewmembersRepository.findById(crew_id).get();
            if (crew == null)
                return new ResponseEntity<>(null, null, HttpStatus.NOT_FOUND);
            Iterable<Product> products = productRepository.findAll();
            products.forEach(product -> {
                crew.removeProduct(product, -1);
            });
            Iterable<Player> players = playerRepository.findAll();
            players.forEach(player -> {
                if (player.getCrewmembers() != null) {
                    if (player.getCrewmembers().equals(crew))
                        crew.removePlayer(player);
                }
                ;
            });
            crewmembersRepository.delete(crew);
            return new ResponseEntity<>(crew, null, HttpStatus.OK);
        } catch (Exception e) {
            logger.info(e.toString());
            return new ResponseEntity<>(null, null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
