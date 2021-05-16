package Firebase;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ForumViewModel extends AndroidViewModel {

    FirebaseDatabase firebaseDatabase ;
    DatabaseReference databaseReference ;
    private boolean flag = false ;
    private List<Post> postList ;

    public ForumViewModel(@NonNull Application application) {
        super(application);

        firebaseDatabase = FirebaseDatabase.getInstance() ;
        databaseReference = firebaseDatabase.getReference().child("AllPosts") ;
        postList = new ArrayList<>() ;

    }

    public boolean writeData(String username, String msg, String img)
    {

        Post post = new Post(msg,username,img,0,0) ;
        databaseReference.child(UUID.randomUUID().toString()).setValue(post).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                flag = true ;
            }
        }) ;

        return true ;
    }




}
