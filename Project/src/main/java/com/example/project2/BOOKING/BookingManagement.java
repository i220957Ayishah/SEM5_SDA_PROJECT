package com.example.project2.BOOKING;
import com.example.project2.CAR.Car;
import com.example.project2.DRIVER.Driver;
import com.example.project2.PAYMENT.Payment;
import com.example.project2.Storage.DataBase;
import com.example.project2.USER.User;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;

public class BookingManagement {
    private static DataBase DB = new DataBase();
    private static ArrayList<Booking> bookingList = new ArrayList<>();

    public BookingManagement(DataBase dataBase) {
        DB = dataBase;
        bookingList = dataBase.readBookingsList();
    }
    // Method to add a booking
    public static void addBooking(Car car, User user, Driver driver, Payment payment, double totalCharges,
                                  LocalDate issueDate, LocalDate returnDate, LocalDate customerReturnDate,
                                  boolean paymentPending) {
        int bookingID = bookingList.getLast().getBookingID()+1;
        Booking booking = new Booking(bookingID, car, user, driver, payment, totalCharges, issueDate, returnDate, customerReturnDate, paymentPending);
        bookingList.add(booking);
        DB.addBooking(booking);
        System.out.println("Booking added successfully: Booking ID " + booking.getBookingID());
        booking.getCar().setAvailable(false);
        DB.setCarStatus(booking.getCar().getCarID(), false);
    }

    // Method to delete a booking by bookingID
    public static boolean deleteBooking(int bookingID) {
        for (Booking booking : bookingList) {
            if (booking.getBookingID() == bookingID) {
                bookingList.remove(booking);
                DB.deleteBooking(bookingID);
                DB.deletePayment(booking.getPayment().getPaymentID());
                System.out.println("Booking deleted successfully: Booking ID " + bookingID);
                booking.getCar().setAvailable(true);
                DB.setCarStatus(booking.getCar().getCarID(), true);
                return true;
            }
        }
        System.out.println("Booking with ID " + bookingID + " not found.");
        return false;
    }

    // Method to find a booking by bookingID
    public static Booking findBooking(int bookingID) {
        for (Booking booking : bookingList) {
            if (booking.getBookingID() == bookingID) {
                System.out.println("Booking found: Booking ID " + bookingID);
                return booking;
            }
        }
        System.out.println("Booking with ID " + bookingID + " not found.");
        return null;
    }

    // Method to update a booking by bookingID
    public static boolean updateBooking(int bookingID, Car carID, User userID, Driver driverID, double totalCharges,
                                        LocalDate issueDate, LocalDate returnDate, LocalDate customerReturnDate,
                                        boolean paymentPending) {
        Booking booking = findBooking(bookingID);
        if (booking != null) {
            booking.setCar(carID);
            booking.setUser(userID);
            booking.setDriver(driverID);
            booking.setTotalCharges(totalCharges);
            booking.setIssueDate(issueDate);
            booking.setReturnDate(returnDate);
            booking.setCustomerReturnDate(customerReturnDate);
            booking.setPaymentPending(paymentPending);
            System.out.println("Booking updated successfully: Booking ID " + bookingID);
            DB.updateBooking(booking);
            return true;
        } else {
            System.out.println("Update failed. Booking with ID " + bookingID + " not found.");
            return false;
        }
    }

    public static void updateBooking(int bookingID, LocalDate newReturnDate) {
        Booking booking = findBooking(bookingID);
        LocalDate returnDate = booking.getReturnDate();
        booking.setReturnDate(newReturnDate);
        Period period = Period.between(returnDate, newReturnDate);
        int days = period.getDays();
        booking.setTotalCharges(booking.calculateUpdatedCharges(days));
    }

    public ArrayList<Booking> getBookingList() {
        return bookingList;
    }
    public ArrayList<Booking> getBookingListByID(int id){
        ArrayList<Booking> newList = new ArrayList<>();
        for(Booking booking: bookingList){
            if(booking.getUser().getUserID() == id)
                newList.add(booking);
        }
        return newList;
    }

    public ArrayList<Booking> getUserBookingList(int userID) {
        ArrayList<Booking> bookings = new ArrayList<>();
        bookings = DB.readBookingsByID(userID);
        for (Booking booking : bookings) {
            if (booking.getUser().getUserID() == userID) {
                bookings.add(booking);
            }
        }
        return bookings;
    }

    public double calculateTotal(int bookingID) {
        double charges = 0.0;
        for (Booking booking : bookingList) {
            if (booking.getBookingID() == bookingID) {
                charges = booking.calculateTotal(DB);
                booking.setTotalCharges(charges);
            }
        }
        return charges;
    }
    public void payCharges(int bookingID, String mode){
        for (Booking booking : bookingList) {
            if (booking.getBookingID() == bookingID) {
                booking.payCharges(DB, mode);
                booking.setPaymentPending(false);
                System.out.println("Payment charges successfully: Booking ID " + bookingID);
            }
        }
    }
}
