package application.controllers;

import application.database.ConnectionUtil;
import application.models.Admin;
import application.models.Teacher;
import application.models.User;
import application.repository.AdminRepository;
import application.repository.TeacherRepository;
import application.repository.UserRepository;
import application.service.PasswordHasher;
import application.service.UserSession;
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
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

public class profileController implements Initializable {

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

    private UserSession session;

    @FXML
    private Button showValues, changePassword, cancel;
    @FXML
    private TextField OldPassword, NewPassword;

    @FXML
    private Button teacherManageBtn;
    @FXML
    private Button scheduleBtn;
    @FXML
    private Button manageButton;
    @FXML
    private Button classScheduleBtn;

    @FXML
    private Button logoutBtn;
     private Admin admin;
     private Teacher teacher;
     @FXML
     private Button homeManage,profileBtn;
     @FXML
     private Text profileLabel,fullName,personalNr,numberOfChildren;;
     @FXML
     private RadioButton alButton;
     @FXML
     private RadioButton enButton;


    public  void changeLanguage() {
        ToggleGroup languageToggleGroup = new ToggleGroup();
        alButton.setToggleGroup(languageToggleGroup);
        enButton.setToggleGroup(languageToggleGroup);
        languageToggleGroup.selectedToggleProperty().addListener((observable, oldToggle, newToggle) -> {
            if(newToggle == alButton) {
                Locale currentLocale = new Locale("sq", "AL");
                ResourceBundle bundle = ResourceBundle.getBundle("translations.AL_SQ", currentLocale);
                fullName.setText(bundle.getString("fullname.profilelabel"));
                personalNr.setText(bundle.getString("personalnum.profilelabel"));
                numberOfChildren.setText(bundle.getString("numofchildren.profilelabel "));
                showValues.setText(bundle.getString("changepassword.button"));
                //MenuBar
                homeManage.setText(bundle.getString("home.manageteacherlabel"));
                manageButton.setText(bundle.getString("manage.manageteacherlabel"));
                profileBtn.setText(bundle.getString("profile.manageteacherlanel"));
                teacherManageBtn.setText(bundle.getString("teacher.manage.manageteacherlabel"));
                scheduleBtn.setText(bundle.getString("schedule.manageteaclabel"));
                classScheduleBtn.setText(bundle.getString("classSchedule.manageteacherlabel"));
                logoutBtn.setText(bundle.getString("logout.button.manageteachertext"));
                numberOfChildren.setText(bundle.getString("numofchildren.profilelabel"));
                profileLabel.setText(bundle.getString("profile.profilelabel"));



            }else if(newToggle == enButton)  {
                Locale currentLocale = new Locale("en", "US");
                ResourceBundle bundle = ResourceBundle.getBundle("translations.US_EN", currentLocale);
                fullName.setText(bundle.getString("fullname.profilelabel"));
                personalNr.setText(bundle.getString("personalnum.profilelabel"));
                numberOfChildren.setText(bundle.getString("numofchildren.profilelabel "));
                showValues.setText(bundle.getString("changepassword.button"));
                //MenuBar
                homeManage.setText(bundle.getString("home.manageteacherlabel"));
                manageButton.setText(bundle.getString("manage.manageteacherlabel"));
                profileBtn.setText(bundle.getString("profile.manageteacherlanel"));
                teacherManageBtn.setText(bundle.getString("teacher.manage.manageteacherlabel"));
                scheduleBtn.setText(bundle.getString("schedule.manageteaclabel"));
                classScheduleBtn.setText(bundle.getString("classSchedule.manageteacherlabel"));
                logoutBtn.setText(bundle.getString("logout.button.manageteachertext"));
                numberOfChildren.setText(bundle.getString("numofchildren.profilelabel"));
                profileLabel.setText(bundle.getString("profile.profilelabel"));


            }

        });
        languageToggleGroup.selectToggle(alButton);
    }
    public void initialize(UserSession session) throws SQLException {
             this.session = session;
            myName.setText(session.getFullName());
            myPersonalNr.setText(session.getPersonalNr());
            userName.setText(session.getFullName());
            nrChildren.setText(Integer.toString(session.getNrChildren(session.getId())));
        if(session.getAccessLevel() == 3){
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
        } else if(session.getAccessLevel() == 1) {
            manageButton.setVisible(false);
            manageButton.setManaged(false);
            scheduleBtn.setVisible(false);
            scheduleBtn.setManaged(false);
            classScheduleBtn.setVisible(false);
            classScheduleBtn.setManaged(false);
        } else {
            System.out.println("There is a problem in session passing");
        }
        // set all the labels using the User object


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
                showLabel.setVisible(true);
                String oldpass = OldPassword.getText();
                String newpass = NewPassword.getText();
                if (oldpass == null || newpass == null) {
                    return;
                } else if (oldpass == newpass) {
                    return;
                }
                int access = UserSession.getAccessLevel();
                int id = session.getId();
                if(access == 1){
                    setChangePassword(id, oldpass, newpass,"admins");
                }else if (access == 2){
                    setChangePassword(id, oldpass, newpass,"teachers");
                }else if (access == 3){
                    setChangePassword(id, oldpass, newpass,"parents");
                }
            });
        });

    }

    @FXML
    private void handleHomeButton(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/home.fxml"));
            Parent root = loader.load();
            if (session != null) {
                homeController homeC = loader.getController();
                homeC.initialize(session);
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

    private void setChangePassword(int id, String oldPass, String newPass, String access) {
        Connection conn = null;
        try {
            conn = ConnectionUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement("SELECT salt, salted_hash FROM "+access+" WHERE id = ?");
            PreparedStatement updateStmt = conn.prepareStatement("UPDATE "+access+" SET salt = ?, salted_hash = ? WHERE id = ?");
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
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        changeLanguage();
    }

}

