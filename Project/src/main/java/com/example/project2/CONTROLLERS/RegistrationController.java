package com.example.project2.CONTROLLERS;

import java.io.IOException;
import java.util.Date;

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

public class RegistrationController {

    private MainController mainController;
    public void setMainController(MainController mainController) { this.mainController = mainController; }
    private String uname,pass,fname,lname,email,pnum,adress,gender;
    private LocalDate dob;
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
    @FXML
    private DatePicker dobField;
    @FXML
    private void showLogin() {mainController.showLogIn();}
    @FXML
    private void setUsername(){uname = usrField.getText();}
    @FXML
    private void setPassword(){pass = psrField.getText();}
    @FXML
    private void setEmail(){email = emailField.getText();}
    @FXML
    private void setFname(){fname = fnameField.getText();}
    @FXML
    private void setLname(){lname = lnameField.getText();}
    @FXML
    private void setPnum(){pnum = pnumField.getText();}
    @FXML
    private void setAdress(){adress = addrField.getText();}
    @FXML
    private void setGender(){gender = genderBox.getValue();}
    @FXML
    private void setDob(){dob = dobField.getValue();}
    @FXML
    private void initialize(){genderBox.getItems().addAll("Male","Female");}
    @FXML
    private void getDetails(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        if(usrField.getText().equals("") || psrField.getText().equals("") || emailField.getText().equals("") || fnameField.getText().equals("")
        || lnameField.getText().equals("") || pnumField.getText().equals("") || addrField.getText().equals("") || genderBox.getValue().equals("")){
            alert.setTitle("Incomplete information");
            alert.setHeaderText(null);
            alert.setContentText("Please enter all required information.");
            alert.showAndWait();
        }
        mainController.CRM.addUser(uname,pass,fname,lname,email,dob,pnum,adress,gender,"customer");
        showLogin();
    }


}
