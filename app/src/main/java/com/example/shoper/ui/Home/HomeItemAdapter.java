package com.example.shoper.ui.Home;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.shoper.R;
import com.example.shoper.databinding.CustomHomeRvItemBinding;
import com.example.shoper.model.Item;

import java.util.ArrayList;
import java.util.Collections;

public class HomeItemAdapter extends RecyclerView.Adapter<HomeItemAdapter.MyViewHolder> {

    private final ClickListener listener;
    private final ArrayList<Item> items;
    private Context context;

    public HomeItemAdapter(ClickListener listener, ArrayList<Item> items, Context context) {
        this.listener = listener;
        this.items = items;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        CustomHomeRvItemBinding binding = CustomHomeRvItemBinding.inflate(inflater, parent, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.bind(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        private CustomHomeRvItemBinding binding;

        public MyViewHolder(@NonNull CustomHomeRvItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Item item) {
            Glide.with(context).load(item.getMainPhoto()).into(binding.customHomeRvIv);
            binding.customHomeRvTv.setText(item.getPrice());
            binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onClick(item);
                }
            });
        }

    }

    public interface ClickListener {
        void onClick(Item item);
    }
}
