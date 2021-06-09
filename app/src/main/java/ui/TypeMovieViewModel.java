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
import server.ApiInterface;
import server.ApiService;
import server.TopRatedLoadBefore;
import server.TopRatedPagingFactory;
import server.TypeLoadBefore;
import server.TypeMoviePagingFactory;

public class TypeMovieViewModel extends AndroidViewModel {


    private LiveData<List<Movie>> movieList;

    MutableLiveData<TypeLoadBefore> pagingLoadBeforeLiveData;
    private Executor executor;
    private LiveData<PagedList<Movie>> moviesPagedList;
    private TypeMoviePagingFactory factory;

    public TypeMovieViewModel(@NonNull Application application) {
        super(application);


        ApiInterface apiInterface = ApiService.getApiInterface();
        factory = new TypeMoviePagingFactory(apiInterface, application);
        pagingLoadBeforeLiveData = factory.getMutableLiveData();

        PagedList.Config config = (new PagedList.Config.Builder())
                .setEnablePlaceholders(true)
                .setInitialLoadSizeHint(20)
                .setPageSize(20)
                .setPrefetchDistance(4)
                .build();

        executor = Executors.newFixedThreadPool(5);

        moviesPagedList = (new LivePagedListBuilder<Long, Movie>(factory, config))
                .setFetchExecutor(executor)
                .build();
    }

    public void setGenre(String genre) {
        factory.setGenre(genre);
    }
    /*---------------- Getting Popular Movies -----------------*/

    public LiveData<PagedList<Movie>> getMoviesPagedList(String genre) {

        return moviesPagedList;
    }

}
