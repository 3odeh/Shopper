package com.example.shoper.ui.cart;

import com.example.shoper.model.Item;

import java.util.ArrayList;


public interface CartMvp {



    interface view{
        void displayPrice(ArrayList<Integer> prices);
        void displayAllCheck(ArrayList<Boolean> check);
        void onItemClick(Item item);
    }
}
