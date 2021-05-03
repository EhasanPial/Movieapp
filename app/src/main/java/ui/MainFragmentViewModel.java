package ui;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import Model.Movie;
import database.Repository;

public class MainFragmentViewModel extends AndroidViewModel {

    private Repository repository ;
    private LiveData<List<Movie>> movieList;

    public MainFragmentViewModel(@NonNull Application application) {
        super(application);

        repository = new Repository(application) ;
    }



    /*---------------- Getting Popular Movies -----------------*/

    public LiveData<List<Movie>> getPopularMovies(String apikey)
    {
        return  movieList = repository.getPopularMovies(apikey) ;
    }
}
