package com.example.lopuxi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import com.example.lopuxi.authoregistration.AuthorizationRegistrationViewModel;
import com.example.lopuxi.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        AuthorizationRegistrationViewModel sharedViewModel = new ViewModelProvider(this).get(AuthorizationRegistrationViewModel.class);
    }
}