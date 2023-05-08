package application.controllers;

import application.models.Admin;
import application.models.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class homeController {


//    public homeController(User user) {
//        this.loggedInUser = user;
//    }
    private User loggedInUser;
    private Admin loggedInAdmin;

    @FXML
    private Button loginBtn, logoutBtn, profileBtn;
    @FXML
    private Label nameLabel;
    @FXML
    private TextField txtFullName;

    @FXML
    private Button profileButton;

    @FXML
    void btnLoginClick(ActionEvent event) {

    }
    public void setUser(User user ) {
        this.loggedInUser = user;
        nameLabel.setText(loggedInUser.getFullName());
    }

    public void setAdmin(Admin admin ) {
        this.loggedInAdmin = admin;
        nameLabel.setText(loggedInAdmin.getFullName());
    }
    //    public void initialize() {
//        nameLabel.setText(loggedInUser.getFullName()+ "m");
//    }
    @FXML
    private void handleManageButton(ActionEvent event) {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/manage.fxml"));
            Parent root = loader.load();
            if(loggedInUser != null) {
                manageController manageC = loader.getController();
                manageC.initialize(loggedInUser);
            }
            Scene manageScene = new Scene(root);
            Stage primaryStage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            primaryStage.setScene(manageScene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void logOutBtn(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/login.fxml"));
            Parent root = loader.load();
            Scene manageScene = new Scene(root);
            Stage primaryStage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            primaryStage.setScene(manageScene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleProfileButton(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/profile.fxml"));
            Parent root = loader.load();
            if(loggedInUser != null) {
                profileController profileC = loader.getController();
                profileC.setUser(loggedInUser);
            }
            Scene profileScene = new Scene(root);
            Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            primaryStage.setScene(profileScene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}