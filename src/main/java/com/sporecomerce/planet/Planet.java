package com.sporecomerce.planet;

import java.util.ArrayList;

import com.sporecomerce.product.Product;

public class Planet {
    
    private Long planet_id;
    private String planet_name;
    private ArrayList<Product> product_list;


    public Planet() {
    }

    public Planet(Long planet_id, String planet_name, ArrayList<Product> product_list) {
        this.planet_id = planet_id;
        this.planet_name = planet_name;
        this.product_list = product_list;
    }

    public Long getPlanet_id() {
        return this.planet_id;
    }

    public void setPlanet_id(Long planet_id) {
        this.planet_id = planet_id;
    }

    public String getPlanet_name() {
        return this.planet_name;
    }

    public void setPlanet_name(String planet_name) {
        this.planet_name = planet_name;
    }

    public ArrayList<Product> getProduct_list() {
        return this.product_list;
    }

    public void setProduct_list(ArrayList<Product> product_list) {
        this.product_list = product_list;
    }

    @Override
    public String toString() {
        return "{" +
            " planet_id='" + getPlanet_id() + "'" +
            ", planet_name='" + getPlanet_name() + "'" +
            ", product_list='" + getProduct_list() + "'" +
            "}";
    }
    
}
