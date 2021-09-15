package com.sporecomerce.api.demo.planet;

import java.util.ArrayList;

import com.sporecomerce.api.demo.product.Product;
import com.sporecomerce.api.demo.product.ProductRepository;
import com.sporecomerce.api.demo.productxplanet.Productxplanet;
import com.sporecomerce.api.demo.productxplanet.ProductxplanetRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequestMapping(value = "/planet")
public class PlanetController {
    @Autowired
    PlanetRepository planetRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ProductxplanetRepository productxplanetRepository;
    Logger logger = LoggerFactory.getLogger(PlanetController.class);

    @GetMapping("/view")
    public String getMainPage(Model model) {
        Iterable<Planet> planets = planetRepository.findAll();
        Iterable<Product> products = productRepository.findAll();
        model.addAttribute("planets", planets);
        model.addAttribute("products", products);
        return "planet";
    }

    // http://localhost:8080/planet/planets
    @GetMapping("/planets")
    public ResponseEntity<ArrayList<Planet>> getPlanets() {
        ArrayList<Planet> response = new ArrayList<>();
        Iterable<Planet> planetsDB = planetRepository.findAll();
        planetsDB.forEach(response::add);
        return new ResponseEntity<>(response, null, HttpStatus.OK);
    }

    // http://localhost:8080/planet?planet_id=..
    @GetMapping("")
    public ResponseEntity<Planet> getPlanet(@RequestParam Long planet_id) {
        try {
            Planet planet = planetRepository.findById(planet_id).get();
            return new ResponseEntity<>(planet, null, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // http://localhost:8080/planet/add-product?planet_id=15&product_id=35
    @PutMapping("/add-product")
    public ResponseEntity<Planet> addProduct(@RequestParam Long planet_id, @RequestParam Long product_id) {
        try {
            Boolean validation = true;
            Iterable<Productxplanet> pxp = productxplanetRepository.findAll();
            for (Productxplanet productxplanet : pxp) {
                if (productxplanet.getPlanet() != null && productxplanet.getProduct() != null)
                    if (productxplanet.getPlanet().getId() == planet_id
                            && productxplanet.getProduct().getId() == product_id)
                        validation = false;
            }
            if (!validation)
                return new ResponseEntity<>(null, null, HttpStatus.CONFLICT);
            Planet planet = planetRepository.findById(planet_id).get();
            Product product = productRepository.findById(product_id).get();
            planet.addProduct(product);
            planetRepository.save(planet);
            return new ResponseEntity<>(planet, null, HttpStatus.OK);
        } catch (Exception e) {
            logger.info(e.toString());
            return new ResponseEntity<>(null, null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // http://localhost:8080/planet/remove-product?planet_id=15&product_id=35
    @PutMapping("/remove-product")
    public ResponseEntity<Planet> removeProduct(@RequestParam Long planet_id, @RequestParam Long product_id) {
        try {
            Planet planet = planetRepository.findById(planet_id).get();
            Product product = productRepository.findById(product_id).get();
            if (planet == null || product == null)
                return new ResponseEntity<>(null, null, HttpStatus.NOT_FOUND);
            planet.removeProduct(product);
            planetRepository.save(planet);
            return new ResponseEntity<>(planet, null, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // localhost:8080/planet?planet_id=15
    // { "planet_name":"planert"}
    @PutMapping("")
    public ResponseEntity<Planet> modifyPlanet(@RequestBody Planet planet, @RequestParam Long planet_id) {
        try {
            Planet old_planet = planetRepository.findById(planet_id).get();
            if (planet.getPlanet_name() == null || planet.getPlanet_name() == "")
                planet.setPlanet_name(old_planet.getPlanet_name());
            planet.setId(old_planet.getId());
            planet.setStar(old_planet.getStar());
            planet.setProduct_list(old_planet.getProduct_list());
            planetRepository.save(planet);
            return new ResponseEntity<>(planet, null, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // localhost:8080/planet?planet_id=15
    // { "planet_name":"planert"}
    @PostMapping("")
    public ResponseEntity<Planet> createPlanet(@RequestBody Planet planet) {
        try {
            if (planet.getPlanet_name() == null || planet.getPlanet_name() == "")
                return new ResponseEntity<>(null, null, HttpStatus.INTERNAL_SERVER_ERROR);
            planetRepository.save(planet);
            return new ResponseEntity<>(planet, null, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // http://localhost:8080/planet?planet_id=15
    @DeleteMapping("")
    public ResponseEntity<Planet> removePlanet(@RequestParam Long planet_id) {
        try {

            Planet planet = planetRepository.findById(planet_id).get();
            Iterable<Product> products = productRepository.findAll();
            products.forEach(product -> {
                planet.removeProduct(product);
            });
            planetRepository.delete(planet);
            return new ResponseEntity<>(planet, null, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
