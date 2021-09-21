package com.sporecomerce.api.demo.productxplanet;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.sporecomerce.api.demo.planet.Planet;
import com.sporecomerce.api.demo.product.Product;

@Entity
public class Productxplanet {
    @Id
    @GeneratedValue
    private long id;
    @ManyToOne(cascade = CascadeType.ALL)
    private Product product;
    @ManyToOne(cascade = CascadeType.ALL)
    @JsonBackReference(value = "planet-pxp")
    private Planet planet;
    private Integer stock;
    private Integer demand;
    private double sales_price;
    private boolean SP_;
    private Integer offer;
    private double purchase_price;
    private boolean PP_;

    public Productxplanet() {
    }

    public long getId() {
        return id;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Planet getPlanet() {
        return planet;
    }

    public Productxplanet(Product product, Planet planet, Integer demand, double sales_price, boolean sP_,
            Integer offer, double purchase_price, boolean pP_) {
        this.product = product;
        this.planet = planet;
        this.demand = demand;
        this.sales_price = sales_price;
        SP_ = sP_;
        this.offer = offer;
        this.purchase_price = purchase_price;
        PP_ = pP_;
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

    public void setPlanet(Planet planet) {
        this.planet = planet;
    }

    public Productxplanet(Product product, Planet planet) {
        this.product = product;
        this.planet = planet;
    }

    @Override
    public String toString() {
        return "Productxplanet [PP_=" + PP_ + ", SP_=" + SP_ + ", demand=" + demand + ", id=" + id + ", offer=" + offer
                + ", planet=" + planet + ", product=" + product + ", purchase_price=" + purchase_price
                + ", sales_price=" + sales_price + "]";
    }

    public Boolean updatePrices(){
        Boolean sentry = false;

        //If the product is to be sold
        if(this.PP_){
            this.purchase_price = offer/(1.0+stock);
            sentry = true;
        }

        //If the product is to be purchased
        if(this.SP_){
            this.sales_price = demand/(1.0+stock);
            sentry = true;
        }
        return sentry;
    }

    public boolean checkStock(){
        Boolean sentry = false;

        if(this.stock > 0){
            sentry = true;
        }
        return sentry;
    }
}