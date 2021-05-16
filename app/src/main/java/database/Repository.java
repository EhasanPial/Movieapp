package database;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import java.util.List;

import Model.Cast;
import Model.Movie;
import Model.MovieDetails;
import Model.Review;
import server.ApiService;

public class Repository {
    private final ApiService apiService = ApiService.getInstance();
    private final MovieDao mDao;
    private final LiveData<List<Movie>> mFavMovies;
    private final AppExecutors mExecutors = AppExecutors.getInstance();

    public Repository(Application application) {

        MovieDB db = MovieDB.getDatabase(application);
        mDao = db.movieDao();
        mFavMovies = mDao.getAllMovies();
    }


    public LiveData<List<Movie>> getFavMovies() {
        return mFavMovies;
    }

    public void insert(final Movie movie) {
        mExecutors.diskIO().execute(new Runnable() {
            @Override
            public void run() {
                mDao.insertMovie(movie);
                Log.d("Room","Inserted") ;

            }
        });
    }

    public void delete(final Movie movie) {
        mExecutors.diskIO().execute(new Runnable() {
            @Override
            public void run() {
                mDao.delete(movie);
                Log.d("Room","Deleted") ;

            }
        });
    }


    public LiveData<Movie> getMovieById(int id) {
        return mDao.getMovieById(id);
    }

    /* ---- Network ---- */
    public LiveData<List<Movie>> getPopularMovies(String apiKey, Long page) {
        return apiService.getPopularMovies(apiKey, page);

    }

    public LiveData<List<Movie>> getTopRatedMovies(String apiKey, Long page) {
        return apiService.getTopRatedMovies(apiKey, page);
    }

    public LiveData<MovieDetails> getMovieDetails(String apiKey, int id) {
        return apiService.getMovieDetails(apiKey, id);
    }

    public LiveData<List<Cast>> getCast(String apiKey, int id) {
        return apiService.getCredits(apiKey, id);
    }

    public LiveData<List<Review>> getRevies(String apiKey, int id) {
        return apiService.getReviews(apiKey, id);
    }

    public LiveData<List<Movie>> getSearchedMovies(String apiKey, String query) {
        return  apiService.getSearchedMovies(apiKey, query) ;
    }
}
