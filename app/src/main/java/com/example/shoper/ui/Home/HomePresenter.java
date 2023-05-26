package com.example.shoper.ui.Home;


import androidx.annotation.NonNull;

import com.example.shoper.model.Category;
import com.example.shoper.model.Item;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;

import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class HomePresenter implements HomeMvp.Presenter {

    private final HomeMvp.View view;
    private FirebaseFirestore db;
    private StorageReference storageRef;


    public HomePresenter(HomeMvp.View view) {
        this.view = view;
        db = FirebaseFirestore.getInstance();
        storageRef = FirebaseStorage.getInstance().getReference();
    }

    public void setCategoryAdapter() {

        ArrayList<Category> categories = new ArrayList<>();
        db.collection("Category").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    System.out.println(task.getResult().size());
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        String name = document.getId();

                        String photo = document.getString("photo");
                        Category category = new Category(name, photo);
                        categories.add(category);
                        Category.putCategoryPhoto(name, photo);
                    }
                    view.displayCategories(categories);
                } else {
                    System.out.println("Error getting documents." + task.getException());
                }
            }
        });
    }

    public void test() {

        FirebaseAuth.getInstance().signOut();
    }

    public void setItemAdapter() {


        {
//        db.collection("Category").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                if (task.isSuccessful()) {
//                    ArrayList<Category> categories = new ArrayList<>();
//                    for (QueryDocumentSnapshot document : task.getResult()) {
//                        categories.add(new Category(document.getId(), document.getString("photo")));
//                    }
//
//                    for (Category category : categories) {
//                        db.collection("Category").document(category.getCategoryName()).collection("Items")
//                                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                                    @Override
//                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                                        if (task.isSuccessful()) {
//                                            ArrayList<String> allId = new ArrayList<>();
//
//                                            for (QueryDocumentSnapshot document : task.getResult()) {
//                                                allId.add(document.getId());
//                                            }
//
//                                            for (String id : allId) {
//
//
//                                                final String[] name = new String[1];
//                                                final String[] price = new String[1];
//                                                final String[] description = new String[1];
//                                                final String[] size = new String[1];
//
//
//                                                db.collection("Category").document(category.getCategoryName()).collection("Items").document(id)
//                                                        .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
//                                                            @Override
//                                                            public void onSuccess(DocumentSnapshot document) {
//                                                                name[0] = document.getString("name");
//                                                                price[0] = document.getString("price");
//                                                                description[0] = document.getString("description");
//                                                                size[0] = document.getString("size");
//
//                                                                ArrayList<String> photos = new ArrayList<>();
//                                                                FirebaseStorage.getInstance().getReference().child("Item/" + id).listAll().addOnSuccessListener(new OnSuccessListener<ListResult>() {
//                                                                    @Override
//                                                                    public void onSuccess(ListResult listResult) {
//                                                                        for (StorageReference item : listResult.getItems()) {
//                                                                            System.out.println("Name : " + item.getName());
//                                                                            System.out.println("Download Url : " + item.getDownloadUrl());
//                                                                            photos.add(item.getDownloadUrl() + "");
//                                                                        }
//                                                                        Item item = new Item(category.getCategoryName(), category.getCategoryPhoto(), id, name[0], price[0], description[0], size[0], photos);
//                                                                        allItems.add(item);
//                                                                    }
//                                                                }).addOnCompleteListener(new OnCompleteListener<ListResult>() {
//                                                                    @Override
//                                                                    public void onComplete(@NonNull Task<ListResult> task) {
//
//                                                                        if (allId.get(allId.size() - 1).equals(id)) {
//                                                                            System.out.println("Doneeeeeeeee");
//                                                                            view.displayItems(allItems);
//                                                                        }
//
//                                                                    }
//                                                                });
//
//                                                            }
//                                                        });
//
//                                            }
//
//
////                                            for (QueryDocumentSnapshot document : task.getResult()) {
////
////
////                                                String name = document.getString("name");
////                                                String id = document.getId();
////                                                String price = document.getString("price");
////                                                String description = document.getString("description");
////                                                String size = document.getString("size");
////
////                                                ArrayList<String> photos = new ArrayList<>();
////                                                FirebaseStorage.getInstance().getReference().child("Item/" + id).listAll().addOnSuccessListener(new OnSuccessListener<ListResult>() {
////                                                    @Override
////                                                    public void onSuccess(ListResult listResult) {
////                                                        for (StorageReference item : listResult.getItems()) {
////                                                            System.out.println("Name : " + item.getName());
////                                                            System.out.println("Download Url : " + item.getDownloadUrl());
////                                                            photos.add(item.getDownloadUrl() + "");
////                                                        }
////                                                    }
////                                                });
////
////                                                Item item = new Item(category.getCategoryName(), category.getCategoryPhoto(), id, name, price, description, size, photos);
////                                                allItems.add(item);
////                                            }
//
//                                        } else {
//                                            System.out.println("Error getting documents." + task.getException());
//                                        }
//                                    }
//                                });
//
//                    }
//
//                } else {
//                    System.out.println("Error getting documents." + task.getException());
//                }
//
//            }
//        });

        }


        ArrayList<Item> allItems = new ArrayList<>();


        db.collection("Item").orderBy("id").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful() && !task.getResult().isEmpty()){
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        String category = document.getString("category");
                        String name = document.getString("name");
                        String price = document.getString("price");
                        String description = document.getString("description");
                        String size = document.getString("size");
                        String mainPhoto = document.getString("photo");
                        Item item1 = new Item(category, document.getId(), size,name,price,description,mainPhoto);
                        allItems.add(item1);
                    }
                }
                System.out.println("done");
                view.displayItems(allItems);
//                ArrayList<String> photos = new ArrayList<>();
//                for (Item item : allItems) {
//                    FirebaseStorage.getInstance().getReference().child("Item/" + item.getId()).listAll().addOnSuccessListener(new OnSuccessListener<ListResult>() {
//                        @Override
//                        public void onSuccess(ListResult listResult) {
//                            for (StorageReference item1 : listResult.getItems()) {
//                                item1.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                                    @Override
//                                    public void onSuccess(Uri uri) {
//                                        photos.add(uri + "");
//
//                                    }
//                                }).addOnSuccessListener(new OnSuccessListener<Uri>() {
//                                    @Override
//                                    public void onSuccess(Uri uri) {
//                                        for (Item temp : allItems) {
//                                            if (uri.getLastPathSegment().startsWith(temp.getItemPhotoPath())) {
//                                                temp.setItemPhoto(photos);
//                                            }
//                                        }
//                                    }
//                                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
//                                    @Override
//                                    public void onComplete(@NonNull Task<Uri> task) {
//                                        if(listResult.getItems().size() == photos.size()){
//                                            System.out.println("Doneeeeeeeee");
//                                            view.displayItems(allItems);
//                                        }
//                                    }
//                                });
//                            }
//                        }
//                    });
//                }
            }
        });


    }

    public void setAllCategoryItem(Category category) {
        System.out.println(category.getCategoryName());
        if (!category.getCategoryName().equals("All")) {

            ArrayList<Item> allItems = new ArrayList<>();

            db.collection("Item").whereEqualTo("category", category.getCategoryName()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful() && !task.getResult().isEmpty()) {
                        System.out.println("isSuccessful");
                        for (QueryDocumentSnapshot document : task.getResult()) {

                            String name = document.getString("name");
                            String price = document.getString("price");
                            String description = document.getString("description");
                            String size = document.getString("size");
                            String mainPhoto = document.getString("photo");


                            Item item1 = new Item(category.getCategoryName(), document.getId(), size, name, price, description, mainPhoto);
                            allItems.add(item1);


//                            ArrayList<String> photos = new ArrayList<>();
//                            for (Item item : allItems) {
//                                FirebaseStorage.getInstance().getReference().child("Item").child(item.getId()).listAll().addOnSuccessListener(new OnSuccessListener<ListResult>() {
//                                    @Override
//                                    public void onSuccess(ListResult listResult) {
//                                        for (StorageReference item1 : listResult.getItems()) {
//                                            item1.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                                                @Override
//                                                public void onSuccess(Uri uri) {
//                                                    photos.add(uri + "");
//
//                                                }
//                                            }).addOnSuccessListener(new OnSuccessListener<Uri>() {
//                                                @Override
//                                                public void onSuccess(Uri uri) {
//                                                    System.out.println("set photots");
//                                                    for (Item temp : allItems) {
//                                                        if (uri.getLastPathSegment().startsWith(temp.getItemPhotoPath())) {
//                                                            temp.setItemPhoto(photos);
//                                                        }
//                                                    }
//                                                }
//                                            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
//                                                @Override
//                                                public void onComplete(@NonNull Task<Uri> task) {
//                                                    if(listResult.getItems().size() == photos.size()){
//                                                        System.out.println("Doneeeeeeeee");
//                                                        view.displayItems(allItems);
//                                                    }
//                                                }
//                                            }).addOnFailureListener(new OnFailureListener() {
//                                                @Override
//                                                public void onFailure(@NonNull Exception e) {
//                                                    view.displayItems(allItems);
//                                                }
//                                            });
//                                        }
//
//
//                                    }
//                                });
//                            }

                        }
                    }
                    view.displayItems(allItems);

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    view.displayItems(allItems);
                }
            });
        } else {
            System.out.println("setItemAdapter");
            setItemAdapter();
        }

    }

    {
        //
//    public void putItem(Item item) {
//        // Create a new user with a first and last name
//        Map<String, String> user = new HashMap<>();
//        user.put("name", item.getName());
//        user.put("price", item.getPrice());
//        user.put("description", item.getDescription());
//        user.put("size", item.getSize());
//
//        FirebaseFirestore db = FirebaseFirestore.getInstance();
//
//        // Add a new document with a generated ID
//        db.collection("Category").document(item.getCategoryName()).collection("Items")
//                .add(user)
//                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
//                    @Override
//                    public void onSuccess(DocumentReference documentReference) {
//                        Log.d("TAG", "DocumentSnapshot added with ID: " + documentReference.getId());
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Log.w("TAG", "Error adding document", e);
//                    }
//                });
//    }

    }

}
