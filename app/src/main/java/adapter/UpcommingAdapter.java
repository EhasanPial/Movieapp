package adapter;

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

import Model.Movie;
import server.BaseString;

public class UpcommingAdapter extends RecyclerView.Adapter<UpcommingAdapter.MyHolder> {

    List<Movie> movieList;

    public void setMovie(List<Movie> movieList) {
        this.movieList = movieList;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.upcomming_item, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        Movie movie = movieList.get(position);
        holder.title.setText("Movie: "+movie.getTitle());
        holder.overView.setText("OverView: \n\n"+movie.getOverview());
        holder.date.setText("Release Date: "+movie.getRelease_date());

        String posterUrl = BaseString.POSTER_PATH;
        Picasso.get()
                .load(posterUrl + movie.getPoster_path())
                .error(R.drawable.drop_shadow)
                .into(holder.poster);
    }

    @Override
    public int getItemCount() {
        if (movieList == null) return 0;
        return movieList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        private TextView title, overView, date;
        private ImageView poster;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.up_title);
            overView = itemView.findViewById(R.id.up_overview);
            date = itemView.findViewById(R.id.up_relase);
            poster = itemView.findViewById(R.id.back_upcomming);
        }
    }
}
