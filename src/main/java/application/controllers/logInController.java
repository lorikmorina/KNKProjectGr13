package application.controllers;

import application.models.Admin;
import application.models.Teacher;
import application.models.User;
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
    private Label labelLogin;
    @FXML
    private TextField txtEmail;
    @FXML
    private PasswordField txtPassword;
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