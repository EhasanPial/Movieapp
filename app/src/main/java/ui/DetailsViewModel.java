package ui;


import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import Model.Movie;
import Model.MovieDetails;
import database.Repository;
import server.ApiInterface;
import server.ApiService;
import server.PagingFactory;
import server.PagingLoadBefore;

public class DetailsViewModel extends AndroidViewModel {

    private Repository repository;
    private LiveData<MovieDetails> movieList;
    private MutableLiveData<Movie> movieLiveData;
    private MutableLiveData<MovieDetails> movieDetailsMutableLiveData;


    public DetailsViewModel(@NonNull Application application) {
        super(application);

        repository = new Repository(application);
        movieLiveData = new MutableLiveData<>();
        movieDetailsMutableLiveData = new MutableLiveData<>();


    }



    /*---------------- Getting Movie Details -----------------*/

    public LiveData<MovieDetails> getMovieDetails(String apikey, int id) {
        return movieList = repository.getMovieDetails(apikey, id);
    }

    public void selectMovie(Movie movie) {
        movieLiveData.setValue(movie);
    }

    public LiveData<Movie> getMovie() {
        return movieLiveData;
    }

    public void selectMovieDetails(MovieDetails movie) {
        movieDetailsMutableLiveData.setValue(movie);
    }

    public LiveData<MovieDetails> getMovieDetails() {
        return movieDetailsMutableLiveData;
    }


}
