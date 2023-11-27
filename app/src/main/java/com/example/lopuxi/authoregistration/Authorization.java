package com.example.lopuxi.authoregistration;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.lopuxi.ApiStatus;
import com.example.lopuxi.databinding.FragmentAuthorizationBinding;
import com.example.lopuxi.network.App;

public class Authorization extends Fragment {

    private FragmentAuthorizationBinding binding;

    private AuthorizationRegistrationViewModel sharedViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentAuthorizationBinding.inflate(inflater, container, false);
        sharedViewModel = new ViewModelProvider(requireActivity()).get(AuthorizationRegistrationViewModel.class);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.setSharedViewModel(sharedViewModel);
        binding.setLifecycleOwner(this);

        sharedViewModel.authStatus.observe(getViewLifecycleOwner(), new Observer<ApiStatus>() {
            @Override
            public void onChanged(ApiStatus apiStatus) {
                if(apiStatus == ApiStatus.DONE){
                    sharedViewModel._authRegStatus.setValue(ApiStatus.DONE);
                    NavDirections action = AuthorizationDirections.actionAuthorizationToFeed();
                    Navigation.findNavController(requireView()).navigate(action);
                    Log.v("TESTOSTERON", "AuthReq" + sharedViewModel.token);
                    sharedViewModel.onCleared();
                } else if (apiStatus == ApiStatus.ERROR) {
                    binding.statusImage.setVisibility(View.GONE);
                    binding.signInBtn.setVisibility(View.VISIBLE);
                    Toast.makeText(requireContext(), "Wrong password or username", Toast.LENGTH_SHORT).show();
                }
            }
        });

        binding.toSignUpTv.setOnClickListener(view1 -> {
            NavDirections action = AuthorizationDirections.actionAuthorizationToRegistration();
            Navigation.findNavController(view1).navigate(action);
        });

        binding.authorizationEmailEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                sharedViewModel.setEmail(binding.authorizationEmailEt.getText().toString());
            }
        });

        binding.authorizationPasswordEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                sharedViewModel.setPassword(binding.authorizationPasswordEt.getText().toString());
            }
        });

        binding.signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO раскоментить строчку ниже, удалить хардкод _authStatus
                //sharedViewModel._authStatus.setValue(ApiStatus.DONE);
                binding.signInBtn.setVisibility(View.INVISIBLE);
                binding.statusImage.setVisibility(View.VISIBLE);
                sharedViewModel.authorization(binding.authorizationEmailEt.getText().toString(), binding.authorizationPasswordEt.getText().toString());
            }
        });

    }
}