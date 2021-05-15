package Model;

import java.util.List;

public class MovieDetails {

    List<Genre> genres;
    String release_date;
    int runtime;
    float budget;
    float revenue;
    String status;
    String original_title;

    public MovieDetails(List<Genre> genres, String release_date, int runtime, float budget, float revenue, String status, String original_title) {
        this.genres = genres;
        this.release_date = release_date;
        this.runtime = runtime;
        this.budget = budget;
        this.revenue = revenue;
        this.status = status;
        this.original_title = original_title;
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

    public float getBudget() {
        return budget;
    }

    public float getRevenue() {
        return revenue;
    }

    public String getStatus() {
        return status;
    }

    public String getOriginal_title() {
        return original_title;
    }
}
