package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieapp.R;

import java.util.List;

import Model.Review;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.MyHolder> {

    private List<Review> reviewList;
    private Context context ;

    public ReviewAdapter(Context context) {
        this.context = context ;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.reviews_adapter, null, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        Review review = reviewList.get(holder.getAdapterPosition());
        holder.name.setText(review.getAuthor());
        holder.context.setText(review.getContent());
    }

    @Override
    public int getItemCount() {
        if (reviewList == null) return 0;

        return reviewList.size();
    }

    public void setReviewList(List<Review> reviewList) {
        this.reviewList = reviewList;
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        TextView name, context;

        public MyHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name_id_review);
            context = itemView.findViewById(R.id.context_id);
        }
    }
}
