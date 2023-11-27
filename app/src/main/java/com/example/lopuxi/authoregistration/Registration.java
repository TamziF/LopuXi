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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.lopuxi.ApiStatus;
import com.example.lopuxi.authoregistration.AuthorizationRegistrationViewModel;
import com.example.lopuxi.databinding.FragmentRegistrationBinding;

public class Registration extends Fragment {

    private FragmentRegistrationBinding binding;

    private AuthorizationRegistrationViewModel sharedViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentRegistrationBinding.inflate(inflater, container, false);
        sharedViewModel = new ViewModelProvider(requireActivity()).get(AuthorizationRegistrationViewModel.class);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.setSharedViewModel(sharedViewModel);
        binding.setLifecycleOwner(this);

        sharedViewModel.regStatus.observe(getViewLifecycleOwner(), new Observer<ApiStatus>() {
            @Override
            public void onChanged(ApiStatus apiStatus) {
                if(apiStatus == ApiStatus.ERROR) {
                    binding.statusImage.setVisibility(View.GONE);
                    binding.signUpBtn.setVisibility(View.VISIBLE);
                    Toast.makeText(requireContext(), "Registration error", Toast.LENGTH_SHORT).show();
                }
                if(apiStatus == ApiStatus.DONE){
                    //TODO
                    Toast.makeText(requireContext(), "You already have an account", Toast.LENGTH_SHORT).show();
                    NavDirections action = RegistrationDirections.actionRegistrationToAuthorization();
                    Navigation.findNavController(requireView()).navigate(action);
                }
            }
        });

        sharedViewModel.authStatus.observe(getViewLifecycleOwner(), new Observer<ApiStatus>() {
            @Override
            public void onChanged(ApiStatus apiStatus) {
                if(apiStatus == ApiStatus.DONE){
                    NavDirections action = RegistrationDirections.actionRegistrationToFeed();
                    Navigation.findNavController(requireView()).navigate(action);
                    sharedViewModel.onCleared();
                }
            }
        });

        binding.registrationEmailEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                sharedViewModel.setRegEmail(binding.registrationEmailEt.getText().toString());
            }
        });

        binding.registrationPasswordEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                sharedViewModel.setRegPassword(binding.registrationPasswordEt.getText().toString());
            }
        });

        binding.registrationRepeatPasswordEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                sharedViewModel.setRegPasswordRepeat(binding.registrationRepeatPasswordEt.getText().toString());
            }
        });

        binding.signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedViewModel.registration(binding.registrationEmailEt.getText().toString(), binding.registrationPasswordEt.getText().toString());
                binding.signUpBtn.setVisibility(View.INVISIBLE);
                binding.statusImage.setVisibility(View.VISIBLE);
            }
        });
    }
}