package com.hari.asus.jetlag;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class about extends AppCompatActivity {
Button sub;
String username,takedate,landdate,timedig;
long diffmins;
int stat;
ImageView img;
TextView sleeptime,waketime,lastdate;
AutoCompleteTextView agee;
Calendar c;
int mday,mmonth,myear,mhour,mmin;
TimePickerDialog dt,dt1;
DatePickerDialog dt2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        c= Calendar.getInstance();

        Bundle b = new Bundle();
        b= getIntent().getExtras();

        username = b.getString("name");
        takedate = b.getString("takeoff");
        landdate = b.getString("landing");
        timedig = b.getString("time_diff");

        stat = b.getInt("status");

        String [] age = getResources().getStringArray(R.array.age);
        String [] choice = getResources().getStringArray(R.array.choose);

         mday = c.get(Calendar.DAY_OF_MONTH);
        mmonth = c.get(Calendar.MONTH);
        myear = c.get(Calendar.YEAR);
        mhour= c.get(Calendar.HOUR_OF_DAY);
        mmin = c.get(Calendar.MINUTE);

        sub = findViewById(R.id.sbt);
        img = findViewById(R.id.backbtn);
        sleeptime = findViewById(R.id.sleeptime);
        waketime = findViewById(R.id.waketime);
        lastdate = findViewById(R.id.lastday);
        agee = findViewById(R.id.agee);



        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,age);
        agee.setAdapter(arrayAdapter);
        ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,choice);


        agee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                agee.showDropDown();
            }
        });

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(about.this,MainActivity.class));
                finish();
            }
        });

        sleeptime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

              dt = new TimePickerDialog(about.this, new TimePickerDialog.OnTimeSetListener() {
                  @Override
                  public void onTimeSet(TimePicker timePicker, int i, int i1) {
                      sleeptime.setText(i+":"+i1);
                  }
              },mhour,mmin, DateFormat.is24HourFormat(about.this));
              dt.show();

            }
        });
        waketime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dt1 = new TimePickerDialog(about.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int i, int i1) {
                        waketime.setText(i+":"+i1);
                    }
                },mhour,mmin, DateFormat.is24HourFormat(about.this));
                dt1.show();

            }
        });
        lastdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dt2 = new DatePickerDialog(about.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        lastdate.setText(i2+"/"+(i1+1)+"/"+i);
                    }
                },mday,mmonth,myear);
                dt2.show();

            }
        });

        sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String wakeup_time = waketime.getText().toString();
                String sleep_time = sleeptime.getText().toString();
                String last_date =   lastdate.getText().toString();
                String agegroup = agee.getText().toString();
                String s=null;

                if(!TextUtils.isEmpty(wakeup_time)&&!TextUtils.isEmpty(sleep_time)
                        &&!TextUtils.isEmpty(last_date)&&!TextUtils.isEmpty(agegroup)){
                    if(compareTime(wakeup_time,sleep_time)==1){
                    if(compareDate(last_date,landdate)==1){
                        String timemimf = "02:00";
                        String timemimf2 = "03:00";
                        String agein = agee.getText().toString();
                        if(agein.equals("Below 12")||agein.equals("Above 55")){
                            s= differenceTime(wakeup_time,timemimf);
                           }
                        else if(agein.equals("Middle Age")){
                            s= differenceTime(wakeup_time,timemimf2);
                            }
                        String timechecker = "12:00";
                        String casee =null;
                        if(stat==1){
                            if(compareTime2(timedig,timechecker)==0){
                                casee = "left shift";
                            }else{casee = "right shift";}
                        }
                        else if(stat ==0){if(compareTime2(timedig,timechecker)==0){
                            casee = "right shift";
                        }else{casee = "left shift";}}
                        Intent intent = new Intent(about.this,schedule.class);
                        intent.putExtra("name",username);
                        intent.putExtra("time_diff",timedig);
                        intent.putExtra("status",stat);
                        intent.putExtra("agein",agein);
                        intent.putExtra("takeoff",takedate);
                        intent.putExtra("landing",landdate);
                        intent.putExtra("case",casee);
                        intent.putExtra("wake",wakeup_time);
                        intent.putExtra("sleep",sleep_time);
                        intent.putExtra("lastdate",last_date);
                        intent.putExtra("tmin",s);
                        startActivity(intent);

                    }else{
                         Toast.makeText(about.this, "Entered date shows mismatch: algorithm available for 6 day visits", Toast.LENGTH_SHORT).show();
                        }

                    }else{
                        Toast.makeText(about.this, "Currently the algorithm for your sleep time is not defined", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(about.this, "Enter all fields", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }


    private int compareDate(String s1,String s2){
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        Date d1 = null;
        Date d2 = null;
        long convert_days=0;
        try{
            d1 = format.parse(s1);
            d2 = format.parse(s2);
            long millis = d1.getTime()-d2.getTime();

            convert_days = Math.abs(TimeUnit.MILLISECONDS.toDays(millis));
        if(convert_days>5){return 1;}else{return 0;}

        }catch (ParseException e){
            Toast.makeText(this, "error in dates found", Toast.LENGTH_SHORT).show();
        }
        return 0;
    }
    private int compareTime2(String s1,String s2){
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        Date d1 = null;
        Date d2 = null;
        long millis=0;
        try{
            d1 = format.parse(s1);
            d2 = format.parse(s2);
            millis = d1.getTime()-d2.getTime();


        }catch (ParseException e){
            Toast.makeText(this, "error in dates found", Toast.LENGTH_SHORT).show();
        }
        if(millis<0){return 0;}else{return 1;}
    }
    private int compareTime(String s1,String s2){
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        Date d1 = null;
        Date d2 = null;
        long convert_hours=0;
        try{
            d1 = format.parse(s1);
            d2 = format.parse(s2);
            long millis = d1.getTime()-d2.getTime();
            if(millis<0){
                Date datemax = format.parse("24:00");
                Date datemin = format.parse("00:00");
                millis = (datemax.getTime()-d2.getTime())+(d1.getTime()-datemin.getTime());
            }
             convert_hours = Math.abs(TimeUnit.MILLISECONDS.toHours(millis));


        }catch (ParseException e){
            Toast.makeText(this, "error in dates found", Toast.LENGTH_SHORT).show();
        }
        if(convert_hours<5||convert_hours>11){return 0;}else{return 1;}
    }
    private String differenceTime(String s1, String s2){
        String tt = null;
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        Date d1 = null;
        Date d2 = null;
        try{
            d1 = format.parse(s1);
            d2 = format.parse(s2);
            long millis = d1.getTime()-d2.getTime();
            if(millis<0){
                Date datemax = format.parse("24:00");
                Date datemin = format.parse("00:00");
             millis = (datemax.getTime()-d2.getTime())+(d1.getTime()-datemin.getTime());
            }
            long convert_hours = Math.abs(TimeUnit.MILLISECONDS.toHours(millis));
            long convert_min =  Math.abs( TimeUnit.MILLISECONDS.toMinutes(millis) -
                    TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)));

            tt = String.format("%02d:%02d",convert_hours , convert_min);

        }catch (ParseException e){
            Toast.makeText(this, "error in dates found", Toast.LENGTH_SHORT).show();
        }
        return (tt);
    }
}