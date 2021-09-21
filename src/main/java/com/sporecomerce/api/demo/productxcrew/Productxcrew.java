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

    private Integer stock;
    private Integer demand;
    private double sales_price;
    private boolean SP_;
    private Integer offer;
    private double purchase_price;
    private boolean PP_;

    public Productxcrew(Long id, Product product, Crewmembers crewmembers, Integer stock, Integer demand,
            double sales_price, boolean SP_, Integer offer, double purchase_price, boolean PP_) {
        this.id = id;
        this.product = product;
        this.crewmembers = crewmembers;
        this.stock = stock;
        this.demand = demand;
        this.sales_price = sales_price;
        this.SP_ = SP_;
        this.offer = offer;
        this.purchase_price = purchase_price;
        this.PP_ = PP_;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Integer getDemand() {
        return demand;
    }

    public void setDemand(Integer demand) {
        this.demand = demand;
    }

    public double getSales_price() {
        return sales_price;
    }

    public void setSales_price(double sales_price) {
        this.sales_price = sales_price;
    }

    public boolean isSP_() {
        return SP_;
    }

    public void setSP_(boolean sP_) {
        SP_ = sP_;
    }

    public Integer getOffer() {
        return offer;
    }

    public void setOffer(Integer offer) {
        this.offer = offer;
    }

    public double getPurchase_price() {
        return purchase_price;
    }

    public void setPurchase_price(double purchase_price) {
        this.purchase_price = purchase_price;
    }

    public boolean isPP_() {
        return PP_;
    }

    public void setPP_(boolean pP_) {
        PP_ = pP_;
    }

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

    public Crewmembers obtainCrew() {
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

    public Boolean updatePrices() {
        Boolean sentry = false;

        // If the product is to be sold
        if (this.PP_) {
            this.purchase_price = offer / (1.0 + stock);
            sentry = true;
        }

        // If the product is to be purchased
        if (this.SP_) {
            this.sales_price = demand / (1.0 + stock);
            sentry = true;
        }
        return sentry;
    }

    public boolean checkStock() {
        Boolean sentry = false;

        if (this.stock > 0) {
            sentry = true;
        }
        return sentry;
    }

}
