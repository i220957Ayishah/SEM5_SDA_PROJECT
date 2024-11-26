package com.example.project2.USER;

import com.example.project2.Storage.DataBase;
import java.time.LocalDate;
import java.util.ArrayList;

public class UserManagement {
    private static DataBase DB = new DataBase();
    private static ArrayList<User> userList = new ArrayList<>();

    public UserManagement(DataBase db) {
        DB = db;
        userList = db.readUserList();
    }

    public static void addUser(String username, String password, String fName, String lName, String email,
                               LocalDate dob, String phone, String address, String gender, String type) {
        int userID = userList.getLast().getUserID()+1;
        if(type == "customer"){
            User customer = new Customer(username, userID, password, fName, lName, email, dob, phone, address, gender, type);
            userList.add(customer);
            DB.addUser(customer);
            System.out.println("User added successfully: " + customer.getUsername());
        }
        else if(type == "admin"){
            User admin = new Admin(username, userID, password, fName, lName, email, dob, phone, address, gender, type);
            userList.add(admin);
            DB.addUser(admin);
            System.out.println("User added successfully: " + admin.getUsername());
        }

    }

    public boolean deleteUser(int userID) {
        for (User user : userList) {
            if (user.getUserID() == userID) {
                userList.remove(user);
                DB.deleteUser(userID);
                System.out.println("User deleted successfully: " + user.getUsername());
                return true;
            }
        }
        System.out.println("User with ID " + userID + " not found.");
        return false;
    }

    public static User searchUserbyID(int userID) {
        for (User user : userList) {
            if (user.getUserID() == userID) {
                System.out.println("User found: " + user.getUsername());
                return user;
            }
        }
        System.out.println("User with ID " + userID + " not found.");
        return null;
    }

    public User findUserByID(int userID) {
        for (User user : userList) {
            if (user.getUserID() == userID) {
                System.out.println("User found: " + user.getUsername());
                return user;
            }
        }
        System.out.println("User with ID " + userID + " not found.");
        return null;
    }

    public User searchUserbyUsername(String username) {
        for (User user : userList) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        System.out.println("User with name " + username + " not found.");
        return null;
    }

    public static void updateUser(int id, String username, String password, String fname, String lname, String email, LocalDate dob, String phone, String address, String gender, String type) {
        User user = searchUserbyID(id);
        if (user != null) {
            user.setUsername(username);
            user.setPassword(password);
            user.setFName(fname);
            user.setLName(lname);
            user.setEmail(email);
            user.setDob(dob);
            user.setPhone(phone);
            user.setAddress(address);
            user.setGender(gender);
            user.setType(type);
            System.out.println("User updated successfully: " + user.getUsername());
            DB.updateUser(user);
        } else {
            System.out.println("Update failed. User with ID " + id + " not found.");
        }
    }

    public ArrayList<User> getUserList() { return DB.readUserList(); }


}
