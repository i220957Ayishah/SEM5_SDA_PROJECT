package com.example.project2.CONTROLLERS;
import com.example.project2.CONTROLLERS.RegistrationController;
import com.example.project2.CarRentalManagement;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;

import java.io.IOException;

public class MainController {

    @FXML
    private StackPane stackPane;

    public CarRentalManagement CRM;
    
    private Parent logIn;
    private Parent register;
    private Parent customer;
    private Parent availableCars;
    private Parent admin;
    private CustomerViewController customerViewController;
    private AdminViewController adminViewController;



    @FXML
    public void initialize() {
        try {
            // Load Form1
            FXMLLoader logInLoader = new FXMLLoader(getClass().getResource("/com/example/project2/Login.fxml"));
            logIn = logInLoader.load();
            LoginController logInController = logInLoader.getController();
            logInController.setMainController(this);

            // Load Form2
            FXMLLoader registerLoader = new FXMLLoader(getClass().getResource("/com/example/project2/registration.fxml"));
            register = registerLoader.load();
            RegistrationController registerController = registerLoader.getController();
            registerController.setMainController(this);
            register.setVisible(false);

            // Load Form3
            FXMLLoader customerLoader = new FXMLLoader(getClass().getResource("/com/example/project2/CustomerView.fxml"));
            customer = customerLoader.load();
            CustomerViewController customerController = customerLoader.getController();
            customerController.setMainController(this);
            customer.setVisible(false);

            FXMLLoader availCarLoader = new FXMLLoader(getClass().getResource("/com/example/project2/AvailableCars.fxml"));
            availableCars = availCarLoader.load();
            AvailableCarsController availcarsController = availCarLoader.getController();
            availcarsController.setMainController(this);
            availableCars.setVisible(false);

            FXMLLoader adminLoader = new FXMLLoader(getClass().getResource("/com/example/project2/AdminView.fxml"));
            admin = adminLoader.load();
            AdminViewController adminViewController2 = adminLoader.getController();
            adminViewController2.setMainController(this);
            admin.setVisible(false);

            // Add forms to StackPane
            stackPane.getChildren().addAll(logIn, register, customer,availableCars,admin);

            // Initially show Form1
            logIn.toFront();

            customerViewController = customerController;
            adminViewController = adminViewController2;

            CRM = new CarRentalManagement();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void showLogIn() {
        logIn.toFront();  // Bring Form1 to the front
        logIn.setVisible(true);  // Ensure Form1 is visible
        register.setVisible(false);
        customer.setVisible(false);
        availableCars.setVisible(false);
        admin.setVisible(false);
    }

    public void showRegister() {
        register.toFront();
        logIn.setVisible(false);  // Ensure Form1 is visible
        register.setVisible(true);
        customer.setVisible(false);
        availableCars.setVisible(false);
        admin.setVisible(false);
    }

    public void showCustomer() {
        customer.toFront();
        logIn.setVisible(false);
        register.setVisible(false);
        customer.setVisible(true);
        availableCars.setVisible(false);
        admin.setVisible(false);
    }

    public void showAvailableCars() {
        availableCars.toFront();
        logIn.setVisible(false);
        register.setVisible(false);
        customer.setVisible(false);
        availableCars.setVisible(true);
        admin.setVisible(false);
    }

    public void showAdmin() {
        admin.toFront();
        logIn.setVisible(false);
        register.setVisible(false);
        customer.setVisible(false);
        availableCars.setVisible(false);
        admin.setVisible(true);
    }

    public void sendCustomerInfo(String username) {
        customerViewController.customerProfile(username);
    }

    public void sendAdminInfo(String username) {adminViewController.adminProfile(username);}


}

