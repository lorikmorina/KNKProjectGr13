package application.controllers;

import application.models.User;
import application.service.UserService;
import application.service.interfaces.UserServiceInterface;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class logInController {

    private UserServiceInterface userService;
//    ....


    public logInController() {
        System.out.println("Controller");
        this.userService = new UserService();
    }
    @FXML
    private Label welcomeText;
    @FXML
    private TextField txtEmail;
    @FXML
    private PasswordField txtPassword;
    @FXML
    private void btnLoginClick(ActionEvent event) {
        String email = this.txtEmail.getText();
        String password = this.txtPassword.getText();
        try{
            User user = this.userService.login(email, password);
            if(user == null){
                System.out.println("Username or password is incorrect!");
                return;
            }
            System.out.println("User is correct!");
//            Parent root = FXMLLoader.load(getClass().getResource("/views/home.fxml"));
//
//            Scene scene = new Scene(root);
//            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
//            stage.setScene(scene);
//            stage.show();


            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/home.fxml"));
            Parent root = loader.load();
            homeController homeController = loader.getController();
            // homeController.setUser(user);
            System.out.println("test");
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }catch (SQLException sqlException){
            System.out.println("Gabim ne baze te te dhenave!");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    @FXML
    void signupBtn(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/views/signup.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

}