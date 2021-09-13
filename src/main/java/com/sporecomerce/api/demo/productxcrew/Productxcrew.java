package com.sporecomerce.api.demo.productxcrew;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sporecomerce.api.demo.crewmembers.Crewmembers;
import com.sporecomerce.api.demo.product.Product;

@Entity
public class Productxcrew {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    private Product product;

    @ManyToOne(cascade = CascadeType.ALL)
    @JsonIgnore
    private Crewmembers crewmembers;

    public Productxcrew() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Crewmembers getCrew() {
        return crewmembers;
    }

    public void setCrew(Crewmembers crew) {
        this.crewmembers = crew;
    }

    public Productxcrew(Product product, Crewmembers crew) {
        this.product = product;
        this.crewmembers = crew;
    }

    public Crewmembers getCrewmembers() {
        return crewmembers;
    }

    public void setCrewmembers(Crewmembers crewmembers) {
        this.crewmembers = crewmembers;
    }

}
