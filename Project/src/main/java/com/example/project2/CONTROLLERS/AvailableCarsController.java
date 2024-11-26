package com.example.project2.CONTROLLERS;

//import com.example.project2.CONTROLLERS.MainController;
import com.example.project2.CAR.Car;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import java.util.List;
import com.example.project2.CAR.CarManagement;
import com.example.project2.CAR.Car;
import javafx.scene.control.Label;

import javax.swing.*;

public class AvailableCarsController {
    private MainController mainController;
    public void setMainController(MainController mainController) {this.mainController = mainController;}
    //public void setCarManagement(CarManagement carManagement) {this.carManagement = carManagement;}


    @FXML
    private ComboBox<Integer> idComboBox;
    @FXML
    private Label nameLabel;
    @FXML
    private Label modelLabel;
    @FXML
    private Label colorLabel;
    @FXML
    private Label sunroofLabel;
    @FXML
    private Label chargesLabel;


    @FXML
    private void listAvailableCars()
    {
        List<Car> toAdd = mainController.CRM.getCarManagement().getCarsByAvailability(true);
        mainController.CRM.getCarManagement();
        idComboBox.getItems().clear();
        for (Car car : toAdd) {
            idComboBox.getItems().addAll(car.getCarID());
        }
    }

    @FXML
    private void carDetails()
    {
        Car toShow = mainController.CRM.getCarManagement().searchCarByID(idComboBox.getValue());
        nameLabel.setText(toShow.getName());
        modelLabel.setText(toShow.getModel());
        colorLabel.setText(toShow.getColor());
        if(toShow.isSunroof())
            sunroofLabel.setText("Yes");
        else
            sunroofLabel.setText("No");
        chargesLabel.setText(String.valueOf(toShow.getChargesPerDay()));
    }
    @FXML
    private void showCustomerView(){mainController.showCustomer();}
}
