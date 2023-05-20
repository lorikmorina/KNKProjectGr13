package application.controllers;

import application.database.ConnectionUtil;
import application.models.*;
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
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;
import java.util.ResourceBundle;

public class scheduleController implements Initializable {

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
    private Button homeManage;
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
    private RadioButton alButton;
    @FXML
    private RadioButton enButton;
    @FXML
    private Text Id, day, startTime, endTime, teacher, classroomNR;
    ;

    @FXML
    private Button updateButton;

    @FXML
    private Button updateManageButton;
    @FXML
    private Text homeTitle,scheduleTitle;


    public void changeLanguage() {
        ToggleGroup languageToggleGroup = new ToggleGroup();
        alButton.setToggleGroup(languageToggleGroup);
        enButton.setToggleGroup(languageToggleGroup);
        languageToggleGroup.selectedToggleProperty().addListener((observable, oldToggle, newToggle) -> {
            if (newToggle == alButton) {
                Locale currentLocale = new Locale("sq", "AL");
                ResourceBundle bundle = ResourceBundle.getBundle("translations.AL_SQ", currentLocale);
                idColumn.setText(bundle.getString("id.text.schedule"));
                dayColumn.setText(bundle.getString("day.text.schedule"));


                teacherColumn.setText(bundle.getString("teacher.text.schedule"));
                classroomNr.setText(bundle.getString("classroom.text.schedule"));
                startTimeColumn.setText(bundle.getString("start.time.text.schedule"));
                endTimeColumn.setText(bundle.getString("end.time.text.schedule"));
                //MenuBar
                scheduleTitle.setText(bundle.getString("schedule.schedule.label"));
                homeManage.setText(bundle.getString("home.schedule.label"));
                manageButton.setText(bundle.getString("manage.schedule.label"));
                profileBtn.setText(bundle.getString("profile.schedule.label"));
                teacherManageBtn.setText(bundle.getString("teacher.schedule.label"));
                scheduleBtn.setText(bundle.getString("schedule.schedule.label"));
                classScheduleBtn.setText(bundle.getString("classSchedule.schedule.label"));
                logoutBtn.setText(bundle.getString("logout.button.schedule.text"));
                searchField.setPromptText(bundle.getString("search.placeholder.schedule"));


                dayField.setPromptText(bundle.getString("day.text.schedule"));
                startTimeField.setPromptText(bundle.getString("start.time.text.schedule"));
                endTimeField.setPromptText(bundle.getString("end.time.text.schedule"));
                classroomNrField.setPromptText(bundle.getString("classroom.text.schedule"));

            } else if (newToggle == enButton) {
                Locale currentLocale = new Locale("en", "US");
                ResourceBundle bundle = ResourceBundle.getBundle("translations.US_EN", currentLocale);
                idColumn.setText(bundle.getString("id.text.schedule"));
                dayColumn.setText(bundle.getString("day.text.schedule"));


                teacherColumn.setText(bundle.getString("teacher.text.schedule"));
                classroomNr.setText(bundle.getString("classroom.text.schedule"));
                startTimeColumn.setText(bundle.getString("start.time.text.schedule"));
                endTimeColumn.setText(bundle.getString("end.time.text.schedule"));
                //MenuBar
                scheduleTitle.setText(bundle.getString("schedule.schedule.label"));
                homeManage.setText(bundle.getString("home.schedule.label"));
                manageButton.setText(bundle.getString("manage.schedule.label"));
                profileBtn.setText(bundle.getString("profile.schedule.label"));
                teacherManageBtn.setText(bundle.getString("teacher.schedule.label"));
                scheduleBtn.setText(bundle.getString("schedule.schedule.label"));
                classScheduleBtn.setText(bundle.getString("classSchedule.schedule.label"));
                logoutBtn.setText(bundle.getString("logout.button.schedule.text"));
                searchField.setPromptText(bundle.getString("search.placeholder.schedule = Search"));


                dayField.setPromptText(bundle.getString("day.text.schedule"));
                startTimeField.setPromptText(bundle.getString("start.time.text.schedule"));
                endTimeField.setPromptText(bundle.getString("end.time.text.schedule"));
                classroomNrField.setPromptText(bundle.getString("classroom.text.schedule"));
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

        if (session.getAccessLevel() == 3) {
            teacherManageBtn.setVisible(false);
            teacherManageBtn.setManaged(false);
            scheduleBtn.setVisible(false);
            scheduleBtn.setManaged(false);
            classScheduleBtn.setVisible(false);
            classScheduleBtn.setManaged(false);
            addButton.setVisible(false);
            deleteButton.setVisible(false);
            addButton.setManaged(false);
            deleteButton.setManaged(false);
            updateButton.setManaged(false);
            updateButton.setVisible(false);
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

        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        dayColumn.setCellValueFactory(new PropertyValueFactory<>("day"));
        startTimeColumn.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        endTimeColumn.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        teacherColumn.setCellValueFactory(new PropertyValueFactory<>("teacher"));
        classroomNr.setCellValueFactory(new PropertyValueFactory<>("classroomNr"));
        userTable.setItems(getSchedules());

        userTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                // Set the values of the selected item as default values in the input fields
                idField.setText(String.valueOf(newSelection.getId()));
                dayField.setText(newSelection.getDay());
                startTimeField.setText(newSelection.getStartTime());
                endTimeField.setText(newSelection.getEndTime());
                classroomNrField.setText(String.valueOf(newSelection.getClassroomNr()));
            }
        });

        deleteButton.setOnAction(event -> {
            showManageView();
            dayField.setVisible(false);
            startTimeField.setVisible(false);
            endTimeField.setVisible(false);
            classroomNrField.setVisible(false);
            deleteButton.setVisible(false);
            userManage.setVisible(false);
            deleteID.setVisible(true);
            deleteID.setOnAction(event3 -> {
                String id = idField.getText();
                deleteSchedule(id);
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
            dayField.setVisible(true);
            startTimeField.setVisible(true);
            endTimeField.setVisible(true);
            classroomNrField.setVisible(true);
            updateManageButton.setVisible(false);

            addButtonManage.setOnAction(event1 -> {
                String day = dayField.getText();
                String starTime = startTimeField.getText();
                String endTime = endTimeField.getText();
                int classroomNrText = Integer.parseInt(classroomNrField.getText());

                addSchedule(day, starTime, endTime, session.getId(), classroomNrText);
            });
            gobackButton.setOnAction(event1 -> {
                hideManageView();
                clearManage();
            });
        });

        updateButton.setOnAction(event -> {
            showManageView();
            idField.setVisible(false);
            addButtonManage.setVisible(false);
            dayField.setVisible(true);
            startTimeField.setVisible(true);
            endTimeField.setVisible(true);
            classroomNrField.setVisible(true);
            updateManageButton.setVisible(true);

            updateManageButton.setOnAction(event1 -> {
                int scheduleId = Integer.parseInt(idField.getText());
                String day = dayField.getText();
                String startTime = startTimeField.getText();
                String endTime = endTimeField.getText();
                int classroomNrText = Integer.parseInt(classroomNrField.getText());

                updateSchedule(scheduleId,day, startTime, endTime, classroomNrText);
            });
            gobackButton.setOnAction(event1 -> {
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
            PreparedStatement stmt;
            if (session.getAccessLevel() == 2) {
                stmt = connection.prepareStatement("SELECT * FROM `schedules` WHERE teacher = ?");
                stmt.setInt(1, session.getId());

            } else if (session.getAccessLevel() == 3) {
                ArrayList<Child> children = getChildrenFromParent(session.getId());
                int[] classroomNumbers = children.stream().mapToInt(Child::getClassNr).toArray();
                StringBuilder sb = new StringBuilder();
                sb.append("SELECT * FROM `schedules` WHERE classroomNr IN (");
                for (int i = 0; i < classroomNumbers.length; i++) {
                    if (i > 0) {
                        sb.append(",");
                    }
                    sb.append("?");
                }
                sb.append(")");

                stmt = connection.prepareStatement(sb.toString());
                for (int i = 0; i < classroomNumbers.length; i++) {
                    stmt.setInt(i + 1, classroomNumbers[i]);
                }
            } else {
                stmt = connection.prepareStatement("");
                stmt.setInt(1, session.getId());
            }


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

    private ArrayList<Child> getChildrenFromParent(int parent_id) {
        ArrayList<Child> children = new ArrayList<Child>();
        Connection connection = null;
        try {
            connection = ConnectionUtil.getConnection();
            PreparedStatement stmt2 = connection.prepareStatement("SELECT * FROM `children` WHERE parent_id = ?");
            stmt2.setInt(1, session.getId());
            ResultSet resultSet = stmt2.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("child_id");
                String childsName = resultSet.getString("childsName");
                int age = resultSet.getInt("age");
                String teacher = resultSet.getString("teacher");
                int classroomNr = resultSet.getInt("classroomNr");
                String contactInfo = resultSet.getString("contactInfo");
                String medicalInfo = resultSet.getString("medicalInfo");


                Child child = new Child(id, childsName, parent_id, age, teacher, classroomNr, contactInfo, medicalInfo);
                children.add(child);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return children;
    }

    private void deleteSchedule(String id) {
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

    private void updateSchedule(int scheduleId,String day, String startTime, String endTime, int classroomNrText) {
        if (day.isEmpty() || startTime.isEmpty() || endTime.isEmpty()) {
            userManage.setText("Please fill in all fields");
            timeLabel(userManage);
            return;
        }

        Connection connection = null;
        try {
            connection = ConnectionUtil.getConnection();
            PreparedStatement stmt = connection.prepareStatement("UPDATE schedules SET day = ?, startTime = ?, endTime = ?, classroomNr = ? WHERE id = ?");
            stmt.setString(1, day);
            stmt.setString(2, startTime);
            stmt.setString(3, endTime);
            stmt.setInt(4, classroomNrText);
            stmt.setInt(5, scheduleId);

            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                userManage.setText("Schedule updated successfully");
                timeLabel(userManage);
                clearManage();
                hideManageView();
                ObservableList<Schedule> scheduleList = getSchedules(); // get updated list of schedules from database
                userTable.setItems(FXCollections.observableArrayList(scheduleList));
            } else {
                userManage.setText("No schedule found with ID: " + idColumn.getText());
                timeLabel(userManage);
            }
            stmt.close();
            connection.close();
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

    private void clearManage() {
        dayField.clear();
        startTimeField.clear();
        endTimeField.clear();
        classroomNrField.clear();
    }

    private void dynamicSearch() {
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
    private void handleProfileButton(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/profile.fxml"));
            Parent root = loader.load();
            profileController profileController = loader.getController();
            profileController.initialize(session);
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
            manageController manageController = loader.getController();
            manageController.initialize(session);
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        changeLanguage();
    }
}