package application.controllers;

import application.models.User;
import application.service.UserService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class signUpController {
    @FXML
    private Button loginBtn;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtFullName;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private TextField txtPersonalNr;

    @FXML
    private void signUpBtn(ActionEvent event) throws IOException {
        String fullName = this.txtFullName.getText();
        String email = this.txtEmail.getText();
        String personalNr = this.txtPersonalNr.getText();
        String password = this.txtPassword.getText();

        try{
            User user = UserService.signUp(
                    fullName, email, personalNr, password
            );
        }catch (SQLException sqlException){

        }

        System.out.printf("Email: %s, Password: %s", email, password);
        Parent root = FXMLLoader.load(getClass().getResource("/views/login.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();

    }
    @FXML
    void loginBtn(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/views/login.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}