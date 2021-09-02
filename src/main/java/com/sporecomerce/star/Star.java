package com.sporecomerce.star;

import java.util.HashMap;

public class Star {
    private int x;
    private int y;
    private int z;
    private String name;
    private HashMap<Integer, Double> nearStars = new HashMap<>();
    private Boolean isInHabited;

    public Star() {

    }

    public Star(int x, int y, int z, String name, Boolean isInHabited) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.name = name;
        this.isInHabited = isInHabited;
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

}