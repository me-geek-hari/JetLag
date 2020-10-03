package com.hari.asus.jetlag;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

public class schedule extends AppCompatActivity {

ListView lst; int convert_hours;
String cdate,username,time_difference,takeoffdate,landingdate,wakeuptime,sleeptime,lastdate,tmin,casee,agein;
int stat;
    ArrayList<String> sleeping;   ArrayList<String> waking; ArrayList<String> days;ArrayList<String> days2;
    ArrayList<String> light;ArrayList<String> light2;
    ArrayList<String> dark;ArrayList<String> dark2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);


            lst = findViewById(R.id.listview);

        ImageView img = findViewById(R.id.backbtn2);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(schedule.this,about.class));
                finish();
            }
        });



    }

    @Override
    protected void onStart() {
        super.onStart();
        Bundle b = new Bundle();
        b= getIntent().getExtras();
        username =b.getString("name");
        time_difference=b.getString("time_diff");
        takeoffdate= b.getString("takeoff");
        landingdate=b.getString("landing");
        wakeuptime=b.getString("wake");
        sleeptime=b.getString("sleep");
        lastdate=b.getString("lastdate");
        tmin=b.getString("tmin");
        casee= b.getString("case");
        agein= b.getString("agein");
        stat = b.getInt("status");

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/M/yyyy");
        cdate = simpleDateFormat.format(new Date());
if(stat==1){
    String re = "12:00";
    if(differencetime(time_difference,re)>=0){
        if(compareDate(takeoffdate,cdate)==1){
            String tes = "24:00";
            String shifttime  = leasttimeshift(time_difference,tes);
            middleage(shifttime);

        }
        else{
            Toast.makeText(this, "Algorithm to be created2", Toast.LENGTH_SHORT).show();
            lst.setVisibility(View.INVISIBLE);
        }

    }else{
        if(compareDate(takeoffdate,cdate)==1){
            Toast.makeText(this, "this", Toast.LENGTH_SHORT).show();
            String shifttime  = time_difference;
           middleage2(shifttime);


        }
        else{
            Toast.makeText(this, "Algorithm to be created2", Toast.LENGTH_SHORT).show();
            lst.setVisibility(View.INVISIBLE);
        }
    }
}else{
    Toast.makeText(this, "Algorithm to be made1", Toast.LENGTH_SHORT).show();
    lst.setVisibility(View.INVISIBLE);
}

    }

    private void middleage2(String sr) {
        String tc = "01:00";

        long da=  differencetime(sr,tc);

        String sleepme = rightshifttime(leftshifttime(sleeptime,tc),time_difference);


        String wakeme = rightshifttime(leftshifttime(wakeuptime,tc),time_difference);
        days = new ArrayList<String>();
        light = new ArrayList<String>();
        dark = new ArrayList<String>();
        dark2 = new ArrayList<String>();
        days2 = new ArrayList<String>();
        light2 = new ArrayList<String>();

        days.add("1 day after landing");
        days.add("2 day after landing");
        days.add("3 day after landing");
        days.add("4 day after landing");
        days.add("5 day after landing");
        days.add("6 day after landing");
        days.add("7 day after landing");
        days.add("8 day after landing");
        days.add("9 day after landing");
        days.add("10 day after landing");
        days.add("11 day after landing");
        days.add("12 day after landing");
        days.add("13 day after landing");
        days.add("14 day after landing");
        days.add("15 day after landing");

        light.add("2h");
        light.add("2h");
        light.add("2h");
        light.add("2h");
        light.add("2h");
        light.add("1.5h");
        light.add("1.5h");
        light.add("1h");
        light.add("1h");
        light.add("1h");
        light.add("0");
        light.add("0");
        light.add("0");
        light.add("0");
        light.add("0");
        light.add("0");
        light.add("0");
        light.add("0");



        dark.add("0");
        dark.add("0");
        dark.add("0");
        dark.add("0");
        dark.add("0");
        dark.add("0");
        dark.add("0");
        dark.add("0");
        dark.add("0");
        dark.add("0");
        dark.add("0");
        dark.add("0");
        dark.add("0");
        dark.add("0");
        dark.add("0");
        dark.add("0");
        dark.add("0");
        dark.add("0");
        dark.add("0");
        dark.add("0");
        dark.add("0");
        dark.add("0");
        dark.add("0");
        dark.add("0");
        dark.add("0");
        dark.add("0");

        convert_hours = (int) TimeUnit.MILLISECONDS.toHours(da);
        Toast.makeText(this, " " + convert_hours, Toast.LENGTH_SHORT).show();
        if((convert_hours+1)>=0){
            String tcd = "1:00";
            int i=0;
            sleeping = new ArrayList<String>();
            sleeping.add(sleepme);


            waking = new ArrayList<String>();
            waking.add(wakeme);


            while(convert_hours-1>0){
                sleeping.add((leftshifttime(sleeping.get(i),tcd)));
                waking.add((leftshifttime(waking.get(i),tcd)));
                days2.add(days.get(i));
                dark2.add(dark.get(i));
                light2.add(light.get(i));
                convert_hours--;
                i++;
            }
            customadapterclass customadapterclass = new customadapterclass();
            lst.setAdapter(customadapterclass);
        }else{lst.setVisibility(View.INVISIBLE);

        }
    }

    private void middleage(String sr) {
        String tc = "02:00";
        String tc1 = "04:00";
        String sleepme = rightshifttime(sleeptime,tc);
        String sleepme2 =rightshifttime(sleepme,tc);

        String wakeme = rightshifttime(wakeuptime,tc);
        String wakeme2 =rightshifttime(wakeme,tc);

        sleeping = new ArrayList<String>();
        sleeping.add(sleepme);
        sleeping.add(sleepme2);

        waking = new ArrayList<String>();
        waking.add(wakeme);
        waking.add(wakeme2);

        days = new ArrayList<String>();
        light = new ArrayList<String>();
        light2 = new ArrayList<String>();
        dark = new ArrayList<String>();
        dark2 = new ArrayList<String>();

        days.add("1 day after landing");
        days.add("2 day after landing");
        days.add("3 day after landing");
        days.add("4 day after landing");
        days.add("5 day after landing");
        days.add("6 day after landing");
        days.add("7 day after landing");
        days.add("8 day after landing");
        days.add("9 day after landing");
        days.add("10 day after landing");
        days.add("11 day after landing");
        days.add("12 day after landing");
        days.add("13 day after landing");
        days.add("14 day after landing");
        days.add("15 day after landing");

            light2.add("2h");
        light2.add("2h");
        light.add("1h");
        light.add("1h");
        light.add("1h");
        light.add("1h");
        light.add("1h");
        light.add("0.5h");
        light.add("0.5h");
        light.add("0.5h");
        light.add("0.5h");
        light.add("0");
        light.add("0");
        light.add("0");
        light.add("0");
        light.add("0");

        dark2.add("1h");
        dark2.add("2h");

        dark.add("0");
        dark.add("0");
        dark.add("0");
        dark.add("0");
        dark.add("0");dark.add("0");
        dark.add("0");
        dark.add("0");
        dark.add("0");
        dark.add("0");


        days2 = new ArrayList<String>();
        days2.add("2 day before flight");
        days2.add("1 day before flight");
      long da=  differencetime(sr,tc1);

         convert_hours = (int) TimeUnit.MILLISECONDS.toHours(da);

        if(convert_hours>=0){
         String tcd = "1:00";
         int i=0;


         while(convert_hours>0){
             sleeping.add(rightshifttime(rightshifttime(sleeping.get(i+1),tcd),time_difference));
             waking.add(rightshifttime(rightshifttime(waking.get(i+1),tcd),time_difference));
             days2.add(days.get(i));
             dark2.add(dark.get(i));
             light2.add(light.get(i));
             convert_hours--;
             i++;
         }
         customadapterclass customadapterclass = new customadapterclass();
         lst.setAdapter(customadapterclass);
        }else{lst.setVisibility(View.INVISIBLE);

           }
    }
    class customadapterclass extends BaseAdapter{

        @Override
        public int getCount() {
            return sleeping.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            view = getLayoutInflater().inflate(R.layout.customlayout,null);
            TextView txt_list = view.findViewById(R.id.txt_list);
            TextView list_wake = view.findViewById(R.id.list_wake);
            TextView list_sleep = view.findViewById(R.id.list_sleep);
            TextView list_expose = view.findViewById(R.id.list_expose);
            TextView list_dark = view.findViewById(R.id.list_dark);
           list_wake.setText(waking.get(i));
           list_sleep.setText(sleeping.get(i));

            list_expose.setText(light2.get(i));


           txt_list.setText(days2.get(i));
            return view;
        }
    }
    private long differencetime(String s1,String s2){
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        Date d1 = null;
        Date d2 = null;
        long millis =0;
        try{
            d1 = format.parse(s1);
            d2 = format.parse(s2);
             millis = d1.getTime()-d2.getTime();

        }catch (ParseException e){
            Toast.makeText(this, "error in dates found", Toast.LENGTH_SHORT).show();
        }
        return millis;
    }
    private String leasttimeshift(String s1,String s2){
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        Date d1 = null;
        Date d2 = null;
        String tt=null;
        try{
            d1 = format.parse(s1);
            d2 = format.parse(s2);
            long millis = d1.getTime()-d2.getTime();

           long convert_hours = Math.abs(TimeUnit.MILLISECONDS.toHours(millis));

            long convert_min =  Math.abs( TimeUnit.MILLISECONDS.toMinutes(millis) -
                    TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)));

            tt = String.format("%02d:%02d",convert_hours , convert_min);
        }catch (ParseException e){
            Toast.makeText(this, "error in dates found", Toast.LENGTH_SHORT).show();
        }
        return tt;
       }

       private String rightshifttime (String s1, String s2){
           SimpleDateFormat format = new SimpleDateFormat("HH:mm");
           format.setTimeZone(TimeZone.getTimeZone("UTC"));
           Date d1 = null;
           Date d2 = null;
           String tt=null;
           try{
               d1 = format.parse(s1);
               d2 = format.parse(s2);
               long millis = d1.getTime()+d2.getTime();
tt = format.format(new Date(millis));
//               long convert_hours = Math.abs(TimeUnit.MILLISECONDS.toHours(millis));
//
//               long convert_min =  Math.abs( TimeUnit.MILLISECONDS.toMinutes(millis) -
//                       TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)));
//
//               tt = String.format("%02d:%02d",convert_hours , convert_min);
           }catch (ParseException e){
               Toast.makeText(this, "error in dates found", Toast.LENGTH_SHORT).show();
           }
           return tt;

        }
    private String leftshifttime (String s1, String s2){
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        format.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date d1 = null;
        Date d2 = null;
        String tt=null;
        try{
            d1 = format.parse(s1);
            d2 = format.parse(s2);
            long millis = d1.getTime()-d2.getTime();
            tt = format.format(new Date(millis));
//               long convert_hours = Math.abs(TimeUnit.MILLISECONDS.toHours(millis));
//
//               long convert_min =  Math.abs( TimeUnit.MILLISECONDS.toMinutes(millis) -
//                       TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)));
//
//               tt = String.format("%02d:%02d",convert_hours , convert_min);
        }catch (ParseException e){
            Toast.makeText(this, "error in dates found", Toast.LENGTH_SHORT).show();
        }
        return tt;

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

            convert_days = (TimeUnit.MILLISECONDS.toDays(millis));

            if(convert_days>2){return 1;}else{return 0;}

        }catch (ParseException e){
            Toast.makeText(this, "error in dates found", Toast.LENGTH_SHORT).show();
        }
        return 0;
    }
}