package Model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class ReviewList {

    @SerializedName("results")
    private List<Review> result = null;

    @SerializedName("total_results")
    private int total_results;

    @SerializedName("id")
    private Integer id;

    public ReviewList() {
    }

    public ReviewList(ArrayList<Review> review, int total_results, Integer id) {
        this.result = review;
        this.total_results = total_results;
        this.id = id;
    }

    public List<Review> getResult() {
        return result;
    }

    public void setResult(ArrayList<Review> result) {
        this.result = result;
    }

    public int getTotal_results() {
        return total_results;
    }

    public void setTotal_results(int total_results) {
        this.total_results = total_results;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
