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
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.movieapp.R;

import java.util.List;

import Model.Movie;
import adapter.MovieAdapter;
import server.BaseString;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;


public class MainFragment extends Fragment implements MovieAdapter.ListClickListener {

    private MainFragmentViewModel mainFragmentViewModel;
    private final String apikey = BaseString.API;
    private MovieAdapter movieAdapter;
    private MovieAdapter TopmovieAdapter;



    /*------ UI ---- */
    RecyclerView pop_moives_recycler;
    private Button pop_see_all ;
    RecyclerView top_moives_recycler;
    private Button top_see_all ;

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

        pop_see_all = view.findViewById(R.id.popSeeAllID) ;

        top_moives_recycler = view.findViewById(R.id.top_recycler_id);
        GridLayoutManager gridLayoutManager2 = new GridLayoutManager(getContext(), 3);
        top_moives_recycler.setLayoutManager(gridLayoutManager2);
        top_moives_recycler.setHasFixedSize(true);
        TopmovieAdapter = new MovieAdapter(getActivity().getApplicationContext(), this::onListClick);
        top_moives_recycler.setAdapter(TopmovieAdapter);

        pop_see_all = view.findViewById(R.id.popSeeAllID) ;
        top_see_all = view.findViewById(R.id.topSeeAllID) ;

        loadPopularMovies();
        loadTopMovies();


        pop_see_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController = Navigation.findNavController(v) ;
                navController.navigate(R.id.action_mainFragment_to_popularMovies);
            }
        });

        top_see_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController = Navigation.findNavController(v) ;
                navController.navigate(R.id.action_mainFragment_to_topRatedMovies);
            }
        });

    }

    private void loadTopMovies() {

        mainFragmentViewModel.getTopRatedMovies(apikey, (long) 1).observe(getViewLifecycleOwner(), movies -> {

            if (movies.isEmpty())
                Toast.makeText(getContext(), "Empty", Toast.LENGTH_SHORT).show();
            else {
                Toast.makeText(getContext(), "Not Empty", Toast.LENGTH_SHORT).show();
                TopmovieAdapter.setMovieItem(movies);
            }

        });

    }


    public void loadPopularMovies() {

        mainFragmentViewModel = ViewModelProviders.of(this).get(MainFragmentViewModel.class);

        mainFragmentViewModel.getPopularMovies(apikey, (long) 1).observe(getViewLifecycleOwner(), movies -> {

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