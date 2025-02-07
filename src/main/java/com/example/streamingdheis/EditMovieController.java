package com.example.streamingdheis;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.SQLException;

public class EditMovieController {
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
    private Movie movie;

    public void setDbController(DatabaseController dbController) {
        this.dbController = dbController;
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
        titleField.setText(movie.getTitle());
        genreField.setText(movie.getGenre());
        durationField.setText(String.valueOf(movie.getDuration()));
        releaseYearField.setText(String.valueOf(movie.getReleaseYear()));
        ratingField.setText(String.valueOf(movie.getRating()));
    }

    @FXML
    public void saveMovie() throws SQLException {
        movie.setTitle(titleField.getText());
        movie.setGenre(genreField.getText());
        movie.setDuration(Integer.parseInt(durationField.getText()));
        movie.setReleaseYear(Integer.parseInt(releaseYearField.getText()));
        movie.setRating(Integer.parseInt(ratingField.getText()));
        dbController.updateMovie(movie);
        dialogStage.close();
    }
}