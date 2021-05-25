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

import com.example.movieapp.R;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;

import server.BaseString;


public class SignOut extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_out, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() != null) {
            firebaseAuth.signOut();
            BaseString.USER_NAME = "" ;
            Snackbar.make(view, "Logged out", Snackbar.LENGTH_LONG ).show();

            NavController  navController = Navigation.findNavController(view) ;
            navController.navigate(R.id.action_signOut2_to_mainFragment);

        }

        else
        {

            NavController  navController = Navigation.findNavController(view) ;
            navController.navigate(R.id.action_signOut2_to_mainFragment);
            Snackbar.make(view, "Your aren't logged in", Snackbar.LENGTH_LONG ).show();
        }
    }
}