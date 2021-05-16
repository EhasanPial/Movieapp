package ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.movieapp.R;

import java.util.List;

import Model.Movie;
import adapter.MovieAdapter;


public class Favourites extends Fragment implements MovieAdapter.ListClickListener {

    private RecyclerView recyclerView;
    private MovieAdapter movieAdapter ;
    private FavViewModel favViewModel ;
    private NavController navController ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         return inflater.inflate(R.layout.fragment_favourites, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.fav_recycler_id);
        movieAdapter = new MovieAdapter(getContext(), this,false) ;
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 3) ;
        recyclerView.setLayoutManager(gridLayoutManager);

        navController = Navigation.findNavController(view) ;

        favViewModel = new ViewModelProvider(requireActivity()).get(FavViewModel.class) ;

        favViewModel.getFav().observe(getViewLifecycleOwner(), new Observer<List<Movie>>() {
            @Override
            public void onChanged(List<Movie> movies) {
                movieAdapter.setMovieItem(movies);
                recyclerView.setAdapter(movieAdapter);
            }
        });

    }

    @Override
    public void onListClick(Movie movie) {
         FavouritesDirections.ActionFavouritesToDetailsActivity action = FavouritesDirections.actionFavouritesToDetailsActivity(movie) ;
         navController.navigate(action);
    }
}