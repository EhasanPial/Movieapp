package Firebase;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.movieapp.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;


public class Comment extends Fragment {

    private RecyclerView recyclerView ;
    private Button replyButton  ;
    private EditText msg_comment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.reply_bottom_sheet, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        CommentArgs commentArgs = CommentArgs.fromBundle(getArguments());
        recyclerView = view.findViewById(R.id.reply_recyler_id) ;
        replyButton = view.findViewById(R.id.reply_button) ;
        msg_comment = view.findViewById(R.id.reply_msg) ;

        String postID = commentArgs.getPostID();

        FirebaseRecyclerOptions<ReplyModel> options =
                new FirebaseRecyclerOptions.Builder<ReplyModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Comments"), ReplyModel.class)
                        .build();


        FirebaseRecyclerAdapter<ReplyModel, CommentAdapter> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<ReplyModel, CommentAdapter>(options) {


            @Override
            protected void onBindViewHolder(@NonNull CommentAdapter holder, int position, @NonNull ReplyModel msg) {
                holder.textView.setText(msg.getMsg());
            }

            @NonNull
            @Override
            public CommentAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.reply_item, parent, false);
                return new CommentAdapter(view);
            }
        };

        firebaseRecyclerAdapter.startListening();
        recyclerView.setAdapter(firebaseRecyclerAdapter);

    }
}