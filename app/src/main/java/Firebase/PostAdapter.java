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

import java.util.List;

import Model.Cast;
import adapter.CastAdapter;

import server.BaseString;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.MyViewHolder>{

    private List<Post> casts;
    private Context context;
    private PostAdapter.ListClickListener mlistClickListener;


    public PostAdapter(Context context) {
        this.context = context;

    }

    public void setCasts(List<Post> casts) {

        this.casts = casts ;
        notifyDataSetChanged();

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.test, null, false);
        return new PostAdapter.MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
       Post cast = casts.get(holder.getAdapterPosition());

        holder.msg.setText(cast.getMsg());
    }

    @Override
    public int getItemCount() {
        if(casts == null)
            return  0 ;
        return casts.size();
    }


    public interface ListClickListener {
        void onListClick(Cast cast);
    }



    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView msg;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            msg = itemView.findViewById(R.id.msg_id);

        }


    }
}
