package com.sporecomerce.api.demo.productxcrew;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.sporecomerce.api.demo.crewmembers.Crewmembers;
import com.sporecomerce.api.demo.product.Product;

@Entity
public class Productxcrew {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    private Product product = new Product();

    @ManyToOne(cascade = CascadeType.ALL)
    private Crewmembers crew = new Crewmembers();

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
        return crew;
    }

    public void setCrew(Crewmembers crew) {
        this.crew = crew;
    }

    public Productxcrew(Product product, Crewmembers crew) {
        this.product = product;
        this.crew = crew;
    }

}
