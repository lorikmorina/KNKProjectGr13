package application.controllers;

import application.database.ConnectionUtil;
import application.models.Teacher;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class teacherManageController {

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
    private Button gobackButton;

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
    private TableColumn<Teacher, String> emailId, personalNrColumn;

    @FXML
    private TextField personalNumber;

    @FXML
    private TextField searchField;

    @FXML
    private TextField teachersNameField;

    @FXML
    private Label userManage;

    @FXML
    private TableView<Teacher> userTable;

    @FXML
    private VBox vBoxManage;
    private Teacher manageLoggedInUser;
    private int userId;


   
    public void setUser(Teacher user ) {
        this.manageLoggedInUser = user;
        this.userId = user.getId();

    }
    public void setId(int id ) {

        this.userId =id;
        System.out.println(userId);
    }
    public void initialize(Teacher manageLoggedInUser) {
        this.manageLoggedInUser = manageLoggedInUser;
        nameLabel.setText(manageLoggedInUser.getFullName());
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        fullNameColumn.setCellValueFactory(new PropertyValueFactory<>("fullname"));
        emailId.setCellValueFactory(new PropertyValueFactory<>("email"));
        personalNrColumn.setCellValueFactory(new PropertyValueFactory<>("personalNr"));


        userTable.setItems(getUsers());

        deleteButton.setOnAction(event -> {
            showManageView();
            teachersNameField.setVisible(false);
            emailField.setVisible(false);
            personalNumber.setVisible(false);
            deleteButton.setVisible(false);
            userManage.setVisible(false);
            deleteID.setVisible(true);
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

            addButtonManage.setOnAction(event1->{
                String teachersName = teachersNameField.getText();
                String email = emailField.getText();
                String personalNr = personalNumber.getText();

                addUser(teachersName, email, personalNr);
            });
            gobackButton.setOnAction(event1 ->{
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
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM `teachers` WHERE id = ?");
            stmt.setInt(1, manageLoggedInUser.getId());


            ResultSet resultSet = stmt.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String fullName = resultSet.getString("fullname");
                String email = resultSet.getString("email");
                String personalNr = resultSet.getString("personalNr");

                Teacher teacher = new Teacher(id, fullName, email, personalNr);
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
    private void addUser(String fullname, String email, String personalNr) {
        if (fullname.isEmpty() || email.isEmpty() || personalNr.isEmpty()) {
            userManage.setText("Please fill in all fields");
            timeLabel(userManage);
            return;
        }
        Connection connection = null;
        try {
            connection = ConnectionUtil.getConnection();
            PreparedStatement stmt = connection.prepareStatement(
                    "INSERT INTO teachers (fullname, email, personalNr) VALUES (?, ?, ?)"
            );
            stmt.setString(1, fullname);
            stmt.setInt(2, manageLoggedInUser.getId());
            stmt.setString(3, email);
            stmt.setString(4, personalNr);

            int rowsInserted = stmt.executeUpdate();

            if (rowsInserted > 0) {
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
    }
    private void hideManageView() {
        vBoxManage.setVisible(false);
        addButtonManage.setVisible(false);
        addButton.setVisible(true);
        deleteButton.setVisible(true);
        gobackButton.setVisible(false);
        userManage.setVisible(false);
        deleteID.setVisible(false);
    }
    private void clearManage(){
        teachersNameField.clear();
        emailField.clear();
        personalNumber.clear();
    }
    private void dynamicSearch(){
        ObservableList<Teacher> teachers = getUsers();
        userTable.setItems(teachers);
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            ObservableList<Teacher> filteredUsers = teachers.filtered(child ->
                    child.getFullName().toLowerCase().contains(newValue.toLowerCase()));
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
           // homeController.setSession(teacherManageController.getFullName());
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
}