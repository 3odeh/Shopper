package com.example.shoper.ui.Item;


import android.net.Uri;



import com.example.shoper.model.Item;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class ItemPresenter implements ItemMvp.presenter {
    private ItemMvp.view view;

    public ItemPresenter(ItemMvp.view view) {
        this.view = view;
    }

    public void setItemPhoto(Item item) {

        final int[] size = {0};
        ArrayList<String> photos = new ArrayList<>();
        FirebaseStorage.getInstance().getReference().child("Item/" + item.getId()).listAll().addOnSuccessListener(new OnSuccessListener<ListResult>() {
            @Override
            public void onSuccess(ListResult listResult) {
                if (listResult != null && listResult.getItems().size() != 0)
                    for (StorageReference item1 : listResult.getItems()) {
                        item1.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                photos.add(uri + "");
                                size[0]++;
                                if (listResult.getItems().size() == size[0]) {

                                    view.displayPhotos(photos);
                                }
                            }
                        });
                    }
                else {
                    photos.add(item.getCategoryPhoto(item.getCategoryName()));
                    view.displayPhotos(photos);
                }
            }
        });
    }

    @Override
    public void itemChecked(String id, boolean check) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Users").child(user.getUid()).child("Favorite");
        if (check)
            myRef.child(id).setValue(true);
        else
            myRef.child(id).removeValue();
    }

    public void check(String id) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Users").child(user.getUid()).child("Favorite");
        myRef.child(id).get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                if (dataSnapshot != null) {
                    view.displayCheck(dataSnapshot.exists());
                } else
                    view.displayCheck(false);
            }
        });
    }


}
