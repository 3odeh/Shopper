package com.example.shoper.ui.Home;


import com.example.shoper.model.Category;
import com.example.shoper.model.Item;

import java.util.ArrayList;

public interface HomeMvp {

     interface Presenter{

    }

     interface View{
         void displayCategories(ArrayList<Category> categories);
         void displayItems(ArrayList<Item> items);

    }

}
