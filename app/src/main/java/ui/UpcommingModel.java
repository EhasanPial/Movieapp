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

public class UpcommingModel extends AndroidViewModel {

    private final Repository repository;


    public UpcommingModel(@NonNull Application application) {
        super(application);

        repository = new Repository(application);



    }



    /*---------------- Getting Popular Movies -----------------*/


    public LiveData<List<Movie>> getupcommingMovie(String apikey, Long page) {

        return  repository.getupcommingMovies(apikey, page);
    }



}
