package com.sporecomerce.star;

import java.util.ArrayList;
import java.util.HashMap;
import com.sporecomerce.planet.Planet;
import com.sporecomerce.spaceship.Spaceship;

public class Star {

    private long star_id;

    private int x;
    private int y;
    private int z;
    private String name;
    private HashMap<Integer, Double> nearStars = new HashMap<>();
    private Boolean isInHabited;

    private ArrayList<Planet> planetList;
    private ArrayList<Spaceship> spaceLobby;

    public Star() {

    }

    public Star(int x, int y, int z, String name, Boolean isInHabited) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.name = name;
        this.isInHabited = isInHabited;
    }

    public Star(long star_id, int x, int y, int z, String name, HashMap<Integer,Double> nearStars, Boolean isInHabited, ArrayList<Planet> planetList, ArrayList<Spaceship> spaceLobby) {
        this.star_id = star_id;
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

    public long getStar_id() {
        return this.star_id;
    }

    public void setStar_id(long star_id) {
        this.star_id = star_id;
    }

    public ArrayList<Planet> getPlanetList() {
        return this.planetList;
    }

    public void setPlanetList(ArrayList<Planet> planetList) {
        this.planetList = planetList;
    }

    public ArrayList<Spaceship> getSpaceLobby() {
        return this.spaceLobby;
    }

    public void setSpaceLobby(ArrayList<Spaceship> spaceLobby) {
        this.spaceLobby = spaceLobby;
    }

    @Override
    public String toString() {
        return "{" +
            " star_id='" + getStar_id() + "'" +
            ", x='" + getX() + "'" +
            ", y='" + getY() + "'" +
            ", z='" + getZ() + "'" +
            ", name='" + getName() + "'" +
            ", nearStars='" + getNearStars() + "'" +
            ", isInHabited='" + getIsInHabited() + "'" +
            ", planetList='" + getPlanetList() + "'" +
            ", spaceLobby='" + getSpaceLobby() + "'" +
            "}";
    }

}