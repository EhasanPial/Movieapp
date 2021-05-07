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
import database.Repository;
import server.ApiInterface;
import server.ApiService;
import server.PagingFactory;
import server.PagingLoadBefore;

public class MainFragmentViewModel extends AndroidViewModel {

    private Repository repository;
    private LiveData<List<Movie>> movieList;

    LiveData<PagingLoadBefore> pagingLoadBeforeLiveData;
    private Executor executor;
    private LiveData<PagedList<Movie>> moviesPagedList;


    public MainFragmentViewModel(@NonNull Application application) {
        super(application);

        repository = new Repository(application);


    }



    /*---------------- Getting Popular Movies -----------------*/

    public LiveData<List<Movie>> getPopularMovies(String apikey, Long page) {
        return movieList = repository.getPopularMovies(apikey, page);
    }

    public LiveData<List<Movie>> getTopRatedMovies(String apikey, Long page) {
        return movieList = repository.getTopRatedMovies(apikey, page);
    }



}
