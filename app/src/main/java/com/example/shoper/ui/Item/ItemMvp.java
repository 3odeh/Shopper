package com.example.shoper.ui.Item;

import com.example.shoper.model.Item;

import java.util.ArrayList;

public interface ItemMvp {
    interface presenter{

        void itemChecked(String id,boolean check);

    }

    interface view{
        void displayPhotos(ArrayList<String> photo);
        void displayCheck(boolean check);
    }
}
