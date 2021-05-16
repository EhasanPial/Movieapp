package ui;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import java.util.List;

import Model.Movie;
import database.Repository;

public class FavViewModel extends AndroidViewModel {

    private Repository repository;
    private LiveData<List<Movie>> listLiveData;

    public FavViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);
        listLiveData = new LiveData<List<Movie>>() {
            @Override
            public void observe(@NonNull LifecycleOwner owner, @NonNull Observer<? super List<Movie>> observer) {
                super.observe(owner, observer);
            }
        }  ;

    }


    public LiveData<List<Movie>> getFav() {
        return listLiveData = repository.getFavMovies();
    }
}
