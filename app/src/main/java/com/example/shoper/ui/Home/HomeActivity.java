package com.example.shoper.ui.Home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.example.shoper.R;
import com.example.shoper.databinding.ActivityHomeBinding;

import com.example.shoper.databinding.HeaderNavigationDrawerHomeBinding;
import com.example.shoper.model.Category;
import com.example.shoper.model.Item;
import com.example.shoper.ui.Item.ItemActivity;
import com.example.shoper.ui.ProgressDialog;
import com.example.shoper.ui.cart.CartActivity;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;


public class HomeActivity extends AppCompatActivity implements HomeMvp.View, HomeItemAdapter.ClickListener, HomeCategoryAdapter.ClickListener {

    private ActivityHomeBinding binding;
    private ProgressDialog dialog;
    private HomePresenter presenter;
    private HeaderNavigationDrawerHomeBinding headerBinding;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        user = FirebaseAuth.getInstance().getCurrentUser();
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        dialog = new ProgressDialog(this, "Loading");
        setContentView(binding.getRoot());
        displayView();

        binding.homeTopAppBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.menu_home_cart:
                        Intent intent = new Intent(HomeActivity.this, CartActivity.class);
                        startActivity(intent);
                        break;
                }
                return false;
            }
        });


    }

    private void displayView() {
        dialog.showProgress();
//        headerBinding = HeaderNavigationDrawerHomeBinding.bind(binding.navigationView.getHeaderView(0));
//
//        headerBinding.headerHomeName.setText(user.getDisplayName());
//        headerBinding.headerHomeEmail.setText(user.getEmail());
//        if(user.getPhotoUrl() !=null && !user.getPhotoUrl().toString().isEmpty()){
//            Glide.with(this).load(user.getPhotoUrl()).into(headerBinding.headerHomeIv);
//        }else
//            headerBinding.headerHomeIv.setImageResource(R.drawable.ic_love);





//        binding.navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                Toast.makeText(HomeActivity.this, item.getTitle() + " : " + item.getItemId(), Toast.LENGTH_SHORT).show();
//
//
//
//                switch (item.getItemId()){
//                    case R.id.menu_home_profile:
//                        FirebaseAuth.getInstance().signOut();
//                        finish();
//                        break;
//                    case R.id.menu_home_cart:
//                        Toast.makeText(HomeActivity.this, " **cart** " + item.getItemId(), Toast.LENGTH_SHORT).show();
//                }
//                binding.drawerLayout.close();
//
//                return true;
//            }
//        });
        presenter = new HomePresenter(this);
        presenter.setCategoryAdapter();
        presenter.setItemAdapter();

        binding.homeTopAppBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                presenter.test();
            }
        });

    }


    @Override
    public void displayCategories(ArrayList<Category> categories) {
        HomeCategoryAdapter adapter = new HomeCategoryAdapter(this, categories, this);
        binding.homeRvCategory.setAdapter(adapter);
        binding.homeRvCategory.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
    }

    @Override
    public void displayItems(ArrayList<Item> items) {

        HomeItemAdapter adapter = new HomeItemAdapter(this, items, this);
        binding.homeRvItem.setAdapter(adapter);
        binding.homeRvItem.setLayoutManager(new GridLayoutManager(this, 2));
        binding.homeRvItem.setHasFixedSize(true);
        dialog.hideProgress();
    }


    @Override
    public void onClick(Category category) {
        dialog.showProgress();
        presenter.setAllCategoryItem(category);
    }

    @Override
    public void onClick(Item item) {
        Intent intent = new Intent(this, ItemActivity.class);
        intent.putExtra("item", item);
        startActivity(intent);

    }
}