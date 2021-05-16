package ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
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
import android.widget.LinearLayout;

import com.example.movieapp.R;

import java.util.List;

import Model.Movie;
import adapter.MovieAdapter;
import server.BaseString;

public class Search extends Fragment implements MovieAdapter.ListClickListener {

   private SearchView searchView ;
   private SearchViewModel searchViewModel ;
   private MovieAdapter movieAdapter ;
   private RecyclerView recyclerView ;
   private NavController navController ;
   private LinearLayout linearLayout ;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        searchView = view.findViewById(R.id.search_view_id);
        recyclerView = view.findViewById(R.id.search_recycler_id);
        linearLayout = view.findViewById(R.id.search_linear);

        navController = Navigation.findNavController(view) ;

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 3) ;
        recyclerView.setLayoutManager(gridLayoutManager);
        movieAdapter = new MovieAdapter(getContext(), this, false) ;

        searchViewModel = new ViewModelProvider(requireActivity()).get(SearchViewModel.class) ;

        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {

                        searchViewModel.getSearchedMovies(BaseString.API, query).observe(getViewLifecycleOwner(), new Observer<List<Movie>>() {
                            @Override
                            public void onChanged(List<Movie> movies) {
                                movieAdapter.setMovieItem(movies);
                                recyclerView.setAdapter(movieAdapter);

                            }
                        });

                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {


                        searchViewModel.getSearchedMovies(BaseString.API, newText).observe(getViewLifecycleOwner(), new Observer<List<Movie>>() {
                            @Override
                            public void onChanged(List<Movie> movies) {
                                movieAdapter.setMovieItem(movies);
                                recyclerView.setAdapter(movieAdapter);

                            }
                        });

                        return false;
                    }
                });
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

               searchViewModel.getSearchedMovies(BaseString.API, query).observe(getViewLifecycleOwner(), new Observer<List<Movie>>() {
                   @Override
                   public void onChanged(List<Movie> movies) {
                       movieAdapter.setMovieItem(movies);
                       recyclerView.setAdapter(movieAdapter);

                   }
               });

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {


                searchViewModel.getSearchedMovies(BaseString.API, newText).observe(getViewLifecycleOwner(), new Observer<List<Movie>>() {
                    @Override
                    public void onChanged(List<Movie> movies) {
                        movieAdapter.setMovieItem(movies);
                        recyclerView.setAdapter(movieAdapter);

                    }
                });

                return false;
            }
        });
    }

    @Override
    public void onListClick(Movie movie) {
       SearchDirections.ActionSearchMenuToDetailsActivity action = SearchDirections.actionSearchMenuToDetailsActivity(movie) ;
       navController.navigate(action);
    }
}