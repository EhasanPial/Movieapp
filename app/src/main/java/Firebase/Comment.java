package Firebase;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class Comment extends Fragment {

    private RecyclerView recyclerView;
    private Button replyButton;
    private EditText msg_comment;
    private String postID;
    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;
    private List<ReplyModel> replyModelList;
    private CommentAdapter commentAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.reply_bottom_sheet, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        CommentArgs commentArgs = CommentArgs.fromBundle(getArguments());
        recyclerView = view.findViewById(R.id.reply_recyler_id);
        replyButton = view.findViewById(R.id.reply_button);
        msg_comment = view.findViewById(R.id.reply_msg);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Comments");


        firebaseAuth = FirebaseAuth.getInstance();


        postID = commentArgs.getPostID();

        replyButton.setOnClickListener(v -> postReply());

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        commentAdapter = new CommentAdapter();
        replyModelList = new ArrayList<>();





        databaseReference.child(postID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                replyModelList.clear();
                for (DataSnapshot d : snapshot.getChildren()) {
                    String key = d.getKey();
                    FirebaseDatabase.getInstance().getReference().child("Comments").child(postID).child(key).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            ReplyModel replyModel = snapshot.getValue(ReplyModel.class);
                            recyclerView.setAdapter(commentAdapter);
                            replyModelList.add(replyModel);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }

                commentAdapter.setComment(replyModelList);
                recyclerView.setAdapter(commentAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        recyclerView.setAdapter(commentAdapter);


    }

    private void postReply() {
        String msg = msg_comment.getText().toString();


        String userID = firebaseAuth.getCurrentUser().getUid();

        long commentTime = System.currentTimeMillis();

        ReplyModel replyModel = new ReplyModel(msg, userID);

        if (!msg.isEmpty()) {

            databaseReference.child(postID).child(commentTime + "").setValue(replyModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    databaseReference.child(postID).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            commentAdapter.notifyDataSetChanged();
                            recyclerView.setAdapter(commentAdapter);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }
            });

            msg_comment.setText("");
            msg_comment.clearFocus();
        }
    }
}