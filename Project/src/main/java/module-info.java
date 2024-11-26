module com.example.project2 {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires java.sql;
    requires java.desktop;

    opens com.example.project2 to javafx.fxml;
    exports com.example.project2;
    exports com.example.project2.CONTROLLERS;
    opens com.example.project2.CONTROLLERS to javafx.fxml;

}