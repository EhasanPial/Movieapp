package ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.movieapp.R;

import java.util.List;

import Model.Movie;
import Model.MovieDetails;
import Model.Review;
import adapter.ReviewAdapter;
import server.BaseString;


public class Info extends Fragment {

    private DetailsViewModel detailsViewModel;
    private TextView overView, avg, original_title, status, rev, budget, relesase_date;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_info, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ///// UI /////
        overView = view.findViewById(R.id.overView_id_info);
        avg = view.findViewById(R.id.avg_rating);
        original_title = view.findViewById(R.id.Original_title_id);
        status = view.findViewById(R.id.status_id);
        rev = view.findViewById(R.id.revenue_id);
        relesase_date = view.findViewById(R.id.release_date_id);
        budget = view.findViewById(R.id.budget_id);






        detailsViewModel = new ViewModelProvider(requireActivity()).get(DetailsViewModel.class);


            detailsViewModel.getMovie().observe(getViewLifecycleOwner(), movie -> {
                overView.setText(movie.getOverview());
                avg.setText(movie.getVote_average()+"");

            });
            detailsViewModel.getMovieDetails().observe(getViewLifecycleOwner(), new Observer<MovieDetails>() {
                @Override
                public void onChanged(MovieDetails movieDetails) {
                    original_title.setText(movieDetails.getOriginal_title());
                    status.setText(movieDetails.getStatus());
                    relesase_date.setText(movieDetails.getRelease_date());
                    rev.setText(movieDetails.getRevenue()+"");
                    budget.setText(movieDetails.getBudget()+"");


                }
            });

    }
}