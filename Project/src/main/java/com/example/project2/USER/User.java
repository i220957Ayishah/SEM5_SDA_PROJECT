package com.example.project2.USER;
import java.time.LocalDate;
import java.util.Date;

public class User {
    private String username;
    private int userID;
    private String password;
    private String fName;
    private String lName;
    private String email;
    private LocalDate dob;
    private String phone;
    private String address;
    private String gender;
    private String type;

    public User(String username, int userID, String password, String fName, String lName, String email,
                LocalDate dob, String phone, String address, String gender, String type) {
        this.username = username;
        this.userID = userID;
        this.password = password;
        this.fName = fName;
        this.lName = lName;
        this.email = email;
        this.dob = dob;
        this.phone = phone;
        this.address = address;
        this.gender = gender;
        this.type = type;
    }


    // Getters and setters for each attribute
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public int getUserID() {
        return userID;
    }
    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
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

    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }

    public void displayUser() {
        System.out.println("...............................\n");
        System.out.println("UserID: " + getUserID());
        System.out.println("Username: " + getUsername());
        System.out.println("Name: " + getFName() + " " + getLName());
        System.out.println("Email: " + getEmail());
        System.out.println("DOB: " + getDob());
        System.out.println("Phone: " + getPhone());
        System.out.println("Address: " + getAddress());
        System.out.println("Gender: " + getGender());
        System.out.println("Type: " + getType());
        System.out.println("\n...............................\n");
    }
}
