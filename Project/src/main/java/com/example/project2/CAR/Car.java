package com.example.project2.CAR;

public class Car {
    private int carID;
    private String name;
    private String model;
    private String color;
    private boolean available;
    private double chargesPerDay;
    private boolean sunroof;

    // Constructor
    public Car(int id, String name, String model, String color, boolean available, double chargesPerDay, boolean sunroof) {
        this.carID = id;
        this.name = name;
        this.model = model;
        this.color = color;
        this.available = available;
        this.chargesPerDay = chargesPerDay;
        this.sunroof = sunroof;
    }

    // Getters and setters for each attribute
    public int getCarID() {
        return carID;
    }
    public void setCarID(int carID) {
        this.carID = carID;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getModel() {
        return model;
    }
    public void setModel(String model) {
        this.model = model;
    }

    public String getColor() {
        return color;
    }
    public void setColor(String color) {
        this.color = color;
    }

    public boolean isAvailable() {
        return available;
    }
    public void setAvailable(boolean available) {
        this.available = available;
    }

    public double getChargesPerDay() {
        return chargesPerDay;
    }
    public void setChargesPerDay(double chargesPerDay) {
        this.chargesPerDay = chargesPerDay;
    }

    public boolean isSunroof() {
        return sunroof;
    }
    public void setSunroof(boolean sunroof) {
        this.sunroof = sunroof;
    }

    public void displayCar() {
        System.out.println("...............................\n");
        System.out.println("CarID: " + carID);
        System.out.println("Name: " + name);
        System.out.println("Model: " + model);
        System.out.println("Color: " + color);
        System.out.println("Available: " + available);
        System.out.println("Charges per day: " + chargesPerDay);
        System.out.println("Sunroof: " + sunroof);
        System.out.println("\n...............................\n");
    }
}

