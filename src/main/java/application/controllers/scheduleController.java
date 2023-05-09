package application.controllers;

import application.database.ConnectionUtil;
import application.models.Schedule;
import application.models.Teacher;
import application.models.dto.CreateScheduleDto;
import application.models.dto.CreateTeacherDto;
import application.repository.SchedulesRepository;
import application.repository.TeacherRepository;
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
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class scheduleController {

    @FXML
    private Button addButton;

    @FXML
    private Button addButtonManage;

    @FXML
    private Button deleteButton;

    @FXML
    private Button deleteID;

    @FXML
    private TextField startTimeField;


    @FXML
    private Button gobackButton, profileBtn;

    @FXML
    private Button homeBtn;

    @FXML
    private TableColumn<Schedule, Integer> idColumn, teacherColumn, classroomNr;

    @FXML
    private TextField idField;

    @FXML
    private Button logoutBtn;

    @FXML
    private Label nameLabel;

    @FXML
    private TableColumn<Schedule, String> dayColumn, startTimeColumn, endTimeColumn;

    @FXML
    private TextField endTimeField;
    @FXML
    private TextField searchField;

    @FXML
    private TextField dayField;

    @FXML
    private TextField classroomNrField;

    @FXML
    private Label userManage;

    @FXML
    private TableView<Schedule> userTable;

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



   


    public void initialize(UserSession session) {
        this.session = session;
        nameLabel.setText(session.getFullName());

        if(session.getAccessLevel() == 3){
            teacherManageBtn.setVisible(false);
            teacherManageBtn.setManaged(false);
            scheduleBtn.setVisible(false);
            scheduleBtn.setManaged(false);
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
        dayColumn.setCellValueFactory(new PropertyValueFactory<>("day"));
        startTimeColumn.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        endTimeColumn.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        teacherColumn.setCellValueFactory(new PropertyValueFactory<>("teacher"));
        classroomNr.setCellValueFactory(new PropertyValueFactory<>("classroomNr"));
        userTable.setItems(getSchedules());

        deleteButton.setOnAction(event -> {
            showManageView();
            dayField.setVisible(false);
            startTimeField.setVisible(false);
            endTimeField.setVisible(false);
            classroomNrField.setVisible(false);
            deleteButton.setVisible(false);
            userManage.setVisible(false);
            deleteID.setVisible(true);
            deleteID.setOnAction(event3 ->{
                String id = idField.getText();
                deleteSchedule(id);
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
            dayField.setVisible(true);
            startTimeField.setVisible(true);
            endTimeField.setVisible(true);
            classroomNrField.setVisible(true);

            addButtonManage.setOnAction(event1->{
                String day = dayField.getText();
                String starTime = startTimeField.getText();
                String endTime = endTimeField.getText();
                int classroomNrText = Integer.parseInt(classroomNrField.getText());

               addSchedule(day, starTime, endTime, session.getId(),classroomNrText);
            });
            gobackButton.setOnAction(event1 ->{
                hideManageView();
                clearManage();
            });
        });
        dynamicSearch();
    }


    private ObservableList<Schedule> getSchedules() {
        ObservableList<Schedule> schedules = FXCollections.observableArrayList();
        Connection connection = null;
        try {
            connection = ConnectionUtil.getConnection();
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM `schedules`");


            ResultSet resultSet = stmt.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String day = resultSet.getString("day");
                String startTime = resultSet.getString("startTime");
                String endTime = resultSet.getString("endTime");
                int teacher = resultSet.getInt("teacher");
                int classroomNr = resultSet.getInt("classroomNr");


                Schedule schedule = new Schedule(id, day, startTime, endTime, teacher, classroomNr);
                schedules.add(schedule);
            }
            stmt.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return schedules;
    }
    private void deleteSchedule(String id){
        Connection connection = null;
        try {
            connection = ConnectionUtil.getConnection();
            PreparedStatement stmt = connection.prepareStatement("DELETE FROM schedules WHERE id = ?");
            stmt.setString(1, id);

            int rowsDeleted = stmt.executeUpdate();
            if (rowsDeleted > 0) {
                userManage.setText("Schedule deleted successfully");
                timeLabel(userManage);
                clearManage();
                ObservableList<Schedule> scheduleList = getSchedules(); // get updated list of teachers from database
                userTable.setItems(FXCollections.observableArrayList(scheduleList));
            } else {
                userManage.setText("No schedule found with ID: " + id);
                timeLabel(userManage);
            }
            stmt.close();
            connection.close();
            getSchedules();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void addSchedule(String day, String startTime, String endTime, int teacher, int classroomNr) {
        if (day.isEmpty() || startTime.isEmpty() || endTime.isEmpty()) {
            userManage.setText("Please fill in all fields");
            timeLabel(userManage);
            return;
        }
        try {
            SchedulesRepository scheduleRepository = new SchedulesRepository();
            CreateScheduleDto user = new CreateScheduleDto(day, startTime, endTime, teacher, classroomNr);
            Schedule tempSchedule = scheduleRepository.insert(user);


            if (tempSchedule != null) {
                userManage.setText("Schedule added successfully");
                timeLabel(userManage);
                clearManage();
                hideManageView();
                ObservableList<Schedule> scheduleList = getSchedules(); // get updated list of users from database
                userTable.setItems(FXCollections.observableArrayList(scheduleList));
            } else {
                userManage.setText("Failed to add schedule");
                timeLabel(userManage);
            }

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
        dayField.clear();
        startTimeField.clear();
        endTimeField.clear();
        classroomNrField.clear();
    }
    private void dynamicSearch(){
        ObservableList<Schedule> schedules = getSchedules();
        userTable.setItems(schedules);
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            ObservableList<Schedule> filteredUsers = schedules.filtered(teacher ->
                    teacher.getDay().toLowerCase().contains(newValue.toLowerCase()));
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
}