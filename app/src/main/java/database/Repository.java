package database;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

import Model.Movie;
import server.ApiService;

public class Repository {
    private ApiService apiService = ApiService.getInstance();

    public Repository(Application application) {

    }






    public LiveData<List<Movie>> getPopularMovies(String apiKey) {
        return apiService.getPopularMovies(apiKey);
    }

}