package com.sporecomerce.api.demo.product;

import java.util.ArrayList;

import com.sporecomerce.api.demo.crewmembers.Crewmembers;
import com.sporecomerce.api.demo.crewmembers.CrewmembersRepository;
import com.sporecomerce.api.demo.planet.Planet;
import com.sporecomerce.api.demo.planet.PlanetRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
@RequestMapping("/product")
public class ProductController {
    @Autowired
    ProductRepository productRepository;

    @Autowired
    PlanetRepository planetRepository;

    @Autowired
    CrewmembersRepository crewmembersRepository;
    Logger logger = LoggerFactory.getLogger(ProductController.class);

    // http://localhost:8080/product/products
    @GetMapping("/products")
    @CrossOrigin(origins = "http://localhost:4200")

    public ResponseEntity<ArrayList<Product>> getProducts() {
        ArrayList<Product> response = new ArrayList<>();
        Iterable<Product> db = productRepository.findAll();
        db.forEach(response::add);
        return new ResponseEntity<>(response, null, HttpStatus.OK);
    }

    // http://localhost:8080/product?product_id=..
    @GetMapping("")
    @CrossOrigin(origins = "http://localhost:4200")

    public ResponseEntity<Product> getProduct(@RequestParam Long product_id) {
        try {
            Product product = productRepository.findById(product_id).get();
            if (product.equals(null))
                return new ResponseEntity<>(null, null, HttpStatus.NOT_FOUND);
            return new ResponseEntity<>(product, null, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // http://localhost:8080/product?product_id=21
    /**
     * { "id": ..., "product_name": ..., "stock": ..., "load_capacity": ...,
     * "demand": ..., "sales_price": ..., "offer": ..., "purchase_price": ...,
     * "sp_": ..., "pp_": ... }
     */
    @PutMapping("")
    @CrossOrigin(origins = "http://localhost:4200")

    public ResponseEntity<Product> modProduct(@RequestBody Product product, @RequestParam Long product_id) {
        try {
            Product oldProduct = productRepository.findById(product_id).get();

            if (oldProduct.equals(null))
                return new ResponseEntity<>(null, null, HttpStatus.NOT_FOUND);

            product.setId(product_id);
            if (product.getProduct_name() == null || product.getProduct_name() == "")
                product.setProduct_name(oldProduct.getProduct_name());
            if (product.getLoad_capacity() == 0.0)
                product.setLoad_capacity(oldProduct.getLoad_capacity());
            productRepository.save(product);

            return new ResponseEntity<>(product, null, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // http://localhost:8080/product?product_id=...
    @DeleteMapping("")
    @CrossOrigin(origins = "http://localhost:4200")

    public ResponseEntity<Product> deleteProduct(@RequestParam Long product_id) {

        try {
            Product product = productRepository.findById(product_id).get();
            if (product == null)
                return new ResponseEntity<>(null, null, HttpStatus.NOT_FOUND);

            Iterable<Planet> planets = planetRepository.findAll();
            Iterable<Crewmembers> crews = crewmembersRepository.findAll();
            for (Planet planet : planets) {
                product.removePlanet(planet);
            }

            for (Crewmembers crewmembers : crews) {
                product.removeCrewmember(crewmembers);
            }
            productRepository.delete(product);
            return new ResponseEntity<>(product, null, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("error", e);
            return new ResponseEntity<>(null, null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // localhost:8080/product
    /**
     * { "product_name": "pr nuevo", "stock": 10, "load_capacity": 10.0, "demand":
     * 10, "sales_price": 10.0, "offer": 10, "purchase_price": 10.0, "sp_": true,
     * "pp_": false }
     */
    @PostMapping("")
    @CrossOrigin(origins = "http://localhost:4200")

    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        try {
            if (product.getProduct_name() == null || product.getLoad_capacity() == 0.0)
                return new ResponseEntity<>(null, null, HttpStatus.CONFLICT);
            productRepository.save(product);
            return new ResponseEntity<>(product, null, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}