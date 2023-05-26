package com.example.shoper.ui.Home;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.shoper.databinding.CustomHomeRvCategoreBinding;
import com.example.shoper.model.Category;
import com.example.shoper.model.Item;

import java.util.ArrayList;

public class HomeCategoryAdapter extends RecyclerView.Adapter<HomeCategoryAdapter.MyViewHolder> {

    private final ClickListener listener;
    private final ArrayList<Category> categories;
    private Context context;

    public HomeCategoryAdapter(ClickListener listener, ArrayList<Category> categories, Context context) {
        this.listener = listener;
        this.categories = categories;
        this.context = context;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        CustomHomeRvCategoreBinding binding = CustomHomeRvCategoreBinding.inflate(inflater, parent, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.bind(categories.get(position));
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        private CustomHomeRvCategoreBinding binding;

        public MyViewHolder(@NonNull CustomHomeRvCategoreBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Category category) {
            Glide.with(context).load(category.getCategoryPhoto()).into(binding.customHomeCategoryRvIv);
            binding.customHomeCategoryRvTv.setText(category.getCategoryName());
            binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onClick(category);
                }
            });
        }


    }

    public interface ClickListener {
        void onClick(Category category);
    }
}
