package com.example.mymovie.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.mymovie.entity.WatchListMovie;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface WatchListMovieDao {
    @Query("select * from watchlistmovie")
    LiveData<List<WatchListMovie>> getAll();

    @Query("SELECT * FROM watchlistmovie WHERE mid = :WatchListMovieId LIMIT 1")
    LiveData<WatchListMovie> findByID(int WatchListMovieId);

    @Insert
    void insertAll(WatchListMovie... WatchListMovies);

    @Insert     
    long insert(WatchListMovie WatchListMovie);

    @Delete
    void delete(WatchListMovie WatchListMovie);

    @Update(onConflict = REPLACE)     
    void updateWatchListMovies(WatchListMovie... WatchListMovies);

    @Query("DELETE FROM watchlistmovie")
    void deleteAll();

}
