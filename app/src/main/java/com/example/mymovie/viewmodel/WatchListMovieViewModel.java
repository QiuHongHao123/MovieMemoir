package com.example.mymovie.viewmodel;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mymovie.entity.WatchListMovie;
import com.example.mymovie.repository.WatchListMovieRepository;

import java.util.List;

public class WatchListMovieViewModel extends ViewModel {
    private WatchListMovieRepository wRepository;
    private MutableLiveData<List<WatchListMovie>> allWatchListMovies;
    public WatchListMovieViewModel() {
        allWatchListMovies = new MutableLiveData<>();
    }
    public void setWatchListMovies(List<WatchListMovie> watchlistmovies) {
        allWatchListMovies.setValue(watchlistmovies);
    }
    public LiveData<List<WatchListMovie>> getAllWatchListMovies() {
        return wRepository.getAllWatchListMovies();
    }
    public LiveData<WatchListMovie> findByID(int watchListId) {
        return wRepository.findByID(watchListId);
    }
    public void initalizeVars(Application application) {
        wRepository = new WatchListMovieRepository(application);
    }
    public void insert(WatchListMovie watchlistmovie) {
        wRepository.insert(watchlistmovie);
    }
    public void insertAll(WatchListMovie... watchlistmovies) {
        wRepository.insertAll(watchlistmovies);
    }
    public void deleteAll() {
        wRepository.deleteAll();
    }

    public void delete(WatchListMovie watchlistmovie) {
        wRepository.delete(watchlistmovie);
    }

    public void update(WatchListMovie... watchlistmovies) {
        wRepository.updateWatchlistmovies(watchlistmovies);
    }



}
