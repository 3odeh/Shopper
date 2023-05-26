package com.example.shoper.ui.Item;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.View;


import com.example.shoper.databinding.ActivityItemBinding;
import com.example.shoper.model.Item;
import com.example.shoper.ui.ProgressDialog;

import java.util.ArrayList;

public class ItemActivity extends AppCompatActivity implements ItemMvp.view{


    private ActivityItemBinding binding;
    private Item item;
    private ArrayList<String> photos;
    private ItemPresenter presenter;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityItemBinding.inflate(getLayoutInflater());
        dialog = new ProgressDialog(this,"Loading");


        dialog.showProgress();
        item = (Item) (getIntent().getSerializableExtra("item"));

        binding.itemTvName.setText(item.getName());
        binding.itemTvPrice.setText(item.getPrice());
        binding.itemTvDescription.setText(item.getDescription());

        presenter = new ItemPresenter(this);
        presenter.check(item.getId());
        presenter.setItemPhoto(item);

        binding.itemCbBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        binding.itemBtnCart.setOnClickListener(  new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    public void displayPhotos(ArrayList<String> photo) {
        item.setItemPhoto(photo);
        photos = photo;
        binding.itemTvPosition.setText("0 / "+photos.size());
        binding.itemVpPhoto.setAdapter(new ItemActivity.ViewPagerAdapter(this));

        binding.itemVpPhoto.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                String s =(position+1) +" / "+photos.size();
                binding.itemTvPosition.setText(s);
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }
        });
        setContentView(binding.getRoot());
        dialog.hideProgress();


    }

    @Override
    public void displayCheck(boolean check) {
        binding.itemCbFavorite.setChecked(check);
    }

    class ViewPagerAdapter extends FragmentStateAdapter {

        public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
            super(fragmentActivity);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            return ItemPhotoFragment.newInstance(photos.get(position));
        }
        @Override
        public int getItemCount() {
            return photos.size();
        }

    }

    @Override
    public void finish() {
        super.finish();
        presenter.itemChecked(item.getId(),binding.itemCbFavorite.isChecked());
    }
}