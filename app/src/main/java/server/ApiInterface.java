package server;

import java.util.List;

import Model.Movie;
import Model.MovieList;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("movie/popular")
    Call<MovieList> getpopularmovies(@Query("api_key") String apiKey, @Query("page") long page) ;

    @GET("movie/top_rated")
    Call<MovieList> gettopratedmovies(@Query("api_key") String apiKey,  @Query("page") long page ) ;
}
