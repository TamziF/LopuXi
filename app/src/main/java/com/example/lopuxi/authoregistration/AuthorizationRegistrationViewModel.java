package com.example.lopuxi.authoregistration;

import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.Locale;
import java.util.Objects;

public class AuthorizationRegistrationViewModel extends ViewModel {

    private Boolean endRegistration = false;

    private MutableLiveData<String> _email = new MutableLiveData<String>("");
    public LiveData<String> email = _email;

    private MutableLiveData<String> _password = new MutableLiveData<String>("");
    public LiveData<String> password = _password;

    private MutableLiveData<String> _regEmail = new MutableLiveData<String>("");
    public LiveData<String> regEmail = _regEmail;

    private MutableLiveData<String> _regPassword = new MutableLiveData<String>("");
    public LiveData<String> regPassword = _regPassword;

    private MutableLiveData<String> _regPasswordRepeat = new MutableLiveData<String>("");
    public LiveData<String> regPasswordRepeat = _regPasswordRepeat;

    private MutableLiveData<Boolean> _showAuthButton = new MutableLiveData<Boolean>(false);
    public LiveData<Boolean> showAuthButton = _showAuthButton;

    private MutableLiveData<Boolean> _showRegButton = new MutableLiveData<Boolean>(false);
    public LiveData<Boolean> showRegButton = _showRegButton;

    public void setEmail(String email) {
        _email.setValue(email);
        if (!this.email.getValue().isEmpty() & !password.getValue().isEmpty())
            _showAuthButton.setValue(true);
        else
            _showAuthButton.setValue(false);
    }

    public void setPassword(String password) {
        _password.setValue(password);
        if (!email.getValue().isEmpty() & !this.password.getValue().isEmpty())
            _showAuthButton.setValue(true);
        else
            _showAuthButton.setValue(false);
    }

    public void setRegEmail(String regEmail) {
        _regEmail.setValue(regEmail);
        if(!this.regEmail.getValue().isEmpty() & !regPassword.getValue().isEmpty() & Objects.equals(regPassword.getValue(), regPasswordRepeat.getValue()))
            _showRegButton.setValue(true);
        else
            _showRegButton.setValue(false);
    }

    public void setRegPassword(String regPassword) {
        _regPassword.setValue(regPassword);
        if(!regEmail.getValue().isEmpty() & !this.regPassword.getValue().isEmpty() & Objects.equals(this.regPassword.getValue(), regPasswordRepeat.getValue()))
            _showRegButton.setValue(true);
        else
            _showRegButton.setValue(false);
    }

    public void setRegPasswordRepeat(String regPasswordRepeat) {
        _regPasswordRepeat.setValue(regPasswordRepeat);
        if(!regEmail.getValue().isEmpty() & !regPassword.getValue().isEmpty() & Objects.equals(regPassword.getValue(), this.regPasswordRepeat.getValue()))
            _showRegButton.setValue(true);
        else
            _showRegButton.setValue(false);
    }

    public Boolean getEndRegistration() {
        return endRegistration;
    }

    public void setEndRegistration(Boolean endRegistration) {
        this.endRegistration = endRegistration;
    }
}