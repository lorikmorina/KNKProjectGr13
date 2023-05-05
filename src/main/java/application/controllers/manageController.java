package application.controllers;

import application.service.PasswordHasher;
import application.models.User;
import application.database.ConnectionUtil;
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

public class manageController {
    @FXML
    private TableView<User> userTable;
    @FXML
    private TableColumn<User, Integer> idColumn;
    @FXML
    private TableColumn<User, String> fullNameColumn,emailColumn,personalNrColumn;
    @FXML
    private VBox vBoxManage;
    @FXML
    private TextField personalnrManage,idManage, fullnameManage, emailManage,searchField ;
    @FXML
    private Button deleteID,addButton,gobackButton,addButtonManage,deleteButton ;
    @FXML
    private PasswordField passwordManage;
    @FXML
    private Label userManage;
    public void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        fullNameColumn.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        personalNrColumn.setCellValueFactory(new PropertyValueFactory<>("personalNr"));
        userTable.setItems(getUsers());

        deleteButton.setOnAction(event -> {
            showManageView();
            personalnrManage.setVisible(false);
            fullnameManage.setVisible(false);
            emailManage.setVisible(false);
            deleteButton.setVisible(false);
            userManage.setVisible(false);
            deleteID.setVisible(true);
            deleteID.setOnAction(event3 ->{
                String id = idManage.getText();
                deleteUser(id);
            });
            gobackButton.setOnAction(event1 ->{
                hideManageView();
                clearManage();
            });
        });
        addButton.setOnAction(event ->{
            showManageView();
            addButtonManage.setVisible(true);
            idManage.setVisible(false);
            passwordManage.setVisible(true);
            personalnrManage.setVisible(true);
            fullnameManage.setVisible(true);
            emailManage.setVisible(true);
            addButtonManage.setOnAction(event1->{
                String fullname = fullnameManage.getText();
                String email = emailManage.getText();
                String personalNr = personalnrManage.getText();
                String password = passwordManage.getText();
                addUser(fullname, email, personalNr, password);
            });
            gobackButton.setOnAction(event1 ->{
                hideManageView();
                clearManage();
            });
        });
        dynamicSearch();
    }
    private ObservableList<User> getUsers() {
        ObservableList<User> users = FXCollections.observableArrayList();
        Connection connection = null;
        try {
            connection = ConnectionUtil.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM `parents`");
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String fullName = resultSet.getString("fullname");
                String email = resultSet.getString("email");
                String personalNr = resultSet.getString("personalNr");
                User user = new User(id, fullName, email, personalNr);
                users.add(user);
            }
            statement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }
    private void deleteUser(String id){
        Connection connection = null;
        try {
            connection = ConnectionUtil.getConnection();
            PreparedStatement stmt = connection.prepareStatement("DELETE FROM parents WHERE id = ?");
            stmt.setString(1, id);

            int rowsDeleted = stmt.executeUpdate();
            if (rowsDeleted > 0) {
                userManage.setText("User deleted successfully");
                timeLabel(userManage);
                clearManage();
                ObservableList<User> userList = getUsers(); // get updated list of users from database
                userTable.setItems(FXCollections.observableArrayList(userList));
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
    private void addUser(String fullName, String email, String personalNr, String password) {
        if (fullName.isEmpty() || email.isEmpty() || personalNr.isEmpty() || password.isEmpty()) {
            userManage.setText("Please fill in all fields");
            timeLabel(userManage);
            return;
        }
        Connection connection = null;
        try {
            String salt = PasswordHasher.generateSalt();
            String saltedHash = PasswordHasher.generateSaltedHash(password, salt);
            connection = ConnectionUtil.getConnection();
            PreparedStatement stmt = connection.prepareStatement(
                    "INSERT INTO parents (fullname, email, personalNr, salted_hash, salt) VALUES (?, ?, ?, ?, ?)"
            );
            stmt.setString(1, fullName);
            stmt.setString(2, email);
            stmt.setString(3, personalNr);
            stmt.setString(4, saltedHash);
            stmt.setString(5, salt);

            int rowsInserted = stmt.executeUpdate();

            if (rowsInserted > 0) {
                userManage.setText("User added successfully");
                timeLabel(userManage);
                clearManage();
                hideManageView();
                ObservableList<User> userList = getUsers(); // get updated list of users from database
                userTable.setItems(FXCollections.observableArrayList(userList));
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
        idManage.setVisible(true);
    }
    private void hideManageView() {
        vBoxManage.setVisible(false);
        addButtonManage.setVisible(false);
        addButton.setVisible(true);
        deleteButton.setVisible(true);
        gobackButton.setVisible(false);
        userManage.setVisible(false);
        deleteID.setVisible(false);
        passwordManage.setVisible(false);
    }
    private void clearManage(){
        fullnameManage.clear();
        emailManage.clear();
        personalnrManage.clear();
        passwordManage.clear();
        idManage.clear();
    }
    private void dynamicSearch(){
        ObservableList<User> users = getUsers();
        userTable.setItems(users);
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            ObservableList<User> filteredUsers = users.filtered(user ->
                    user.getFullName().toLowerCase().contains(newValue.toLowerCase()));
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
            Parent manageRoot = FXMLLoader.load(getClass().getResource("/views/home.fxml"));
            Scene manageScene = new Scene(manageRoot);
            Stage primaryStage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            primaryStage.setScene(manageScene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}