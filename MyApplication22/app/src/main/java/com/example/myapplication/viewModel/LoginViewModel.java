package com.example.myapplication.viewModel;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.Model.Apiresponseadd;
import com.example.myapplication.Model.User;
import com.example.myapplication.constants.constants;
import com.example.myapplication.repository.RetrofitInstance;
import com.example.myapplication.repository.RetrofitInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginViewModel extends ViewModel {
    Context context;
    public MutableLiveData<String> email = new MutableLiveData<>();
    public MutableLiveData<String> password = new MutableLiveData<>();
    public MutableLiveData<String> errorPassword = new MutableLiveData<>();
    public MutableLiveData<String> errorEmail = new MutableLiveData<>();
    private MutableLiveData<Boolean> navigateToNextScreen = new MutableLiveData<>();
    private MutableLiveData<Boolean> navigateToHomeScreen = new MutableLiveData<>();
    public MutableLiveData<Integer> busy;

    public LoginViewModel(Context context) {
        this.context = context;
    }

    com.example.myapplication.constants.constants constants = new constants();
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(constants.baseurl)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    com.example.myapplication.backendapi backendapi = retrofit.create(com.example.myapplication.backendapi.class);

    public MutableLiveData<Integer> getBusy() {
        if (busy == null) {
            busy = new MutableLiveData<>();
            busy.setValue(8);
        }
        return busy;
    }

    private MutableLiveData<User> userMutableLiveData;

    LiveData<User> getUser() {
        if (userMutableLiveData == null) {
            userMutableLiveData = new MutableLiveData<>();
        }

        return userMutableLiveData;
    }

    public void navigateToNextScreen() {
        navigateToNextScreen.setValue(true);
    }

    // Getter để lắng nghe sự kiện chuyển màn hình
    public LiveData<Boolean> getNavigateToNextScreen() {
        return navigateToNextScreen;
    }


    public void navigateToHomeScreen() {
        navigateToHomeScreen.setValue(true);
    }

    // Getter để lắng nghe sự kiện chuyển màn hình
    public LiveData<Boolean> getNavigateToNextHomeScreen() {
        return navigateToHomeScreen;
    }

    public void onLoginClicked() {

        getBusy().setValue(0); //View.VISIBLE
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if (userMutableLiveData == null) {
                    userMutableLiveData = new MutableLiveData<>();
                }
                User user = new User(0, "", "", email.getValue(), password.getValue(), "", 0);

                if (!user.isEmailValid()) {
                    errorEmail.setValue("Enter a valid email address");

                } else {
                    errorEmail.setValue(null);
                }

                if (!user.isPasswordLengthGreaterThan5())
                    errorPassword.setValue("Password Length should be greater than 5");
                else {
                    errorPassword.setValue(null);
                }


                if (errorPassword.getValue() == null && errorEmail.getValue() == null) {
                    userMutableLiveData.setValue(user);
                    Call<Apiresponseadd> call = backendapi.userlogin(userMutableLiveData.getValue().getEmail(), userMutableLiveData.getValue().getPassword());
                    call.enqueue(new Callback<Apiresponseadd>() {
                        @Override
                        public void onResponse(Call<Apiresponseadd> call, Response<Apiresponseadd> response) {
                            if (response.body().isError() == false) {
                                Apiresponseadd.UserData userData = response.body().getData();
                                SharedPreferences sharedPref = context.getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPref.edit();
                                editor.putString("Token", userData.getToken());
                                editor.putString("emailuser", email.getValue());
                                editor.putInt("iduser", userData.getUserId());
                                String token = userData.getToken();
                                Log.d("token0", "tk" + token);
                                editor.commit();
                                navigateToHomeScreen();
                            } else {
                                Toast.makeText(context, "email or password not found", Toast.LENGTH_SHORT).show();
                            }

                        }

                        @Override
                        public void onFailure(Call<Apiresponseadd> call, Throwable t) {

                        }
                    });


                }
                busy.setValue(8); //8 == View.GONE

            }
        }, 3000);
    }


}
