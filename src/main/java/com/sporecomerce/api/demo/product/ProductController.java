package com.sporecomerce.api.demo.product;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    ProductRepository productRepository;

    @GetMapping("/products")
    public ResponseEntity<ArrayList<Product>> getProducts() {
        ArrayList<Product> response = new ArrayList<>();
        Iterable<Product> db = productRepository.findAll();
        db.forEach(response::add);
        return new ResponseEntity<>(response, null, HttpStatus.OK);
    }
}
