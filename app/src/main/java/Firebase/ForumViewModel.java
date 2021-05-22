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

    public boolean writeData(long id, String des, String username, String img, int upVotes, int downVotes, float rating, String movieTitle, List<Comments> commentsList)
    {

        Post post = new Post(id,des,username,img,upVotes,downVotes,rating,movieTitle, null) ;
        databaseReference.child(id+"").setValue(post).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                flag = true ;
            }
        }) ;

        return true ;
    }




}
