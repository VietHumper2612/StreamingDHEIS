package com.example.streamingdheis;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseController {
    private static final String URL = "jdbc:mysql://localhost:3306/streaming_platform";
    private static final String USER = "root";
    private static final String PASSWORD = "root1803";

    public Connection connect() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public void createUser(User user) throws SQLException {
        String query = "INSERT INTO Users (email, name) VALUES (?, ?)";
        try (Connection connection = connect();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, user.getEmail());
            statement.setString(2, user.getName());
            statement.executeUpdate();
        }
    }

    public void addFavorite(String email, int movieId) throws SQLException {
        String query = "INSERT INTO favorites (email, movie_id) VALUES (?, ?)";
        try (Connection connection = connect();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, email);
            statement.setInt(2, movieId);
            statement.executeUpdate();
        }
    }

    public List<Movie> getAllMovies() throws SQLException {
        String query = "SELECT * FROM Movies";
        List<Movie> movies = new ArrayList<>();
        try (Connection connection = connect();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                movies.add(new Movie(
                        resultSet.getInt("movie_id"),
                        resultSet.getString("title"),
                        resultSet.getString("genre"),
                        resultSet.getInt("duration"),
                        resultSet.getInt("release_year"),
                        resultSet.getInt("rating")
                ));
            }
        }
        return movies;
    }

    public Movie getMovieById(int movieId) throws SQLException {
        String query = "SELECT * FROM Movies WHERE movie_id = ?";
        try (Connection connection = connect();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, movieId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new Movie(
                        resultSet.getInt("movie_id"),
                        resultSet.getString("title"),
                        resultSet.getString("genre"),
                        resultSet.getInt("duration"),
                        resultSet.getInt("release_year"),
                        resultSet.getInt("rating")
                );
            }
        }
        return null;
    }

    public void createMovie(Movie movie) throws SQLException {
        String query = "INSERT INTO Movies (title, genre, duration, release_year, rating) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = connect();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, movie.getTitle());
            statement.setString(2, movie.getGenre());
            statement.setInt(3, movie.getDuration());
            statement.setInt(4, movie.getReleaseYear());
            statement.setInt(5, movie.getRating());
            statement.executeUpdate();
        }
    }

    public void updateMovie(Movie movie) throws SQLException {
        String query = "UPDATE Movies SET title = ?, genre = ?, duration = ?, release_year = ?, rating = ? WHERE movie_id = ?";
        try (Connection connection = connect();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, movie.getTitle());
            statement.setString(2, movie.getGenre());
            statement.setInt(3, movie.getDuration());
            statement.setInt(4, movie.getReleaseYear());
            statement.setInt(5, movie.getRating());
            statement.setInt(6, movie.getMovieId());
            statement.executeUpdate();
        }
    }

    public void deleteMovie(int movieId) throws SQLException {
        String query = "DELETE FROM Movies WHERE movie_id = ?";
        try (Connection connection = connect();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, movieId);
            statement.executeUpdate();
        }
    }

    public List<Movie> getFavoriteMoviesByEmail(String email) throws SQLException {
        List<Movie> favoriteMovies = new ArrayList<>();
        String query = "SELECT * FROM favorites WHERE email = ?";
        try (Connection connection = connect();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int movieId = resultSet.getInt("movie_id");
                Movie movie = getMovieById(movieId);
                favoriteMovies.add(movie);
            }
        }
        return favoriteMovies;
    }

    public List<Movie> getMoviesByTitle(String title) throws SQLException {
        String query = "SELECT * FROM Movies WHERE title LIKE ?";
        List<Movie> movies = new ArrayList<>();
        try (Connection connection = connect();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, "%" + title + "%");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                movies.add(new Movie(
                        resultSet.getInt("movie_id"),
                        resultSet.getString("title"),
                        resultSet.getString("genre"),
                        resultSet.getInt("duration"),
                        resultSet.getInt("release_year"),
                        resultSet.getInt("rating")
                ));
            }
        }
        return movies;
    }
}