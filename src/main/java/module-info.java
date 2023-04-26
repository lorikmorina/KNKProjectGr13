module com.example.knkprojectgr13 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.knkprojectgr13 to javafx.fxml;
    exports com.example.knkprojectgr13;
}