package ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.movieapp.R;

import Model.Movie;
import adapter.MovieAdapter;
import adapter.PagingAdapterMovies;


public class PopularMovies extends Fragment implements  PagingAdapterMovies.ListClickListener {

    private PagingAdapterMovies pagingAdapterMovies ;
    private  PopularMoviesViewModel popularMoviesViewModel ;
    
    //////// UI //////////
    private RecyclerView recyclerView ;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_popular_movies, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.popular_movie_fragment_recycler_id) ;


        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 3);
        recyclerView.setLayoutManager(gridLayoutManager);

        recyclerView.setHasFixedSize(true);
        pagingAdapterMovies = new PagingAdapterMovies(getActivity().getApplicationContext(), this::onListClick);
        recyclerView.setAdapter(pagingAdapterMovies);

        loadPopularMovies();

    }

    private void loadPopularMovies() {
        popularMoviesViewModel = ViewModelProviders.of(this).get(PopularMoviesViewModel.class);

        popularMoviesViewModel.getMoviesPagedList().observe(getViewLifecycleOwner(), new Observer<PagedList<Movie>>() {
            @Override
            public void onChanged(PagedList<Movie> movies) {
                pagingAdapterMovies.submitList(movies);
            }
        });

    }

    @Override
    public void onListClick(Movie movie) {
        Toast.makeText(getContext(), movie.getTitle(), Toast.LENGTH_SHORT).show();
    }
}