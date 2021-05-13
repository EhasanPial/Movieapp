package adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.movieapp.R;
import com.smarteist.autoimageslider.SliderViewAdapter;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import Model.Movie;
import server.BaseString;

public class SliderAdapter extends
        SliderViewAdapter<SliderAdapter.SliderAdapterVH> {

    private Context context;
    private List<Movie> mSliderItems = new ArrayList<>();

    public SliderAdapter(Context context ) {
        this.context = context;

    }

    public void setMovies(List<Movie> mSliderItems )
    {
        this.mSliderItems = mSliderItems ;
        notifyDataSetChanged();
    }

    @Override
    public SliderAdapterVH onCreateViewHolder(ViewGroup parent) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.slider_adapter, null);
        return new SliderAdapterVH(inflate);
    }

    @Override
    public void onBindViewHolder(SliderAdapterVH viewHolder, final int position) {

        Movie movie = mSliderItems.get(position) ;

        viewHolder.textViewDescription.setText(movie.getTitle());
        viewHolder.textViewDescription.setTextSize(14);
        viewHolder.textViewDescription.setTextColor(Color.WHITE);

        viewHolder.textViewOverView.setText(movie.getOverview());
        viewHolder.textViewOverView.setTextSize(12);

        String posterUrl = BaseString.POSTER_PATH;
        Picasso.get()
                .load(posterUrl + movie.getPoster_path())
                .error(R.drawable.bg_overlay)
                .into(viewHolder.imageViewBackground);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, movie.getTitle(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getCount() {

        return mSliderItems.size();
    }

    class SliderAdapterVH extends SliderViewAdapter.ViewHolder {

        View itemView;
        ImageView imageViewBackground;
        ImageView imageGifContainer;
        TextView textViewDescription;
        TextView textViewOverView;

        public SliderAdapterVH(View itemView) {
            super(itemView);
            imageViewBackground = itemView.findViewById(R.id.iv_auto_image_slider);
            imageGifContainer = itemView.findViewById(R.id.iv_gif_container);
            textViewDescription = itemView.findViewById(R.id.tv_auto_image_slider);
            textViewOverView = itemView.findViewById(R.id.overview_id_slider);
            this.itemView = itemView;
        }
    }

}