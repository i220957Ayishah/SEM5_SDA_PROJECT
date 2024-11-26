package com.example.project2.DRIVER;

import com.example.project2.Storage.DataBase;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DriverManagement {
    private static DataBase DB = new DataBase();
    private static List<Driver> driverList = new ArrayList<>();

    public DriverManagement(DataBase db) {
        DB = db;
        driverList = db.readDriversList();
    }
    // Method to add a driver
    public static void addDriver(String fName, String lName, String email, LocalDate dob,
                                 String phone, String address, String gender) {
        int driverID = driverList.getLast().getDriverID()+1;
        Driver driver = new Driver(driverID, fName, lName, email, dob, phone, address, gender);
        driverList.add(driver);
        DB.addDriver(driver);
        System.out.println("Driver added successfully: " + driver.getFName() + " " + driver.getLName());
    }

    // Method to delete a driver by driverID
    public boolean deleteDriver(int driverID) {
        for (Driver driver : driverList) {
            if (driver.getDriverID() == driverID) {
                driverList.remove(driver);
                DB.deleteDriver(driverID);
                System.out.println("Driver deleted successfully: " + driver.getFName() + " " + driver.getLName());
                return true;
            }
        }
        System.out.println("Driver with ID " + driverID + " not found.");
        return false;
    }

    // Method to find a driver by driverID
    public static Driver searchDriverbyID(int driverID) {
        for (Driver driver : driverList) {
            if (driver.getDriverID() == driverID) {
                System.out.println("Driver found: " + driver.getFName() + " " + driver.getLName());
                return driver;
            }
        }
        System.out.println("Driver with ID " + driverID + " not found.");
        return null;
    }

    public Driver findDriverByID(int driverID) {
        for (Driver driver : driverList) {
            if (driver.getDriverID() == driverID) {
                System.out.println("Driver found: " + driver.getFName() + " " + driver.getLName());
                return driver;
            }
        }
        System.out.println("Driver with ID " + driverID + " not found.");
        return null;
    }

    public static Driver searchDriverbyName(String driverName) {
        for (Driver driver : driverList) {
            if (driver.getFName().equals(driverName)) {
                System.out.println("Driver found: " + driver.getFName() + " " + driver.getLName());
                return driver;
            }
        }
        System.out.println("Driver with name " + driverName + " not found.");
        return null;
    }

    public ArrayList<Driver> getDriversByAge(int age) {
        ArrayList<Driver> drivers = new ArrayList<>();
        Date date = new Date();
        for (Driver driver : driverList) {
            if (driver.getAge() == age) {
                drivers.add(driver);
            }
        }
        return drivers;
    }

    public ArrayList<Driver> getDriversByGender(String gender) {
        ArrayList<Driver> drivers = new ArrayList<>();
        for (Driver driver : driverList) {
            if (driver.getGender().equals(gender)) {
                drivers.add(driver);
            }
        }
        return drivers;
    }

    // Method to update a driver by driverID
    public static boolean updateDriver(int driverID, String fName, String lName, String email, LocalDate dob,
                                       String phone, String address, String gender) {
        Driver driver = searchDriverbyID(driverID);
        if (driver != null) {
            driver.setFName(fName);
            driver.setLName(lName);
            driver.setEmail(email);
            driver.setDob(dob);
            driver.setPhone(phone);
            driver.setAddress(address);
            driver.setGender(gender);
            System.out.println("Driver updated successfully: " + driver.getFName() + " " + driver.getLName());
            DB.updateDriver(driver);
            return true;
        } else {
            System.out.println("Update failed. Driver with ID " + driverID + " not found.");
            return false;
        }
    }

    public ArrayList<Driver> getDriverList() {
        return DB.readDriversList();
    }
}
