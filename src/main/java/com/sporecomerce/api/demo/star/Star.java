package com.sporecomerce.api.demo.star;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.sporecomerce.api.demo.planet.Planet;
import com.sporecomerce.api.demo.spaceship.Spaceship;

@Entity
public class Star {

    @Id
    @GeneratedValue
    private long id;

    private int x;
    private int y;
    private int z;
    private String name;
    @Transient
    private HashMap<Integer, Double> nearStars = new HashMap<>();
    private Boolean isInHabited;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "star")
    @JsonManagedReference(value = "star-spaceship")
    private List<Spaceship> spaceLobby = new ArrayList<>();

    @OneToMany(mappedBy = "star")
    @JsonManagedReference(value = "star-planet")
    private List<Planet> planetList = new ArrayList<>();

    public Star() {

    }

    public Star(int x, int y, int z, String name, Boolean isInHabited) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.name = name;
        this.isInHabited = isInHabited;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getZ() {
        return z;
    }

    public void setZ(int z) {
        this.z = z;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public HashMap<Integer, Double> getNearStars() {
        return nearStars;
    }

    public void setNearStars(HashMap<Integer, Double> nearStars) {
        this.nearStars = nearStars;
    }

    public Boolean getIsInHabited() {
        return isInHabited;
    }

    public void setIsInHabited(Boolean isInHabited) {
        this.isInHabited = isInHabited;
    }

    public List<Spaceship> getSpaceLobby() {
        return spaceLobby;
    }

    public void setSpaceLobby(List<Spaceship> spaceLobby) {
        this.spaceLobby = spaceLobby;
    }

    public List<Planet> getPlanetList() {
        return planetList;
    }

    public void setPlanetList(List<Planet> planetList) {
        this.planetList = planetList;
    }

    public boolean addPlanet(Planet p) {
        if (!planetList.contains(p)) {
            planetList.add(p);
            p.setStar(this);
            return true;
        }
        return false;
    }

    public boolean removePlanet(Planet p) {
        if (planetList.contains(p)) {
            planetList.remove(p);
            p.setStar(null);
            return true;
        }
        return false;
    }

    public void addSpaceShip(Spaceship s) {
        if (!spaceLobby.contains(s)) {
            spaceLobby.add(s);
            s.setStar(this);
        }
    }

    public void removeSpaceShip(Spaceship s) {
        spaceLobby.remove(s);
        s.setStar(null);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Star [id=" + id + ", isInHabited=" + isInHabited + ", name=" + name + ", nearStars=" + nearStars
                + ", planetList=" + planetList + ", spaceLobby=" + spaceLobby + ", x=" + x + ", y=" + y + ", z=" + z
                + "]";
    }

}