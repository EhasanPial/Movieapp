package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieapp.R;
import com.squareup.picasso.Picasso;

import Model.Movie;
import server.BaseString;

public class PagingAdapterMovies extends PagedListAdapter<Movie, PagingAdapterMovies.MovieViewHolder> {

    private Context context;
    final private PagingAdapterMovies.ListClickListener mListClickListener;
    private MovieViewHolder holder;


    public PagingAdapterMovies(Context context,  ListClickListener onListClickListener) {
        super(Movie.CALLBACK);
        this.context = context;
        this.mListClickListener = onListClickListener ;

    }



    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_layout, parent, false);
        return new MovieViewHolder(viewItem) ;
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        this.holder = holder;

        Movie movie = getItem(position);
        loadImagePoster(holder, movie.getPoster_path());
        holder.movieTitle.setText(movie.getTitle());
    }




    private void loadImagePoster(final MovieViewHolder holder, String imageUrl) {
        String posterUrl = BaseString.POSTER_PATH;
        Picasso.get()
                .load(posterUrl + imageUrl)
                .error(R.drawable.drop_shadow)
                .into(holder.movieImageView);
    }

    public interface ListClickListener {
        void onListClick(Movie movie);
    }


    public class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView movieImageView = itemView.findViewById(R.id.movie_imageView);


        TextView movieTitle = itemView.findViewById(R.id.movie_title);

        MovieViewHolder(View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {

            mListClickListener.onListClick(getItem(getAdapterPosition()));
        }
    }

}
