package ui;

import android.app.Application;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.lifecycle.ViewModelStore;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.movieapp.R;

import java.util.List;

import Model.Movie;
import adapter.MovieAdapter;
import server.BaseString;


public class MainFragment extends Fragment implements MovieAdapter.ListClickListener {

    private MainFragmentViewModel mainFragmentViewModel;
    private final String apikey = BaseString.API;
    private MovieAdapter movieAdapter;


    /*------ UI ---- */
    RecyclerView pop_moives_recycler;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        /* ----- Pop Movies Recycler ----*/
        pop_moives_recycler = view.findViewById(R.id.popular_moives_recycler_id);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 3);
        pop_moives_recycler.setLayoutManager(gridLayoutManager);
        pop_moives_recycler.setHasFixedSize(true);
        movieAdapter = new MovieAdapter(getActivity().getApplicationContext(), this::onListClick);
        pop_moives_recycler.setAdapter(movieAdapter);

        loadPopularMovies();

    }

    public void loadPopularMovies() {

        mainFragmentViewModel = ViewModelProviders.of(this).get(MainFragmentViewModel.class);

        mainFragmentViewModel.getPopularMovies(apikey).observe(getViewLifecycleOwner(), movies -> {

            if (movies.isEmpty())
                Toast.makeText(getContext(), "Empty", Toast.LENGTH_SHORT).show();
            else {
                Toast.makeText(getContext(), "Not Empty", Toast.LENGTH_SHORT).show();
                movieAdapter.setMovieItem(movies);
            }

        });

    }

    @Override
    public void onListClick(Movie movie) {
        Toast.makeText(getContext(), movie.getTitle(), Toast.LENGTH_SHORT).show();
    }
}