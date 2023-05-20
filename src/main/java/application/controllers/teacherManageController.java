package application.controllers;

import application.database.ConnectionUtil;
import application.models.LanguageManager;
import application.models.Teacher;
import application.service.PasswordHasher;
import application.service.TeacherService;
import application.service.UserSession;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;

public class teacherManageController implements Initializable {

    @FXML
    private Button addButton;

    @FXML
    private Button addButtonManage;

    @FXML
    private Button deleteButton;

    @FXML
    private Button deleteID;

    @FXML
    private TextField emailField;

    @FXML
    private TableColumn<Teacher, String> fullNameColumn;

    @FXML
    private Button gobackButton, profileBtn;

    @FXML
    private Button homeBtn;

    @FXML
    private TableColumn<Teacher, Integer> idColumn;

    @FXML
    private TextField idField;

    @FXML
    private Button logoutBtn;

    @FXML
    private Label nameLabel;

    @FXML
    private TableColumn<Teacher, String> emailId, personalNrColumn, securityQuestionColumn;

    @FXML
    private TextField personalNumber;

    @FXML
    private TextField searchField;

    @FXML
    private TextField teachersNameField;
    @FXML
    private TextField secQuestionA,secQuestionB;

    @FXML
    private TextField passText;

    @FXML
    private Label userManage;

    @FXML
    private TableView<Teacher> userTable;

    @FXML
    private Button scheduleBtn;
    @FXML
    private Button manageButton;
    @FXML
    private Button classScheduleBtn;
    @FXML
    private Button teacherManageBtn;
    @FXML
    private VBox vBoxManage;
    private UserSession session;
    @FXML
    private RadioButton alButton,enButton;
    @FXML
    private Button homeManageTeacher;

    @FXML
    private Button updateButton;

    @FXML
    private Button updateManageButton;
    @FXML
    private Text manageTeacherTitle;


    public  void changeLanguage() {
        ToggleGroup languageToggleGroup = new ToggleGroup();
        alButton.setToggleGroup(languageToggleGroup);
        enButton.setToggleGroup(languageToggleGroup);
        languageToggleGroup.selectedToggleProperty().addListener((observable, oldToggle, newToggle) -> {
            if(newToggle == alButton) {
                Locale currentLocale = new Locale("sq", "AL");
                ResourceBundle bundle = ResourceBundle.getBundle("translations.AL_SQ", currentLocale);
                searchField.setPromptText(bundle.getString("search.placeholderteacher"));
                idColumn.setText(bundle.getString("id.labele.teacher.text"));
                fullNameColumn.setText(bundle.getString("fullname.name.manageteacherlabel.text"));
                emailId.setText(bundle.getString("email.manageteacherlabel.text"));
                personalNrColumn.setText(bundle.getString("personalnumber.manageteacherlabel.text"));
                deleteButton.setText(bundle.getString("delete.menageteacherbutton"));
                addButton.setText(bundle.getString("add.manageteacherbutton"));
                gobackButton.setText(bundle.getString("go.back.button.teacher"));
                addButtonManage.setText(bundle.getString("add.manageteacherbutton"));
                idField.setPromptText(bundle.getString("id.manageteacherlabel.text"));
                deleteID.setText(bundle.getString("delete.menagebutton"));
                teachersNameField.setPromptText(bundle.getString("teacherNameDelete.manageteacher"));
                emailField.setPromptText(bundle.getString("email.manageteacherlabel.text"));
                personalNumber.setPromptText(bundle.getString("personalnumber.manageteacherlabel.text"));
                passText.setPromptText(bundle.getString("passwordText.manageteacher"));
                secQuestionB.setPromptText(bundle.getString("security.question.managetextField"));
                updateButton.setText(bundle.getString("updade.manage.button"));
                securityQuestionColumn.setText(bundle.getString("security.question.managetextField"));
                updateManageButton.setText(bundle.getString("updade.manage.button"));
                secQuestionA.setPromptText(bundle.getString("securityquesion.txt"));
                secQuestionB.setPromptText(bundle.getString("securityquesion.txt"));


                //MenuBar
                manageTeacherTitle.setText(bundle.getString("teacher.manage.manageteacherlabel"));
                homeManageTeacher.setText(bundle.getString("home.profile.label"));
                manageButton.setText(bundle.getString("manage.profile.label.text"));
                profileBtn.setText(bundle.getString("profile.profile.label.text"));
                teacherManageBtn.setText(bundle.getString("teacher.profile.label.text"));
                scheduleBtn.setText(bundle.getString("schedule.profile.label"));
                classScheduleBtn.setText(bundle.getString("classSchedule.profile.label"));
                logoutBtn.setText(bundle.getString("logout.button.profile.text"));


            }else if(newToggle == enButton)  {
                Locale currentLocale = new Locale("en", "US");
                ResourceBundle bundle = ResourceBundle.getBundle("translations.US_EN", currentLocale);
                searchField.setPromptText(bundle.getString("search.placeholderteacher"));
                idColumn.setText(bundle.getString("id.labele.teacher.text"));
                fullNameColumn.setText(bundle.getString("fullname.name.manageteacherlabel.text"));
                emailId.setText(bundle.getString("email.manageteacherlabel.text"));
                personalNrColumn.setText(bundle.getString("personalnumber.manageteacherlabel.text"));
                deleteButton.setText(bundle.getString("delete.menageteacherbutton"));
                addButton.setText(bundle.getString("add.manageteacherbutton"));
                gobackButton.setText(bundle.getString("go.back.button.teacher"));
                addButtonManage.setText(bundle.getString("add.manageteacherbutton"));
                idField.setPromptText(bundle.getString("id.manageteacherlabel.text"));
                deleteID.setText(bundle.getString("delete.menagebutton"));
                teachersNameField.setPromptText(bundle.getString("teacherNameDelete.manageteacher"));
                emailField.setPromptText(bundle.getString("email.manageteacherlabel.text"));
                personalNumber.setPromptText(bundle.getString("personalnumber.manageteacherlabel.text"));
                passText.setPromptText(bundle.getString("passwordText.manageteacher"));
                secQuestionB.setPromptText(bundle.getString("security.question.managetextField"));
                updateButton.setText(bundle.getString("updade.manage.button"));
                securityQuestionColumn.setText(bundle.getString("security.question.managetextField"));
                updateManageButton.setText(bundle.getString("updade.manage.button"));
                //MenuBar
                manageTeacherTitle.setText(bundle.getString("teacher.manage.manageteacherlabel"));
                secQuestionA.setPromptText(bundle.getString("securityquesion.txt"));
                secQuestionB.setPromptText(bundle.getString("securityquesion.txt"));

                homeManageTeacher.setText(bundle.getString("home.profile.label"));
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
   


    public void initialize(UserSession session) {
        this.session = session;
        nameLabel.setText(session.getFullName());

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

        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        fullNameColumn.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        emailId.setCellValueFactory(new PropertyValueFactory<>("email"));
        personalNrColumn.setCellValueFactory(new PropertyValueFactory<>("personalNr"));
        securityQuestionColumn.setCellValueFactory(new PropertyValueFactory<>("securityQuestion"));
        userTable.setItems(getUsers());

        userTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                // Set the values of the selected item as default values in the input fields
                teachersNameField.setText(newSelection.getFullName());
                emailField.setText(newSelection.getEmail());
                personalNumber.setText(newSelection.getPersonalNr());
                secQuestionB.setText(newSelection.getSecQuestion());
            }
        });

        deleteButton.setOnAction(event -> {
            showManageView();
            teachersNameField.setVisible(false);
            emailField.setVisible(false);
            personalNumber.setVisible(false);
            deleteButton.setVisible(false);
            userManage.setVisible(false);
            deleteID.setVisible(true);
            passText.setVisible(false);
            secQuestionA.setVisible(false);
            secQuestionB.setVisible(false);
            deleteID.setOnAction(event3 ->{
                String id = idField.getText();
                deleteUser(id);
            });
            gobackButton.setOnAction(event1 ->{
                idField.setVisible(false);
                hideManageView();
                clearManage();
            });
        });
        addButton.setOnAction(event ->{
            //childsNameField,parentIdField, ageField, teacherField,classroomNrField, contactInfoField, medicalInfoField
            showManageView();
            idField.setVisible(false);
            addButtonManage.setVisible(true);
            teachersNameField.setVisible(true);
            emailField.setVisible(true);
            personalNumber.setVisible(true);
            passText.setVisible(true);
            secQuestionB.setVisible(false);
            secQuestionA.setVisible(true);
            addButtonManage.setOnAction(event1->{
                String teachersName = teachersNameField.getText();
                String email = emailField.getText();
                String personalNr = personalNumber.getText();
                String setPassword = passText.getText();
                String secQuestion1 = secQuestionA.getText();
                addUser(teachersName, email, personalNr,setPassword,secQuestion1);
            });
            gobackButton.setOnAction(event1 ->{
                secQuestionB.setVisible(false);
                hideManageView();
                clearManage();
            });
        });

        updateButton.setOnAction(event -> {
            showManageView();
            idField.setVisible(false);
            addButtonManage.setVisible(false);
            teachersNameField.setVisible(true);
            emailField.setVisible(true);
            personalNumber.setVisible(true);
            passText.setVisible(false);
            gobackButton.setVisible(true);
            secQuestionB.setVisible(true);
            secQuestionA.setVisible(false);
            updateManageButton.setVisible(true);

            updateManageButton.setOnAction(event1->{
                String teachersName = teachersNameField.getText();
                String email = emailField.getText();
                String personalNr = personalNumber.getText();
                String secQuestion2 = secQuestionB.getText();
                int teacherId = idColumn.getCellData(userTable.getSelectionModel().getSelectedItem());
                updateUser(teacherId,teachersName, email, personalNr, secQuestion2);
            });
            gobackButton.setOnAction(event1 ->{
                secQuestionB.setVisible(false);
                hideManageView();
                clearManage();
            });
        });


        dynamicSearch();
    }


    private ObservableList<Teacher> getUsers() {
        ObservableList<Teacher> teachers = FXCollections.observableArrayList();
        Connection connection = null;
        try {
            connection = ConnectionUtil.getConnection();
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM `teachers`");


            ResultSet resultSet = stmt.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String fullName = resultSet.getString("fullname");
                String email = resultSet.getString("email");
                String personalNr = resultSet.getString("personalNr");
                String securityQuestion1 = resultSet.getString("securityQuestion");

                Teacher teacher = new Teacher(id, fullName, email, personalNr,securityQuestion1);
                teachers.add(teacher);
            }
            stmt.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return teachers;
    }

    private void deleteUser(String id){
        Connection connection = null;
        try {
            connection = ConnectionUtil.getConnection();
            PreparedStatement stmt = connection.prepareStatement("DELETE FROM teachers WHERE id = ?");
            stmt.setString(1, id);

            int rowsDeleted = stmt.executeUpdate();
            if (rowsDeleted > 0) {
                userManage.setText("User deleted successfully");
                timeLabel(userManage);
                clearManage();
                ObservableList<Teacher> teachersList = getUsers(); // get updated list of teachers from database
                userTable.setItems(FXCollections.observableArrayList(teachersList));
            } else {
                userManage.setText("No user found with ID: " + id);
                timeLabel(userManage);
            }
            stmt.close();
            connection.close();
            getUsers();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void addUser(String fullname, String email, String personalNr, String setPassword,String secQuestion1) {
        if (fullname.isEmpty() || email.isEmpty() || personalNr.isEmpty() || setPassword.isEmpty() || secQuestion1.isEmpty()) {
            userManage.setText("Please fill in all fields");
            timeLabel(userManage);
            return;
        }
        try {
            Teacher teacherInserted = TeacherService.signUp(fullname,email,personalNr,setPassword,secQuestion1);


            if (teacherInserted != null) {
                userManage.setText("User added successfully");
                timeLabel(userManage);
                clearManage();
                hideManageView();
                ObservableList<Teacher> teachersList = getUsers(); // get updated list of users from database
                userTable.setItems(FXCollections.observableArrayList(teachersList));
            } else {
                userManage.setText("Failed to add user");
                timeLabel(userManage);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void updateUser(int teacherId,String fullname, String email, String personalNr, String secQuestion1) {
        if (fullname.isEmpty() || email.isEmpty() || personalNr.isEmpty()) {
            userManage.setText("Please fill in all fields");
            timeLabel(userManage);
            return;
        }
        Connection connection = null;
        try {
            connection = ConnectionUtil.getConnection();
            PreparedStatement stmt = connection.prepareStatement(
                    "UPDATE teachers SET fullname = ?, email = ?, personalNr = ?, securityQuestion = ? where id = ?"
            );
            stmt.setString(1, fullname);
            stmt.setString(2, email);
            stmt.setString(3, personalNr);
            stmt.setString(4,secQuestion1);
            stmt.setInt(5,teacherId);

            int rowsUpdated = stmt.executeUpdate();

            if (rowsUpdated > 0) {
                userManage.setText("User updated successfully");
                timeLabel(userManage);
                clearManage();
                hideManageView();
                ObservableList<Teacher> teacherList = getUsers(); // get updated list of users from database
                userTable.setItems(FXCollections.observableArrayList(teacherList));
            } else {
                userManage.setText("Failed to update user");
                timeLabel(userManage);
            }
            stmt.close();
            connection.close();
            getUsers();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void showManageView() {
        vBoxManage.setVisible(true);
        userManage.setVisible(false);
        gobackButton.setVisible(true);
        deleteButton.setVisible(false);
        addButton.setVisible(false);
        idField.setVisible(true);
        updateButton.setVisible(false);
    }
    private void hideManageView() {
        vBoxManage.setVisible(false);
        addButtonManage.setVisible(false);
        addButton.setVisible(true);
        deleteButton.setVisible(true);
        gobackButton.setVisible(false);
        userManage.setVisible(false);
        deleteID.setVisible(false);
        updateButton.setVisible(true);
        updateManageButton.setVisible(false);

    }
    private void clearManage(){
        teachersNameField.clear();
        emailField.clear();
        personalNumber.clear();
        passText.clear();
    }
    private void dynamicSearch(){
        ObservableList<Teacher> teachers = getUsers();
        userTable.setItems(teachers);
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            ObservableList<Teacher> filteredUsers = teachers.filtered(teacher ->
                    teacher.getFullName().toLowerCase().contains(newValue.toLowerCase()));
            userTable.setItems(filteredUsers);
        });
    }
    private void timeLabel(Label label) {
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO, event -> {
                    // Show the label
                    label.setVisible(true);
                }),
                new KeyFrame(Duration.seconds(2), event -> {
                    // Hide the label after 2 seconds
                    label.setVisible(false);
                })
        );
        timeline.play();
    }
    @FXML
    private void goHome(ActionEvent event){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/home.fxml"));
            Parent root = loader.load();
            homeController homeController = loader.getController();
            homeController.initialize(session);
            Scene manageScene = new Scene(root);
            Stage primaryStage = (Stage) ((Node)event.getSource()).getScene().getWindow();
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
            profileController profileController = loader.getController();
            profileController.initialize(session);
            Scene manageScene = new Scene(root);
            Stage primaryStage = (Stage) ((Node)event.getSource()).getScene().getWindow();
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
        manageController manageController = loader.getController();
        manageController.initialize(session);
        Scene manageScene = new Scene(root);
        Stage primaryStage = (Stage) ((Node)event.getSource()).getScene().getWindow();
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
            Stage primaryStage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            primaryStage.setScene(manageScene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        changeLanguage();
    }
}