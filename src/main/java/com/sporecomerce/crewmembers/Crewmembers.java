package com.sporecomerce.crewmembers;

import java.util.ArrayList;

import com.sporecomerce.player.Player;
import com.sporecomerce.product.Product;
import com.sporecomerce.spaceship.Spaceship;

public class Crewmembers {
    
    private long crew_id;
    private String crew_name;
    private Integer accTime;
    private double credits;
    private Spaceship space_crew;
    private ArrayList<Player> player_list;
    private ArrayList<Product> product_list;


    public Crewmembers() {
    }

    public Crewmembers(long crew_id, String crew_name, Integer accTime, double credits, Spaceship space_crew, ArrayList<Player> player_list, ArrayList<Product> product_list) {
        this.crew_id = crew_id;
        this.crew_name = crew_name;
        this.accTime = accTime;
        this.credits = credits;
        this.space_crew = space_crew;
        this.player_list = player_list;
        this.product_list = product_list;
    }

    public long getCrew_id() {
        return this.crew_id;
    }

    public void setCrew_id(long crew_id) {
        this.crew_id = crew_id;
    }

    public String getCrew_name() {
        return this.crew_name;
    }

    public void setCrew_name(String crew_name) {
        this.crew_name = crew_name;
    }

    public Integer getAccTime() {
        return this.accTime;
    }

    public void setAccTime(Integer accTime) {
        this.accTime = accTime;
    }

    public double getCredits() {
        return this.credits;
    }

    public void setCredits(double credits) {
        this.credits = credits;
    }

    public Spaceship getSpace_crew() {
        return this.space_crew;
    }

    public void setSpace_crew(Spaceship space_crew) {
        this.space_crew = space_crew;
    }

    public ArrayList<Player> getPlayer_list() {
        return this.player_list;
    }

    public void setPlayer_list(ArrayList<Player> player_list) {
        this.player_list = player_list;
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
            " crew_id='" + getCrew_id() + "'" +
            ", crew_name='" + getCrew_name() + "'" +
            ", accTime='" + getAccTime() + "'" +
            ", credits='" + getCredits() + "'" +
            ", space_crew='" + getSpace_crew() + "'" +
            ", player_list='" + getPlayer_list() + "'" +
            ", product_list='" + getProduct_list() + "'" +
            "}";
    }
    
}
