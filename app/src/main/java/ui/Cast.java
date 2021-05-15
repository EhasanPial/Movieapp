package ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.movieapp.R;

import java.util.List;

import adapter.CastAdapter;
import adapter.MovieAdapter;
import server.BaseString;


public class Cast extends Fragment implements CastAdapter.ListClickListener {

    DetailsViewModel detailsViewModel;
    private CastAdapter castAdapter;
    private RecyclerView recyclerView;

    Integer id;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cast, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        recyclerView = view.findViewById(R.id.cast_recycler_id);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        castAdapter = new CastAdapter(getActivity().getApplicationContext(), this);
        recyclerView.setAdapter(castAdapter);


        detailsViewModel = new ViewModelProvider(requireActivity()).get(DetailsViewModel.class);
        detailsViewModel.getId().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                id = integer;
                detailsViewModel.getCast(BaseString.API, id).observe(getViewLifecycleOwner(), new Observer<List<Model.Cast>>() {
                    @Override
                    public void onChanged(List<Model.Cast> casts) {
                        castAdapter.setCasts(casts);
                    }
                });
            }
        });

    }

    @Override
    public void onListClick(Model.Cast cast) {

    }
}