package application.controllers;

import application.database.ConnectionUtil;
import application.models.User;
import application.models.Admin;
import application.models.Teacher;
import application.models.User;
import application.repository.AdminRepository;
import application.repository.TeacherRepository;
import application.repository.UserRepository;
import application.service.PasswordHasher;
import application.service.UserSession;
import application.service.interfaces.UserServiceInterface;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

public class forgotPasswordController implements Initializable{
    boolean finish = false;
    private UserServiceInterface userService;
    private UserSession session;
    @FXML
    private TextField forgotName;
    @FXML
    private Button changeButton;
    @FXML
    private TextField forgotEmail;
    @FXML
    private TextField forgotPersonalNr;
    @FXML
    private TextField secQuestion2;
    @FXML
    private TextField newPassword;
    @FXML
    private Label showLabel;

    public void initialize(){
        changeButton.setOnAction(this::initChangeButton);
    }

    public void initChangeButton(ActionEvent event) {
        showLabel.setVisible(true);
        String newPassowrd = newPassword.getText();
        String email = forgotEmail.getText();
        String fullname = forgotName.getText();
        String personalNr = forgotPersonalNr.getText();
        String secQuestion = secQuestion2.getText();
        setChangePassword(email, newPassowrd, fullname, personalNr, secQuestion);


        if (finish) {
            try {
                Parent root = FXMLLoader.load(getClass().getResource("/views/login.fxml"));
                Scene scene = new Scene(root);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void setChangePassword(String email, String newPassword, String fullname, String personalNr, String secQuestion) {
        Connection conn = null;
        try {
            conn = ConnectionUtil.getConnection();
            PreparedStatement stmtParents = conn.prepareStatement("SELECT * FROM parents WHERE fullname = ?");
            PreparedStatement stmtTeachers = conn.prepareStatement("SELECT * FROM teachers WHERE fullname = ?");
            PreparedStatement stmtAdmins = conn.prepareStatement("SELECT * FROM admins WHERE fullname = ?");

            stmtParents.setString(1, fullname);
            stmtTeachers.setString(1, fullname);
            stmtAdmins.setString(1, fullname);
            ResultSet rsParents = stmtParents.executeQuery();
            ResultSet rsTeachers = stmtTeachers.executeQuery();
            ResultSet rsAdmins = stmtAdmins.executeQuery();

            String getEmail = "";
            String getFullName = "";
            String getPersonalNr = "";
            String getSecQuestion = "";
            boolean userFound = false;
            String access = "";
            if (rsParents.next()) {
                getFullName = rsParents.getString("fullname");
                getEmail = rsParents.getString("email");
                getPersonalNr = rsParents.getString("personalNr");
                getSecQuestion = rsParents.getString("securityQuestion");
                userFound = true;
                access = "parents";
            } else if (rsTeachers.next()) {
                getEmail = rsTeachers.getString("email");
                getFullName = rsTeachers.getString("fullname");
                getPersonalNr = rsTeachers.getString("personalNr");
                getSecQuestion = rsTeachers.getString("securityQuestion");
                userFound = true;
                access = "teacher";

            } else if (rsAdmins.next()) {
                getEmail = rsAdmins.getString("email");
                getFullName = rsAdmins.getString("fullname");
                getPersonalNr = rsAdmins.getString("personalNr");
                getSecQuestion = rsAdmins.getString("securityQuestion");
                userFound = true;
                access = "admins";
            }
            System.out.println(getEmail);
            System.out.println(email);
            if (!userFound) {
                showLabel.setText("User not found");
                return;
            }

            if ((getSecQuestion != null && !getSecQuestion.equals(secQuestion))
                    || (getEmail != null && !getEmail.equals(email))
                    || (getFullName != null && !getFullName.equals(fullname))
                    || (getPersonalNr != null && !getPersonalNr.equals(personalNr))) {
                showLabel.setText("Please insert correct information");
                return;
            }
            String newSalt = PasswordHasher.generateSalt();
            String newSaltedHash = PasswordHasher.generateSaltedHash(newPassword, newSalt);
            PreparedStatement updateStmt = null;

            try {

                updateStmt = conn.prepareStatement("UPDATE "+access+" SET salt = ?, salted_hash = ? WHERE fullname = ?");


                updateStmt.setString(1, newSalt);
                updateStmt.setString(2, newSaltedHash);
                updateStmt.setString(3, fullname);
                int rowsUpdated = updateStmt.executeUpdate();
                finish = true;

                if (rowsUpdated != 1) {
                    showLabel.setText("Error updating password");
                    return;
                }

            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                // Close the PreparedStatement if it was initialized
                if (updateStmt != null) {
                    try {
                        updateStmt.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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

    }


}
