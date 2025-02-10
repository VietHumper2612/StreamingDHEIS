package com.example.streamingdheis;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class StreamingController {
    @FXML
    private TextField movieField;
    @FXML
    private TableView<Movie> moviesTable;
    @FXML
    private TableColumn<Movie, String> titleColumn;
    @FXML
    private TableColumn<Movie, String> genreColumn;
    @FXML
    private TableColumn<Movie, Integer> durationColumn;
    @FXML
    private TableColumn<Movie, Integer> releaseYearColumn;
    @FXML
    private TableColumn<Movie, Integer> ratingColumn;
    @FXML
    private TableColumn<Movie, Integer> movieIdColumn;
    @FXML
    private TableColumn<Movie, Boolean> favoriteColumn;
    @FXML
    private TextField emailField;
    private List<Movie> favoriteMovies;

    private DatabaseController dbController;


    @FXML
    public void addUser(ActionEvent actionEvent) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/streamingdheis/AddUserDialog.fxml"));
            VBox page = loader.load();
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Add User");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            AddUserDialogController controller = loader.getController();
            controller.setDbController(dbController);
            controller.setDialogStage(dialogStage);

            dialogStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void addFavorite(ActionEvent actionEvent) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/streamingdheis/AddFavoriteDialog.fxml"));
            VBox page = loader.load();
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Add Favorite");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            AddFavoriteDialogController controller = loader.getController();
            controller.setDbController(dbController);
            controller.setDialogStage(dialogStage);

            dialogStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void initialize() throws SQLException {
        dbController = new DatabaseController();
        favoriteMovies = dbController.getFavoriteMovies("user@example.com");
        favoriteColumn = new TableColumn<>("Favorite");
        favoriteColumn.setCellValueFactory(cellData -> {
            boolean isFavorite = favoriteMovies.contains(cellData.getValue());
            return new SimpleBooleanProperty(isFavorite);
        });
        favoriteColumn.setCellFactory(col -> new TableCell<Movie, Boolean>() {
            @Override
            protected void updateItem(Boolean isFavorite, boolean empty) {
                super.updateItem(isFavorite, empty);
                setText((empty || isFavorite == null) ? null : (isFavorite ? "★" : " "));
            }
        });
        moviesTable.getColumns().add(favoriteColumn);
        movieIdColumn.setCellValueFactory(new PropertyValueFactory<>("movieId"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        genreColumn.setCellValueFactory(new PropertyValueFactory<>("genre"));
        durationColumn.setCellValueFactory(new PropertyValueFactory<>("duration"));
        releaseYearColumn.setCellValueFactory(new PropertyValueFactory<>("releaseYear"));
        ratingColumn.setCellValueFactory(new PropertyValueFactory<>("rating"));

        loadMovies();
    }

    @FXML
    public void searchFavorites(ActionEvent actionEvent) throws SQLException {
        String email = emailField.getText();
        if (email != null && !email.isEmpty()) {
            List<Movie> favoriteMovies = dbController.getFavoriteMovies(email);
            moviesTable.getItems().setAll(favoriteMovies);
        }
    }

    @FXML
    public void addMovie(ActionEvent actionEvent) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/streamingdheis/AddMovieDialog.fxml"));
            VBox page = loader.load();
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Add Movie");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            AddMovieDialogController controller = loader.getController();
            controller.setDbController(dbController);
            controller.setDialogStage(dialogStage);

            dialogStage.showAndWait();
            loadMovies();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void editMovie(ActionEvent actionEvent) throws IOException {
        Movie selectedMovie = moviesTable.getSelectionModel().getSelectedItem();
        if (selectedMovie != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/streamingdheis/EditMovieDialog.fxml"));
                VBox page = loader.load();
                Stage dialogStage = new Stage();
                dialogStage.setTitle("Edit Movie");
                dialogStage.initModality(Modality.WINDOW_MODAL);
                Scene scene = new Scene(page);
                dialogStage.setScene(scene);

                EditMovieController controller = loader.getController();
                controller.setDbController(dbController);
                controller.setDialogStage(dialogStage);
                controller.setMovie(selectedMovie);

                dialogStage.showAndWait();
                loadMovies();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    public void deleteMovie(ActionEvent actionEvent) {
        Movie selectedMovie = moviesTable.getSelectionModel().getSelectedItem();
        if (selectedMovie != null) {
            try {
                dbController.deleteMovie(selectedMovie.getMovieId());
                loadMovies();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void loadMovies() {
        try {
            List<Movie> movies = dbController.getAllMovies();
            ObservableList<Movie> movieList = FXCollections.observableArrayList(movies);
            moviesTable.setItems(movieList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}