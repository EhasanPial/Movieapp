package server;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.paging.PageKeyedDataSource;

import java.util.List;

import Model.Movie;
import Model.MovieList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TypeLoadBefore extends PageKeyedDataSource<Long, Movie> {
    private ApiInterface apiInterface;
    private Application application;
    private String genre;

    public TypeLoadBefore(ApiInterface movieDataService, Application application, String genre) {
        this.apiInterface = movieDataService;
        this.application = application;
        this.genre = genre ;
    }

    @Override
    public void loadInitial(@NonNull PageKeyedDataSource.LoadInitialParams<Long> params, @NonNull PageKeyedDataSource.LoadInitialCallback<Long, Movie> callback) {

        apiInterface = ApiService.getApiInterface();

        apiInterface.getTypeMovie(BaseString.API, genre, 1).enqueue(new Callback<MovieList>() {
            @Override
            public void onResponse(Call<MovieList> call, Response<MovieList> response) {
                int statusCode = response.code();
                Log.d("type", genre);
                if (response.isSuccessful()) {
                    MovieList mutableLiveData = response.body();
                    List<Movie> movieList;
                    movieList = mutableLiveData.getMovieResults() ;
                    callback.onResult(movieList, null, (long) 2);
                }
            }

            @Override
            public void onFailure(Call<MovieList> call, Throwable t) {

                Log.e(TopRatedLoadBefore.class.getSimpleName(), "onResponse: " + t.getMessage());
                t.printStackTrace();
            }
        });


    }

    @Override
    public void loadBefore(@NonNull PageKeyedDataSource.LoadParams<Long> params, @NonNull PageKeyedDataSource.LoadCallback<Long, Movie> callback) {

    }

    @Override
    public void loadAfter(@NonNull PageKeyedDataSource.LoadParams<Long> params, @NonNull PageKeyedDataSource.LoadCallback<Long, Movie> callback) {
        apiInterface = ApiService.getApiInterface();

        apiInterface.getTypeMovie(BaseString.API, genre, params.key).enqueue(new Callback<MovieList>() {
            @Override
            public void onResponse(Call<MovieList> call, Response<MovieList> response) {
                int statusCode = response.code();
                Log.d(TopRatedLoadBefore.class.getSimpleName(), "onResponse: " + statusCode);
                if (response.isSuccessful()) {
                    MovieList mutableLiveData = response.body();
                    List<Movie> movieList;
                    movieList = mutableLiveData.getMovieResults() ;
                    callback.onResult(movieList, params.key + 1);
                }
            }

            @Override
            public void onFailure(Call<MovieList> call, Throwable t) {

                Log.e(TopRatedLoadBefore.class.getSimpleName(), "onResponse: " + t.getMessage());
                t.printStackTrace();
            }
        });

    }
}