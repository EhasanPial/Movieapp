package adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieapp.R;

import java.util.List;

import Model.CategoryModel;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.MyViewHolder> {

    List<CategoryModel> categoryModels;
    private Context context;
    private OnClick onClick;

    public CategoryAdapter(Context context, OnClick onClick) {

        this.context = context;
        this.onClick = onClick;
    }

    @NonNull
    @Override
    public CategoryAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cat_recy_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.MyViewHolder holder, int position) {
        CategoryModel categoryModel = categoryModels.get(position);
        holder.name.setText(categoryModel.getCategoryName());
        int type = categoryModel.getType();

        if (type == 1) {
            holder.back.setImageDrawable(ResourcesCompat.getDrawable(context.getResources(), R.drawable.action_poster, null));
        } else if (type == 2) {
            holder.back.setImageDrawable(ResourcesCompat.getDrawable(context.getResources(), R.drawable.horror_poster, null));
        } else if (type == 3) {
            holder.back.setImageDrawable(ResourcesCompat.getDrawable(context.getResources(), R.drawable.romantic_poster, null));
        } else if (type == 4) {
            holder.back.setImageDrawable(ResourcesCompat.getDrawable(context.getResources(), R.drawable.comedy_poster, null));
        } else if (type == 5) {
            holder.back.setImageDrawable(ResourcesCompat.getDrawable(context.getResources(), R.drawable.si_fi_movie_poster, null));
        } else if (type == 6) {
            holder.back.setImageDrawable(ResourcesCompat.getDrawable(context.getResources(), R.drawable.thriller_poster, null));
        } else if (type == 7) {
            holder.back.setImageDrawable(ResourcesCompat.getDrawable(context.getResources(), R.drawable.darama_poster, null));

        } else if (type == 8) {
            holder.back.setImageDrawable(ResourcesCompat.getDrawable(context.getResources(), R.drawable.advanture_poster, null));

        } else if (type == 9) {
            holder.back.setImageDrawable(ResourcesCompat.getDrawable(context.getResources(), R.drawable.animation_poster, null));

        } else if (type == 10) {
            holder.back.setImageDrawable(ResourcesCompat.getDrawable(context.getResources(), R.drawable.crime_poster, null));

        } else if (type == 11) {
            holder.back.setImageDrawable(ResourcesCompat.getDrawable(context.getResources(), R.drawable.documentry_poster, null));

        } else if (type == 12) {
            holder.back.setImageDrawable(ResourcesCompat.getDrawable(context.getResources(), R.drawable.family_poster, null));

        }else if (type == 13) {
            holder.back.setImageDrawable(ResourcesCompat.getDrawable(context.getResources(), R.drawable.war_poster, null));

        }
else if (type == 14) {
            holder.back.setImageDrawable(ResourcesCompat.getDrawable(context.getResources(), R.drawable.mystery_poster, null));

        }

    }

    public void setCategoryModels(List<CategoryModel> categoryModels) {
        this.categoryModels = categoryModels;
    }

    @Override
    public int getItemCount() {
        if (categoryModels == null)
            return 0;

        return categoryModels.size();
    }

    public interface OnClick {
        void OnClickListener(int type);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView name;
        ImageView back;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.cat_name);
            back = itemView.findViewById(R.id.back_cat_img);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int pos = getAdapterPosition();
            onClick.OnClickListener(pos + 1);
        }
    }
}
