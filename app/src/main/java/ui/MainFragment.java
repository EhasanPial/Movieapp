package ui;

import android.app.Application;
import android.graphics.Color;
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
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.List;

import Model.Movie;
import adapter.MovieAdapter;
import adapter.SliderAdapter;
import server.BaseString;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;


public class MainFragment extends Fragment implements MovieAdapter.ListClickListener {

    private MainFragmentViewModel mainFragmentViewModel;
    private final String apikey = BaseString.API;
    private MovieAdapter movieAdapter;
    private MovieAdapter TopmovieAdapter;
    private SliderAdapter sliderAdapter;



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

        /* ----- Pop Movies Recycler ----*/
        top_moives_recycler = view.findViewById(R.id.top_recycler_id);
        GridLayoutManager gridLayoutManager2 = new GridLayoutManager(getContext(), 3);
        top_moives_recycler.setLayoutManager(gridLayoutManager2);
        top_moives_recycler.setHasFixedSize(true);
        TopmovieAdapter = new MovieAdapter(getActivity().getApplicationContext(), this::onListClick);
        top_moives_recycler.setAdapter(TopmovieAdapter);

        /* ----- seall id ----*/
        pop_see_all = view.findViewById(R.id.popSeeAllID) ;
        top_see_all = view.findViewById(R.id.topSeeAllID) ;

        /* ----- Slider ----*/

        SliderView sliderView = view.findViewById(R.id.imageSlider);

        sliderAdapter = new SliderAdapter(getContext());

        sliderView.setSliderAdapter(sliderAdapter);

        sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM); //set indicator animation by using IndicatorAnimationType. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        sliderView.setIndicatorSelectedColor(Color.WHITE);
        sliderView.setIndicatorUnselectedColor(Color.GRAY);
        sliderView.setScrollTimeInSec(4);
        sliderView.startAutoCycle();


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
                List<Movie> movies1 = new ArrayList<>();
                for(int i = 0 ; i<6; i++)
                {
                    movies1.add(movies.get(i)) ;
                }
                sliderAdapter.setMovies(movies1);
            }

        });


    }

    @Override
    public void onListClick(Movie movie) {
        Toast.makeText(getContext(), movie.getTitle(), Toast.LENGTH_SHORT).show();
    }
}