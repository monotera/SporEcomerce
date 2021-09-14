package com.sporecomerce.api.demo.productxplanet;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.sporecomerce.api.demo.planet.Planet;
import com.sporecomerce.api.demo.product.Product;

@Entity
public class Productxplanet {

    @Id
    @GeneratedValue
    private long id;

    @ManyToOne(cascade = CascadeType.ALL)
    private Product product;
    @ManyToOne(cascade = CascadeType.ALL)
    @JsonBackReference(value = "planet-pxp")
    private Planet planet;

    public Productxplanet() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Planet getPlanet() {
        return planet;
    }

    public void setPlanet(Planet planet) {
        this.planet = planet;
    }

    public Productxplanet(Product product, Planet planet) {
        this.product = product;
        this.planet = planet;
    }

}
