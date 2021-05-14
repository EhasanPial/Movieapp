package Model;

import java.util.List;

public class MovieDetails {

    List<Genre> genres ;
    String release_date ;
    int runtime;

    public MovieDetails(List<Genre> genres, String release_date, int runtime) {
        this.genres = genres;
        this.release_date = release_date;
        this.runtime = runtime;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public String getRelease_date() {
        return release_date;
    }

    public int getRuntime() {
        return runtime;
    }
}
