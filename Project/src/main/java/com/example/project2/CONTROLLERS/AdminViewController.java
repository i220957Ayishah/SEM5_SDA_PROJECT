package com.example.project2.CONTROLLERS;

import com.example.project2.BOOKING.Booking;
import com.example.project2.CAR.Car;
import com.example.project2.CONTROLLERS.MainController;
import com.example.project2.USER.User;
import javafx.fxml.FXML;
import com.example.project2.DRIVER.Driver;
import java.util.List;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.w3c.dom.Text;

import java.time.LocalDate;
import java.util.List;

public class AdminViewController {
    private MainController mainController;
    public void setMainController(MainController mainController) {this.mainController = mainController;}
    private int currentId;

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
    private TextField pnumField;
    @FXML
    private TextField addrField;
    @FXML
    private ComboBox<String> genderBox;

    // add another admin by admin parameters
    @FXML
    private DatePicker dobField;
    @FXML
    private TextField usernameField;
    @FXML
    private TextField passwordField;
    @FXML
    private TextField fNameField;
    @FXML
    private TextField lNameField;
    @FXML
    private TextField emailFieldd;
    @FXML
    private TextField phoneField;
    @FXML
    private TextField addressField;
    @FXML
    private ComboBox<String> gField;

    // Add driver parameters
    @FXML
    private TextField dAddress;
    @FXML
    private TextField dPhone;
    @FXML
    private TextField dfName;
    @FXML
    private TextField dlName;
    @FXML
    private TextField dEmail;
    @FXML
    private DatePicker dDOB;
    @FXML
    private ComboBox<String> dGender;

    // add customer parameters
    @FXML
    private TextField cUsername;
    @FXML
    private TextField cPassword;
    @FXML
    private TextField cEmail;
    @FXML
    private TextField cfName;
    @FXML
    private TextField clName;
    @FXML
    private DatePicker cDOB;
    @FXML
    private TextField cPhone;
    @FXML
    private TextField cAddress;
    @FXML
    private ComboBox<String> cGender;

    // add car parameters
    @FXML
    private TextField cName;
    @FXML
    private TextField cModel;
    @FXML
    private TextField cColor;
    @FXML
    private TextField cCharges;
    @FXML
    private ComboBox<String> cAvailable;
    @FXML
    private ComboBox<String> cSunroof;

    // view car details and delete car by admin parameters
    @FXML
    private ComboBox<Integer> ccCarID;
    @FXML
    private Label ccName;
    @FXML
    private Label ccModel;
    @FXML
    private Label ccColor;
    @FXML
    private Label ccAvailable;
    @FXML
    private Label ccSunroof;
    @FXML
    private Label ccCharges;

    // view customer details and delete customer by admin parameters
    @FXML
    private Label ccUsername;
    @FXML
    private Label ccGender;
    @FXML
    private Label ccAddress;
    @FXML
    private Label ccPhone;
    @FXML
    private Label ccDOB;
    @FXML
    private Label cclName;
    @FXML
    private Label ccfName;
    @FXML
    private Label ccEmail;
    @FXML
    private ComboBox<Integer> ccCustomerID;

    // view driver details and delete driver by admin parameters
    @FXML
    private ComboBox<Integer> ddID;
    @FXML
    private Label ddfName;
    @FXML
    private Label ddlName;
    @FXML
    private Label ddEmail;
    @FXML
    private Label ddDOB;
    @FXML
    private Label ddPhone;
    @FXML
    private Label ddAddress;
    @FXML
    private Label ddGender;

    // Update driver by admin parameters
    @FXML
    private TextField udfName;
    @FXML
    private TextField udlName;
    @FXML
    private TextField udEmail;
    @FXML
    private DatePicker udDOB;
    @FXML
    private TextField udPhone;
    @FXML
    private TextField udAddress;
    @FXML
    private ComboBox<Integer> udID;

    // Add and Delete Booking by admin parameters
    @FXML
    private ComboBox<Integer> bID;
    @FXML
    private Label bCarID;
    @FXML
    private Label bIssue;
    @FXML
    private Label bCharges;
    @FXML
    private Label bUserID;
    @FXML
    private Label bDriverID;
    @FXML
    private Label bPayed;
    @FXML
    private Label bReturn;

    @FXML
    public void initialize() {
        genderBox.getItems().addAll("Male", "Female");
        gField.getItems().addAll("Male", "Female");
        cGender.getItems().addAll("Male", "Female");
        dGender.getItems().addAll("Male", "Female");
        cSunroof.getItems().addAll("Yes", "No");
        cAvailable.getItems().addAll("Yes", "No");
    }

//........................................ HOMEPAGE ..............................................

    @FXML
    private void showLogIn(){
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

//.......................................... ADMIN ..............................................

    // UPDATE ADMIN PROFILE
    @FXML
    private void updateAdminDetails() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Update Admin");
        alert.setHeaderText(null);
        alert.setContentText("This information will be updated.");
        alert.showAndWait();
        if (alert.getResult() == ButtonType.OK) {
            User toShow = mainController.CRM.searchUserbyID(currentId);
            mainController.CRM.updateUser(currentId, usrField.getText(), psrField.getText(), fnameField.getText(), lnameField.getText(), emailField.getText(), dobField.getValue(), phoneField.getText(), addrField.getText(), genderBox.getValue(), "admin");
        }
    }

    // ADD A NEW ADMIN
    @FXML
    private void addAdmin(){
        mainController.CRM.addUser(usernameField.getText(),passwordField.getText(),fNameField.getText(),lNameField.getText(),emailFieldd.getText(),dobField.getValue(),phoneField.getText(),addressField.getText(),gField.getValue(),"admin");
    }

    // DISPLAY ADMIN PROFILE
    public void adminProfile(String uname){
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
        this.currentId = toShow.getUserID();
    }

//......................................... CUSTOMER ..............................................

    // ADD A NEW CUSTOMER IN ADMIN
    @FXML
    private void addCustomer() {
        mainController.CRM.addUser(cUsername.getText(),cPassword.getText(),cfName.getText(),clName.getText(),cEmail.getText(),cDOB.getValue(),cPhone.getText(),cAddress.getText(),cGender.getValue(),"customer");
    }

    // VIEW USER DETAILS AND DELETE USERS
    @FXML
    private void listUsers() {
        List<User> toAdd = mainController.CRM.getUserManagement().getUserList();
        ccCustomerID.getItems().clear();
        for (User user : toAdd) {
            ccCustomerID.getItems().addAll(user.getUserID());
        }
    }
    @FXML
    private void listUserDetails() {
        User toShow = mainController.CRM.getUserManagement().findUserByID(ccCustomerID.getValue());
        ccUsername.setText(toShow.getUsername());
        ccfName.setText(toShow.getFName());
        cclName.setText(toShow.getLName());
        ccEmail.setText(toShow.getEmail());
        ccPhone.setText(toShow.getPhone());
        ccAddress.setText(toShow.getAddress());
        ccGender.setText(toShow.getGender());
        ccDOB.setText(toShow.getDob().toString());
    }
    @FXML
    private void deleteUser(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete User");
        alert.setHeaderText(null);
        alert.setContentText("This user will be deleted.");
        alert.showAndWait();
        if(alert.getResult() == ButtonType.OK) {
            mainController.CRM.deleteUser(ccCustomerID.getValue());
            ccCustomerID.getItems().clear();
            ccDOB.setText("");
            ccPhone.setText("");
            ccAddress.setText("");
            ccGender.setText("");
            ccfName.setText("");
            cclName.setText("");
            ccEmail.setText("");
            ccUsername.setText("");
            listUsers();
        }
    }

//........................................... CARS ..............................................

    // ADD A NEW CAR IN ADMIN
    @FXML
    private void addCar() {
        String a = cAvailable.getValue();
        boolean availability;
        if(a == "Yes")
        { availability = true;}
        else{ availability = false;}
        boolean sRoof;
        String s = cSunroof.getValue();
        if(s == "Yes")
        { sRoof = true;}
        else{ sRoof = false;}
        String chargesText = cCharges.getText(); // Get the text from cCharges
        double charges = Double.parseDouble(chargesText); // Convert String to double
        System.out.println("Charges as a double: " + charges);


        mainController.CRM.addCar(cName.getText(), cModel.getText(), cColor.getText(), availability,charges,sRoof);
    }

    // VIEW CAR DETAILS AND DELETE CARS
    @FXML
    private void listAvailableCars() {
        List<Car> toAdd = mainController.CRM.getCarManagement().getCarList();
        ccCarID.getItems().clear();
        for (Car car : toAdd) {
            ccCarID.getItems().addAll(car.getCarID());
        }
    }
    @FXML
    private void carDetails() {
        Car toShow = mainController.CRM.getCarManagement().searchCarByID(ccCarID.getValue());
        ccName.setText(toShow.getName());
        ccModel.setText(toShow.getModel());
        ccColor.setText(toShow.getColor());
        if(toShow.isSunroof())
            ccSunroof.setText("Yes");
        else
            ccSunroof.setText("No");
        ccCharges.setText(String.valueOf(toShow.getChargesPerDay()));
        if(toShow.isAvailable())
            ccAvailable.setText("Yes");
        else
            ccAvailable.setText("No");
    }
    @FXML
    private void deleteCar() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Customer");
        alert.setHeaderText(null);
        alert.setContentText("This Car will be deleted.");
        alert.showAndWait();
        if(alert.getResult() == ButtonType.OK) {
            mainController.CRM.deleteCar(ccCarID.getValue());
            ccCarID.getItems().clear();
            ccAvailable.setText("");
            ccCharges.setText("");
            ccModel.setText("");
            ccColor.setText("");
            ccName.setText("");
            ccSunroof.setText("");
            listAvailableCars();
        }
    }


//.......................................... DRIVERS ..............................................

    // ADD NEW DRIVER IN ADMIN
    @FXML
    private void addDriver() {
        if(dfName.getText().isEmpty() || dlName.getText().isEmpty() || dEmail.getText().isEmpty() || dPhone.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Incomplete Information");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all the fields correctly.");
            alert.showAndWait();
        }
        mainController.CRM.addDriver(dfName.getText(), dlName.getText(), dEmail.getText(), dDOB.getValue(), dPhone.getText(), dAddress.getText(), dGender.getValue());
    }

    // VIEW DRIVER DETAILS AND DELETE DRIVER
    @FXML
    private void listDrivers() {
        List<Driver> toAdd = mainController.CRM.getDriverManagement().getDriverList();
        ddID.getItems().clear();
        udID.getItems().clear();
        for (Driver driver : toAdd) {
            ddID.getItems().addAll(driver.getDriverID());
            udID.getItems().addAll(driver.getDriverID());
        }
    }
    @FXML
    private void listDriverDetails() {
        Driver toShow = mainController.CRM.getDriverManagement().findDriverByID(ddID.getValue());
        ddfName.setText(toShow.getFName());
        ddlName.setText(toShow.getLName());
        ddEmail.setText(toShow.getEmail());
        ddDOB.setText(toShow.getDob().toString());
        ddPhone.setText(toShow.getPhone());
        ddAddress.setText(toShow.getAddress());
        ddGender.setText(toShow.getGender());
    }
    @FXML
    private void listDriverDetails1() {
        Driver toShow = mainController.CRM.getDriverManagement().findDriverByID(udID.getValue());
        udfName.setText(toShow.getFName());
        udlName.setText(toShow.getLName());
        udEmail.setText(toShow.getEmail());
        udDOB.setValue(toShow.getDob());
        udPhone.setText(toShow.getPhone());
        udAddress.setText(toShow.getAddress());
    }
    @FXML
    private void deleteDriver() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Driver");
        alert.setHeaderText(null);
        alert.setContentText("This driver will be deleted.");
        alert.showAndWait();
        if(alert.getResult() == ButtonType.OK) {
            mainController.CRM.deleteDriver(ddID.getValue());
            ddID.getItems().clear();
            ddPhone.setText("");
            ddAddress.setText("");
            ddGender.setText("");
            ddDOB.setText("");
            ddAddress.setText("");
            ddlName.setText("");
            ddfName.setText("");
            ddEmail.setText("");
            listDrivers();
        }
    }

    // UPDATE DRIVER DETAILS
    @FXML
    private void updateDriverDetails() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Update Driver");
        alert.setHeaderText(null);
        alert.setContentText("This information will be updated.");
        alert.showAndWait();
        if(alert.getResult() == ButtonType.OK) {
            Driver toShow = mainController.CRM.searchDriverbyID(udID.getValue());
            mainController.CRM.updateDriver(udID.getValue(), udfName.getText(), udlName.getText(), udEmail.getText(), udDOB.getValue(), udPhone.getText(), udAddress.getText(), toShow.getGender());
        }
    }

//.......................................... BOOKINGS ..............................................

    // VIEW BOOKING DETAILS AND DELETE BOOKING IN ADMIN
    @FXML
    private void listBookings() {
        List<Booking> toAdd = mainController.CRM.getBookingManagement().getBookingList();
        bID.getItems().clear();
        for (Booking booking : toAdd) {
            bID.getItems().addAll(booking.getBookingID());
        }
    }
    @FXML
    private void listBookingDetails() {
        bCarID.setText("");
        bUserID.setText("");
        bReturn.setText("");
        bIssue.setText("");
        bDriverID.setText("");
        bCharges.setText("");
        bPayed.setText("");
        Booking toShow = mainController.CRM.searchBookingbyID(bID.getValue());
        if(bID.getValue() != null) {
            bCarID.setText(String.valueOf(toShow.getCar().getCarID()));
            bUserID.setText(String.valueOf(toShow.getUser().getUserID()));
            bIssue.setText(toShow.getIssueDate().toString());
            bReturn.setText(toShow.getReturnDate().toString());
            if (toShow.getDriver() == null) {
                bDriverID.setText("None");
            } else {
                bDriverID.setText(String.valueOf(toShow.getDriver().getDriverID()));
            }
            bCharges.setText(String.valueOf(toShow.getTotalCharges()));
            if (toShow.isPaymentPending())
                bPayed.setText("UnPaid");
            else
                bPayed.setText("Paid");
        }
    }
    @FXML
    private void deleteBooking() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete  Booking");
        alert.setHeaderText(null);
        alert.setContentText("This booking record will be deleted.");
        alert.showAndWait();
        if(alert.getResult() == ButtonType.OK) {
            mainController.CRM.deleteBooking(bID.getValue());
            bID.getItems().clear();
            bCarID.setText("");
            bUserID.setText("");
            bIssue.setText("");
            bDriverID.setText("");
            bCharges.setText("");
            bPayed.setText("");
            bReturn.setText("");
            listBookings();
        }
    }



}