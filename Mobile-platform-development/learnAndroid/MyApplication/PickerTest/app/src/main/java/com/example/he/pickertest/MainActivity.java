package com.example.he.pickertest;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.DatePicker;
import android.widget.TimePicker;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    private DatePicker datePicker;
    private TimePicker timePicker;
    private Calendar calendar;//日期类
    private  int year;
    private  int month;
    private  int day;
    private  int hour;
    private  int minute;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        calendar=Calendar.getInstance();
        year=calendar.get(Calendar.YEAR);
        month=calendar.get(Calendar.MONTH)+1;
        day=calendar.get(Calendar.DAY_OF_MONTH);
        minute=calendar.get(Calendar.MINUTE);
        hour=calendar.get(Calendar.HOUR_OF_DAY);
        setTitle(year+"-"+month+"-"+day+"-"+hour+":"+minute);

        datePicker= (DatePicker) findViewById(R.id.dataPicker1);
        timePicker= (TimePicker) findViewById(R.id.timePicker1);

         datePicker.init(year, month, day, new DatePicker.OnDateChangedListener() {
             @Override
             public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    setTitle(year+"-"+(monthOfYear+1)+"-"+dayOfMonth+"-"+hour+":"+minute);
             }
         });

        //
        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                setTitle(hourOfDay+":"+minute);
            }
        });



        //以对话框的形式选择日期
        new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                setTitle(year+"-"+(monthOfYear+1)+"-"+dayOfMonth);
            }
        },year,month,day).show();

        //以对话框的形式选择时间
//        new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
//            @Override
//            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
//                setTitle(hourOfDay+":"+minute);
//            }
//        },hour,minute,true).show();





    }
}
