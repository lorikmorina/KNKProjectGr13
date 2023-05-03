module com.example.knkprojectgr13 {
    requires javafx.controls;
    requires javafx.fxml;



    exports application;
    opens application to javafx.fxml;
    exports application.controllers;
    opens application.controllers to javafx.fxml;
}