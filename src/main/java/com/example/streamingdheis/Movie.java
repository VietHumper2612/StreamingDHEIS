package com.example.streamingdheis;

public class Movie {
    private int movieId;
    private String title;
    private String genre;
    private int duration;
    private int releaseYear;
    private double rating;
    private boolean isFavorite;

    public Movie() {}

    public Movie(int movieId, String title, String genre, int duration, int releaseYear, double rating, boolean isFavorite) {
        this.movieId = movieId;
        this.title = title;
        this.genre = genre;
        this.duration = duration;
        this.releaseYear = releaseYear;
        this.rating = rating;
        this.isFavorite = isFavorite;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    public void setId(int id) {
        this.movieId = id;
    }
}