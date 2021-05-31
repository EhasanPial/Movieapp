package ui;

import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.util.TimeUtils;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.movieapp.R;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import com.squareup.picasso.Picasso;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import Model.Genre;
import Model.Movie;
import Model.MovieDetails;
import Model.Review;
import adapter.ViewPageAdapter;
import server.ApiService;
import server.BaseString;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;

import android.util.Log;


public class DetailsActivity extends Fragment {

    //// ui ////

    private TextView movieName, runtime, genre, toolbar_movie;
    private ImageView backPoster;
    private Movie movie;
    private Toolbar toolbar;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private TextView genreTextView;
    private ViewPager viewPager;
    private ViewPageAdapter viewPageAdapter;
    private TabLayout tabLayout;
    private FloatingActionButton floatingActionButton;
    private boolean isFav;


    private int id;
    private DetailsViewModel detailsViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_details_activity, container, false);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        backPoster = view.findViewById(R.id.detail_header_movie_backdrop);


        runtime = view.findViewById(R.id.detail_movie_runtime);
        genre = view.findViewById(R.id.detail_movie_certification_genre);
        toolbar = view.findViewById(R.id.detail_toolbar_main);
        collapsingToolbarLayout = view.findViewById(R.id.collapsing_toolbar);
        genreTextView = view.findViewById(R.id.detail_movie_certification_genre);
        floatingActionButton = view.findViewById(R.id.floating_id);


        NavController navController = Navigation.findNavController(view);

        NavigationUI.setupWithNavController(toolbar, navController);


        DetailsActivityArgs detailsActivityArgs = DetailsActivityArgs.fromBundle(getArguments());

        detailsActivityArgs.getMoviepass();
        movie = detailsActivityArgs.getMoviepass();
        id = movie.getId();

        detailsViewModel = new ViewModelProvider(requireActivity()).get(DetailsViewModel.class);
        detailsViewModel.selectMovie(movie);

        detailsViewModel.passId(id);
        loadMovieDetails();

        addFav();

        collapsingToolbarLayout.setTitle(movie.getTitle());
        collapsingToolbarLayout.setCollapsedTitleTextColor(getResources().getColor(R.color.white));
        //  movieName.setText(movie.getTitle()) ;
        //     toolbar.setTitle(movie.getTitle());

        Picasso.get()
                .load(BaseString.POSTER_PATH + movie.getPoster_path())
                .error(R.drawable.bg_overlay)
                .into(backPoster);


        viewPager = view.findViewById(R.id.viewPager);
        viewPageAdapter = new ViewPageAdapter(getChildFragmentManager());
        viewPager.setAdapter(viewPageAdapter);
        tabLayout = view.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager, true);


        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


    }

    private void addFav() {
        int id = movie.getId();

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isFav) {
                    floatingActionButton.setImageDrawable(getContext().getDrawable(R.drawable.ic_baseline_favorite_24));
                    detailsViewModel.saveMovie(movie);

                    Snackbar.make(getView(),"Added", BaseTransientBottomBar.LENGTH_SHORT).show(); ;

                } else {
                    floatingActionButton.setImageDrawable(getContext().getDrawable(R.drawable.ic_baseline_favorite_border_24));
                    detailsViewModel.deleteMovie(movie);
                    Snackbar.make(getView(),"Removed", BaseTransientBottomBar.LENGTH_SHORT).show(); ;


                }
            }
        });
    }

    private void checkFav() {
        int id = movie.getId();
        detailsViewModel.loadFavById(id).observe(getViewLifecycleOwner(), new Observer<Movie>() {
            @Override
            public void onChanged(@Nullable Movie movie) {
                if (movie == null) {
                    floatingActionButton.setImageDrawable(getContext().getDrawable(R.drawable.ic_baseline_favorite_border_24));
                    isFav = false;

                } else {
                    floatingActionButton.setImageDrawable(getContext().getDrawable(R.drawable.ic_baseline_favorite_24));
                    isFav = true;

                }
            }
        });
    }

    private void loadMovieDetails() {
        checkFav();
        //  addFav() ;
        detailsViewModel.getMovieDetails(BaseString.API, id).observe(getViewLifecycleOwner(), new Observer<MovieDetails>() {
            @Override
            public void onChanged(MovieDetails movieDetails) {

                detailsViewModel.selectMovieDetails(movieDetails);

                /* --- Setting Genre ---- */
                List<Genre> genreList = movieDetails.getGenres();
                String setGenre = "";
                for (int i = 0; i < genreList.size(); i++) {
                    setGenre += genreList.get(i).getName();
                    if (i != genreList.size() - 1)
                        setGenre += ", ";
                }
                genreTextView.setText(setGenre);

                int hours = movieDetails.getRuntime() / 60;
                int minutes = movieDetails.getRuntime() % 60;


                if (minutes != 0)
                    runtime.setText(hours + "H " + " " + movieDetails.getRelease_date());
                else
                    runtime.setText(hours + "H " + minutes + "M " + movieDetails.getRelease_date());

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
    }

    @Override
    public void onStop() {
        super.onStop();
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
        movie = null;
    }
}