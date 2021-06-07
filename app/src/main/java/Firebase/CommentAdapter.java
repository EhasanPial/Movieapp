package Firebase;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieapp.R;

public class CommentAdapter   extends RecyclerView.ViewHolder {

    TextView textView ;


    public CommentAdapter(@NonNull View itemView) {
        super(itemView);

        textView = itemView.findViewById(R.id.reply_text) ;
    }
}
