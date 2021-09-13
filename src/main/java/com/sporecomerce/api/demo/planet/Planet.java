package com.sporecomerce.api.demo.planet;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.sporecomerce.api.demo.product.Product;
import com.sporecomerce.api.demo.productxplanet.Productxplanet;
import com.sporecomerce.api.demo.star.Star;

@Entity
public class Planet {

    @Id
    @GeneratedValue
    private Long id;
    private String planet_name;

    @JsonManagedReference
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "planet")
    private Set<Productxplanet> product_list = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    private Star star;

    public Planet() {
    }

    public Planet(String planet_name) {
        this.planet_name = planet_name;
    }

    public void addProduct(Product p) {
        Productxplanet pxp = new Productxplanet(p, this);
        product_list.add(pxp);
        p.getPlanets().add(pxp);
    }

    public Planet(String planet_name, Star star) {
        this.planet_name = planet_name;
        this.star = star;
    }

    public Planet(String planet_name, List<Product> product_list, Star star) {
        this.planet_name = planet_name;
        // this.product_list = product_list;
        this.star = star;
    }

    public Long getid() {
        return this.id;
    }

    public void setid(Long id) {
        this.id = id;
    }

    public String getPlanet_name() {
        return this.planet_name;
    }

    public void setPlanet_name(String planet_name) {
        this.planet_name = planet_name;
    }

    public Star getStar() {
        return star;
    }

    public void setStar(Star star) {
        this.star = star;
    }

    public Set<Productxplanet> getProduct_list() {
        return product_list;
    }

    public void setProduct_list(Set<Productxplanet> product_list) {
        this.product_list = product_list;
    }

}
