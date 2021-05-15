package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieapp.R;

import java.util.List;

import Model.Cast;
import Model.Movie;

public class CastAdapter extends RecyclerView.Adapter<CastAdapter.MyViewHolder> {

    private List<Model.Cast> casts;
    private Context context;
    private ListClickListener mlistClickListener;


    public CastAdapter(Context context, ListClickListener mlistClickListener) {
        this.context = context;
        this.mlistClickListener = mlistClickListener ;
    }

    public void setCasts(List<Model.Cast> casts) {
        this.casts = casts;
        notifyDataSetChanged();

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cast_adapter, null, false);
        return new MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Model.Cast cast = casts.get(holder.getAdapterPosition());
        holder.org_name.setText(cast.getOriginal_name());
        holder.name.setText(cast.getName());

    }

    @Override
    public int getItemCount() {
        return casts.size();
    }


    public interface ListClickListener {
        void onListClick(Cast cast);
    }



    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView profile;
        TextView name, org_name;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            profile = itemView.findViewById(R.id.profile_image);
            name = itemView.findViewById(R.id.name_id);
            org_name = itemView.findViewById(R.id.original_name);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            mlistClickListener.onListClick(casts.get(position));
        }
    }
}
