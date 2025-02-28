package com.example.streamingdheis;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddMovieDialogController {
    @FXML
    private TextField titleField;
    @FXML
    private TextField genreField;
    @FXML
    private TextField durationField;
    @FXML
    private TextField releaseYearField;
    @FXML
    private TextField ratingField;

    private DatabaseController dbController;
    private Stage dialogStage;

    public void setDbController(DatabaseController dbController) {
        this.dbController = dbController;
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    @FXML
    public void addMovie() {
        try {
            Movie movie = new Movie(
                0, // Assuming movie_id is auto-generated
                titleField.getText(),
                genreField.getText(),
                Integer.parseInt(durationField.getText()),
                Integer.parseInt(releaseYearField.getText()),
                Integer.parseInt(ratingField.getText()),
                false
            );
            dbController.createMovie(movie);
            dialogStage.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}