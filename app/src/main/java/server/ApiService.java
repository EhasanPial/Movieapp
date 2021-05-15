package server;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import Model.Cast;
import Model.CastList;
import Model.Movie;
import Model.MovieDetails;
import Model.MovieList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiService {

    private static ApiService apiService = null;
    private static ApiInterface apiInterface;
    public static final String MOVIES_BASE_URL = "https://api.themoviedb.org/3/";

    private ApiService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(MOVIES_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiInterface = retrofit.create(ApiInterface.class);

    }


    public synchronized static ApiService getInstance() {
        if (apiService == null) {
            apiService = new ApiService();
        }
        return apiService;
    }

    public static ApiInterface getApiInterface() {
        if (apiService == null) {
            apiService = new ApiService();
        }
        return apiInterface;
    }

    public LiveData<List<Movie>> getPopularMovies(String apiKey, Long page) {
        final MutableLiveData<List<Movie>> mutableLiveData = new MutableLiveData<>();
        apiInterface.getpopularmovies(apiKey, page).enqueue(new Callback<MovieList>() {
            @Override
            public void onResponse(Call<MovieList> call, Response<MovieList> response) {
                int statusCode = response.code();
                Log.d(ApiService.class.getSimpleName(), "onResponse: " + statusCode);
                if (response.isSuccessful())
                    mutableLiveData.setValue(response.body().getMovieResults());
            }

            @Override
            public void onFailure(Call<MovieList> call, Throwable t) {
                mutableLiveData.setValue(null);
                Log.e(ApiService.class.getSimpleName(), "onResponse: " + t.getMessage());
                t.printStackTrace();
            }
        });
        return mutableLiveData;


    }

    public LiveData<List<Movie>> getTopRatedMovies(String apiKey, Long page) {
        final MutableLiveData<List<Movie>> mutableLiveData = new MutableLiveData<>();
        apiInterface.gettopratedmovies(apiKey, page).enqueue(new Callback<MovieList>() {
            @Override
            public void onResponse(Call<MovieList> call, Response<MovieList> response) {
                int statusCode = response.code();
                Log.d(ApiService.class.getSimpleName(), "onResponse: " + statusCode);
                if (response.isSuccessful())
                    mutableLiveData.setValue(response.body().getMovieResults());
            }

            @Override
            public void onFailure(Call<MovieList> call, Throwable t) {
                mutableLiveData.setValue(null);
                Log.e(ApiService.class.getSimpleName(), "onResponse: " + t.getMessage());
                t.printStackTrace();
            }
        });
        return mutableLiveData;


    }

    public LiveData<MovieDetails> getMovieDetails(String apiKey, int id) {
        final MutableLiveData<MovieDetails> movieDetailsMutableLiveData = new MutableLiveData<>();
        apiInterface.getMovieDetails(id, apiKey).enqueue(new Callback<MovieDetails>() {
            @Override
            public void onResponse(Call<MovieDetails> call, Response<MovieDetails> response) {
                int statusCode = response.code();
                Log.d(ApiService.class.getSimpleName(), "onResponse: " + statusCode);
                if (response.isSuccessful())
                    movieDetailsMutableLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<MovieDetails> call, Throwable t) {
                movieDetailsMutableLiveData.setValue(null);
                Log.e(ApiService.class.getSimpleName(), "onResponse: " + t.getMessage());
                t.printStackTrace();
            }
        });

        return movieDetailsMutableLiveData;
    }

    public LiveData<List<Cast>> getCredits(String apiKey, int id) {
        final MutableLiveData<List<Cast>> castMutableLiveData = new MutableLiveData<>();
        apiInterface.getCredits(id, apiKey).enqueue(new Callback<CastList>() {
            @Override
            public void onResponse(Call<CastList> call, Response<CastList> response) {
                int statusCode = response.code();
                Log.d(ApiService.class.getSimpleName(), "onResponse: " + statusCode);
                if (response.isSuccessful())
                    castMutableLiveData.setValue(response.body().getCasts());
            }

            @Override
            public void onFailure(Call<CastList> call, Throwable t) {
                castMutableLiveData.setValue(null);
                Log.e(ApiService.class.getSimpleName(), "onResponse: " + t.getMessage());
                t.printStackTrace();
            }
        });

        return castMutableLiveData;
    }

}