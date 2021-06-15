package ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.movieapp.R;

import java.util.List;

import Model.Movie;
import adapter.UpcommingAdapter;
import server.BaseString;


public class UpcommingMovies extends Fragment {


    private RecyclerView recyclerView;
    private UpcommingModel upcommingModel;
    private List<Movie> movies;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_upcomming_movies, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        upcommingModel =  ViewModelProviders.of(this).get(UpcommingModel.class);
        recyclerView = view.findViewById(R.id.upComming_recy);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);

        UpcommingAdapter upcommingAdapter = new UpcommingAdapter();

        upcommingModel.getupcommingMovie(BaseString.API, 1L).observe(getViewLifecycleOwner(), new Observer<List<Movie>>() {
            @Override
            public void onChanged(List<Movie> movies) {
                if (movies.isEmpty()) {

                } else {
                    upcommingAdapter.setMovie(movies);

                    recyclerView.setAdapter(upcommingAdapter);
                }
            }
        });

        recyclerView.setAdapter(upcommingAdapter);

    }
}
