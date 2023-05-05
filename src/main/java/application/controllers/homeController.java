package application.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import application.controllers.manageController;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class homeController {
    @FXML
    private Button loginBtn;

    @FXML
    private TextField txtFullName;

    @FXML
    void btnLoginClick(ActionEvent event) {

    }

    @FXML
    private void handleManageButton(ActionEvent event) {
        try {
            Parent manageRoot = FXMLLoader.load(getClass().getResource("/views/manage.fxml"));
            Scene manageScene = new Scene(manageRoot, 600, 400);
            Stage primaryStage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            primaryStage.setScene(manageScene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}