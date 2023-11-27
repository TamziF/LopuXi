package com.example.lopuxi.authoregistration;

import android.content.SharedPreferences;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.lopuxi.ApiStatus;
import com.example.lopuxi.network.App;
import com.example.lopuxi.network.AuthAnswer;
import com.example.lopuxi.network.PostAuthReg;
import com.example.lopuxi.network.RegAnswer;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AuthorizationRegistrationViewModel extends ViewModel {

    //TODO private _authStatus
    private MutableLiveData<String> _usernameF = new MutableLiveData<String>("");
    public LiveData<String> usernameF = _usernameF;

    private MutableLiveData<String> _passwordF = new MutableLiveData<String>("");
    public LiveData<String> passwordF = _passwordF;

    public MutableLiveData<ApiStatus> _authRegStatus = new MutableLiveData<ApiStatus>(ApiStatus.LOADING);
    public LiveData<ApiStatus> authRegStatus = _authRegStatus;

    public MutableLiveData<ApiStatus> _authStatus = new MutableLiveData<ApiStatus>(ApiStatus.LOADING);
    public LiveData<ApiStatus> authStatus = _authStatus;
    private MutableLiveData<ApiStatus> _regStatus = new MutableLiveData<ApiStatus>(ApiStatus.LOADING);
    public LiveData<ApiStatus> regStatus = _regStatus;

    /*private MutableLiveData<Boolean> _endRegistration = new MutableLiveData<Boolean>(false);
    public LiveData<Boolean> endRegistration = _endRegistration;*/

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

    public String token;

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
        if (!this.regEmail.getValue().isEmpty() & !regPassword.getValue().isEmpty() & Objects.equals(regPassword.getValue(), regPasswordRepeat.getValue()))
            _showRegButton.setValue(true);
        else
            _showRegButton.setValue(false);
    }

    public void setRegPassword(String regPassword) {
        _regPassword.setValue(regPassword);
        if (!regEmail.getValue().isEmpty() & !this.regPassword.getValue().isEmpty() & Objects.equals(this.regPassword.getValue(), regPasswordRepeat.getValue()))
            _showRegButton.setValue(true);
        else
            _showRegButton.setValue(false);
    }

    public void setRegPasswordRepeat(String regPasswordRepeat) {
        _regPasswordRepeat.setValue(regPasswordRepeat);
        if (!regEmail.getValue().isEmpty() & !regPassword.getValue().isEmpty() & Objects.equals(regPassword.getValue(), this.regPasswordRepeat.getValue()))
            _showRegButton.setValue(true);
        else
            _showRegButton.setValue(false);
    }

    public void registration(String username, String password) {
        App.getApi().registration(new PostAuthReg(username, password)).enqueue(new Callback<RegAnswer>() {
            @Override
            public void onResponse(Call<RegAnswer> call, Response<RegAnswer> response) {
                if (response.code() == 200) {
                    authorization(username, password);
                } else if (response.code() == 403){
                    _regStatus.setValue(ApiStatus.DONE);
                }
                else{
                    _regStatus.setValue(ApiStatus.ERROR);
                }
                Log.v("TESTOSTERON", String.valueOf(response.code()));
            }

            @Override
            public void onFailure(Call<RegAnswer> call, Throwable t) {
                _regStatus.setValue(ApiStatus.ERROR);
                Log.v("TESTOSTERON", t.toString());
            }
        });
    }

    public void authorization(String username, String password){
        App.getApi().authorization(new PostAuthReg(username, password)).enqueue(new Callback<AuthAnswer>() {
            @Override
            public void onResponse(Call<AuthAnswer> call, Response<AuthAnswer> response) {
                if (response.code() == 200){
                    token = response.body().token;
                    Log.v("TESTOSTERON", "AuthReq " + response.body().token);
                    _usernameF.setValue(username);
                    _passwordF.setValue(password);
                    _authStatus.setValue(ApiStatus.DONE);
                }
                else _authStatus.setValue(ApiStatus.ERROR);
            }

            @Override
            public void onFailure(Call<AuthAnswer> call, Throwable t) {
                _authStatus.setValue(ApiStatus.ERROR);
            }
        });
    }

    @Override
    public void onCleared() {
        super.onCleared();
        _usernameF.setValue("");
        _passwordF.setValue("");
        _authRegStatus.setValue(ApiStatus.LOADING);
        _authStatus.setValue(ApiStatus.LOADING);
        _regStatus.setValue(ApiStatus.LOADING);
        _email.setValue("");
        _regEmail.setValue("");
        _password.setValue("");
        _regPassword.setValue("");
        _regPasswordRepeat.setValue("");
        _showAuthButton.setValue(false);
        _showRegButton.setValue(false);
        token = "";
    }
}