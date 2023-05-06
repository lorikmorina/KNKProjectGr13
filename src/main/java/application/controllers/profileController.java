package application.controllers;

import application.models.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class profileController {

    @FXML
    private Label myName;

    @FXML
    private Label myPersonalNr;

    @FXML
    private Label nrChildren;

    @FXML
    private PieChart pieChart;

    @FXML
    private Label userName;

    private User loggedInUser;


    public void initialize() {
        // add some data to the pie chart
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                new PieChart.Data("Absent", 20),
                new PieChart.Data("Present", 50)
        );
        pieChart.setData(pieChartData);

        pieChart.setLegendVisible(true);
        pieChart.setTitle("Attendance");
    }

    public void setUser(User user) {
        this.loggedInUser = user;

        // set all the labels using the User object
        myName.setText(loggedInUser.getFullName());
        myPersonalNr.setText(loggedInUser.getPersonalNr());
        userName.setText(loggedInUser.getFullName());


        // add some data to the pie chart
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                new PieChart.Data("Absent", 20),
                new PieChart.Data("Present", 50)
        );

    }
    @FXML
    private void handleHomeButton(ActionEvent event){
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
