package com.example.shoper.ui.Item;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.shoper.R;
import com.example.shoper.databinding.FragmentItemPhotoBinding;
import com.example.shoper.databinding.FragmentLoginBinding;
import com.example.shoper.model.Category;


public class ItemPhotoFragment extends Fragment {


    private static final String PHOTO = "photo";

    // TODO: Rename and change types of parameters
    private String photo;
    private FragmentItemPhotoBinding binding;

    public ItemPhotoFragment() {
        // Required empty public constructor
    }


    public static ItemPhotoFragment newInstance(String param1) {
        ItemPhotoFragment fragment = new ItemPhotoFragment();
        Bundle args = new Bundle();
        args.putString(PHOTO, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            photo = getArguments().getString(PHOTO);
        }else
            photo = Category.getCategoryPhoto("All");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentItemPhotoBinding.inflate(getActivity().getLayoutInflater());
        Glide.with(getActivity()).load(photo).into(binding.imageView);
        return binding.getRoot();
    }
}