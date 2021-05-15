package database;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

import Model.Cast;
import Model.CastList;
import Model.Movie;
import Model.MovieDetails;
import server.ApiService;

public class Repository {
    private ApiService apiService = ApiService.getInstance();

    public Repository(Application application) {

    }


    public LiveData<List<Movie>> getPopularMovies(String apiKey, Long page) {
        return apiService.getPopularMovies(apiKey, page);

    }

    public LiveData<List<Movie>> getTopRatedMovies(String apiKey, Long page) {
        return apiService.getTopRatedMovies(apiKey, page);
    }

    public LiveData<MovieDetails> getMovieDetails(String apiKey, int id) {
        return apiService.getMovieDetails(apiKey, id);
    }

    public LiveData<List<Cast>> getCast(String apiKey, int id) {
        return apiService.getCredits(apiKey, id);
    }

}
