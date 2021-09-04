package com.sporecomerce.spaceship;

public class Spaceship {
    
    private long ship_id;
    private String ship_name;
    private double ship_load;
    private double velocity;


    public Spaceship() {
    }

    public Spaceship(long ship_id, String ship_name, double ship_load, double velocity) {
        this.ship_id = ship_id;
        this.ship_name = ship_name;
        this.ship_load = ship_load;
        this.velocity = velocity;
    }

    public long getShip_id() {
        return this.ship_id;
    }

    public void setShip_id(long ship_id) {
        this.ship_id = ship_id;
    }

    public String getShip_name() {
        return this.ship_name;
    }

    public void setShip_name(String ship_name) {
        this.ship_name = ship_name;
    }

    public double getShip_load() {
        return this.ship_load;
    }

    public void setShip_load(double ship_load) {
        this.ship_load = ship_load;
    }

    public double getVelocity() {
        return this.velocity;
    }

    public void setVelocity(double velocity) {
        this.velocity = velocity;
    }

    @Override
    public String toString() {
        return "{" +
            " ship_id='" + getShip_id() + "'" +
            ", ship_name='" + getShip_name() + "'" +
            ", ship_load='" + getShip_load() + "'" +
            ", velocity='" + getVelocity() + "'" +
            "}";
    }

}
