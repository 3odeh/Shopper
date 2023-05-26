package com.example.shoper;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.shoper.databinding.ActivityMainBinding;
import com.example.shoper.ui.Home.HomeActivity;
import com.example.shoper.ui.Login.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


    }

    private void updateUI(FirebaseUser user) {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent;
                if (user != null)
                    intent = new Intent(MainActivity.this, HomeActivity.class);
                else
                    intent = new Intent(MainActivity.this, LoginActivity.class);

                startActivity(intent);
            }
        },1000);

    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        updateUI(user);
    }


}