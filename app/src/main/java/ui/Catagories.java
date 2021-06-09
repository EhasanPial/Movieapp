package ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.movieapp.R;

import java.util.ArrayList;
import java.util.List;

import Model.CategoryModel;
import adapter.CategoryAdapter;


public class Catagories extends Fragment implements CategoryAdapter.OnClick {


    private RecyclerView recyclerView ;
    private List<CategoryModel> categoryModels ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_catagories, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.cat_recy_id) ;


        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 3) ;
        recyclerView.setLayoutManager(gridLayoutManager);
         categoryModels = new ArrayList<>() ;
        categoryModels.add(new CategoryModel(1,"Action")) ;
        categoryModels.add(new CategoryModel(2,"Horror")) ;
        categoryModels.add(new CategoryModel(3,"Romantic")) ;
        categoryModels.add(new CategoryModel(4,"Comedy")) ;
        categoryModels.add(new CategoryModel(5,"Si-Fi")) ;
        categoryModels.add(new CategoryModel(6,"Thriller")) ;
        categoryModels.add(new CategoryModel(7,"Drama")) ;

        CategoryAdapter categoryAdapter = new CategoryAdapter(getContext(), this) ;

        categoryAdapter.setCategoryModels(categoryModels) ;
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(categoryAdapter);

    }

    @Override
    public void OnClickListener(int type) {
        NavController navController = Navigation.findNavController(getView());
        CatagoriesDirections.ActionCatagoriesToType actionCatagoriesToType = CatagoriesDirections.actionCatagoriesToType(type);
        navController.navigate(actionCatagoriesToType);
    }
}