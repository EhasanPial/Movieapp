package Firebase;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieapp.R;
import com.google.android.material.button.MaterialButton;
import com.squareup.picasso.Picasso;

import java.util.List;

import Model.Cast;
import adapter.CastAdapter;

import server.BaseString;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.MyViewHolder> {

    private List<Post> casts;
    private Context context;
    private PostAdapter.ListClickListener mlistClickListener;


    public PostAdapter(Context context , ListClickListener mlistClickListener) {
        this.context = context;
        this.mlistClickListener = mlistClickListener ;

    }

    public void setCasts(List<Post> casts) {

        this.casts = casts;
        notifyDataSetChanged();

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.test1, null, false);
        return new PostAdapter.MyViewHolder(view);

    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Post cast = casts.get(holder.getAdapterPosition());

        holder.movieTilte.setText(cast.getMovieTitle());
        holder.rating.setText("Rating: "+cast.getRating()+"");
        holder.dex.setText("Description: \n\n"+cast.getDes());

        Picasso.get()
                .load(cast.getImg())
                .error(R.drawable.drop_shadow)
                .into(holder.imgUrl);


    }

    @Override
    public int getItemCount() {
        if (casts == null)
            return 0;
        return casts.size();
    }


    public interface ListClickListener {
        void onListClick(Post cast);
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView dex, uptext, downtext;
        MaterialButton upvote, downvote, commentes;
        TextView movieTilte, rating;
        ImageView imgUrl;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            dex = itemView.findViewById(R.id.forum_dex_id);
            uptext = itemView.findViewById(R.id.upText);
            downtext = itemView.findViewById(R.id.downText);
            upvote = itemView.findViewById(R.id.button_up_vote);
            downvote = itemView.findViewById(R.id.button_dwn_vote);
            commentes = itemView.findViewById(R.id.comments_button);
            imgUrl = itemView.findViewById(R.id.forum_image_view_id);
            movieTilte = itemView.findViewById(R.id.formu_title_id);
            rating = itemView.findViewById(R.id.forum_rating_id);


        }


    }
}
