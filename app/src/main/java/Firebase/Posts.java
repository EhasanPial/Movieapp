package Firebase;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
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
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
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
import com.squareup.picasso.Picasso;

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
    private DatabaseReference likeRef, dislikeRef;

    private FirebaseRecyclerAdapter<Post, PostAdapter> firebaseRecyclerAdapter;


    private RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_posts, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        post = view.findViewById(R.id.new_post_id);


        recyclerView = view.findViewById(R.id.post_recy_id);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);


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


        NavigationView navView = getActivity().findViewById(R.id.nav_view);
      //  TextView userName = navView.getHeaderView(0).findViewById(R.id.nav_header_userName);
     //   userName.setText(mAuth.getCurrentUser().getEmail());


        likeRef = FirebaseDatabase.getInstance().getReference().child("Likes");
        dislikeRef = FirebaseDatabase.getInstance().getReference().child("DisLikes");

        FirebaseRecyclerOptions<Post> options =
                new FirebaseRecyclerOptions.Builder<Post>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("AllPosts"), Post.class)
                        .build();


        FirebaseRecyclerAdapter<Post, PostAdapter> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Post, PostAdapter>(options) {


            @Override
            protected void onBindViewHolder(@NonNull PostAdapter holder, int position, @NonNull Post post) {
                FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                final String userid = firebaseUser.getUid();
                final String postkey = getRef(position).getKey();


                holder.movieTilte.setText(post.getMovieTitle());
                holder.rating.setText(post.getRating() + "");
                holder.dex.setText(post.getDes() + "");
                Log.d("Pic", post.getImg() + "");
                Picasso.get()
                        .load(post.getImg())
                        .error(R.drawable.bg_overlay)
                        .into(holder.imgUrl);


                likeRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        int upVoteCount = (int) snapshot.child(postkey).getChildrenCount();
                        holder.uptext.setText(upVoteCount + "");
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                dislikeRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        int downVoteCount = (int) snapshot.child(postkey).getChildrenCount();
                        holder.downtext.setText(downVoteCount + "");
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


                holder.upvote.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //testClick = true;
                        likeRef.addListenerForSingleValueEvent(new ValueEventListener() {
                            @SuppressLint("SetTextI18n")
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                // if (testClick) {
                                if (!snapshot.child(postkey).hasChild(userid)) {
                                    likeRef.child(postkey).child(userid).setValue(true);
                                    holder.uptext.setText(snapshot.child(postkey).getChildrenCount() + "");
                                    dislikeRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            if (snapshot.child(postkey).hasChild(userid)) {
                                                dislikeRef.child(postkey).child(userid).removeValue();
                                                int downVoteCount = (int) snapshot.child(postkey).getChildrenCount();
                                                holder.downtext.setText(downVoteCount + "");

                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    });


                                } else {
                                    Snackbar.make(getView(), "Already Voted", Snackbar.LENGTH_SHORT).show();
                                }


                            }
                            // }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                        // testClick = false;
                    }
                });


                holder.downvote.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // testClick2 = true;

                        dislikeRef.addListenerForSingleValueEvent(new ValueEventListener() {
                            @SuppressLint("SetTextI18n")
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {

                                //   if (testClick2) {
                                if (!snapshot.child(postkey).hasChild(userid)) {
                                    dislikeRef.child(postkey).child(userid).setValue(true);
                                    holder.downtext.setText(snapshot.child(postkey).getChildrenCount() + "");
                                    likeRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            if (snapshot.child(postkey).hasChild(userid)) {
                                                likeRef.child(postkey).child(userid).removeValue();
                                                int downVoteCount = (int) snapshot.child(postkey).getChildrenCount();
                                                holder.uptext.setText(downVoteCount + "");
                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    });


                                } else {
                                    Snackbar.make(view, "Already Unvoted", Snackbar.LENGTH_SHORT).show();
                                }

                            }

                            //   }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                        // testClick2 = false;


                    }


                });

                holder.commentes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                      PostsDirections.ActionPostsToComment actionPostsToComment =  PostsDirections.actionPostsToComment(postkey) ;
                        NavController navController = Navigation.findNavController(view);
                        navController.navigate(actionPostsToComment);
                    }
                });


            }

            @NonNull
            @Override
            public PostAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.test1, parent, false);
                return new PostAdapter(view);
            }
        };


        firebaseRecyclerAdapter.startListening();
        recyclerView.setAdapter(firebaseRecyclerAdapter);
    }


}