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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
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


public class Posts extends Fragment implements PostAdapter.ListClickListener {


    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private FloatingActionButton post;
    private boolean upVoteToke = false, downVoteToken = false;
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
        postList = new ArrayList<>();

        mAuth = FirebaseAuth.getInstance();
        NavController nav = Navigation.findNavController(view) ;
        if (mAuth.getCurrentUser() == null)
        {
            nav.navigate(R.id.action_posts_to_login);
            return;
        }

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("AllPosts");
        postList = new ArrayList<>();


        NavigationView navView = getActivity().findViewById(R.id.nav_view);
        TextView userName = navView.getHeaderView(0).findViewById(R.id.nav_header_userName);
        userName.setText(mAuth.getCurrentUser().getEmail());


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
        postAdapter = new PostAdapter(getContext(), this);


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
    public void onListClick(Post cast, int typeClick) {
        long id = cast.getId();
        if (typeClick == 1 && upVoteToke == false) {
            int voted = cast.getUpVotes();
            databaseReference.child(id + "").child("upVotes").setValue(voted + 1).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    postList.clear();
                    readData();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });

            if (downVoteToken) {
                int votedwn = cast.getDownVotes();
                if(votedwn == 0) votedwn = 1 ;
                databaseReference.child(id + "").child("downVotes").setValue(votedwn - 1).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        postList.clear();
                        readData();

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
            }
            upVoteToke = true;
            downVoteToken = false;

        }
        else if (typeClick == 2 && !downVoteToken) {
            int voted = cast.getDownVotes();
            databaseReference.child(id + "").child("downVotes").setValue(voted + 1).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    postList.clear();
                    readData();

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });

            if (upVoteToke) {
                int votedUp = cast.getUpVotes();
                if(votedUp == 0) votedUp = 1 ;
                databaseReference.child(id + "").child("upVotes").setValue(votedUp - 1).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        postList.clear();
                        readData();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
            }
            downVoteToken = true;
            upVoteToke = false;
        }
    }
}