package ui;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import Model.Cast;
import Model.Movie;
import Model.MovieDetails;
import Model.Review;
import database.Repository;

public class SearchViewModel extends AndroidViewModel {

    private Repository repository;
    private LiveData<List<Movie>> movieList;



    public SearchViewModel(@NonNull Application application) {
        super(application);

        repository = new Repository(application);



    }


    public LiveData<List<Movie>> getSearchedMovies(String api, String query)
    {
          return movieList = repository.getSearchedMovies(api,query) ;

    }

}
