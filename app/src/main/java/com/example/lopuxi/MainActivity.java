package com.example.lopuxi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.lopuxi.authoregistration.AuthorizationDirections;
import com.example.lopuxi.authoregistration.AuthorizationRegistrationViewModel;
import com.example.lopuxi.databinding.ActivityMainBinding;
import com.example.lopuxi.mainpart.MainViewModel;
import com.example.lopuxi.network.App;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        AuthorizationRegistrationViewModel sharedViewModel = new ViewModelProvider(this).get(AuthorizationRegistrationViewModel.class);
        MainViewModel mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        sharedViewModel.authStatus.observe(this, new Observer<ApiStatus>() {
            @Override
            public void onChanged(ApiStatus apiStatus) {
                if (apiStatus == ApiStatus.DONE) {
                    binding.bottomNavigation.setVisibility(View.VISIBLE);
                    SharedPreferences sharedPreferences = getSharedPreferences("LopuXi", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    Log.v("TESTOSTERON", "Share: " + sharedViewModel.usernameF.getValue());
                    editor.putString("username", sharedViewModel.usernameF.getValue());
                    editor.putString("password", sharedViewModel.passwordF.getValue());
                    editor.apply();
                    App.reNewDate();
                    mainViewModel.token = sharedViewModel.token;
                    Log.v("TESTOSTERON", "Main" + mainViewModel.token);
                    //sharedViewModel._authStatus.setValue(ApiStatus.LOADING);
                }
            }
        });

        AppBarConfiguration appBarConfiguration =new AppBarConfiguration.Builder(
                R.id.feed, R.id.createPost
        ).build();

        NavHostFragment navHostFragment = (NavHostFragment)getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        NavController navController = navHostFragment.getNavController();
        //NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.bottomNavigation, navController);

    }
}