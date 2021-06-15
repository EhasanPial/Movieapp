package Firebase;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.movieapp.MainActivity;
import com.example.movieapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.Executor;

import server.BaseString;

public class Login extends Fragment {


    FirebaseAuth firebaseAuth;

    private EditText email, password;
    private Button signIn, signUp;
    TextView userName;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        firebaseAuth = FirebaseAuth.getInstance();
        NavController navController = Navigation.findNavController(view);
        email = view.findViewById(R.id.login_email);
        password = view.findViewById(R.id.login_pass);
        signUp = view.findViewById(R.id.login_signUp_id);
        signIn = view.findViewById(R.id.login_button);


        if (firebaseAuth.getCurrentUser() != null) {


            String uid = firebaseAuth.getCurrentUser().getUid();
            ValueEventListener valueEventListener = new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    BaseString.USER_NAME = snapshot.getValue(String.class) ;
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            } ;

            FirebaseDatabase.getInstance().getReference().child("Users").child(uid).addValueEventListener(valueEventListener) ;
            NavigationView navView = getActivity().findViewById(R.id.nav_view);
         //   userName = navView.getHeaderView(0).findViewById(R.id.nav_header_userName);
          //  userName.setText(firebaseAuth.getCurrentUser().getEmail());
            navController.navigate(R.id.action_login_to_posts);
        }

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_login_to_registration);
            }
        });




        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn.setEnabled(false);
                String emailText = email.getText().toString();
                String passText = password.getText().toString();
               // userName = view.findViewById(R.id.nav_header_userName);
                if (!(emailText.isEmpty()) && !(passText.isEmpty())) {
                    firebaseAuth.signInWithEmailAndPassword(emailText, passText)
                            .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {


                                        navController.navigate(R.id.action_login_to_posts);

                                    } else {
                                        signIn.setEnabled(true);

                                        Snackbar.make(view, task.getException().getMessage().toString(), BaseTransientBottomBar.LENGTH_SHORT).show();
                                    }
                                }
                            });

                }
            }
        });


    }

    @Override
    public void onStart() {
        super.onStart();
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
    }

    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
    }

    @Override
    public void onStop() {
        super.onStop();
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();

    }
}