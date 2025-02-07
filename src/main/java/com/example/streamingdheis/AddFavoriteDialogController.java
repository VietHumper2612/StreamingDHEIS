package com.example.streamingdheis;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.SQLException;

public class AddFavoriteDialogController {
    @FXML
    private TextField emailField;
    @FXML
    private TextField movieIdField;

    private DatabaseController dbController;
    private Stage dialogStage;

    public void setDbController(DatabaseController dbController) {
        this.dbController = dbController;
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    @FXML
    private void addFavorite() {
        String email = emailField.getText();
        int movieId = Integer.parseInt(movieIdField.getText());
        if (email != null && !email.isEmpty()) {
            try {
                dbController.addFavorite(email, movieId);
                dialogStage.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}