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

import Model.Cast;
import Model.Movie;
import Model.MovieDetails;
import Model.Review;
import database.Repository;
import server.ApiInterface;
import server.ApiService;
import server.PagingFactory;
import server.PagingLoadBefore;

public class DetailsViewModel extends AndroidViewModel {

    private Repository repository;
    private LiveData<MovieDetails> movieList;
    private LiveData<List<Cast>> castLiveData;
    private LiveData<List<Review>> reviewLiveData;
    private MutableLiveData<Movie> movieLiveData;
    private MutableLiveData<MovieDetails> movieDetailsMutableLiveData;
    private MutableLiveData<Integer> movie_id ;


    public DetailsViewModel(@NonNull Application application) {
        super(application);

        repository = new Repository(application);
        movieLiveData = new MutableLiveData<>();
        movieDetailsMutableLiveData = new MutableLiveData<>();
        reviewLiveData = new MutableLiveData<>();
        castLiveData = new MutableLiveData<>() ;
        movie_id= new MutableLiveData<>() ;


    }


    /* --- Favourite --- */
    public void saveMovie(Movie movie) {
        repository.insert(movie);
    }

    public void deleteMovie(Movie movie) {
        repository.delete(movie);
    }

    public LiveData<Movie> loadFavById(int id) {
        return repository.getMovieById(id);
    }


    /*---------------- Getting Movie Details -----------------*/

    public LiveData<MovieDetails> getMovieDetails(String apikey, int id) {
        return movieList = repository.getMovieDetails(apikey, id);
    }

    public LiveData<List<Cast>> getCast(String apikey, int id) {
        return castLiveData = repository.getCast(apikey, id);
    }

    public LiveData<List<Review>> getReviews(String apikey, int id) {
        return  reviewLiveData = repository.getRevies(apikey, id);
    }

    /* ----- Sharing Data ----- */
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

    public  void passId(Integer id)
    {

        movie_id.setValue(id);
    }

    public MutableLiveData<Integer> getId()
    {
        return movie_id;
    }


}
