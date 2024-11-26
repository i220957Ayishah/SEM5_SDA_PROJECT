package com.example.project2.DRIVER;

import java.time.LocalDate;
import java.time.Period;
import java.util.Date;

public class Driver {
    private int driverID;
    private String fName;
    private String lName;
    private String email;
    private LocalDate dob;
    private String phone;
    private String address;
    private String gender;

    // Constructor
    public Driver(int driverID, String fName, String lName, String email, LocalDate dob,
                  String phone, String address, String gender) {
        this.driverID = driverID;
        this.fName = fName;
        this.lName = lName;
        this.email = email;
        this.dob = dob;
        this.phone = phone;
        this.address = address;
        this.gender = gender;
    }

    // Getters and setters for each attribute
    public int getDriverID() {
        return driverID;
    }
    public void setDriverID(int driverID) {
        this.driverID = driverID;
    }

    public String getFName() {
        return fName;
    }
    public void setFName(String fName) {
        this.fName = fName;
    }

    public String getLName() {
        return lName;
    }
    public void setLName(String lName) {
        this.lName = lName;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getDob() {
        return dob;
    }
    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }

    public String getGender() {
        return gender;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getAge() {
        Period period = Period.between(dob, LocalDate.now());
        return period.getYears();
    }

    public void displayDriver() {
        System.out.println("...............................\n");
        System.out.println("Driver ID: " + driverID);
        System.out.println("Name: " + fName + lName);
        System.out.println("Email: " + email);
        System.out.println("DOB: " + dob);
        System.out.println("Phone: " + phone);
        System.out.println("Address: " + address);
        System.out.println("Gender: " + gender);
        System.out.println("\n...............................\n");
    }
}

