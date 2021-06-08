package Firebase;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import Model.Cast;
import Model.Movie;
import server.BaseString;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.MyViewHolder> {

    private List<ReplyModel> comments;


    public CommentAdapter() {


    }

    public void setComment(List<ReplyModel> comments) {

        this.comments = comments;
        notifyDataSetChanged();

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.reply_item, parent, false);
        return new MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

          holder.msg.setText(comments.get(position).getMsg());
    }

    @Override
    public int getItemCount() {
        if (comments == null)
            return 0;
        return comments.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView msg;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            msg = itemView.findViewById(R.id.reply_text);

        }


    }
}
