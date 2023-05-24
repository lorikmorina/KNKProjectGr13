package application.controllers;

import application.models.LanguageManager;
import application.models.RememberMeConfig;
import application.service.UserSession;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;

public class helpController implements Initializable {
    @FXML
    private Text accountRoles;

    @FXML
    private Text adminExplain;

    @FXML
    private Text adminHelp;

    @FXML
    private RadioButton alButton;

    @FXML
    private Button classScheduleBtn;

    @FXML
    private RadioButton enButton;

    @FXML
    private Button helpButton;

    @FXML
    private Text helpTitle;

    @FXML
    private Button homeHome;

    @FXML
    private Text hopeHelp;

    @FXML
    private Text intro;

    @FXML
    private Text logHelp;

    @FXML
    private Text logUse;

    @FXML
    private Button logoutBtn;

    @FXML
    private Button manageButton;

    @FXML
    private Label nameLabel;

    @FXML
    private Text parentExplain;

    @FXML
    private Text parentHelp;

    @FXML
    private Button profileBtn;

    @FXML
    private Button scheduleBtn;

    @FXML
    private Text signHelp;

    @FXML
    private Text signUse;

    @FXML
    private Text teacherExplain;

    @FXML
    private Button teacherManageBtn;

    @FXML
    private Text teachersHelp;
    private UserSession session;

    public  void changeLanguage() {
        ToggleGroup languageToggleGroup = new ToggleGroup();
        alButton.setToggleGroup(languageToggleGroup);
        enButton.setToggleGroup(languageToggleGroup);
        languageToggleGroup.selectedToggleProperty().addListener((observable, oldToggle, newToggle) -> {
            if(newToggle == alButton) {
                Locale currentLocale = new Locale("sq", "AL");
                ResourceBundle bundle = ResourceBundle.getBundle("translations.AL_SQ", currentLocale);
                LanguageManager.getInstance().setSelectedLanguage("sq_AL");
                helpTitle.setText(bundle.getString("helpTitle.text"));
                intro.setText(bundle.getString("intro.text"));
                logHelp.setText(bundle.getString("logHelp.text"));
                logUse.setText(bundle.getString("logUse.text"));
                signUse.setText(bundle.getString("signUse.text"));
                signHelp.setText(bundle.getString("signHelp.text"));
                accountRoles.setText(bundle.getString("accountRoles.text"));
                adminHelp.setText(bundle.getString("adminHelp.text"));
                adminExplain.setText(bundle.getString("adminExplain.text"));
                teachersHelp.setText(bundle.getString("teachersHelp.text"));
                teacherExplain.setText(bundle.getString("teacherExplain.text"));
                parentHelp.setText(bundle.getString("parentHelp.text"));
                parentExplain.setText(bundle.getString("parentExplain.text"));
                hopeHelp.setText(bundle.getString("hopeHelp.text"));

                manageButton.setText(bundle.getString("manage.profile.label.text"));
                profileBtn.setText(bundle.getString("profile.profile.label.text"));
                teacherManageBtn.setText(bundle.getString("teacher.profile.label.text"));
                scheduleBtn.setText(bundle.getString("schedule.profile.label"));
                classScheduleBtn.setText(bundle.getString("classSchedule.profile.label"));
                logoutBtn.setText(bundle.getString("logout.button.profile.text"));


            }else if(newToggle == enButton)  {
                Locale currentLocale = new Locale("en", "US");
                ResourceBundle bundle = ResourceBundle.getBundle("translations.US_EN", currentLocale);
                LanguageManager.getInstance().setSelectedLanguage("en_US");

                helpTitle.setText(bundle.getString("helpTitle.text"));
                intro.setText(bundle.getString("intro.text"));
                logHelp.setText(bundle.getString("logHelp.text"));
                logUse.setText(bundle.getString("logUse.text"));
                signUse.setText(bundle.getString("signUse.text"));
                signHelp.setText(bundle.getString("signHelp.text"));
                accountRoles.setText(bundle.getString("accountRoles.text"));
                adminHelp.setText(bundle.getString("adminHelp.text"));
                adminExplain.setText(bundle.getString("adminExplain.text"));
                teachersHelp.setText(bundle.getString("teachersHelp.text"));
                teacherExplain.setText(bundle.getString("teacherExplain.text"));
                parentHelp.setText(bundle.getString("parentHelp.text"));
                parentExplain.setText(bundle.getString("parentExplain.text"));
                hopeHelp.setText(bundle.getString("hopeHelp.text"));
                profileBtn.setText(bundle.getString("home.profile.label"));
                manageButton.setText(bundle.getString("manage.profile.label.text"));
                profileBtn.setText(bundle.getString("profile.profile.label.text"));
                teacherManageBtn.setText(bundle.getString("teacher.profile.label.text"));
                scheduleBtn.setText(bundle.getString("schedule.profile.label"));
                classScheduleBtn.setText(bundle.getString("classSchedule.profile.label"));
                logoutBtn.setText(bundle.getString("logout.button.profile.text"));
            }

        });
        String selectedLanguage = LanguageManager.getInstance().getSelectedLanguage();
        if (selectedLanguage.equals("sq_AL")) {
            languageToggleGroup.selectToggle(alButton);
        } else if (selectedLanguage.equals("en_US")) {
            languageToggleGroup.selectToggle(enButton);
        }
    }
    public void initialize(UserSession session) throws SQLException {
        this.session = session;
        nameLabel.setText(session.getFullName());
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
        }

        else {
            System.out.println("There is a problem in session passing");
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
    private void logOutBtn(ActionEvent event) {
        try {
            RememberMeConfig.clearSavedCredentials();
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
    public void setLogIn(boolean logIn){
        if(logIn) {
            manageButton.setVisible(false);
            manageButton.setManaged(false);
            scheduleBtn.setVisible(false);
            scheduleBtn.setManaged(false);
            classScheduleBtn.setVisible(false);
            classScheduleBtn.setManaged(false);
            teacherManageBtn.setVisible(false);
            teacherManageBtn.setManaged(false);
            homeHome.setVisible(false);
            profileBtn.setVisible(false);
        }
        else {

        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        changeLanguage();
    }

}