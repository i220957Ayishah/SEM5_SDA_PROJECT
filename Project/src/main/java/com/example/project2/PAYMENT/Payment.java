package com.example.project2.PAYMENT;

public class Payment {
    private int paymentID;
    private String modeOfPayment;
    private double amount;
    // Constructor
    public Payment(int id, String modeOfPayment, double amount) {
        this.paymentID = id;
        this.modeOfPayment = modeOfPayment;
        this.amount = amount;
    }

    // Getters and setters for each attribute
    public int getPaymentID() {
        return paymentID;
    }
    public void setPaymentID(int paymentID) {
        this.paymentID = paymentID;
    }

    public String getModeOfPayment() {
        return modeOfPayment;
    }
    public void setModeOfPayment(String modeOfPayment) {
        this.modeOfPayment = modeOfPayment;
    }

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }

    public void displayPayment() {
        System.out.println("\nPayment ID: " + paymentID);
        System.out.println("Mode of Payment: " + modeOfPayment);
        System.out.println("Amount: " + amount + "\n");
    }
}
