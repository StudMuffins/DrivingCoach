package com.studmuffins.application;

/**
 * Created by Zoe on 21/05/2015.
 */
public class Car {
    private String name;
    private String transmission;
    private String fuel;

    public Car(){}

    public Car(String name, String transmission, String fuel) {
        super();
        this.name = name;
        this.transmission = transmission;
        this.fuel = fuel;

    }

    @Override
    public String toString() {
        return "Car [name=" + name + ", transmission=" + transmission + ", fuel=" + fuel + "]";
    }

    // Get methods
    public String getName() {return name;}
    public String getTransmission() {return transmission;}
    public String getFuel() {return fuel;}

    // Set methods
    public void setName(String name) {this.name = name;}
    public void setTransmission(String transmission) {this.transmission = transmission;}
    public void setFuel(String fuel) {this.fuel = fuel;}
}
