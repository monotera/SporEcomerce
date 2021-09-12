package com.sporecomerce.api.demo.crewmembers;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.sporecomerce.api.demo.player.Player;
import com.sporecomerce.api.demo.product.Product;
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
    private List<Player> player_list;

    @ManyToMany
    @JoinTable(name = "productxcrew", joinColumns = @JoinColumn(name = "crew_id"), inverseJoinColumns = @JoinColumn(name = "product_id"))
    private List<Product> products = new ArrayList<>();

    public Crewmembers() {
    }

    public Crewmembers(long crew_id, String crew_name, Integer accTime, double credits, Spaceship space_crew,
            ArrayList<Player> player_list, ArrayList<Product> products) {
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

    public void setSpace_crew(Spaceship space_crew) {
        this.space_crew = space_crew;
    }

    public List<Player> getPlayer_list() {
        return this.player_list;
    }

    public void setPlayer_list(ArrayList<Player> player_list) {
        this.player_list = player_list;
    }

    public List<Product> getProducts() {
        return this.products;
    }

    public void setproducts(ArrayList<Product> products) {
        this.products = products;
    }

}
