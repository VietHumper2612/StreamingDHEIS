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

    public void addFavorite(String userEmail, int movieId) throws SQLException {
        String sql = "INSERT INTO Favorites (user_email, movie_id) VALUES (?, ?)";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, userEmail);
            pstmt.setInt(2, movieId);
            pstmt.executeUpdate();
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
                        resultSet.getInt("rating"),
                        resultSet.getBoolean("is_favorite")
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
                        resultSet.getInt("rating"),
                        resultSet.getBoolean("is_favorite")
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
            statement.setDouble(5, movie.getRating());
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
            statement.setDouble(5, movie.getRating());
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

    public List<Movie> getFavoriteMovies(String email) throws SQLException {
        List<Movie> favoriteMovies = new ArrayList<>();
        String sql = "SELECT m.* FROM Movies m "
                + "JOIN Favorites f ON m.movie_id = f.movie_id "
                + "JOIN Users u ON f.user_email = u.email "
                + "WHERE u.email = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, email);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    favoriteMovies.add(new Movie(
                            rs.getInt("movie_id"),
                            rs.getString("title"),
                            rs.getString("genre"),
                            rs.getInt("duration"),
                            rs.getInt("release_year"),
                            rs.getDouble("rating"),
                            rs.getBoolean("is_favorite")
                    ));
                }
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
                        resultSet.getInt("rating"),
                        resultSet.getBoolean("is_favorite")
                ));
            }
        }
        return movies;
    }
}