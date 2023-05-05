package application.controllers;

import application.models.User;
import application.database.ConnectionUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import application.service.PasswordHasher;
import java.sql.*;

public class manageController {
    @FXML
    private TableView<User> userTable;
    @FXML
    private TableColumn<User, Integer> idColumn;
    @FXML
    private TableColumn<User, String> fullNameColumn;
    @FXML
    private TableColumn<User, String> emailColumn;
    @FXML
    private TableColumn<User, String> personalNrColumn;
    @FXML
    private VBox vBoxManage;
    @FXML
    private Button deleteButton;
    @FXML
    private TextField emailManage;
    @FXML
    private TextField fullnameManage;
    @FXML
    private TextField personalnrManage;
    @FXML
    private TextField idManage;
    @FXML
    private Button deleteID;
    @FXML
    private Button addButton;
    @FXML
    private Button gobackButton;
    @FXML
    private Button addButtonManage;
    @FXML
    private PasswordField passwordManage;
    @FXML
    private Label userManage;
    public void initialize() {
        // Set the cell value factories for each column
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
                userManage.setVisible(true);
            });
            gobackButton.setOnAction(event1 ->{
                hideManageView();
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
                userManage.setVisible(true);
            });
            gobackButton.setOnAction(event1 ->{
                hideManageView();
            });
        });
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
            } else {
                userManage.setText("No user found with ID: " + id);
            }
            stmt.close();
            connection.close();
            getUsers();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void addUser(String fullName, String email, String personalNr, String password) {
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
            } else {
                userManage.setText("Failed to add user");
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


}