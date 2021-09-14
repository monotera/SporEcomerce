package com.sporecomerce.api.demo.spaceship;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.sporecomerce.api.demo.crewmembers.Crewmembers;
import com.sporecomerce.api.demo.star.Star;

@Entity
public class Spaceship {

    @Id
    @GeneratedValue
    private long id;
    private String ship_name;
    private double ship_load;
    private double velocity;

    @OneToOne(mappedBy = "space_crew")
    @JsonBackReference
    private Crewmembers crew;

    @ManyToOne
    @JsonBackReference(value = "star-spaceship")
    private Star star;

    public Spaceship() {
    }

    public Spaceship(String ship_name, double ship_load, double velocity) {

        this.ship_name = ship_name;
        this.ship_load = ship_load;
        this.velocity = velocity;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Crewmembers getCrew() {
        return crew;
    }

    public void setCrew(Crewmembers crew) {
        this.crew = crew;
    }

    public Star getStar() {
        return star;
    }

    public void setStar(Star star) {
        this.star = star;
    }

    public String getShip_name() {
        return this.ship_name;
    }

    public void setShip_name(String ship_name) {
        this.ship_name = ship_name;
    }

    public double getShip_load() {
        return this.ship_load;
    }

    public void setShip_load(double ship_load) {
        this.ship_load = ship_load;
    }

    public double getVelocity() {
        return this.velocity;
    }

    public void setVelocity(double velocity) {
        this.velocity = velocity;
    }

}
