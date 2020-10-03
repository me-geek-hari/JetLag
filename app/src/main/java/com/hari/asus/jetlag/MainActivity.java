package com.hari.asus.jetlag;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
AutoCompleteTextView scountry,tcountry;
EditText edt;
TextView takeoff_date,landing_date;
Calendar c;
Button nxt;
    int t=0;
    String hms;
DatePickerDialog dt,dt2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String [] countries = getResources().getStringArray(R.array.countries);
final String[] tz = TimeZone.getAvailableIDs();

final ArrayList<String> tz1 = new ArrayList<String>();
final ArrayList<String> ty = new ArrayList<String>();
for(int i=0;i<tz.length;i++){
    if(!tz1.contains(TimeZone.getTimeZone(tz[i]).getDisplayName())){
        tz1.add(TimeZone.getTimeZone(tz[i]).getDisplayName());
        ty.add(tz[i]);
    }
}
        scountry = findViewById(R.id.start1);
        tcountry = findViewById(R.id.target1);
        takeoff_date = findViewById(R.id.takeoff);
        landing_date = findViewById(R.id.landing);
        nxt = findViewById(R.id.nxt);
        edt = findViewById(R.id.name);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,tz1);
        scountry.setAdapter(arrayAdapter);
        tcountry.setAdapter(arrayAdapter);

        nxt.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            String name = edt.getText().toString();
            String starttimezone = scountry.getText().toString();
            String endtimezone = tcountry.getText().toString();
            String takeoffs = takeoff_date.getText().toString();
            String landons = landing_date.getText().toString();

            if(!TextUtils.isEmpty(name)&&!TextUtils.isEmpty(starttimezone)&&!TextUtils.isEmpty(endtimezone)
                    &&!TextUtils.isEmpty(takeoffs)&&!TextUtils.isEmpty(landons)){
                try{
                    int i = tz1.indexOf(starttimezone);
                    int i2 = tz1.indexOf(endtimezone);
                    long lc1 = TimeZone.getTimeZone(ty.get(i)).getRawOffset();
                    long lc2 = TimeZone.getTimeZone(ty.get(i2)).getRawOffset();


                    long millis = (lc2-lc1);
                     hms = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millis),
                            TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
                            TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
                }catch (Exception e){
                    Toast.makeText(MainActivity.this, "Time zone data not found", Toast.LENGTH_SHORT).show();
                }

                try{
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                    Date date1= simpleDateFormat.parse(takeoffs);
                    Date date2 = simpleDateFormat.parse(landons);
                    if(date1.compareTo(date2)<0){
                        try{
                            int i = tz1.indexOf(starttimezone);
                            int i2 = tz1.indexOf(endtimezone);
                            long lc1 = TimeZone.getTimeZone(ty.get(i)).getRawOffset();
                            long lc2 = TimeZone.getTimeZone(ty.get(i2)).getRawOffset();


                            long millis = (lc2-lc1);
                            int t;
                            if(millis>0){t=1;}else{t=0;}
                            long convert_hours = Math.abs(TimeUnit.MILLISECONDS.toHours(millis));
                            long convert_min =  Math.abs( TimeUnit.MILLISECONDS.toMinutes(millis) -
                                    TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)));
                            hms = String.format("%02d:%02d",convert_hours , convert_min);


                            Intent intent = new Intent(MainActivity.this,about.class);
                            intent.putExtra("name",name);
                            intent.putExtra("time_diff",hms);

                            intent.putExtra("status",t);
                            intent.putExtra("takeoff",takeoffs);
                            intent.putExtra("landing",landons);
                            startActivity(intent);



                        }catch (Exception e){
                            Toast.makeText(MainActivity.this, "Time zone data not found", Toast.LENGTH_SHORT).show();
                        }

                    }else{
                        Toast.makeText(MainActivity.this, "error in dates entered found", Toast.LENGTH_SHORT).show();
                    }
                }catch (ParseException e){
                    Toast.makeText(MainActivity.this, "error in dates found", Toast.LENGTH_SHORT).show();
                }


            }else{
                Toast.makeText(MainActivity.this, "Enter all fields", Toast.LENGTH_SHORT).show();
            }






        }
        });
        takeoff_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                c= Calendar.getInstance();
                int day = c.get(Calendar.DAY_OF_MONTH);
                int month = c.get(Calendar.MONTH);
                int year = c.get(Calendar.YEAR);

                dt = new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        takeoff_date.setText(i2+"/"+(i1+1)+"/"+i);
                    }
                },day,month,year);
                dt.show();
            }
        });
        landing_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                c= Calendar.getInstance();
                int day = c.get(Calendar.DAY_OF_MONTH);
                int month = c.get(Calendar.MONTH);
                int year = c.get(Calendar.YEAR);

                dt2 = new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        landing_date.setText(i2+"/"+(i1+1)+"/"+i);
                    }
                },day,month,year);
                dt2.show();
            }
        });
    }
}