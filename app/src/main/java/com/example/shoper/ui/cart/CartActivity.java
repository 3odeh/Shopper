package com.example.shoper.ui.cart;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.example.shoper.databinding.ActivityCartBinding;
import com.example.shoper.model.Item;

import java.util.ArrayList;

public class CartActivity extends AppCompatActivity implements CartMvp.view{

    private ActivityCartBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCartBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        ArrayList<Item> items = new ArrayList<>();
        items.add(new Item("Shoes", "2OvYPud9yX84vTLZLaDT", "M", "Superstar Adidas", "150$",
                "Originally made for basketball courts in the '70s. Celebrated by hip hop royalty in the '80s. The adidas Superstar shoe is now a lifestyle staple for streetwear enthusiasts. The world-famous shell toe feature remains, providing style and protection. Just like it did on the B-ball courts back in the day.",
                "https://firebasestorage.googleapis.com/v0/b/shoper-18c8b.appspot.com/o/Item%2F2OvYPud9yX84vTLZLaDT%2F1.webp?alt=media&token=28d8c4de-b9b7-4791-b875-b24e9956a413"));
        items.add(new Item("Hats", "2OvYPu124X84vTLZLaDT", "M", "Cowboy hat", "70$",
                "he K-Way ECO School Bag prepares the next generation of environmental warriors for school adventures. This bag consists of recycled ripstop polyester made from recycled plastic bottles. Padded shoulder straps for comfortable carrying and heavy-duty lockable YKK zips keep contents secure. The large internal storage compartment makes it easy to organise and store books. The front organiser pocket keeps stationary safe, while the ID/name window makes it easy to identify your bag. It includes a small stationary pouch and is water-repellent.",
                "https://softgoat.centracdn.net/client/dynamic/images/2152_badc85b82b-chunky_t_shirt_natural_sand_2095_front-size1600.jpg"));

        items.add(new Item("Socks", "2OvYPu126X84vTLZLaDT", "M", "Diabetic Socks", "10$",
                "he K-Way ECO School Bag prepares the next generation of environmental warriors for school adventures. This bag consists of recycled ripstop polyester made from recycled plastic bottles. Padded shoulder straps for comfortable carrying and heavy-duty lockable YKK zips keep contents secure. The large internal storage compartment makes it easy to organise and store books. The front organiser pocket keeps stationary safe, while the ID/name window makes it easy to identify your bag. It includes a small stationary pouch and is water-repellent.",
                "https://cdn.shopify.com/s/files/1/1280/1161/products/Dr-Segals-Diabetic-Socks-Neuropathy-Non-Binding-Solid-Black.jpg?v=1583533340&width=990"));
        items.add(new Item("Socks", "2OvYPu121X84vTLZLaDT", "L", "COMFYCUSH OLD SKOOL", "199$",
                "he K-Way ECO School Bag prepares the next generation of environmental warriors for school adventures. This bag consists of recycled ripstop polyester made from recycled plastic bottles. Padded shoulder straps for comfortable carrying and heavy-duty lockable YKK zips keep contents secure. The large internal storage compartment makes it easy to organise and store books. The front organiser pocket keeps stationary safe, while the ID/name window makes it easy to identify your bag. It includes a small stationary pouch and is water-repellent.",
                "https://s7d2.scene7.com/is/image/VansEU/VN0A3WMAVNE-HERO?wid=800&hei=800&fmt=jpg&qlt=85,1&op_sharpen=0&resMode=sharp2&op_usm=1,1,1,0"));


        CartItemAdapter adapter = new CartItemAdapter(this, items, this,true);
        binding.cartRv.setAdapter(adapter);
        binding.cartRv.setLayoutManager(new LinearLayoutManager(this));
        binding.cartRv.setHasFixedSize(true);


        binding.cartCheckAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean b = binding.cartCheckAll.isChecked();
                adapter.setCheck(b);
                adapter.notifyDataSetChanged();
            }
        });

        binding.cartBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }



    @Override
    public void displayPrice(ArrayList<Integer> prices) {
        int price =0;
        for(int i : prices){
            price = price+i;
        }
        binding.cartTvPriceTotal.setText(price+"$");
    }

    @Override
    public void displayAllCheck(ArrayList<Boolean> checks) {
        boolean c = true;
        for (boolean check : checks){
            if(!check){
                c = false;
            }
        }
        binding.cartCheckAll.setChecked(c);
    }

    @Override
    public void onItemClick(Item item) {
        Toast.makeText(this, "onClick", Toast.LENGTH_SHORT).show();
    }

}