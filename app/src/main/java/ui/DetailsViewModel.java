package ui;



import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
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




    public DetailsViewModel(@NonNull Application application) {
        super(application);

        repository = new Repository(application);


    }



    /*---------------- Getting Movie Details -----------------*/

    public LiveData<MovieDetails> getMovieDetails(String apikey, int id) {
        return movieList = repository.getMovieDetails(apikey, id);
    }




}
