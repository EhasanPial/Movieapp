package ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.movieapp.R;

import Model.Movie;
import adapter.PagingAdapterMovies;


public class Type extends Fragment implements PagingAdapterMovies.ListClickListener {

    int type;
    private RecyclerView recyclerView;
    private PagingAdapterMovies pagingAdapterMovies;
    private TypeMovieViewModel typeMovieViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_type, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        TypeArgs typeArgs = TypeArgs.fromBundle(getArguments());
        type = typeArgs.getType();

        recyclerView = view.findViewById(R.id.type_recy_id);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 3);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setHasFixedSize(true);
        pagingAdapterMovies = new PagingAdapterMovies(getContext(), this);


        typeMovieViewModel = new ViewModelProvider(requireActivity()).get(TypeMovieViewModel.class);

        getActivity().getViewModelStore().clear();
        pagingAdapterMovies.submitList(null);


        if (type == 1) {

            getActivity().setTitle("Action");
            typeMovieViewModel = new ViewModelProvider(requireActivity()).get(TypeMovieViewModel.class);
            typeMovieViewModel.setGenre(28 + "");
            typeMovieViewModel.getMoviesPagedList(28 + "").observe(getViewLifecycleOwner(), new Observer<PagedList<Movie>>() {
                @Override
                public void onChanged(PagedList<Movie> movies) {
                    pagingAdapterMovies.submitList(movies);
                    recyclerView.setAdapter(pagingAdapterMovies);
                    pagingAdapterMovies.notifyDataSetChanged();

                }
            });
            Log.d("type", type + " ints");
        }
        if (type == 2) {
            typeMovieViewModel = new ViewModelProvider(requireActivity()).get(TypeMovieViewModel.class);
            typeMovieViewModel.setGenre(27 + "");
            typeMovieViewModel.getMoviesPagedList(27 + "").observe(getViewLifecycleOwner(), new Observer<PagedList<Movie>>() {
                @Override
                public void onChanged(PagedList<Movie> movies) {
                    pagingAdapterMovies.submitList(movies);
                    recyclerView.setAdapter(pagingAdapterMovies);
                    pagingAdapterMovies.notifyDataSetChanged();

                }
            });
            Log.d("type", type + " ints");
        }
        if (type == 3) {
            typeMovieViewModel = new ViewModelProvider(requireActivity()).get(TypeMovieViewModel.class);
            typeMovieViewModel.setGenre(10749 + "");
            typeMovieViewModel.getMoviesPagedList(10749 + "").observe(getViewLifecycleOwner(), new Observer<PagedList<Movie>>() {
                @Override
                public void onChanged(PagedList<Movie> movies) {
                    pagingAdapterMovies.submitList(movies);
                    recyclerView.setAdapter(pagingAdapterMovies);
                    pagingAdapterMovies.notifyDataSetChanged();

                }
            });
            Log.d("type", type + " ints");
        }
        if (type == 4) {
            typeMovieViewModel = new ViewModelProvider(requireActivity()).get(TypeMovieViewModel.class);
            typeMovieViewModel.setGenre(35 + "");
            typeMovieViewModel.getMoviesPagedList(35 + "").observe(getViewLifecycleOwner(), new Observer<PagedList<Movie>>() {
                @Override
                public void onChanged(PagedList<Movie> movies) {
                    pagingAdapterMovies.submitList(movies);
                    recyclerView.setAdapter(pagingAdapterMovies);
                    pagingAdapterMovies.notifyDataSetChanged();

                }
            });
            Log.d("type", type + " ints");
        }
        if (type == 5) {
            typeMovieViewModel = new ViewModelProvider(requireActivity()).get(TypeMovieViewModel.class);
            typeMovieViewModel.setGenre(878 + "");
            typeMovieViewModel.getMoviesPagedList(878 + "").observe(getViewLifecycleOwner(), new Observer<PagedList<Movie>>() {
                @Override
                public void onChanged(PagedList<Movie> movies) {
                    pagingAdapterMovies.submitList(movies);
                    recyclerView.setAdapter(pagingAdapterMovies);
                    pagingAdapterMovies.notifyDataSetChanged();


                }
            });
        }
        if (type == 6) {
            typeMovieViewModel = new ViewModelProvider(requireActivity()).get(TypeMovieViewModel.class);
            typeMovieViewModel.setGenre(53 + "");
            typeMovieViewModel.getMoviesPagedList(53 + "").observe(getViewLifecycleOwner(), new Observer<PagedList<Movie>>() {
                @Override
                public void onChanged(PagedList<Movie> movies) {
                    pagingAdapterMovies.submitList(movies);
                    recyclerView.setAdapter(pagingAdapterMovies);
                    pagingAdapterMovies.notifyDataSetChanged();
                }
            });
            Log.d("type", type + " ints");
        }
        if (type == 7) {
            typeMovieViewModel = new ViewModelProvider(requireActivity()).get(TypeMovieViewModel.class);
            typeMovieViewModel.setGenre(18 + "");
            typeMovieViewModel.getMoviesPagedList(18 + "").observe(getViewLifecycleOwner(), new Observer<PagedList<Movie>>() {
                @Override
                public void onChanged(PagedList<Movie> movies) {
                    pagingAdapterMovies.submitList(movies);
                    recyclerView.setAdapter(pagingAdapterMovies);
                    pagingAdapterMovies.notifyDataSetChanged();

                }
            });
            Log.d("type", type + " ints");
        }
        if (type == 8) {
            typeMovieViewModel = new ViewModelProvider(requireActivity()).get(TypeMovieViewModel.class);
            typeMovieViewModel.setGenre(12 + "");
            typeMovieViewModel.getMoviesPagedList(12 + "").observe(getViewLifecycleOwner(), new Observer<PagedList<Movie>>() {
                @Override
                public void onChanged(PagedList<Movie> movies) {
                    pagingAdapterMovies.submitList(movies);
                    recyclerView.setAdapter(pagingAdapterMovies);
                    pagingAdapterMovies.notifyDataSetChanged();

                }
            });
        }
        if (type == 9) {
            typeMovieViewModel = new ViewModelProvider(requireActivity()).get(TypeMovieViewModel.class);
            typeMovieViewModel.setGenre(16 + "");
            typeMovieViewModel.getMoviesPagedList(16 + "").observe(getViewLifecycleOwner(), new Observer<PagedList<Movie>>() {
                @Override
                public void onChanged(PagedList<Movie> movies) {
                    pagingAdapterMovies.submitList(movies);
                    recyclerView.setAdapter(pagingAdapterMovies);
                    pagingAdapterMovies.notifyDataSetChanged();

                }
            });
        }
        if (type == 10) {
            typeMovieViewModel = new ViewModelProvider(requireActivity()).get(TypeMovieViewModel.class);
            typeMovieViewModel.setGenre(80 + "");
            typeMovieViewModel.getMoviesPagedList(80 + "").observe(getViewLifecycleOwner(), new Observer<PagedList<Movie>>() {
                @Override
                public void onChanged(PagedList<Movie> movies) {
                    pagingAdapterMovies.submitList(movies);
                    recyclerView.setAdapter(pagingAdapterMovies);
                    pagingAdapterMovies.notifyDataSetChanged();

                }
            });
        }
        if (type == 11) {
            typeMovieViewModel = new ViewModelProvider(requireActivity()).get(TypeMovieViewModel.class);
            typeMovieViewModel.setGenre(99 + "");
            typeMovieViewModel.getMoviesPagedList(99 + "").observe(getViewLifecycleOwner(), new Observer<PagedList<Movie>>() {
                @Override
                public void onChanged(PagedList<Movie> movies) {
                    pagingAdapterMovies.submitList(movies);
                    recyclerView.setAdapter(pagingAdapterMovies);
                    pagingAdapterMovies.notifyDataSetChanged();

                }
            });
        }
        if (type == 12) {
            typeMovieViewModel = new ViewModelProvider(requireActivity()).get(TypeMovieViewModel.class);
            typeMovieViewModel.setGenre(10751 + "");
            typeMovieViewModel.getMoviesPagedList(10751 + "").observe(getViewLifecycleOwner(), new Observer<PagedList<Movie>>() {
                @Override
                public void onChanged(PagedList<Movie> movies) {
                    pagingAdapterMovies.submitList(movies);
                    recyclerView.setAdapter(pagingAdapterMovies);
                    pagingAdapterMovies.notifyDataSetChanged();

                }
            });
        }
        if (type == 13) {
            typeMovieViewModel = new ViewModelProvider(requireActivity()).get(TypeMovieViewModel.class);
            typeMovieViewModel.setGenre(10752 + "");
            typeMovieViewModel.getMoviesPagedList(10752 + "").observe(getViewLifecycleOwner(), new Observer<PagedList<Movie>>() {
                @Override
                public void onChanged(PagedList<Movie> movies) {
                    pagingAdapterMovies.submitList(movies);
                    recyclerView.setAdapter(pagingAdapterMovies);
                    pagingAdapterMovies.notifyDataSetChanged();

                }
            });
        }
        if (type == 14) {
            typeMovieViewModel = new ViewModelProvider(requireActivity()).get(TypeMovieViewModel.class);
            typeMovieViewModel.setGenre(9648 + "");
            typeMovieViewModel.getMoviesPagedList(9648 + "").observe(getViewLifecycleOwner(), new Observer<PagedList<Movie>>() {
                @Override
                public void onChanged(PagedList<Movie> movies) {
                    pagingAdapterMovies.submitList(movies);
                    recyclerView.setAdapter(pagingAdapterMovies);
                    pagingAdapterMovies.notifyDataSetChanged();

                }
            });
        }


    }

    @Override
    public void onListClick(Movie movie) {
        TypeDirections.ActionTypeToDetailsActivity actionTypeToDetailsActivity = TypeDirections.actionTypeToDetailsActivity(movie);
        Navigation.findNavController(getView()).navigate(actionTypeToDetailsActivity);
    }
}