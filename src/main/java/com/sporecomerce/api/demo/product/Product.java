package com.sporecomerce.api.demo.product;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.JoinColumn;
import com.sporecomerce.api.demo.crewmembers.Crewmembers;
import com.sporecomerce.api.demo.planet.Planet;

@Entity
public class Product {

    @Id
    @GeneratedValue
    private Long id;

    private String product_name;
    private Integer stock;
    private double load_capacity;

    private Integer demand;
    private double sales_price;
    private boolean SP_;

    private Integer offer;
    private double purchase_price;
    private boolean PP_;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "productxcrew", joinColumns = @JoinColumn(name = "product_id"), inverseJoinColumns = @JoinColumn(name = "crew_id"))
    List<Crewmembers> crew = new ArrayList<>();

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "productxplanet", joinColumns = @JoinColumn(name = "product_id"), inverseJoinColumns = @JoinColumn(name = "planet_id"))
    List<Planet> planets = new ArrayList<>();

    public Product() {
    }

    public Product(String product_name, Integer stock, double load_capacity, Integer demand, double sales_price,
            boolean SP_, Integer offer, double purchase_price, boolean PP_) {

        this.product_name = product_name;
        this.stock = stock;
        this.load_capacity = load_capacity;
        this.demand = demand;
        this.sales_price = sales_price;
        this.SP_ = SP_;
        this.offer = offer;
        this.purchase_price = purchase_price;
        this.PP_ = PP_;
    }

    public Long getProduct_id() {
        return id;
    }

    public void setProduct_id(Long product_id) {
        this.id = product_id;
    }

    public String getProduct_name() {
        return this.product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public Integer getStock() {
        return this.stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public double getLoad_capacity() {
        return this.load_capacity;
    }

    public void setLoad_capacity(double load_capacity) {
        this.load_capacity = load_capacity;
    }

    public Integer getDemand() {
        return this.demand;
    }

    public void setDemand(Integer demand) {
        this.demand = demand;
    }

    public double getSales_price() {
        return this.sales_price;
    }

    public void setSales_price(double sales_price) {
        this.sales_price = sales_price;
    }

    public boolean isSP_() {
        return this.SP_;
    }

    public boolean getSP_() {
        return this.SP_;
    }

    public void setSP_(boolean SP_) {
        this.SP_ = SP_;
    }

    public Integer getOffer() {
        return this.offer;
    }

    public void setOffer(Integer offer) {
        this.offer = offer;
    }

    public double getPurchase_price() {
        return this.purchase_price;
    }

    public void setPurchase_price(double purchase_price) {
        this.purchase_price = purchase_price;
    }

    public boolean isPP_() {
        return this.PP_;
    }

    public boolean getPP_() {
        return this.PP_;
    }

    public void setPP_(boolean PP_) {
        this.PP_ = PP_;
    }

    @Override
    public String toString() {
        return "{" + " product_id='" + getProduct_id() + "'" + ", product_name='" + getProduct_name() + "'"
                + ", stock='" + getStock() + "'" + ", load_capacity='" + getLoad_capacity() + "'" + ", demand='"
                + getDemand() + "'" + ", sales_price='" + getSales_price() + "'" + ", SP_='" + isSP_() + "'"
                + ", offer='" + getOffer() + "'" + ", purchase_price='" + getPurchase_price() + "'" + ", PP_='"
                + isPP_() + "'" + "}";
    }

}
