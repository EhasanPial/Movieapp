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
import android.widget.TextView;

import com.example.movieapp.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import Model.Cast;


public class Posts extends Fragment {


    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private FloatingActionButton post;
    private boolean upVoteToke = false, downVoteToken = false;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    private List<Post> postList;
    private PostAdapter postAdapter;
    private FirebaseRecyclerAdapter<Post, PostAdapter> firebaseRecyclerAdapter;


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

        recyclerView = view.findViewById(R.id.post_recy_id);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        postList = new ArrayList<>();

        mAuth = FirebaseAuth.getInstance();
        NavController nav = Navigation.findNavController(view);
        if (mAuth.getCurrentUser() == null) {
            nav.navigate(R.id.action_posts_to_login);
            return;
        }

        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nav.navigate(R.id.action_posts_to_forum2);
            }
        });

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("AllPosts");
        postList = new ArrayList<>();


        NavigationView navView = getActivity().findViewById(R.id.nav_view);
        TextView userName = navView.getHeaderView(0).findViewById(R.id.nav_header_userName);
        userName.setText(mAuth.getCurrentUser().getEmail());

        postAdapter = new PostAdapter(view);

        firebaseRecyclerAdapter = postAdapter.getFirebaseRecyclerAdapter();
        firebaseRecyclerAdapter.startListening();
        recyclerView.setAdapter(firebaseRecyclerAdapter);
    }


}