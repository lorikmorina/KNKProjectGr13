package application.controllers;

import application.database.ConnectionUtil;
import application.models.Admin;
import application.models.Teacher;
import application.models.User;
import application.service.UserSession;
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

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class homeController {


    //    public homeController(User user) {
//        this.loggedInUser = user;
//    }
    private UserSession session;

    @FXML
    private Label childrenEnrolled, parentsRegistered, teachersEmployed;

    @FXML
    private Button loginBtn, logoutBtn, profileBtn;
    @FXML
    private Label nameLabel;
    @FXML
    private TextField txtFullName;

    @FXML
    private Button profileButton;

    @FXML
    private Button teacherManageBtn;
    @FXML
    private Button scheduleBtn;
    @FXML
    private Button manageButton;
    @FXML
    private Button classScheduleBtn;

    @FXML
    void btnLoginClick(ActionEvent event) {

    }

    public void initialize(UserSession session) throws SQLException {
        this.session = session;
        nameLabel.setText(session.getFullName());
        childrenEnrolled.setText(Integer.toString(getChildren()));
        parentsRegistered.setText(Integer.toString(getNrParents()));
        teachersEmployed.setText(Integer.toString(getNrTeachers()));

        if (session.getAccessLevel() == 3) {
            teacherManageBtn.setVisible(false);
            teacherManageBtn.setManaged(false);
            classScheduleBtn.setVisible(false);
            classScheduleBtn.setManaged(false);
        } else if (session.getAccessLevel() == 2) {
            manageButton.setVisible(false);
            manageButton.setManaged(false);
            teacherManageBtn.setVisible(false);
            teacherManageBtn.setManaged(false);
            classScheduleBtn.setVisible(false);
            classScheduleBtn.setManaged(false);
        } else if (session.getAccessLevel() == 1) {
            manageButton.setVisible(false);
            manageButton.setManaged(false);
            scheduleBtn.setVisible(false);
            scheduleBtn.setManaged(false);
            classScheduleBtn.setVisible(false);
            classScheduleBtn.setManaged(false);
        } else {
            System.out.println("There is a problem in session passing");
        }
    }

    private int getChildren() throws SQLException {
        Connection connection = null;
        int childrenEnrolled = 0;
        try {
            connection = ConnectionUtil.getConnection();
            PreparedStatement stmt = connection.prepareStatement(
                    "SELECT COUNT(*) FROM children"
            );

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                childrenEnrolled = rs.getInt(1);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            connection.close();
        }
        return childrenEnrolled;
    }

    private int getNrTeachers() throws SQLException {
        Connection connection = null;
        int teachersEmployed = 0;
        try {
            connection = ConnectionUtil.getConnection();
            PreparedStatement stmt = connection.prepareStatement(
                    "SELECT COUNT(*) FROM teachers"
            );

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                teachersEmployed = rs.getInt(1);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            connection.close();
        }
        return teachersEmployed;

    }

    public int getNrParents() throws SQLException {
        Connection connection = null;
        int parentsRegistered = 0;
        try {
            connection = ConnectionUtil.getConnection();
            PreparedStatement stmt = connection.prepareStatement(
                    "SELECT COUNT(*) FROM parents"
            );

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                parentsRegistered = rs.getInt(1);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            connection.close();
        }
        return parentsRegistered;
    }


    @FXML
    private void handleManageButton(ActionEvent event) {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/manage.fxml"));
            Parent root = loader.load();
            if (session != null) {
                manageController manageC = loader.getController();
                manageC.initialize(session);
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
    private void handleProfileButton(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/profile.fxml"));
            Parent root = loader.load();
            if (session != null) {
                profileController profileC = loader.getController();
                profileC.initialize(session);
            }
            Scene profileScene = new Scene(root);
            Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            primaryStage.setScene(profileScene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleTeacherManageButton(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/teacherManage.fxml"));
            Parent root = loader.load();
            if (session != null) {
                teacherManageController teacherM = loader.getController();
                // teacherManageController.setUser(loggedInUser);
                teacherM.initialize(session);

            }
            Scene profileScene = new Scene(root);
            Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            primaryStage.setScene(profileScene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleScheduleButton(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/schedule.fxml"));
            Parent root = loader.load();
            if (session != null) {
                scheduleController scheduleC = loader.getController();
                // teacherManageController.setUser(loggedInUser);
                scheduleC.initialize(session);

            }
            Scene profileScene = new Scene(root);
            Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            primaryStage.setScene(profileScene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}