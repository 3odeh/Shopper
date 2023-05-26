package com.example.shoper.ui.cart;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.shoper.databinding.CustomItemRvCartBinding;
import com.example.shoper.model.Item;
import com.example.shoper.ui.Item.ItemPhotoAdapter;

import java.util.ArrayList;
import java.util.Collections;

public class CartItemAdapter extends RecyclerView.Adapter<CartItemAdapter.MyViewHolder> {

    private CartMvp.view viewMvp;
    private final ArrayList<Item> items;
    private Context context;
    private boolean check;
    private ArrayList<Integer> prices;
    private ArrayList<Boolean> checks;





    public CartItemAdapter(CartMvp.view view, ArrayList<Item> items, Context context, boolean check) {
        this.viewMvp = view;
        this.items = items;
        this.context = context;
        this.check = check;
        prices = new ArrayList<>();
        checks = new ArrayList<>();

    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        CustomItemRvCartBinding binding = CustomItemRvCartBinding.inflate(inflater, parent, false);
        return new CartItemAdapter.MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        if (prices.size() > position)
            prices.set(position, 0);
        else
            prices.add(position, 0);
        if (checks.size() > position)
            checks.set(position, true);
        else
            checks.add(position, true);

        holder.price(items.get(position), position);
        holder.bind(items.get(position), check);

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    private void displayPrice() {
        viewMvp.displayPrice(prices);
    }
    private void displayCheck() {
        viewMvp.displayAllCheck(checks);
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {


        private CustomItemRvCartBinding binding;

        public MyViewHolder(@NonNull CustomItemRvCartBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Item item, boolean check) {
            Glide.with(context).load(item.getMainPhoto()).into(binding.customItemRvCartPhoto);
            binding.customItemRvCartPrice.setText(item.getPrice());
            binding.customItemRvCartSize.setText(item.getSize());
            binding.customItemRvCartName.setText(item.getName());
            binding.customItemRvCartCheck.setChecked(check);


            binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    viewMvp.onItemClick(item);
                }
            });


        }

        public void price(Item item, int position) {

            //Initial values
            int itemCount = Integer.parseInt(binding.customItemTvCartBtnCount.getText() + "");
            int itemPrice = Integer.parseInt(item.getPrice().substring(0, item.getPrice().length() - 1));
            int priceItem = itemCount * itemPrice;
            prices.set(position, priceItem);
            displayPrice();


            //Listener buttons

            binding.customItemTvCartBtnAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int itemCount = Integer.parseInt(binding.customItemTvCartBtnCount.getText() + "") + 1;
                    int priceItemAdd = itemCount * itemPrice;
                    binding.customItemTvCartBtnCount.setText(itemCount + "");
                    if (binding.customItemRvCartCheck.isChecked()) {
                        prices.set(position, priceItemAdd);
                        displayPrice();
                    }

                }
            });


            binding.customItemTvCartBtnLess.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int itemCount = Integer.parseInt(binding.customItemTvCartBtnCount.getText() + "") - 1;
                    if (itemCount > 0) {
                        int priceItemLess = itemCount * itemPrice;
                        binding.customItemTvCartBtnCount.setText(itemCount + "");
                        if (binding.customItemRvCartCheck.isChecked()) {
                            prices.set(position, priceItemLess);
                            displayPrice();
                        }
                    }
                }
            });


            binding.customItemRvCartCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    int itemCountCheck = Integer.parseInt(binding.customItemTvCartBtnCount.getText() + "");
                    if (b) {
                        int priceItemCheck = itemCountCheck * itemPrice;
                        prices.set(position, priceItemCheck);
                    } else {
                        prices.set(position, 0);
                    }
                    checks.set(position,b);
                    displayCheck();
                    displayPrice();
                }
            });
        }
    }
}
