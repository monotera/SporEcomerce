package com.sporecomerce.api.demo.planet;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import com.sporecomerce.api.demo.product.Product;
import com.sporecomerce.api.demo.star.Star;

@Entity
public class Planet {

    @Id
    @GeneratedValue
    private Long id;
    private String planet_name;

    @ManyToMany
    @JoinTable(name = "productxplanet", joinColumns = @JoinColumn(name = "planet_id"), inverseJoinColumns = @JoinColumn(name = "product_id"))
    private List<Product> product_list;
    @ManyToOne
    private Star star;

    public Planet() {
    }

    public Planet(Long id, String planet_name, ArrayList<Product> product_list) {
        this.id = id;
        this.planet_name = planet_name;
        this.product_list = product_list;
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

    public List<Product> getProduct_list() {
        return this.product_list;
    }

    public void setProduct_list(ArrayList<Product> product_list) {
        this.product_list = product_list;
    }

    @Override
    public String toString() {
        return "{" + " id='" + getid() + "'" + ", planet_name='" + getPlanet_name() + "'" + ", product_list='"
                + getProduct_list() + "'" + "}";
    }

}
