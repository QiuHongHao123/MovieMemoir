package com.example.mymovie.Tools;

import java.util.Date;

public class MemoirShow {
    private String movie_name;
    private Date release_date;
    private String img_url;
    private Date watch_date;
    private String cinema_postcode;
    private String comment;
    private double score;
    private double public_score;
    private String movieId;

    public String getMovie_name() {
        return movie_name;
    }

    public void setMovie_name(String movie_name) {
        this.movie_name = movie_name;
    }

    public Date getRelease_date() {
        return release_date;
    }

    public void setRelease_date(Date release_date) {
        this.release_date = release_date;
    }

    public String getImg_url() {
        return img_url;
    }

    public Date getWatch_date() {
        return watch_date;
    }

    public void setWatch_date(Date watch_date) {
        this.watch_date = watch_date;
    }

    public String getCinema_postcode() {
        return cinema_postcode;
    }

    public void setCinema_postcode(String cinema_postcode) {
        this.cinema_postcode = cinema_postcode;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public double getPublic_score() {
        return public_score;
    }

    public void setPublic_score(double public_score) {
        this.public_score = public_score;
    }

    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }
}
   