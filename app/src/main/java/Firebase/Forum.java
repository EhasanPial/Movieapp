package Firebase;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.movieapp.R;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;


public class Forum extends Fragment {

    private EditText editText;
    private TextView textView;
    private Button post;
    private ForumViewModel forumViewModel;

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
        post = view.findViewById(R.id.post_button_id);


       // FirebaseUser currentUser = mAuth.getCurrentUser();

        mDatabase = FirebaseDatabase.getInstance().getReference();

        forumViewModel = new ViewModelProvider(requireActivity()).get(ForumViewModel.class);


        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean success = forumViewModel.writeData("Pial", editText.getText().toString(), "/ee");
                if (success) {
                    Snackbar.make(getView(), "Posted", BaseTransientBottomBar.LENGTH_SHORT).show();
                    NavController navController = Navigation.findNavController(v) ;
                    navController.navigate(R.id.action_forum2_to_posts);
                    editText.setText("");
                } else
                    Snackbar.make(getView(), "Something went wrong!", BaseTransientBottomBar.LENGTH_SHORT).setTextColor(Color.parseColor("#FF0000")).show();

            }
        });


    }
}