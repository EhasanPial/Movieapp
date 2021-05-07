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

public class PopularMoviesViewModel extends AndroidViewModel {


    private LiveData<List<Movie>> movieList;

    LiveData<PagingLoadBefore> pagingLoadBeforeLiveData;
    private Executor executor;
    private LiveData<PagedList<Movie>> moviesPagedList;


    public PopularMoviesViewModel(@NonNull Application application) {
        super(application);


        ApiInterface apiInterface=   ApiService.getApiInterface();
        PagingFactory factory=new PagingFactory(apiInterface,application);
        pagingLoadBeforeLiveData = factory.getMutableLiveData();

        PagedList.Config config=(new PagedList.Config.Builder())
                .setEnablePlaceholders(true)
                .setInitialLoadSizeHint(20)
                .setPageSize(20)
                .setPrefetchDistance(4)
                .build();

        executor= Executors.newFixedThreadPool(5);

        moviesPagedList = (new LivePagedListBuilder<Long,Movie>(factory,config))
                .setFetchExecutor(executor)
                .build();
    }



    /*---------------- Getting Popular Movies -----------------*/

    public LiveData<PagedList<Movie>> getMoviesPagedList() {
        return moviesPagedList;
    }

}
