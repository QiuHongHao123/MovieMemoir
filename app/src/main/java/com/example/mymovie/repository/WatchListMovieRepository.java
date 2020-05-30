package com.example.mymovie.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.mymovie.dao.WatchListMovieDao;
import com.example.mymovie.database.WatchListMovieDatabase;
import com.example.mymovie.entity.WatchListMovie;

import java.util.List;

public class WatchListMovieRepository {
    private WatchListMovieDao dao;
    private LiveData<List<WatchListMovie>> allWatchListMovie;
    private LiveData<WatchListMovie> liveWatchListMovie;
    private WatchListMovie watchlistmovie;
    public WatchListMovieRepository(Application application) {
        WatchListMovieDatabase db = WatchListMovieDatabase.getInstance(application);
        dao = db.watchlistmovieDao();
    }
    public LiveData<List<WatchListMovie>> getAllWatchListMovies() {
        allWatchListMovie = dao.getAll();
        return allWatchListMovie;
    }
    public LiveData<WatchListMovie> findByID(final int watchlistmovieId) {
        liveWatchListMovie = dao.findByID(watchlistmovieId);
        return liveWatchListMovie;
    }

    public void insert(final WatchListMovie watchlistmovie) {
        WatchListMovieDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                dao.insert(watchlistmovie);
            }
        });
    }

    public void deleteAll() {
        WatchListMovieDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                dao.deleteAll();
            }
        });
    }
    public void delete(final WatchListMovie watchlistmovie) {
        WatchListMovieDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                dao.delete(watchlistmovie);
            }
        });
    }
    public void insertAll(final WatchListMovie... watchlistmovies) {
        WatchListMovieDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                dao.insertAll(watchlistmovies);
            }
        });
    }
    public void updateWatchlistmovies (final WatchListMovie... watchlistmovies) {
        WatchListMovieDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                dao.updateWatchListMovies(watchlistmovies);
            }
        });
    }
    public void setWatchlistmovie(WatchListMovie watchlistmovie) {
        this.watchlistmovie = watchlistmovie;
    }
}
