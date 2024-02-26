package com.example.myapplication.viewModel;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.Model.Apiresponseadd;
import com.example.myapplication.Model.Saloon;
import com.example.myapplication.Model.User;
import com.example.myapplication.backendapi;
import com.example.myapplication.constants.constants;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisterViewModel extends ViewModel {
    Context context;
    public MutableLiveData<String> email = new MutableLiveData<>();
    public MutableLiveData<String> password = new MutableLiveData<>();
    public MutableLiveData<String> name = new MutableLiveData<>();
    public MutableLiveData<String> mobile = new MutableLiveData<>();
    public MutableLiveData<String> address = new MutableLiveData<>();
    public MutableLiveData<Integer> idadmin = new MutableLiveData<>();
    public MutableLiveData<Integer> selecteditem = new MutableLiveData<>();
    public MutableLiveData<List<String>> salonlistname;
    public MutableLiveData<List<Saloon>> listsalon = new MutableLiveData<>();
    public MutableLiveData<String> errorPassword = new MutableLiveData<>();
    public MutableLiveData<String> errorEmail = new MutableLiveData<>();
    public MutableLiveData<String> errorName = new MutableLiveData<>();
    public MutableLiveData<String> errorMobile = new MutableLiveData<>();
    public MutableLiveData<String> errorAddress = new MutableLiveData<>();
    private MutableLiveData<Boolean> navigateToNextScreen = new MutableLiveData<>();
    public MutableLiveData<Integer> busy;

    public RegisterViewModel(Context context) {
        this.context = context;
        idadmin.setValue(0);
        getlistsalon();
        selecteditem.setValue(0);
        Log.d("sd", "lódo");
        salonlistname = new MutableLiveData<>();

    }

    public LiveData<List<String>> getBddressTypes() {
        return salonlistname;
    }
    com.example.myapplication.constants.constants constants = new constants();
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(constants.baseurl)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    com.example.myapplication.backendapi backendapi = retrofit.create(com.example.myapplication.backendapi.class);


    public void getlistsalon() {

        Call<List<Saloon>> call = backendapi.getSaloon();
        call.enqueue(new Callback<List<Saloon>>() {
            @Override
            public void onResponse(Call<List<Saloon>> call, Response<List<Saloon>> response) {
                List<Saloon> saloonList = response.body();
                listsalon.setValue(saloonList);
                Log.d("uui", "dddd");
                if (saloonList != null && !saloonList.isEmpty()) {
                    List<String> salonname = new ArrayList<>();
                    for (Saloon saloon : saloonList) {
                        salonname.add(saloon.getName());

                    }
                    salonlistname.setValue(salonname);
                }

            }

            @Override
            public void onFailure(Call<List<Saloon>> call, Throwable t) {
                Log.d("faill", t.getMessage());
            }
        });

    }


    public void navigateToNextScreen() {
        navigateToNextScreen.setValue(true);
    }

    // Getter để lắng nghe sự kiện chuyển màn hình
    public LiveData<Boolean> getNavigateToNextScreen() {
        return navigateToNextScreen;
    }

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

    public void getidadmin() {

        List<Saloon> listsaloon = listsalon.getValue();
        Saloon saloon = listsaloon.get(selecteditem.getValue());
        idadmin.setValue(Integer.parseInt(saloon.getId_AdminSaloons()));
        Log.d("huu", String.valueOf(idadmin.getValue()));

    }

    public void onRegisterClicked() {
        getidadmin();
        getBusy().setValue(0); //View.VISIBLE
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if (userMutableLiveData == null) {
                    userMutableLiveData = new MutableLiveData<>();
                }
                User user = new User(0, name.getValue(), mobile.getValue(), email.getValue(), password.getValue(), address.getValue(), idadmin.getValue());

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

                if (!user.isAddressLengthGreaterThan5())
                    errorAddress.setValue("AddressLength should be greater than 5");
                else {
                    errorAddress.setValue(null);
                }


                if (!user.isNameLengthGreaterThan5()) {
                    errorName.setValue("Enter a valid name");
                } else {
                    errorName.setValue(null);
                }

                if (!user.isMobileLengthGreaterThan5())
                    errorMobile.setValue("Password Length should be greater than 5");
                else {
                    errorMobile.setValue(null);
                }
                Log.d("hue", "vaoday");
                if (errorEmail.getValue() == null && errorAddress.getValue() == null && errorMobile.getValue() == null && errorName.getValue() == null && errorPassword.getValue() == null) {
                    userMutableLiveData.setValue(user);

                    Call<Apiresponseadd> call = backendapi.createuser(userMutableLiveData.getValue());
                    call.enqueue(new Callback<Apiresponseadd>() {
                                     @Override
                                     public void onResponse(Call<Apiresponseadd> call, Response<Apiresponseadd> response) {
                                         if (response.isSuccessful()) {

                                             Toast.makeText(context, "successfully", Toast.LENGTH_SHORT).show();
                                             Log.d("MainActivity1", String.valueOf(response.body().getMessage()));
                                             if (response.body().getMessage().equals("successfully")) {
                                                 email.setValue("");
                                                 address.setValue("");
                                                 mobile.setValue("");
                                                 name.setValue("");
                                                 password.setValue("");
                                             }

                                         }
                                     }

                                     @Override
                                     public void onFailure(Call<Apiresponseadd> call, Throwable t) {
                                         Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
                                         Log.d("MainActivity1", t.getMessage());
                                     }
                                 }
                    );


                }
                busy.setValue(8); //8 == View.GONE

            }
        }, 3000);
    }

    public void reseterror() {
        errorAddress.setValue(null);
        errorEmail.setValue(null);
        errorMobile.setValue(null);
        errorName.setValue(null);
        errorPassword.setValue(null);
    }

}
