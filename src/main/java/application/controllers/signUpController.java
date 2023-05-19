package application.controllers;

import application.models.User;
import application.service.UserService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;

public class signUpController implements Initializable {
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
    private RadioButton alButton;
    @FXML
    private RadioButton enButton;
    @FXML
    private TextField getTxtPersonalNr;
    @FXML
    private TextField secQuestion;
    @FXML
    private Button signUpBtn;
    @FXML
    private Hyperlink alrHaveAcc;

    public  void changeLanguage() {
        ToggleGroup languageToggleGroup = new ToggleGroup();
        alButton.setToggleGroup(languageToggleGroup);
        enButton.setToggleGroup(languageToggleGroup);
        languageToggleGroup.selectedToggleProperty().addListener((observable, oldToggle, newToggle) -> {
            if(newToggle == alButton) {
                Locale currentLocale = new Locale("sq", "AL");
                ResourceBundle bundle = ResourceBundle.getBundle("translations.AL_SQ", currentLocale);
                signUpBtn.setText(bundle.getString("signup.button"));
                txtEmail.setPromptText(bundle.getString("email.signup.text"));
                txtPassword.setPromptText(bundle.getString("password.signup.text"));
                txtFullName.setPromptText(bundle.getString("fullname.signup.text"));
                txtPersonalNr.setPromptText(bundle.getString("personal.signup.text"));
                alrHaveAcc.setText(bundle.getString("already.have.an.account.link"));


            }else if(newToggle == enButton)  {
                Locale currentLocale = new Locale("sq", "AL");
                ResourceBundle bundle = ResourceBundle.getBundle("translations.US_EN", currentLocale);
                signUpBtn.setText(bundle.getString("signup.button"));
                txtEmail.setPromptText(bundle.getString("email.signup.text"));
                txtPassword.setPromptText(bundle.getString("password.signup.text"));
                txtFullName.setPromptText(bundle.getString("fullname.signup.text"));
                txtPersonalNr.setPromptText(bundle.getString("personal.signup.text"));
                alrHaveAcc.setText(bundle.getString("already.have.an.account.link"));
            }

        });
        languageToggleGroup.selectToggle(alButton);
    }

    @FXML
    private void signUpBtn(ActionEvent event) throws IOException {
        String fullName = this.txtFullName.getText();
        String email = this.txtEmail.getText();
        String personalNr = this.txtPersonalNr.getText();
        String password = this.txtPassword.getText();
        String secQuestion1 = this.secQuestion.getText();
        try{
            User user = UserService.signUp(
                    fullName, email, personalNr, password,secQuestion1
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
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        changeLanguage();
    }
}