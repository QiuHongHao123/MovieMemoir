package com.example.mymovie.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
@Entity
public class WatchListMovie {
    @PrimaryKey(autoGenerate = true)
    private int mid;
    @ColumnInfo(name = "movie_name")
    private String movieName;
    @ColumnInfo(name = "release_date")
    private String releaseDate;
    @ColumnInfo(name = "add_Time")
    private String addTime;
    @ColumnInfo(name = "movie_Id")
    private String movieId;

    public WatchListMovie(String movieName,String releaseDate,String addTime,String movieId) {
        this.addTime=addTime;
        this.movieName=movieName;
        this.releaseDate=releaseDate;
        this.movieId=movieId;

    }


    public int getMid() {
        return mid;
    }

    public void setMid(int mid) {
        this.mid = mid;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getAddTime() {
        return addTime;
    }

    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }

    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }
}
