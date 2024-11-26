package com.example.project2.CONTROLLERS;
import java.io.IOException;
import java.lang.reflect.Array;
import java.time.Period;
import java.util.ArrayList;
import java.util.Date;
import java.time.ZoneId;

import com.example.project2.BOOKING.Booking;
import com.example.project2.CAR.Car;
import com.example.project2.CONTROLLERS.MainController;
import com.example.project2.DRIVER.Driver;
import com.example.project2.PAYMENT.Payment;
import com.example.project2.USER.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.time.LocalDate;
import java.util.List;
import javafx.stage.Popup;

public class CustomerViewController {
    private MainController mainController;
    public void setMainController(MainController mainController) {this.mainController = mainController;}
    private int currentID;
    private User currentUser;
    @FXML
    private TextField usrField;
    @FXML
    private PasswordField psrField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField fnameField;
    @FXML
    private TextField lnameField;
    @FXML
    private TextField pnumField,bbCarID;
    @FXML
    private TextField addrField;
    @FXML
    private ComboBox<String> genderBox;
    @FXML
    private DatePicker dobField;
    @FXML
    TabPane tabPane;
    @FXML
    Tab paymentTab;

    // view bookings by customer parameters
    @FXML
    private Label bCarID;
    @FXML
    private Label bDriverID;
    @FXML
    private Label bCharges;
    @FXML
    private Label bPayed;
    @FXML
    private ComboBox<Integer> bID;
    @FXML
    private Label bIssue;
    @FXML
    private Label bReturn;
    @FXML
    private DatePicker bbIssue;
    @FXML
    private DatePicker bbReturn;

    // payment
    @FXML
    private TextField pBID;
    @FXML
    private Label pCharges;
    @FXML
    private ComboBox<String> pMode;
    double charges;




    // add bookings by customer parameters

    // Driver selection
    @FXML
    private ComboBox<String> bbDriver;
    @FXML
    private Label bbCharges;

    // update booking parameters

    @FXML
    private TextField ubID;
    @FXML
    private DatePicker ubReturn;

    // complete payment after returning car parameters



    // view and update customer profile parameters




    @FXML
    public void initialize() {
        pMode.getItems().addAll("Credit Card","Debit Card","PayPal");
    }

    @FXML
    private void showAvailableCars() {
        mainController.showAvailableCars();
    }

    @FXML
    private void switchToPayment() {
        if(bPayed.getText() == "Paid") {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Payment");
            alert.setHeaderText(null);
            alert.setContentText("Booking is already paid.");
            alert.showAndWait();
        }
        else {
            tabPane.getSelectionModel().select(paymentTab);
        }
    }


//........................................ HOMEPAGE ..............................................

    @FXML
    private void showLogIn() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Logout ");
        alert.setHeaderText(null);
        alert.setContentText("Proceed to login page?");
        alert.showAndWait();
        if(alert.getResult() == ButtonType.OK){
            mainController.showLogIn();
        } else if(alert.getResult() == ButtonType.CANCEL){

        }
    }

//........................................ BOOKINGS ..............................................

    // VIEW BOOKINGS
    @FXML
    private void listBookings(){
        List<Booking> toAdd = mainController.CRM.getBookingManagement().getBookingListByID(currentID);
        bID.getItems().clear();
        for (Booking booking : toAdd) {
            bID.getItems().addAll(booking.getBookingID());
        }

    }
    @FXML
    private void listBookingDetails() {
        bCarID.setText("");
        bDriverID.setText("");
        bCharges.setText("");
        bIssue.setText("");
        bReturn.setText("");
        bPayed.setText("");
        Booking toShow = mainController.CRM.searchBookingbyID(bID.getValue());
        bCarID.setText(String.valueOf(toShow.getCar().getCarID()));
        bIssue.setText(toShow.getIssueDate().toString());
        bReturn.setText(toShow.getReturnDate().toString());
        if(toShow.getDriver() == null) {
            bDriverID.setText("None");
        }
        else {
            bDriverID.setText(String.valueOf(toShow.getDriver().getDriverID()));
        }
        bCharges.setText(String.valueOf(toShow.getTotalCharges()));
        if(toShow.isPaymentPending())
            bPayed.setText("UnPaid");
        else
            bPayed.setText("Paid");
    }


    // DRIVER DETAILS AND IDS
    @FXML
    private void driverChoice(){
        bbDriver.getItems().addAll("Yes","No");
    }
//    @FXML
//    private void listDrivers() {
//        boolean flag = false;
//
//        List<Driver> drivers = mainController.CRM.getDriverManagement().getDriverList();
//        List<Driver> toAdd = new ArrayList<Driver>();
//        //bbDriverAge.getItems().clear();
//        for(Driver driver : drivers) {
//            for (Booking booking : mainController.CRM.getBookingManagement().getBookingList()) {
//                if (booking.getDriver() != null && booking.getDriver().getDriverID() == driver.getDriverID() && booking.isPaymentPending()) {
//                    flag = true;
//                }
//            }
//            if (!flag) {
//                bbDriverID.getItems().addAll(driver.getDriverID());
//            }
//        }
//    }
    // ADD BOOKING
    @FXML
    private void addBooking(){
        Car car = mainController.CRM.getCarManagement().searchCarByID(Integer.parseInt(bbCarID.getText()));
        Driver d = null;
        boolean flag = false;
        if(bbDriver.getValue().isEmpty() || bbCarID.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Incomplete Information");
            alert.setHeaderText(null);
            alert.setContentText("Please enter all required information");
            alert.showAndWait();
        }
        if(bbDriver.getValue().equals("Yes")) {
            List<Driver> drivers = mainController.CRM.getDriverManagement().getDriverList();
            for(Driver driver : drivers) {
                flag = false;
                for (Booking booking : mainController.CRM.getBookingManagement().getBookingList()) {
                    if (booking.getDriver() != null && booking.getDriver().getDriverID() == driver.getDriverID() && booking.isPaymentPending()) {
                        flag = true;
                    }
                }
                if (!flag) {
                    d = driver;
                    break;
                }
            }

        }

        User user = mainController.CRM.searchUserbyID(currentID);
        Booking booking = new Booking(0, car, user, d, null, 0.0, bbIssue.getValue(), bbReturn.getValue(), null, true);
        Period period = Period.between(bbIssue.getValue(), bbReturn.getValue());
        int days = period.getDays();
        double charges = booking.calculateCharges(days);
        //Payment payment = new Payment(1, "", 0.0);
        mainController.CRM.addBooking(car, user, d, null, charges, bbIssue.getValue(), bbReturn.getValue(), null, true);
        bbCharges.setText(String.valueOf(charges));
    }
    @FXML
    private void calculateChargesPrompt(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Charges");
        alert.setHeaderText(null);
        alert.setContentText("The total fee is calculated on the basis of charges per day of the car.\nThese are pre-set according to its make and model.\n-> An additional fee will be charged per day if the car is returned after the respective due date.");
        alert.showAndWait();
    }

    // UPDATE BOOKING

    @FXML
    private void updateBooking(){
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        if(ubID.getText().isEmpty()){
            errorAlert.setHeaderText(null);
            errorAlert.setTitle("Incomplete Information.");
            errorAlert.setContentText("Please enter a valid booking ID.");
            errorAlert.showAndWait();
        }
        else{
            for(User user : mainController.CRM.getUserManagement().getUserList()){
                if(user.getUserID() == currentID){
                    currentUser = user;
                    break;
                }
            }
            ArrayList<Booking> bookings = mainController.CRM.getBookingManagement().getBookingListByID(currentID);
            boolean flag = false;
            Booking currentBooking = null;
            for(Booking booking : bookings) {
                flag = false;
                if(booking.getBookingID() == Integer.parseInt(ubID.getText())) {
                    flag = true;
                    currentBooking = booking;
                    break;
                }
            }
            LocalDate returnDate = ubReturn.getValue();
            if(flag && Period.between(currentBooking.getReturnDate(), returnDate).getDays() < 0 ) {
                errorAlert.setTitle("Return Date.");
                errorAlert.setContentText("Update return date should be greater than current return date.");
            }
            else if(!flag){
                errorAlert.setTitle("Incorrect ID.");
                errorAlert.setContentText("This BookingID does not exist for this user.\nPlease try again.");
            }
            else
                mainController.CRM.updateBooking(Integer.parseInt(ubID.getText()), ubReturn.getValue());
            errorAlert.showAndWait();
        }
    }

    // RETURN CAR AND COMPLETE PAYMENT

    @FXML
    private void setTotalCharges() {
        pCharges.setText(String.valueOf(mainController.CRM.calculateTotal(Integer.parseInt(pBID.getText()))));
    }

    @FXML
    private void returnCar(){
        Booking currentBooking = null;
        boolean flag = false;
        for(Booking booking: mainController.CRM.getBookingManagement().getBookingListByID(currentID)){
            if(booking.getBookingID() == Integer.parseInt(pBID.getText())){
                flag = true;
                currentBooking = booking;
                break;
            }
        }
        if(flag && !currentBooking.isPaymentPending()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Payment");
            alert.setHeaderText(null);
            alert.setContentText("Booking is already paid.");
            alert.showAndWait();
        } else if (flag && currentBooking.isPaymentPending()) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Payment confirmation: ");
            alert.setHeaderText("");
            alert.setContentText("Are you sure you want to proceed with this payment?");
            alert.showAndWait();
            if(alert.getResult() == ButtonType.OK){
                mainController.CRM.payCharges(Integer.parseInt(pBID.getText()), pMode.getValue());
            } else if(alert.getResult() == ButtonType.CANCEL){
                Stage stage = (Stage) tabPane.getScene().getWindow();
                Label label = new Label("Payment Cancelled.");
                Popup popup = new Popup();
                label.setStyle(" -fx-background-color: grey; -fx-padding: 10px; -fx-font-size: 16px;");
                popup.getContent().add(label);
                /*label.setMinWidth(80);
                label.setMinHeight(50);*/
                popup.setX(900);
                popup.setY(500);
                popup.setAutoHide(true);
                popup.setHideOnEscape(true);
                if (!popup.isShowing())
                    popup.show(stage);
                else
                    popup.hide();
            }
        }

    }

//........................................ PROFILE ..............................................

    // VIEW CUSTOMER PROFILE

    public void customerProfile(String uname){
        this.usrField.setText(uname);
        User toShow = mainController.CRM.searchUserbyUserName(uname);
        this.psrField.setText(toShow.getPassword());
        this.fnameField.setText(toShow.getFName());
        this.lnameField.setText(toShow.getLName());
        this.emailField.setText(toShow.getEmail());
        this.dobField.setValue(toShow.getDob());
        this.genderBox.setValue(toShow.getGender());
        this.addrField.setText(toShow.getAddress());
        this.pnumField.setText(toShow.getPhone());
        this.currentID = toShow.getUserID();
    }

    // UPDATE CUSTOMER PROFILE

    @FXML
    private void updateCustomer() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Update Customer");
        alert.setHeaderText(null);
        alert.setContentText("This information will be updated.");
        alert.showAndWait();
        if(alert.getResult() == ButtonType.OK){
            User toShow = mainController.CRM.searchUserbyID(currentID);
            mainController.CRM.updateUser(currentID,usrField.getText(),psrField.getText(),fnameField.getText(),lnameField.getText(),emailField.getText(),dobField.getValue(),pnumField.getText(),addrField.getText(),genderBox.getValue(),"customer");
        }
    }


}
