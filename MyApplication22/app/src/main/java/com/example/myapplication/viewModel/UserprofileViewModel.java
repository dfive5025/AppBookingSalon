package com.example.myapplication.viewModel;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.Loginactivity;
import com.example.myapplication.Model.Adminname;
import com.example.myapplication.Model.Apiresponseadd;
import com.example.myapplication.Model.Saloon;
import com.example.myapplication.Model.User;
import com.example.myapplication.R;
import com.example.myapplication.Userdashboardactivity;
import com.example.myapplication.backendapi;
import com.example.myapplication.constants.constants;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserprofileViewModel extends ViewModel {
    Context context;
    public MutableLiveData<String> email = new MutableLiveData<>();
    public MutableLiveData<List<Adminname>> listadmin = new MutableLiveData<>();
    public MutableLiveData<Integer> id_user = new MutableLiveData<>();
    public MutableLiveData<Boolean> edittable = new MutableLiveData<>();
    public MutableLiveData<List<String>> adminname = new MutableLiveData<>();

    public MutableLiveData<String> name = new MutableLiveData<>();
    public MutableLiveData<String> mobile = new MutableLiveData<>();
    public MutableLiveData<String> address = new MutableLiveData<>();
    public MutableLiveData<Integer> idadmin = new MutableLiveData<>();
    public MutableLiveData<Integer> selecteditem = new MutableLiveData<>();
    public MutableLiveData<List<String>> salonlistname = new MutableLiveData<>();
    public MutableLiveData<List<Saloon>> listsalon = new MutableLiveData<>();

    LayoutInflater inflater;
    public MutableLiveData<String> errorEmail = new MutableLiveData<>();
    public MutableLiveData<String> errorName = new MutableLiveData<>();
    public MutableLiveData<String> errorMobile = new MutableLiveData<>();
    public MutableLiveData<String> errorAddress = new MutableLiveData<>();
    public MutableLiveData<Integer> busy = new MutableLiveData<>();
    private MutableLiveData<User> userMutableLiveData = new MutableLiveData<>();

    public UserprofileViewModel(Context context, LayoutInflater inflater) {
        this.context = context;
        this.inflater = inflater;
        idadmin.setValue(0);
        // getlistsalon();
        getinforuser();

        edittable.setValue(false);
        selecteditem.setValue(0);

    }

    public LiveData<List<String>> getStringListLiveData() {
        return adminname;
    }

    public void changeedittable() {
        edittable.setValue(!edittable.getValue());
    }

    constants constans;
    constants constants = new constants();
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(constants.baseurl)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    backendapi backendapi = retrofit.create(com.example.myapplication.backendapi.class);

    public void getnameadmin(Spinner spinner) {
        Call<List<Adminname>> ca = backendapi.getnameadmin();
        ca.enqueue(new Callback<List<Adminname>>() {
            @Override
            public void onResponse(Call<List<Adminname>> call, Response<List<Adminname>> response) {

                List<Adminname> adminnames = response.body();
                listadmin.setValue(adminnames);
                if (adminnames != null && !adminnames.isEmpty()) {
                    List<String> listadminname = new ArrayList<>();

                    for (Adminname adminname1 : adminnames) {
                        listadminname.add(adminname1.getFirstname() + " " + adminname1.getLastname());
                        if (idadmin.getValue() == adminname1.getId()) {
                            selecteditem.setValue(adminnames.indexOf(adminname1));

                        }

                    }
                    adminname.setValue(listadminname);


                    ArrayList<String> values = new ArrayList<>();

                    for (String name : adminname.getValue()) {
                        values.add(name);
                    }


                    // Tạo Adapter để hiển thị danh sách item
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, values);

                    // Định dạng cho dropdown item
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    // Gán Adapter cho Spinner
                    spinner.setAdapter(adapter);


                    spinner.setSelection(selecteditem.getValue());

                }

            }

            @Override
            public void onFailure(Call<List<Adminname>> call, Throwable t) {

            }
        });


    }

    public void getidadmin() {

        List<Adminname> listadmin1 = listadmin.getValue();
        Adminname adminname1 = listadmin1.get(selecteditem.getValue());
        idadmin.setValue(adminname1.getId());
        Log.d("huu", String.valueOf(idadmin.getValue()));

    }

    public void onclicksave() {
        getidadmin();
        User user = new User(0, name.getValue(), mobile.getValue(), email.getValue(), "", address.getValue(), 0);
        if (!user.isEmailValid()) {
            errorEmail.setValue("Enter a valid email address");
        } else {
            errorEmail.setValue(null);
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
            errorMobile.setValue("Mobile Length should be greater than 9");
        else {
            errorMobile.setValue(null);
        }
        if (errorEmail.getValue() != null || errorAddress.getValue() != null || errorMobile.getValue() != null || errorName.getValue() != null) {
            Toast.makeText(context, "Invalid information! Try again", Toast.LENGTH_SHORT).show();
            return;
        }


        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Are you sure?");
        builder.setMessage("SAVE ?");
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                Call<Apiresponseadd> apiresponseaddCall = backendapi.updateuser(email.getValue(), id_user.getValue(), name.getValue(), address.getValue(), idadmin.getValue(), mobile.getValue());
                apiresponseaddCall.enqueue(new Callback<Apiresponseadd>() {
                    @Override
                    public void onResponse(Call<Apiresponseadd> call, Response<Apiresponseadd> response) {

                        Toast.makeText(context, "Successfully", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onFailure(Call<Apiresponseadd> call, Throwable t) {
                        Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });


            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.show();


    }


    public void changepassword() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View dialogView = inflater.inflate(R.layout.fragment_change_password, null);
        builder.setView(dialogView);

        // Đặt các thuộc tính khác cho AlertDialog (tiêu đề, nút OK, nút Cancel, vv.)


        // Tạo AlertDialog từ AlertDialog.Builder
        AlertDialog alertDialog = builder.create();
        // Sử dụng LayoutInflater để inflate layout XML cho nội dung của AlertDialog


        EditText oldpass = dialogView.findViewById(R.id.edt_old_password);
        EditText newpass = dialogView.findViewById(R.id.edt_new_password);
        EditText repass = dialogView.findViewById(R.id.edt_re_password);

        Button chnagepass = dialogView.findViewById(R.id.btn_change_password);
        chnagepass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (newpass.getText().toString().equals(repass.getText().toString())) {

                    if (newpass.getText().length() < 5 || oldpass.getText().length() < 5 || repass.getText().length() < 5) {
                        Toast.makeText(context, "Password Length should be greater than 5", Toast.LENGTH_SHORT).show();
                    } else {

                        Call<Apiresponseadd> call = backendapi.changepassworduser(id_user.getValue(), oldpass.getText().toString(), newpass.getText().toString());
                        call.enqueue(new Callback<Apiresponseadd>() {
                            @Override
                            public void onResponse(Call<Apiresponseadd> call, Response<Apiresponseadd> response) {

                                Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                alertDialog.dismiss();
                            }

                            @Override
                            public void onFailure(Call<Apiresponseadd> call, Throwable t) {

                            }
                        });


                    }

                } else {

                    Toast.makeText(context, "Confirm Password Error", Toast.LENGTH_SHORT).show();
                }

            }
        });

        // Đặt layout XML làm nội dung cho AlertDialog


        // Hiển thị AlertDialog
        alertDialog.show();
    }

    public void getinforuser() {
        SharedPreferences sharedPref = context.getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
        int iduser = sharedPref.getInt("iduser", 0);
        id_user.setValue(iduser);

        Call<List<User>> call = backendapi.getinforuser(iduser);
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                User user = response.body().get(0);
                email.setValue(user.getEmail());

                name.setValue(user.getName());
                mobile.setValue(user.getMobile());
                address.setValue(user.getAddress());
                idadmin.setValue(user.getIdadmin());
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {

            }
        });

    }


    public LiveData<List<String>> getBddressTypes() {


        return adminname;
    }

}
