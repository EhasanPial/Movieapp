package server;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.PageKeyedDataSource;

import java.util.ArrayList;
import java.util.List;

import Model.Movie;
import Model.MovieList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PagingLoadBefore extends PageKeyedDataSource<Long, Movie> {

    private ApiInterface apiInterface;
    private Application application;

    public PagingLoadBefore(ApiInterface movieDataService, Application application) {
        this.apiInterface = movieDataService;
        this.application = application;
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Long> params, @NonNull LoadInitialCallback<Long, Movie> callback) {

        apiInterface = ApiService.getApiInterface();


        apiInterface.getpopularmovies(BaseString.API, 1).enqueue(new Callback<MovieList>() {
            @Override
            public void onResponse(Call<MovieList> call, Response<MovieList> response) {
                int statusCode = response.code();
                Log.d(PagingLoadBefore.class.getSimpleName(), "onResponse: " + statusCode);
                if (response.isSuccessful()) {
                    MovieList mutableLiveData = response.body();
                    List<Movie> movieList;
                    movieList = mutableLiveData.getMovieResults() ;
                    callback.onResult(movieList, null, (long) 2);
                }
            }

            @Override
            public void onFailure(Call<MovieList> call, Throwable t) {

                Log.e(PagingLoadBefore.class.getSimpleName(), "onResponse: " + t.getMessage());
                t.printStackTrace();
            }
        });

    }

    @Override
    public void loadBefore(@NonNull LoadParams<Long> params, @NonNull LoadCallback<Long, Movie> callback) {

    }

    @Override
    public void loadAfter(@NonNull LoadParams<Long> params, @NonNull LoadCallback<Long, Movie> callback) {
        apiInterface = ApiService.getApiInterface();


        apiInterface.getpopularmovies(BaseString.API, params.key).enqueue(new Callback<MovieList>() {
            @Override
            public void onResponse(Call<MovieList> call, Response<MovieList> response) {
                int statusCode = response.code();
                Log.d(PagingLoadBefore.class.getSimpleName(), "onResponse: " + statusCode);
                if (response.isSuccessful()) {
                    MovieList mutableLiveData = response.body();
                    List<Movie> movieList;
                    movieList = mutableLiveData.getMovieResults() ;
                    callback.onResult(movieList, params.key + 1);
                }
            }

            @Override
            public void onFailure(Call<MovieList> call, Throwable t) {

                Log.e(PagingLoadBefore.class.getSimpleName(), "onResponse: " + t.getMessage());
                t.printStackTrace();
            }
        });
    }
}
