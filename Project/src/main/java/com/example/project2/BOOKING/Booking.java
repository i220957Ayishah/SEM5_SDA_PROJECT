package com.example.project2.BOOKING;

import com.example.project2.CAR.Car;
import com.example.project2.DRIVER.Driver;
import com.example.project2.PAYMENT.Payment;
import com.example.project2.Storage.DataBase;
import com.example.project2.USER.User;

import java.time.LocalDate;
import java.time.Period;

public class Booking {
    private int bookingID;
    private Car car;
    private User user;
    private Driver driver;
    private Payment payment;
    private double totalCharges;
    private LocalDate issueDate;
    private LocalDate returnDate;
    private LocalDate customerReturnDate;
    private boolean paymentPending;
    // Constructor
    public Booking(){
        paymentPending = false;
        driver = null;
        totalCharges = 0.0;
    }

    public Booking(int bookingID, Car car, User user, Driver driver, Payment payment, double totalCharges,
                   LocalDate issueDate, LocalDate returnDate, LocalDate customerReturnDate,
                   boolean paymentPending) {
        this.bookingID = bookingID;
        this.car = car;
        this.user = user;
        this.driver = driver;
        this.payment = payment;
        this.totalCharges = totalCharges;
        this.issueDate = issueDate;
        this.returnDate = returnDate;
        this.customerReturnDate = customerReturnDate;
        this.paymentPending = paymentPending;
    }

    // Getters and setters for each attribute
    public int getBookingID() {
        return bookingID;
    }
    public void setBookingID(int bookingID) {
        this.bookingID = bookingID;
    }

    public Car getCar() {
        return car;
    }
    public void setCar(Car car) {
        this.car = car;
    }

    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }

    public Driver getDriver() {
        return driver;
    }
    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public Payment getPayment() { return payment; }
    public void setPayment(Payment payment) { this.payment = payment; }

    public double getTotalCharges() {
        return totalCharges;
    }
    public void setTotalCharges(double totalCharges) {
        this.totalCharges = totalCharges;
    }

    public LocalDate getIssueDate() {
        return issueDate;
    }
    public void setIssueDate(LocalDate issueDate) {
        this.issueDate = issueDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }
    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    public LocalDate getCustomerReturnDate() {
        return customerReturnDate;
    }
    public void setCustomerReturnDate(LocalDate customerReturnDate) {
        this.customerReturnDate = customerReturnDate;
    }

    public boolean isPaymentPending() {
        return paymentPending;
    }
    public void setPaymentPending(boolean paymentPending) {
        this.paymentPending = paymentPending;
    }

    public void displayBooking() {
        System.out.println("...............................\n");
        System.out.println("Booking ID: " + bookingID);
        System.out.println("Car ID: " + car.getCarID());
        System.out.println("User ID: " + user.getUserID());
        if(driver != null){
            System.out.println("Driver ID: " + driver.getDriverID());
        }
        else{
            System.out.println("No driver");
        }
        System.out.println("Total charges: " + totalCharges);
        System.out.println("Issue Date: " + issueDate);
        System.out.println("Return Date: " + returnDate);
        System.out.println("Customer Return Date: " + customerReturnDate);
        System.out.println("Payment Pending: " + paymentPending);
        System.out.println("\n...............................\n");
    }

    public double calculateCharges(int days) {
        double total = car.getChargesPerDay();
        total *= days;
        totalCharges = total;
        return totalCharges;
    }

    public double calculateUpdatedCharges(int days){
        return totalCharges + (car.getChargesPerDay()*days);
    }

    public double calculateFine(int extraDays) {
        return car.getChargesPerDay()*extraDays*1.5;
    }

    public double calculateTotal(DataBase db){
        Period period = Period.between(this.issueDate, this.returnDate);
        customerReturnDate = LocalDate.now();
        int days = period.getDays();
        Period period1 = Period.between(returnDate, customerReturnDate);
        int extra = period1.getDays();
        double fee = 0.0;
        fee = calculateCharges(days);
        Payment payment1 = new Payment(0, "", 0.0);
        this.payment = payment1;
        if(customerReturnDate == returnDate) {
            payment.setAmount(fee);
        }
        else if(extra > 0){
            fee += calculateFine(extra);
            payment.setAmount(fee);
        }
        this.totalCharges = fee;
        return fee;
    }

    public void payCharges(DataBase db, String mode) {
        setPaymentPending(false);
        db.addPayment(payment);
        db.setPaymentStatus(bookingID, false);
        payment.setModeOfPayment(mode);
        db.updatePayment(payment);
        payment.displayPayment();
        car.setAvailable(true);
        db.setCarStatus(car.getCarID(), true);
        payment.setPaymentID(db.readPaymentList().getLast().getPaymentID()+1);
        returnDate = customerReturnDate;
        db.updateBooking(this);
    }
}
