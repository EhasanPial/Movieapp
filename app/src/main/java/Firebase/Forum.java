package Firebase;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.movieapp.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.w3c.dom.Text;

import java.net.URI;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.util.List;
import java.util.UUID;


public class Forum extends Fragment {

    private EditText editText, title, rating;
    private TextView textView;
    private Button post, selectPhoto;
    private ForumViewModel forumViewModel;
    private Uri filePath = null;
    private long postTime;
    private ProgressBar progressBar ;

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_forum, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mAuth = FirebaseAuth.getInstance();

        ///// // UI /// /////

        editText = view.findViewById(R.id.post_edit_text);
        title = view.findViewById(R.id.post_title);
        rating = view.findViewById(R.id.post_rating_id);
        selectPhoto = view.findViewById(R.id.post_photo_id);
        post = view.findViewById(R.id.post_button_id);
        progressBar = view.findViewById(R.id.post_progressBar);


        // FirebaseUser currentUser = mAuth.getCurrentUser();

        mDatabase = FirebaseDatabase.getInstance().getReference();


        forumViewModel = new ViewModelProvider(requireActivity()).get(ForumViewModel.class);

        selectPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), 11);
            }
        });

        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progressBar.setVisibility(View.VISIBLE);
                progressBar.setIndeterminate(true);
                post.setEnabled(false);
                if (filePath != null) {
                    postTime = System.currentTimeMillis();
                    StorageReference ref = FirebaseStorage.getInstance().getReference().child("images/" + postTime);
                    ref.putFile(filePath)
                            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    setPost("images/" + postTime,v) ;




                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    post.setEnabled(true);
                                }
                            })
                            .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {

                                }
                            });


                }

                else
                {
                    Snackbar.make(getView(), "Select a movie photo", BaseTransientBottomBar.LENGTH_SHORT).setTextColor(Color.parseColor("#FF0000"))
                            .setBackgroundTint(Color.parseColor("#FFFFFF")).show();
                    progressBar.setVisibility(View.INVISIBLE);
                    post.setEnabled(true);

                }
            }
        });

        progressBar.setVisibility(View.INVISIBLE);


    }

    private void setPost(String id, View v) {
        final StorageReference ref = FirebaseStorage.getInstance().getReference().child(id);
        UploadTask uploadTask = ref.putFile(filePath);

        Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }


                return ref.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    Uri downloadUri = task.getResult();

                    boolean success = forumViewModel.writeData(postTime, editText.getText().toString(), "Pial", downloadUri.toString() , 0, 0, Float.parseFloat(rating.getText().toString()), title.getText().toString(), null);
                    if (success) {
                        Snackbar.make(getView(), "Posted", BaseTransientBottomBar.LENGTH_SHORT).show();
                        NavController navController = Navigation.findNavController(v);
                        navController.navigate(R.id.action_forum2_to_posts);
                        editText.setText("");
                        rating.setText("");
                        title.setText("");
                    } else
                        Snackbar.make(getView(), "Something went wrong!", BaseTransientBottomBar.LENGTH_SHORT).setTextColor(Color.parseColor("#FF0000")).show();

                } else {
                    post.setEnabled(true);
                    progressBar.setVisibility(View.INVISIBLE);
                }
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 11 && data != null && data.getData() != null) {
            filePath = data.getData();

        }
    }
}