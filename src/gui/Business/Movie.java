package gui.Business;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

public class Movie {
    private SimpleStringProperty movieName;
    private SimpleStringProperty categoryMovie;
    private SimpleStringProperty lengthMovie;
    private SimpleDoubleProperty ratingMovie;
    private SimpleStringProperty yearMovie;
    private SimpleStringProperty seenMovie;

    public  Movie(String movieName, String categoryMovie, String lengthMovie, double ratingMovie,String yearMovie, String  seenMovie) {
        this.movieName = new SimpleStringProperty(movieName);
        this.categoryMovie = new SimpleStringProperty(categoryMovie);
        this.lengthMovie = new SimpleStringProperty(lengthMovie);
        this.ratingMovie = new SimpleDoubleProperty(ratingMovie);
        this.yearMovie = new SimpleStringProperty(yearMovie);
        this.seenMovie = new SimpleStringProperty(seenMovie);
    }

    public String getMovieName() {
        return movieName.get();
    }

    public SimpleStringProperty movieNameProperty() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName.set(movieName);
    }

    public String getCategoryMovie() {
        return categoryMovie.get();
    }

    public SimpleStringProperty categoryMovieProperty() {
        return categoryMovie;
    }

    public void setCategoryMovie(String categoryMovie) {
        this.categoryMovie.set(categoryMovie);
    }

    public String getLengthMovie() {
        return lengthMovie.get();
    }

    public SimpleStringProperty lengthMovieProperty() {
        return lengthMovie;
    }

    public void setLengthMovie(String lengthMovie) {
        this.lengthMovie.set(lengthMovie);
    }

    public double getRatingMovie() {
        return ratingMovie.get();
    }

    public SimpleDoubleProperty ratingMovieProperty() {
        return ratingMovie;
    }

    public void setRatingMovie(double ratingMovie) {
        this.ratingMovie.set(ratingMovie);
    }

    public String getYearMovie() {
        return yearMovie.get();
    }

    public SimpleStringProperty yearMovieProperty() {
        return yearMovie;
    }

    public void setYearMovie(String yearMovie) {
        this.yearMovie.set(yearMovie);
    }

    public String getSeenMovie() {
        return seenMovie.get();
    }

    public SimpleStringProperty seenMovieProperty() {
        return seenMovie;
    }

    public void setSeenMovie(String seenMovie) {
        this.seenMovie.set(seenMovie);
    }
}
