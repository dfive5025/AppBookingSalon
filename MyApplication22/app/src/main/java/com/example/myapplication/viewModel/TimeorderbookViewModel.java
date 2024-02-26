package com.example.myapplication.viewModel;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.Timeorderactivity;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class TimeorderbookViewModel extends ViewModel {
    Context context;
    final Calendar c = Calendar.getInstance();
    public MutableLiveData<String> timebegin = new MutableLiveData<>();
    public MutableLiveData<String> timefinish = new MutableLiveData<>();
    public MutableLiveData<String> timedate = new MutableLiveData<>();
    public MutableLiveData<String> workinghr = new MutableLiveData<>();
    public MutableLiveData<Boolean> navigateToNextScreen = new MutableLiveData<>();
    Timeorderactivity timeorderactivity;

    public TimeorderbookViewModel(Context context, String workinghr, Timeorderactivity timeorderactivity) {
        this.context = context;
        timebegin.setValue("SELECT TIME");
        timefinish.setValue("SELECT TIME");
        timedate.setValue("SELECT DATE");
        this.workinghr.setValue(workinghr);
        this.timeorderactivity = timeorderactivity;
    }

    public void settimebegin() {
        TimePickerDialog timePickerDialog = new TimePickerDialog(context,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        if (hourOfDay < 10 && minute < 10) {
                            timebegin.setValue("0" + hourOfDay + ":" + "0" + minute);
                        } else if (hourOfDay >= 10 && minute < 10) {
                            timebegin.setValue(hourOfDay + ":" + "0" + minute);
                        } else if (hourOfDay < 10 && minute >= 10) {
                            timebegin.setValue("0" + hourOfDay + ":" + minute);

                        } else {
                            timebegin.setValue(hourOfDay + ":" + minute);
                        }
//                                Toast.mak eText(context, timebegin.getValue().getText(), Toast.LENGTH_SHORT).show();
                    }
                }, c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), false);
        timePickerDialog.show();
    }

    public void setTimefinish() {
        TimePickerDialog timePickerDialog = new TimePickerDialog(context,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {


                        if (hourOfDay < 10 && minute < 10) {
                            timefinish.setValue("0" + hourOfDay + ":" + "0" + minute);
                        } else if (hourOfDay >= 10 && minute < 10) {
                            timefinish.setValue(hourOfDay + ":" + "0" + minute);
                        } else if (hourOfDay < 10 && minute >= 10) {
                            timefinish.setValue("0" + hourOfDay + ":" + minute);

                        } else {
                            timefinish.setValue(hourOfDay + ":" + minute);
                        }
                    }
                }, c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), false);
        timePickerDialog.show();
    }

    public void setdate() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(context,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        // String m = ConvertMonth(monthOfYear+1);

                        monthOfYear = monthOfYear + 1;
                        final Calendar c = Calendar.getInstance();
                        String date = new SimpleDateFormat("dd-MM-yyyy").format(c.getTime());
                        String[] now = date.trim().split("-");
                        if (Integer.parseInt(now[2]) > year) {
                            Toast.makeText(context, "Năm đặt không hợp lệ", Toast.LENGTH_SHORT).show();
                            return;
                        } else if (Integer.parseInt(now[2]) == year) {
                            if (Integer.parseInt(now[1]) > monthOfYear) {
                                Toast.makeText(context, "Tháng đặt không hợp lệ", Toast.LENGTH_SHORT).show();
                                return;
                            } else if (Integer.parseInt(now[1]) == monthOfYear) {
                                if (Integer.parseInt(now[0]) > dayOfMonth) {
                                    //  Toast.makeText(context, now[0], Toast.LENGTH_SHORT).show();
                                    Toast.makeText(context, "Ngày không hợp lệ", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                            }
                        }


                        timedate.setValue(dayOfMonth + "-" + (monthOfYear) + "-" + year);


                        //   Toast.makeText(context, btn_date.getText(), Toast.LENGTH_SHORT).show();
                    }
                }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    public void gobackpage() {
        timeorderactivity.onBackPressed();

    }

    public void gonextpaage() {

        if (timebegin.getValue().equals("SELECT TIME") || timefinish.getValue().equals("SELECT TIME") || timedate.getValue().equals("SELECT DATE")) {
            Toast.makeText(context, "Hãy chọn ngày giờ", Toast.LENGTH_SHORT).show();
            return;
        }
        String Workinghr = workinghr.getValue();
        if (Workinghr.trim().equals("Chưa cài đặt") == false) {
            String[] time = Workinghr.trim().split("to");
            String[] opentime = time[0].trim().split(":");
            String[] closetime = time[1].trim().split(":");
            String[] ordertimebegin = timebegin.getValue().toString().trim().split(":");
            String[] ordertimefinish = timefinish.getValue().toString().trim().split(":");
            if (timebegin.getValue().toString().equals("Select Time") || timefinish.getValue().toString().equals("Select Time")) {
                Toast.makeText(context, "Vui lòng chọn thời gian", Toast.LENGTH_SHORT).show();
                return;
            }
            if (Integer.parseInt(ordertimebegin[0]) > Integer.parseInt(ordertimefinish[0])) {
                Toast.makeText(context, "Giờ đặt và giờ kết thúc không hợp lệ", Toast.LENGTH_SHORT).show();
                return;
            } else if (Integer.parseInt(ordertimebegin[0]) == Integer.parseInt(ordertimefinish[0])) {
                if (Integer.parseInt(ordertimefinish[1]) <= Integer.parseInt(ordertimebegin[1])) {
                    Toast.makeText(context, "Phút chọn không hợp lệ", Toast.LENGTH_SHORT).show();
                    return;
                }
            }


            //  Toast.makeText(SaloonViewActivity.this, ordertime[0]+"w"+ordertime[1], Toast.LENGTH_SHORT).show();


            if (Integer.parseInt(ordertimebegin[0]) < Integer.parseInt(opentime[0]) || Integer.parseInt(ordertimefinish[0]) > Integer.parseInt(closetime[0])) {
                Toast.makeText(context, "Giờ chọn không hợp lệ", Toast.LENGTH_SHORT).show();
                return;
            } else if (Integer.parseInt(ordertimebegin[0]) == Integer.parseInt(opentime[0]) || Integer.parseInt(ordertimefinish[0]) == Integer.parseInt(closetime[0])) {
                if (Integer.parseInt(ordertimebegin[0]) == Integer.parseInt(opentime[0])) {
                    if (Integer.parseInt(ordertimebegin[1]) < Integer.parseInt(opentime[1])) {
                        Toast.makeText(context, "Phút chọn không hợp lệ", Toast.LENGTH_SHORT).show();
                        return;
                    }

                }

                if (Integer.parseInt(ordertimefinish[0]) == Integer.parseInt(closetime[0])) {
                    if (Integer.parseInt(ordertimefinish[1]) > Integer.parseInt(closetime[1])) {
                        Toast.makeText(context, "Phút chọn không hợp lệ", Toast.LENGTH_SHORT).show();
                        return;
                    }

                }


            }


            final Calendar c = Calendar.getInstance();
            String date = new SimpleDateFormat("dd-MM-yyyy").format(c.getTime());
            String[] now = date.trim().split("-");
            String[] datenow = timedate.getValue().toString().trim().split("-");
            // Toast.makeText(context, date, Toast.LENGTH_SHORT).show();
//                    Toast.makeText(context, btn_date.getText().toString(), Toast.LENGTH_SHORT).show();
            if (Integer.parseInt(now[0]) == Integer.parseInt(datenow[0]) && Integer.parseInt(now[1]) == Integer.parseInt(datenow[1]) && Integer.parseInt(now[2]) == Integer.parseInt(datenow[2])) {
                String timenow = String.valueOf(java.time.LocalTime.now()); //mới ghi 32 cc gì đấy
                String[] timenow1 = timenow.trim().split(":");
                if (Integer.parseInt(ordertimebegin[0]) < Integer.parseInt(timenow1[0])) {
                    Toast.makeText(context, "Giờ đặt lớn hơn giờ hiện tại", Toast.LENGTH_SHORT).show();

                    return;
                } else if (Integer.parseInt(ordertimebegin[0]) == Integer.parseInt(timenow1[0])) {
                    if (Integer.parseInt(ordertimebegin[1]) <= Integer.parseInt(timenow1[1])) {
                        Toast.makeText(context, "Phút đặt phải lớn hơn phút hiện tại", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }


            }

            if (timebegin.getValue().equals("SELECT TIME") || timefinish.getValue().equals("SELECT TIME") || timedate.getValue().equals("SELECT DATE")) {
                Toast.makeText(context, "bo liem", Toast.LENGTH_SHORT).show();
            } else {
                navigateToNextScreen();
            }


        } else {
            Toast.makeText(context, "Salon chưa cài giờ hoạt động", Toast.LENGTH_SHORT).show();
            return;
        }


    }

    public void navigateToNextScreen() {
        navigateToNextScreen.setValue(true);
    }

    // Getter để lắng nghe sự kiện chuyển màn hình
    public LiveData<Boolean> getNavigateToNextScreen() {
        return navigateToNextScreen;
    }

}
