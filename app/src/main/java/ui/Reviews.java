package ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.movieapp.R;

import java.util.List;

import Model.Review;
import adapter.ReviewAdapter;
import server.BaseString;

public class Reviews extends Fragment {

    private DetailsViewModel detailsViewModel;
    private Integer id;
    private ReviewAdapter reviewAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_reviews, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerView = view.findViewById(R.id.review_recy_id);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        reviewAdapter = new ReviewAdapter(getContext());


        detailsViewModel = new ViewModelProvider(requireActivity()).get(DetailsViewModel.class);

        detailsViewModel.getId().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                detailsViewModel.getReviews(BaseString.API, integer).observe(getViewLifecycleOwner(), new Observer<List<Review>>() {
                    @Override
                    public void onChanged(List<Review> reviews) {

                        if (reviews == null)
                            Log.d("Reviews", "NULL ");
                        reviewAdapter.setReviewList(reviews);
                        recyclerView.setAdapter(reviewAdapter);
                    }
                });
            }
        });


    }
}