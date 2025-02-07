package com.example.streamingdheis;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.SQLException;

public class AddUserDialogController {
    @FXML
    private TextField emailField;
    @FXML
    private TextField nameField;

    private DatabaseController dbController;
    private Stage dialogStage;

    public void setDbController(DatabaseController dbController) {
        this.dbController = dbController;
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    @FXML
    private void addUser() {
        String email = emailField.getText();
        String name = nameField.getText();
        if (email != null && !email.isEmpty() && name != null && !name.isEmpty()) {
            try {
                dbController.createUser(new User(email, name));
                dialogStage.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}