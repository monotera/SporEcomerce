package com.sporecomerce.api.demo.crewmembers;

import java.util.ArrayList;

import com.sporecomerce.api.demo.player.Player;
import com.sporecomerce.api.demo.player.PlayerRepository;
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
    public ResponseEntity<ArrayList<Crewmembers>> getCrew() {
        ArrayList<Crewmembers> response = new ArrayList<>();
        Iterable<Crewmembers> crewDB = crewmembersRepository.findAll();
        crewDB.forEach(response::add);
        return new ResponseEntity<>(response, null, HttpStatus.OK);
    }

    // http://localhost:8080/crew?crew_id=9
    @GetMapping("")
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

    @PutMapping("")
    public ResponseEntity<Crewmembers> modCrew(@RequestParam Long crew_id, @RequestBody Crewmembers crew) {
        try {
            Crewmembers oldCrew = crewmembersRepository.findById(crew_id).get();
            if (crew == null)
                return new ResponseEntity<>(null, null, HttpStatus.NOT_FOUND);
            crew.setId(oldCrew.getId());
            crew.setSpace_crew(oldCrew.getSpace_crew());
            crew.setProducts(oldCrew.getProducts());
            if (crew.getCrew_name() == null)
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

    @PutMapping("/change_spaceship")
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

    @PutMapping("add_product")
    public ResponseEntity<Crewmembers> addProduct(@RequestParam Long crew_id, @RequestParam Long product_id) {
        try {
            Boolean validation = true;
            Crewmembers crew = crewmembersRepository.findById(crew_id).get();
            Product product = productRepository.findById(product_id).get();
            logger.info(product.getProduct_name());
            if (crew == null || product == null)
                return new ResponseEntity<>(null, null, HttpStatus.NOT_FOUND);

            Iterable<Productxcrew> pxc = productxcrewRepository.findAll();
            for (Productxcrew productxcrew : pxc) {
                if (productxcrew.getCrew().getId() == crew_id && productxcrew.getProduct().getId() == product_id)
                    validation = false;
            }

            if (!validation)
                return new ResponseEntity<>(null, null, HttpStatus.CONFLICT);
            crew.addProduct(product);
            crewmembersRepository.save(crew);
            return new ResponseEntity<>(crew, null, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("remove_product")
    public ResponseEntity<Crewmembers> removeProduct(@RequestParam Long crew_id, @RequestParam Long product_id) {
        try {
            Crewmembers crew = crewmembersRepository.findById(crew_id).get();
            Product product = productRepository.findById(product_id).get();
            logger.info(product.getProduct_name());
            if (crew == null || product == null)
                return new ResponseEntity<>(null, null, HttpStatus.NOT_FOUND);
            crew.removeProduct(product);
            crewmembersRepository.save(crew);
            return new ResponseEntity<>(crew, null, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("")
    public ResponseEntity<Crewmembers> createCrew(@RequestBody Crewmembers crew) {
        try {
            if (crew.getCrew_name() == null || crew.getAccTime() == null || crew.getCredits() < 0.0)
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
    public ResponseEntity<Crewmembers> deleteCrew(@RequestParam Long crew_id) {
        try {

            Crewmembers crew = crewmembersRepository.findById(crew_id).get();
            if (crew == null)
                return new ResponseEntity<>(null, null, HttpStatus.NOT_FOUND);
            Iterable<Product> products = productRepository.findAll();
            products.forEach(product -> {
                crew.removeProduct(product);
            });
            Iterable<Player> players = playerRepository.findAll();
            players.forEach(player -> {
                if (player.getCrewmembers().equals(crew))
                    crew.removePlayer(player);
            });
            crewmembersRepository.delete(crew);
            return new ResponseEntity<>(crew, null, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
