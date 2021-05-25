


package Firebase;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.movieapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class Registration extends Fragment {

    private EditText email, password, userName;
    private Button signUp;
    private FirebaseAuth auth;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = firebaseDatabase.getReference();
    ;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_registration, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        auth = FirebaseAuth.getInstance();
        email = view.findViewById(R.id.editTextEmail);
        password = view.findViewById(R.id.editTextTextPassword);
        userName = view.findViewById(R.id.editTextUserName);
        signUp = view.findViewById(R.id.reg_sign_up_id);


        signUp.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String emailText = email.getText().toString();
                String passText = password.getText().toString();
                String userText = userName.getText().toString();


                if (!(emailText.isEmpty()) && !(userText.isEmpty()) && !(passText.isEmpty())) {
                    signUp.setEnabled(false);
                    auth.createUserWithEmailAndPassword(emailText, passText)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {



                                        databaseReference.child("Users").child(auth.getUid()).setValue(userText).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {

                                                NavController navController = Navigation.findNavController(v);
                                                navController.navigate(R.id.action_registration_to_posts);
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(getContext(), e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                                            }
                                        });


                                    } else {
                                        signUp.setEnabled(true);
                                        Snackbar.make(view, task.getException().getMessage().toString(), BaseTransientBottomBar.LENGTH_SHORT).show();
                                    }
                                }
                            });


                }
            }
        });


    }
}