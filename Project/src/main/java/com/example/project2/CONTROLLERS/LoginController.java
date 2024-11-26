package com.example.project2.CONTROLLERS;

import com.example.project2.CONTROLLERS.MainController;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {
    private MainController mainController;
    public void setMainController(MainController mainController) {this.mainController = mainController;}
    private String uname, pass,type;
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;

    private int loginAttempts;

    @FXML
    private void showRegister() {
        mainController.showRegister();
    }

    @FXML
    private void showCustomer() {
        mainController.showCustomer();
    }
    @FXML
    private void setUname() {uname = username.getText();
        System.out.println(uname);}
    @FXML
    private void setPass() {pass = password.getText();
        sendUserDetails();
        System.out.println(pass);}

    @FXML
    private void login() {
       if(mainController.CRM.Authenticate(username.getText(),password.getText())){
           if(mainController.CRM.UserType(username.getText())) {
               type = "admin";
               showAdmin();
           }
           else {
               type = "customer";
               showCustomer();
           }
           sendUserDetails();
           username.setText("");
           password.setText("");
       }
       else{
           Alert errorAlert = new Alert(Alert.AlertType.ERROR);
           errorAlert.setHeaderText(null);
           errorAlert.setTitle("Incorrect username or password.");
           errorAlert.setContentText("Please try logging in again or register.");
           errorAlert.showAndWait();
           loginAttempts++;
           //Notifications.create() .title("Title Text") .text("Hello World 0!") .showWarning();
       }
    }
    private void sendUserDetails() {
        if(type == "admin") {
            mainController.sendAdminInfo(uname);
        }
        else if(type == "customer") {
            mainController.sendCustomerInfo(uname);
        }

    }

    @FXML
    private void showAdmin(){
        mainController.showAdmin();
    }

}
