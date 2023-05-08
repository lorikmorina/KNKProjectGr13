package application.controllers;

import application.models.Child;
import application.models.Teacher;
import application.service.PasswordHasher;
import application.models.User;
import application.database.ConnectionUtil;
import application.service.UserSession;
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
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.sql.*;

import static java.sql.Types.NULL;

public class manageController {
    private UserSession session;

    @FXML
    private Label nameLabel;
    @FXML
    private TableView<Child> userTable;
    @FXML
    private TableColumn<Child, Integer> idColumn, parentId, age, classroomNr;
    @FXML
    private TableColumn<Child, String> fullNameColumn, teacher, contactInfo, medicalInfo;
    @FXML
    private VBox vBoxManage;
    @FXML
    private TextField childsNameField, parentIdField, ageField, teacherField, classroomNrField, contactInfoField, medicalInfoField, searchField, idField;
    @FXML
    private Button deleteID, addButton, gobackButton, addButtonManage, deleteButton, logoutBtn, profileBtn, teacherManageBtn;

    @FXML
    private Label userManage;





    public void initialize(UserSession session) {
        this.session = session;
        nameLabel.setText(session.getFullName());
        idColumn.setCellValueFactory(new PropertyValueFactory<>("child_id"));
        fullNameColumn.setCellValueFactory(new PropertyValueFactory<>("childsName"));
        parentId.setCellValueFactory(new PropertyValueFactory<>("parent_id"));
        age.setCellValueFactory(new PropertyValueFactory<>("child_age"));
        teacher.setCellValueFactory(new PropertyValueFactory<>("teacher"));
        classroomNr.setCellValueFactory(new PropertyValueFactory<>("classNr"));
        contactInfo.setCellValueFactory(new PropertyValueFactory<>("contactInfo"));
        medicalInfo.setCellValueFactory(new PropertyValueFactory<>("medicalInfo"));
        userTable.setItems(getUsers());

        deleteButton.setOnAction(event -> {
            showManageView();
            childsNameField.setVisible(false);
            ageField.setVisible(false);
            teacherField.setVisible(false);
            classroomNrField.setVisible(false);
            contactInfoField.setVisible(false);
            medicalInfoField.setVisible(false);
            deleteButton.setVisible(false);
            userManage.setVisible(false);
            deleteID.setVisible(true);
            deleteID.setOnAction(event3 -> {
                String id = idField.getText();
                deleteUser(id);
            });
            gobackButton.setOnAction(event1 -> {
                idField.setVisible(false);
                hideManageView();
                clearManage();
            });
        });
        addButton.setOnAction(event -> {
            //childsNameField,parentIdField, ageField, teacherField,classroomNrField, contactInfoField, medicalInfoField
            showManageView();
            idField.setVisible(false);
            addButtonManage.setVisible(true);
            childsNameField.setVisible(true);
            ageField.setVisible(true);
            teacherField.setVisible(true);
            classroomNrField.setVisible(true);
            contactInfoField.setVisible(true);
            medicalInfoField.setVisible(true);

            addButtonManage.setOnAction(event1 -> {
                String childsName = childsNameField.getText();
                int age = Integer.parseInt(ageField.getText());
                String teacher = teacherField.getText();
                int classroomNr = Integer.parseInt(classroomNrField.getText());
                String contactInfo = contactInfoField.getText();
                String medicalInfo = medicalInfoField.getText();

                addUser(childsName, age, teacher, classroomNr, contactInfo, medicalInfo);
            });
            gobackButton.setOnAction(event1 -> {
                hideManageView();
                clearManage();
            });
        });
        dynamicSearch();
    }


    private ObservableList<Child> getUsers() {
        ObservableList<Child> children = FXCollections.observableArrayList();
        Connection connection = null;
        try {
            connection = ConnectionUtil.getConnection();
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM `children` WHERE parent_id = ?");
            stmt.setInt(1, session.getId());


            ResultSet resultSet = stmt.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("child_id");
                String fullName = resultSet.getString("childsName");
                int parentId = resultSet.getInt("parent_id");
                int age = resultSet.getInt("age");
                String teacher = resultSet.getString("teacher");
                int classroomNr = resultSet.getInt("classroomNr");
                String contactInfo = resultSet.getString("contactInfo");
                String medicalInfo = resultSet.getString("medicalInfo");
                Child child = new Child(id, fullName, parentId, age, teacher, classroomNr, contactInfo, medicalInfo);
                children.add(child);
            }
            stmt.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return children;
    }

    private void deleteUser(String id) {
        Connection connection = null;
        try {
            connection = ConnectionUtil.getConnection();
            PreparedStatement stmt = connection.prepareStatement("DELETE FROM children WHERE child_id = ?");
            stmt.setString(1, id);

            int rowsDeleted = stmt.executeUpdate();
            if (rowsDeleted > 0) {
                userManage.setText("User deleted successfully");
                timeLabel(userManage);
                clearManage();
                ObservableList<Child> childList = getUsers(); // get updated list of children from database
                userTable.setItems(FXCollections.observableArrayList(childList));
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

    private void addUser(String childsName, int child_age, String teacher, int classroomNr, String contactInfo, String medicalInfo) {
        if (childsName.isEmpty() || teacher.isEmpty() || contactInfo.isEmpty()) {
            userManage.setText("Please fill in all fields");
            timeLabel(userManage);
            return;
        }
        Connection connection = null;
        try {
            connection = ConnectionUtil.getConnection();
            PreparedStatement stmt = connection.prepareStatement(
                    "INSERT INTO children (childsName, parent_id, age, teacher, classroomNr, contactInfo, medicalInfo) VALUES (?, ?, ?, ?, ?, ?, ?)"
            );
            stmt.setString(1, childsName);
            stmt.setInt(2, session.getId());
            stmt.setInt(3, child_age);
            stmt.setString(4, teacher);
            stmt.setInt(5, classroomNr);
            stmt.setString(6, contactInfo);
            stmt.setString(7, medicalInfo);

            int rowsInserted = stmt.executeUpdate();

            if (rowsInserted > 0) {
                userManage.setText("User added successfully");
                timeLabel(userManage);
                clearManage();
                hideManageView();
                ObservableList<Child> childList = getUsers(); // get updated list of users from database
                userTable.setItems(FXCollections.observableArrayList(childList));
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

    private void clearManage() {
        childsNameField.clear();
        ageField.clear();
        teacherField.clear();
        classroomNrField.clear();
        contactInfoField.clear();
        medicalInfoField.clear();
        idField.clear();
    }

    private void dynamicSearch() {
        ObservableList<Child> childrens = getUsers();
        userTable.setItems(childrens);
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            ObservableList<Child> filteredUsers = childrens.filtered(child ->
                    child.getChildsName().toLowerCase().contains(newValue.toLowerCase()));
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
    private void goHome(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/home.fxml"));
            Parent root = loader.load();
            homeController homeController = loader.getController();
            homeController.initialize(session);
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
            Parent root;
            root = loader.load();
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
}