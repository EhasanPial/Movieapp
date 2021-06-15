package server;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import Model.Cast;
import Model.CastList;
import Model.Movie;
import Model.MovieDetails;
import Model.MovieList;
import Model.Review;
import Model.ReviewList;
import Model.Video;
import Model.VideoList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ui.Search;

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
    public LiveData<List<Video>> getVideo(String apiKey, int id) {
        final MutableLiveData<List<Video>> mutableLiveData = new MutableLiveData<>();
        apiInterface.getVideos(id, apiKey).enqueue(new Callback<VideoList>() {
            @Override
            public void onResponse(Call<VideoList> call, Response<VideoList> response) {
                int statusCode = response.code();

                if (response.isSuccessful()) {
                    mutableLiveData.setValue(response.body().getVideoList());
                    Log.d("Video", "onResponse: " + statusCode);
                }
            }

            @Override
            public void onFailure(Call<VideoList> call, Throwable t) {
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
                if (response.body().getCasts() == null) {
                    Log.d("Reviews", "null Cast");
                }
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



    public LiveData<List<Review>> getReviews(String apiKey, int id) {
            final MutableLiveData<List<Review>> reviews = new MutableLiveData<>();
            apiInterface.getReviews(id, apiKey).enqueue(new Callback<ReviewList>() {
            @Override
            public void onResponse(@NotNull Call<ReviewList> call, @NotNull Response<ReviewList> response) {
                int statusCode = response.code();
                Log.d("Reviews", "onResponse: " + statusCode);
                if (response.isSuccessful())
                    reviews.setValue(response.body().getResult());

               else if (response.body().getResult() == null) {
                    Log.d("Reviews", "null service");
                }
            }

            @Override
            public void onFailure(@NotNull Call<ReviewList> call, Throwable t) {
                reviews.setValue(null);
                Log.e("Reviews", "onResponse: " + t.getMessage());
                t.printStackTrace();
            }
        });

        return reviews;
    }


  public LiveData<List<Movie>> getSearchedMovies(String apiKey, String query) {
            final MutableLiveData<List<Movie>> reviews = new MutableLiveData<>();
            apiInterface.getSearchedMovies(query, apiKey).enqueue(new Callback<MovieList>() {
            @Override
            public void onResponse(@NotNull Call<MovieList> call, @NotNull Response<MovieList> response) {
                int statusCode = response.code();
                Log.d("Search", "onResponse: " + statusCode);
                if (response.isSuccessful() )
                    reviews.setValue(response.body().getMovieResults());
            }

            @Override
            public void onFailure(@NotNull Call<MovieList> call, Throwable t) {
                reviews.setValue(null);
                Log.e("Search", "onResponse: " + t.getMessage());
                t.printStackTrace();
            }
        });

        return reviews;
    }






}