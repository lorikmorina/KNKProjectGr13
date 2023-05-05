package application.controllers;

import application.models.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import application.controllers.manageController;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class homeController {

    private User loggedInUser;

//    public homeController(User user) {
//        this.loggedInUser = user;
//    }

    @FXML
    private Button loginBtn;
    @FXML
    private Label nameLabel;
    @FXML
    private TextField txtFullName;

    @FXML
    void btnLoginClick(ActionEvent event) {

    }
    public void setUser(User user ) {
        this.loggedInUser = user;
        nameLabel.setText(loggedInUser.getFullName());
    }
    //    public void initialize() {
//        nameLabel.setText(loggedInUser.getFullName()+ "m");
//    }
    @FXML
    private void handleManageButton(ActionEvent event) {
        try {
            Parent manageRoot = FXMLLoader.load(getClass().getResource("/views/manage.fxml"));
            Scene manageScene = new Scene(manageRoot);
            Stage primaryStage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            primaryStage.setScene(manageScene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}