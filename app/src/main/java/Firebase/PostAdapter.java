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





}
