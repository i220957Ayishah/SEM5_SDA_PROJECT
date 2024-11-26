package com.example.project2;

import com.example.project2.BOOKING.Booking;
import com.example.project2.BOOKING.BookingManagement;
import com.example.project2.CAR.Car;
import com.example.project2.CAR.CarManagement;
import com.example.project2.DRIVER.Driver;
import com.example.project2.DRIVER.DriverManagement;
import com.example.project2.PAYMENT.Payment;
import com.example.project2.Storage.DataBase;
import com.example.project2.USER.User;
import com.example.project2.USER.UserManagement;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;


public class CarRentalManagement {
    UserManagement userManager;
    CarManagement carManager;
    DriverManagement driverManager;
    BookingManagement bookingManager;

    DataBase dataBase;

    public CarRentalManagement(){
        dataBase = new DataBase();
        userManager = new UserManagement(dataBase);
        carManager = new CarManagement(dataBase);
        driverManager = new DriverManagement(dataBase);
        bookingManager = new BookingManagement(dataBase);
    }

    public CarRentalManagement(DataBase dataBase){
        this.dataBase = dataBase;
        userManager = new UserManagement(dataBase);
        carManager = new CarManagement(dataBase);
        driverManager = new DriverManagement(dataBase);
        bookingManager = new BookingManagement(dataBase);
    }

    public void addUser(String username, String password, String fName, String lName, String email,
                        LocalDate dob, String phone, String address, String gender, String type){
        userManager.addUser(username, password, fName, lName, email, dob, phone, address, gender, type);
    }
    public void updateUser(int id, String username, String password, String fName, String lName, String email,
                           LocalDate dob, String phone, String address, String gender, String type){
        userManager.updateUser(id, username, password, fName, lName, email, dob, phone, address, gender, type);
    }
    public void deleteUser(int id){
        boolean flag = false;
        for(Booking booking: bookingManager.getBookingList()){
            if(booking.getUser().getUserID() == id){
                flag = true;
            }
        }
        if(!flag){
            userManager.deleteUser(id);
        }
    }
    public User searchUserbyID(int id){
        return userManager.searchUserbyID(id);
    }
    public User searchUserbyUserName(String name){return userManager.searchUserbyUsername(name);}



    public void addCar(String name, String model, String color, boolean available, double chargesPerDay, boolean sunroof){
        carManager.addCar(name, model, color, available, chargesPerDay, sunroof);
    }
    public void updateCar(int carID, String name, String model, String color, boolean available, double chargesPerDay, boolean sunroof){
        carManager.updateCar(carID, name, model, color, available, chargesPerDay, sunroof);
    }
    public void deleteCar(int id){
        carManager.deleteCar(id);
    }
    public Car searchCarbyID(int id){
        return carManager.searchCarByID(id);
    }



    public void addDriver(String fName, String lName, String email, LocalDate dob,
                          String phone, String address, String gender){
        driverManager.addDriver(fName, lName, email, dob, phone, address, gender);
    }
    public void updateDriver(int driverID, String fName, String lName, String email, LocalDate dob,
                             String phone, String address, String gender){
        driverManager.updateDriver(driverID, fName, lName, email, dob, phone, address, gender);
    }
    public void deleteDriver(int id){
        boolean flag = false;
        for(Booking booking: bookingManager.getBookingList()){
            if(booking.getDriver() != null && booking.getDriver().getDriverID() == id && booking.isPaymentPending()){
                flag = true;
                break;
            }
        }
        if(!flag){
            driverManager.deleteDriver(id);
        }
    }
    public Driver searchDriverbyID(int id){
        return driverManager.searchDriverbyID(id);
    }
    public ArrayList<Driver> getDriversByAge(int age) {
        return driverManager.getDriversByAge(age);
    }



    public void addBooking(Car car, User user, Driver driver, Payment payment, double totalCharges,
                           LocalDate issueDate, LocalDate returnDate, LocalDate customerReturnDate,
                           boolean paymentPending){
        bookingManager.addBooking(car, user, driver, payment, totalCharges, issueDate, returnDate, customerReturnDate, paymentPending);
    }
    public void updateBooking(int bookingID, Car car, User user, Driver driver, double totalCharges,
                              LocalDate issueDate, LocalDate returnDate, LocalDate customerReturnDate,
                              boolean paymentPending){
        bookingManager.updateBooking(bookingID, car, user, driver, totalCharges, issueDate, returnDate, customerReturnDate, paymentPending);
    }
    public void updateBooking(int bookingID, LocalDate newReturnDate){
        bookingManager.updateBooking(bookingID, newReturnDate);
    }
    public void deleteBooking(int id){
        bookingManager.deleteBooking(id);
    }
    public Booking searchBookingbyID(int id){
        return bookingManager.findBooking(id);
    }
    public ArrayList<Booking> getBookingListByID(int id){
        return bookingManager.getBookingListByID(id);
    }



    public double calculateTotal(int bookingID) {
        return bookingManager.calculateTotal(bookingID);
    }
    public void payCharges(int bookingID, String mode){
        bookingManager.payCharges(bookingID, mode);
    }



    public boolean Authenticate(String username, String password){
        User user = userManager.searchUserbyUsername(username);
        if(user != null && user.getPassword().equals(password)){
            return true;
        }
        return false;
    }
    public boolean UserType(String username){
        User user = userManager.searchUserbyUsername(username);
        if(Objects.equals(user.getType(), "admin")){
            return true;
        }
        return false;
    }



    public CarManagement getCarManagement(){
        return carManager;
    }
    public UserManagement getUserManagement(){
        return userManager;
    }
    public DriverManagement getDriverManagement(){
        return driverManager;
    }
    public BookingManagement getBookingManagement(){
        return bookingManager;
    }
    public DataBase getDataBase(){ return dataBase; }

}
