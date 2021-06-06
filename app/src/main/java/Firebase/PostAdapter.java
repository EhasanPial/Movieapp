package Firebase;

import android.animation.LayoutTransition;
import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieapp.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.List;

import Model.Cast;
import adapter.CastAdapter;

import server.BaseString;

public class PostAdapter extends RecyclerView.ViewHolder {

    TextView dex, uptext, downtext;
    MaterialButton upvote, downvote, commentes;
    TextView movieTilte, rating;
    ImageView imgUrl;
    private DatabaseReference likeRef, dislikeRef;


    public PostAdapter(@NonNull View itemView) {
        super(itemView);

        dex = itemView.findViewById(R.id.forum_dex_id);
        uptext = itemView.findViewById(R.id.upText);
        downtext = itemView.findViewById(R.id.downText);
        upvote = itemView.findViewById(R.id.button_up_vote);
        downvote = itemView.findViewById(R.id.button_dwn_vote);
        commentes = itemView.findViewById(R.id.comments_button);
        imgUrl = itemView.findViewById(R.id.forum_image_view_id);
        movieTilte = itemView.findViewById(R.id.formu_title_id);
        rating = itemView.findViewById(R.id.forum_rating_id);
        likeRef = FirebaseDatabase.getInstance().getReference().child("Likes");
        dislikeRef = FirebaseDatabase.getInstance().getReference().child("DisLikes");

    }


    FirebaseRecyclerOptions<Post> options =
            new FirebaseRecyclerOptions.Builder<Post>()
                    .setQuery(FirebaseDatabase.getInstance().getReference().child("AllPosts"), Post.class)
                    .build();


    FirebaseRecyclerAdapter<Post, PostAdapter> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Post, PostAdapter>(options) {
        boolean testClick = false;
        boolean testClick2 = false;

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
                                    Snackbar.make(itemView, "Already Voted", Snackbar.LENGTH_SHORT).show();
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


                                }
                                else {
                                    Snackbar.make(itemView, "Already Unvoted", Snackbar.LENGTH_SHORT).show();
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


        }

        @NonNull
        @Override
        public PostAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.test1, parent, false);
            return new PostAdapter(view);
        }
    };


    public FirebaseRecyclerAdapter<Post, PostAdapter> getFirebaseRecyclerAdapter() {
        return firebaseRecyclerAdapter;
    }


}
