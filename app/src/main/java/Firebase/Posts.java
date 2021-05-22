package Firebase;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.movieapp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import Model.Cast;


public class Posts extends Fragment  implements PostAdapter.ListClickListener{


    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private FloatingActionButton post;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    private List<Post> postList;
    PostAdapter postAdapter;


    private RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_posts, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        post = view.findViewById(R.id.new_post_id);
        postList = new ArrayList<>() ;

//        FirebaseUser currentUser = mAuth.getCurrentUser();

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("AllPosts");
        postList = new ArrayList<>();


        readData();
        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController = Navigation.findNavController(v);
                navController.navigate(R.id.action_posts_to_forum2);
            }
        });

        recyclerView = view.findViewById(R.id.post_recy_id);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        postAdapter = new PostAdapter(getContext() , this);



        recyclerView.setAdapter(postAdapter);
    }

    public void readData() {


        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot d : snapshot.getChildren()) {
                    postList.add(d.getValue(Post.class));
                    Log.d("Posts", postList.toString());
                }
                postAdapter.setCasts(postList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };

        databaseReference.addValueEventListener(valueEventListener);




    }

    @Override
    public void onListClick(Post cast) {
        long id   =  cast.getId() ;
        List<Comments> commentsList = cast.getCommentsList() ;
        ///////////////////////////// TOOOOOOOOOOOOOOOOOOO DOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO //////////////////////////////////////
    }
}