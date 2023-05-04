package application.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
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
            // Load the manage FXML file
            Parent manageRoot = FXMLLoader.load(getClass().getResource("/views/manage.fxml"));

            // Create a new scene with the loaded FXML file
            Scene manageScene = new Scene(manageRoot, 600, 400);

            // Get the primary stage from the button
            Stage primaryStage = (Stage) ((Node)event.getSource()).getScene().getWindow();

            // Set the new scene as the primary stage's scene
            primaryStage.setScene(manageScene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}