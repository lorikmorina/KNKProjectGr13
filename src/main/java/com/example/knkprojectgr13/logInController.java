package com.example.knkprojectgr13;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class logInController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}