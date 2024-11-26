package com.example.project2.USER;

import java.time.LocalDate;

public class Customer extends User {

    // Constructor
    public Customer(String username, int userID, String password, String fName, String lName, String email,
                    LocalDate dob, String phone, String address, String gender, String type) {
        super(username, userID, password, fName, lName, email, dob, phone, address, gender, type);
    }
}
