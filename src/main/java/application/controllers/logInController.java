package application.controllers;

import application.models.*;
import application.service.AdminService;
import application.service.TeacherService;
import application.service.UserService;
import application.service.UserSession;
import application.service.interfaces.AdminServiceInterface;
import application.service.interfaces.TeacherServiceInterface;
import application.service.interfaces.UserServiceInterface;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.sql.SQLException;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

import static application.models.RememberMeConfig.*;

public class logInController implements Initializable {

    private UserServiceInterface userService;
    private AdminServiceInterface adminService;
    private TeacherServiceInterface teacherService;
//    ....



    public logInController() {
        System.out.println("Controller");
        this.userService = new UserService();
        this.adminService = new AdminService();
        this.teacherService = new TeacherService();
    }
    @FXML
    private Label welcomeText;
    @FXML
    private Button loginBtn;

    @FXML
    private Button helpButton;
    @FXML
    private Label labelLogin;
    @FXML
    private RadioButton alButton;
    @FXML
    private RadioButton enButton;
    @FXML
    private TextField txtEmail;
    @FXML
    private PasswordField txtPassword;
    @FXML
    private CheckBox rememberMe;
    @FXML
    private Hyperlink dontHaveAcc;
    @FXML
    private Hyperlink forgotPassword;
    @FXML
    private Button signUpBtn;

    private static final String CONFIG_FILE = "config.properties";

    public  void changeLanguage() {
        ToggleGroup languageToggleGroup = new ToggleGroup();
        alButton.setToggleGroup(languageToggleGroup);
        enButton.setToggleGroup(languageToggleGroup);
        languageToggleGroup.selectedToggleProperty().addListener((observable, oldToggle, newToggle) -> {
            if(newToggle == alButton) {
                Locale currentLocale = new Locale("sq", "AL");
                ResourceBundle bundle = ResourceBundle.getBundle("translations.AL_SQ", currentLocale);
                LanguageManager.getInstance().setSelectedLanguage("sq_AL");
                loginBtn.setText(bundle.getString("login.button.text"));
                txtEmail.setPromptText(bundle.getString("email.placeholder"));
                txtPassword.setPromptText(bundle.getString("password.placeholder"));
                rememberMe.setText((bundle.getString("remember.me.link")));
                dontHaveAcc.setText(bundle.getString("don't.have.an.account.link"));

            }else if(newToggle == enButton)  {
                Locale currentLocale = new Locale("en", "US");
                ResourceBundle bundle = ResourceBundle.getBundle("translations.US_EN", currentLocale);
                LanguageManager.getInstance().setSelectedLanguage("en_US");
                loginBtn.setText(bundle.getString("login.button.text"));
                txtEmail.setPromptText(bundle.getString("email.placeholder"));
                txtPassword.setPromptText(bundle.getString("password.placeholder"));
                rememberMe.setText((bundle.getString("remember.me.link")));
                dontHaveAcc.setText(bundle.getString("don't.have.an.account.link"));

            }

        });
        languageToggleGroup.selectToggle(alButton);
    }
    @FXML
    private void btnLoginClick(ActionEvent event) {
        String email = this.txtEmail.getText();
        String password = this.txtPassword.getText();
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/home.fxml"));
            Parent root = loader.load();
            User user = this.userService.login(email, password);
            if(user == null){
                Teacher teacher = this.teacherService.login(email, password);
                if (teacher == null) {
                    Admin admin = this.adminService.login(email, password);
                    if (admin == null) {
                        labelLogin.setText("Email or password is incorrect!");
                        return;
                    } else  {
                        homeController homeController = loader.getController();
                        UserSession session = new UserSession(admin.getId(), admin.getFullName(), admin.getEmail(), admin.getPersonalNr(), 1);
                        homeController.initialize(session);
                    }

                } else  {
                    homeController homeController = loader.getController();
                    UserSession session = new UserSession(teacher.getId(), teacher.getFullName(), teacher.getEmail(), teacher.getPersonalNr(), 2);
                    homeController.initialize(session);
                }


            } else {

                homeController homeController = loader.getController();
                UserSession session = new UserSession(user.getId(), user.getFullName(), user.getEmail(), user.getPersonalNr(), 3);
                homeController.initialize(session);
            }
            System.out.println("User is correct!");
//            Parent root = FXMLLoader.load(getClass().getResource("/views/home.fxml"));
//
//            Scene scene = new Scene(root);
//            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
//            stage.setScene(scene);
//            stage.show();
            if (rememberMe.isSelected()) {
                saveCredentials(email, password);
            } else {
                //
            }


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

    private void saveCredentials(String email, String password) {
        Properties properties = new Properties();
        properties.setProperty("email", email);
        properties.setProperty("password", password);

        try (OutputStream outputStream = new FileOutputStream(CONFIG_FILE)) {
            properties.store(outputStream, "Remember Me Configuration");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void loadSavedCredentials() {
        try (InputStream inputStream = new FileInputStream(CONFIG_FILE)) {
            Properties properties = new Properties();
            properties.load(inputStream);

            String email = properties.getProperty("email");
            String password = properties.getProperty("password");

            if (email != null && password != null) {
                txtEmail.setText(email);
                txtPassword.setText(password);
                rememberMe.setSelected(true);
            }
        } catch (IOException e) {
            // Configuration file doesn't exist or couldn't be loaded
        }
    }
    @FXML
    public void signupBtn(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/views/signup.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    public void handleForgotPasswordBtn(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/views/forgotPassword.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    void handleHelpButton(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/help.fxml"));
            Parent root = loader.load();
            helpController helpC = loader.getController();
            helpC.setLogIn(true);
            Scene profileScene = new Scene(root);
            Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            primaryStage.setScene(profileScene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        changeLanguage();
        loadSavedCredentials();
    }


}