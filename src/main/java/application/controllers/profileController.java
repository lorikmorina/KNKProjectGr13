package application.controllers;

import application.database.ConnectionUtil;
import application.models.Teacher;
import application.models.User;
import application.service.PasswordHasher;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class profileController {

    @FXML
    private Label myName;

    @FXML
    private Label myPersonalNr;

    @FXML
    private Label nrChildren;

    @FXML
    private PieChart pieChart;

    @FXML
    private Label userName, showLabel;

    private User loggedInUser;

    @FXML
    private Button showValues, changePassword, cancel;
    @FXML
    private TextField OldPassword, NewPassword;

    @FXML
    private Button logoutBtn, manageButton, teacherManageBtn;


    public void initialize() {
        // add some data to the pie chart
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                new PieChart.Data("Absent", 20),
                new PieChart.Data("Present", 50)
        );
        pieChart.setData(pieChartData);

        pieChart.setLegendVisible(true);
        pieChart.setTitle("Attendance");

        showValues.setOnAction(event -> {
            showValues.setVisible(false);
            changePassword.setVisible(true);
            OldPassword.setVisible(true);
            NewPassword.setVisible(true);
            cancel.setVisible(true);
            cancel.setOnAction(event2 -> {
                showValues.setVisible(true);
                changePassword.setVisible(false);
                OldPassword.setVisible(false);
                NewPassword.setVisible(false);
                cancel.setVisible(false);
            });
            changePassword.setOnAction(event1 -> {
                String oldpass = OldPassword.getText();
                String newpass = NewPassword.getText();
                showLabel.setVisible(true);
                if (oldpass == null || newpass == null) {
                    return;
                } else if (oldpass == newpass) {
                    return;
                }
                int id = loggedInUser.getId();
                setChangePassword(id, oldpass, newpass);
            });
        });
    }

    public void setUser(User user) {
        this.loggedInUser = user;

        // set all the labels using the User object
        myName.setText(loggedInUser.getFullName());
        myPersonalNr.setText(loggedInUser.getPersonalNr());
        userName.setText(loggedInUser.getFullName());


        // add some data to the pie chart
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                new PieChart.Data("Absent", 20),
                new PieChart.Data("Present", 50)
        );

    }

    @FXML
    private void handleHomeButton(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/home.fxml"));
            Parent root = loader.load();
            if (loggedInUser != null) {
                homeController homeC = loader.getController();
                homeC.setUser(loggedInUser);
            }
            Scene manageScene = new Scene(root);
            Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
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
            Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            primaryStage.setScene(manageScene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleManageButton(ActionEvent event) {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/manage.fxml"));
            Parent root = loader.load();
            if (loggedInUser != null) {
                manageController manageC = loader.getController();
                manageC.initialize(loggedInUser);
            }
            Scene manageScene = new Scene(root);
            Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            primaryStage.setScene(manageScene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleTeacherManageButton(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/teacherManage.fxml"));
            Parent root = loader.load();
            if (loggedInUser != null) {
                teacherManageController teacherM = loader.getController();
                // teacherManageController.setUser(loggedInUser);
                teacherM.initialize(new Teacher(loggedInUser.getId(), loggedInUser.getFullName(), loggedInUser.getEmail(), loggedInUser.getPersonalNr()));

            }
            Scene profileScene = new Scene(root);
            Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            primaryStage.setScene(profileScene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setChangePassword(int id, String oldPass, String newPass) {
        Connection conn = null;
        try {
            conn = ConnectionUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement("SELECT salt, salted_hash FROM parents WHERE id = ?");
            PreparedStatement updateStmt = conn.prepareStatement("UPDATE parents SET salt = ?, salted_hash = ? WHERE id = ?");
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (!rs.next()) {
                showLabel.setText("User not found");
                return;
            }
            String saltedHash = rs.getString("salted_hash");
            String salt = rs.getString("salt");
            if (!PasswordHasher.compareSaltedHash(oldPass, salt, saltedHash)) {
                showLabel.setText("Old password is incorrect");
                return;
            }
            String newSalt = PasswordHasher.generateSalt();
            String newSaltedHash = PasswordHasher.generateSaltedHash(newPass, newSalt);
            updateStmt.setString(1, newSalt);
            updateStmt.setString(2, newSaltedHash);
            updateStmt.setInt(3, id);
            int rowsUpdated = updateStmt.executeUpdate();
            if (rowsUpdated != 1) {
                showLabel.setText("Error updating password");
                return;
            }
            showLabel.setText("Password changed successfully");
            showValues.setVisible(true);
            changePassword.setVisible(false);
            OldPassword.setVisible(false);
            NewPassword.setVisible(false);
            cancel.setVisible(false);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

