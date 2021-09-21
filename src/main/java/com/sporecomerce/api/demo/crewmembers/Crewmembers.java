package com.sporecomerce.api.demo.crewmembers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;

import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @OneToOne
    @JoinColumn(name = "spaceship_id", referencedColumnName = "id")
    private Spaceship space_crew = new Spaceship();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "crewmembers")
    @JsonIgnore
    private List<Player> player_list = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "crewmembers")
    @JsonSerialize(using = CustomSerializer.class)
    private Set<Productxcrew> products = new HashSet<>();

    public Crewmembers() {
    }

    public Crewmembers(String crew_name, Integer accTime, double credits) {
        this.crew_name = crew_name;
        this.accTime = accTime;
        this.credits = credits;
    }

    public Crewmembers(String crew_name, Integer accTime, double credits, Spaceship space_crew,
            ArrayList<Player> player_list, Set<Productxcrew> products) {
        this.crew_name = crew_name;
        this.accTime = accTime;
        this.credits = credits;
        this.space_crew = space_crew;
        this.player_list = player_list;
        this.products = products;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCrew_name() {
        return crew_name;
    }

    public void setCrew_name(String crew_name) {
        this.crew_name = crew_name;
    }

    public Integer getAccTime() {
        return accTime;
    }

    public void setAccTime(Integer accTime) {
        this.accTime = accTime;
    }

    public double getCredits() {
        return credits;
    }

    public void setCredits(double credits) {
        this.credits = credits;
    }

    public Spaceship getSpace_crew() {
        return space_crew;
    }

    public List<Player> getPlayer_list() {
        return player_list;
    }

    public void setPlayer_list(List<Player> player_list) {
        this.player_list = player_list;
    }

    public Set<Productxcrew> getProducts() {
        return products;
    }

    public void setProducts(Set<Productxcrew> products) {
        this.products = products;
    }

    public void setSpace_crew(Spaceship space_crew) {
        this.space_crew = space_crew;
    }

    public boolean changeSpace_crew(Spaceship space_crew) {

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
        p.setCrewmembers(this);
    }

    public void removePlayer(Player p) {
        player_list.remove(p);
        p.setCrewmembers(null);
    }

    public void addProduct(Product p, Integer stock, Integer demand, boolean sP_, Integer offer, boolean pP_) {
        Productxcrew pxc = new Productxcrew(p, this);
        pxc.setStock(stock);
        pxc.setDemand(demand);
        pxc.setSP_(sP_);
        pxc.setOffer(offer);
        pxc.setPP_(pP_);
        pxc.updatePrices();
        products.add(pxc);
        p.getCrew().add(pxc);
    }

    public void removeProduct(Product p) {
        for (Iterator<Productxcrew> iterator = products.iterator(); iterator.hasNext();) {
            Productxcrew pxc = iterator.next();
            if (pxc.obtainCrew().equals(this) && pxc.getProduct().equals(p)) {
                iterator.remove();
                pxc.getProduct().getCrew().remove(pxc);
                pxc.setCrew(null);
                pxc.setProduct(null);
            }
        }
    }

    @Override
    public String toString() {
        return "Crewmembers [accTime=" + accTime + ", credits=" + credits + ", crew_name=" + crew_name + ", id=" + id
                + ", space_crew=" + space_crew + "]";
    }

}
