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
    @JsonManagedReference
    private List<Spaceship> spaceLobby = new ArrayList<>();

    @OneToMany(mappedBy = "star")
    @JsonManagedReference
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

    public Star(long id, int x, int y, int z, String name, HashMap<Integer, Double> nearStars, Boolean isInHabited,
            ArrayList<Planet> planetList, ArrayList<Spaceship> spaceLobby) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.z = z;
        this.name = name;
        this.nearStars = nearStars;
        this.isInHabited = isInHabited;
        this.planetList = planetList;
        this.spaceLobby = spaceLobby;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public long getid() {
        return this.id;
    }

    public void setid(long id) {
        this.id = id;
    }

    public List<Planet> getPlanetList() {
        return this.planetList;
    }

    public void setPlanetList(ArrayList<Planet> planetList) {
        this.planetList = planetList;
    }

    public List<Spaceship> getSpaceLobby() {
        return this.spaceLobby;
    }

    public void setSpaceLobby(ArrayList<Spaceship> spaceLobby) {
        this.spaceLobby = spaceLobby;
    }

    public void setSpaceLobby(List<Spaceship> spaceLobby) {
        this.spaceLobby = spaceLobby;
    }

    public void setPlanetList(List<Planet> planetList) {
        this.planetList = planetList;
    }

    public void addPlanet(Planet p) {
        planetList.add(p);
        p.setStar(this);
    }

    public void removePlanet(Planet p) {
        planetList.remove(p);
        p.setStar(null);
    }

    public void addSpaceShip(Spaceship s) {
        spaceLobby.add(s);
        s.setStar(this);
    }

    public void removeSpaceShip(Spaceship s) {
        spaceLobby.remove(s);
        s.setStar(null);
    }

    @Override
    public String toString() {
        return "Star [id=" + id + ", isInHabited=" + isInHabited + ", name=" + name + ", nearStars=" + nearStars
                + ", planetList=" + planetList + ", spaceLobby=" + spaceLobby + ", x=" + x + ", y=" + y + ", z=" + z
                + "]";
    }

}