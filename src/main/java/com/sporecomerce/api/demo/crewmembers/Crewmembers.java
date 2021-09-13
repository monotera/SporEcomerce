package com.sporecomerce.api.demo.crewmembers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;

import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.sporecomerce.api.demo.player.Player;
import com.sporecomerce.api.demo.product.Product;
import com.sporecomerce.api.demo.productxcrew.Productxcrew;
import com.sporecomerce.api.demo.spaceship.Spaceship;

@Entity
public class Crewmembers {

    @Id
    @GeneratedValue
    private long id;
    private String crew_name;
    private Integer accTime;
    private double credits;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "spaceship_id", referencedColumnName = "id")
    private Spaceship space_crew;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "crewmembers")
    @JsonBackReference
    private List<Player> player_list = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "crewmembers")
    @JsonSerialize(using = CustomSerializer.class)
    private Set<Productxcrew> products = new HashSet<>();

    public Crewmembers() {
    }

    public void addProduct(Product p) {
        Productxcrew pxc = new Productxcrew(p, this);
        products.add(pxc);
        p.getCrew().add(pxc);
    }

    public Crewmembers(String crew_name, Integer accTime, double credits) {
        this.crew_name = crew_name;
        this.accTime = accTime;
        this.credits = credits;
    }

    public Crewmembers(long crew_id, String crew_name, Integer accTime, double credits, Spaceship space_crew,
            ArrayList<Player> player_list, Set<Productxcrew> products) {
        this.id = crew_id;
        this.crew_name = crew_name;
        this.accTime = accTime;
        this.credits = credits;
        this.space_crew = space_crew;
        this.player_list = player_list;
        this.products = products;
    }

    public long getCrew_id() {
        return this.id;
    }

    public void setCrew_id(long crew_id) {
        this.id = crew_id;
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

    public boolean setSpace_crew(Spaceship space_crew) {

        if (space_crew.getCrew() != null)
            return false;
        if (this.space_crew == null) {
            this.space_crew = space_crew;
            space_crew.setCrew(this);
        } else {
            this.space_crew.setCrew(null);
            this.space_crew = space_crew;
            space_crew.setCrew(this);
        }
        return true;
    }

    public void addPlayer(Player p) {
        player_list.add(p);
        p.setCrew_players(this);
    }

    public void removePlayer(Player p) {
        player_list.remove(p);
        p.setCrew_players(null);
    }

    public List<Player> getPlayer_list() {
        return this.player_list;
    }

    public void setPlayer_list(ArrayList<Player> player_list) {
        this.player_list = player_list;
    }

    public Set<Productxcrew> getProducts() {
        return products;
    }

    public void setProducts(Set<Productxcrew> products) {
        this.products = products;
    }

}
