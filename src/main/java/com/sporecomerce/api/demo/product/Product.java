package com.sporecomerce.api.demo.product;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sporecomerce.api.demo.crewmembers.Crewmembers;
import com.sporecomerce.api.demo.planet.Planet;
import com.sporecomerce.api.demo.productxcrew.Productxcrew;
import com.sporecomerce.api.demo.productxplanet.Productxplanet;

@Entity
public class Product {

    @Id
    @GeneratedValue
    private Long id;

    private String product_name;
    private double load_capacity;


    @OneToMany(cascade = CascadeType.ALL, mappedBy = "product")
    @JsonIgnore
    Set<Productxcrew> crew = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "product")
    @JsonBackReference(value = "planet-pxp")
    Set<Productxplanet> planets = new HashSet<>();

    public Product() {
    }

    public Product(String product_name, double load_capacity) {
        this.product_name = product_name;
        this.load_capacity = load_capacity;
    }

    public Product(Long id){
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public double getLoad_capacity() {
        return load_capacity;
    }

    public void setLoad_capacity(double load_capacity) {
        this.load_capacity = load_capacity;
    }


    public Set<Productxcrew> getCrew() {
        return crew;
    }

    public void setCrew(Set<Productxcrew> crew) {
        this.crew = crew;
    }

    public Set<Productxplanet> getPlanets() {
        return planets;
    }

    public void setPlanets(Set<Productxplanet> planets) {
        this.planets = planets;
    }

    public void removePlanet(Planet p) {
        for (Iterator<Productxplanet> iterator = planets.iterator(); iterator.hasNext();) {
            Productxplanet pxp = iterator.next();
            if (pxp.getProduct().equals(this) && pxp.getPlanet().equals(p)) {
                iterator.remove();
                pxp.getPlanet().getProduct_list().remove(pxp);
                pxp.setProduct(null);
                pxp.setPlanet(null);
            }
        }
    }

    public void removeCrewmember(Crewmembers c) {
        for (Iterator<Productxcrew> iterator = crew.iterator(); iterator.hasNext();) {
            Productxcrew pxp = iterator.next();
            if (pxp.getProduct().equals(this) && pxp.getCrew().equals(c)) {
                iterator.remove();
                pxp.getCrew().getProducts().remove(pxp);
                pxp.setCrew(null);
                pxp.setProduct(null);
            }
        }
    }
}
