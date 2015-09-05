package com.spinno.buzczar.time;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by samir on 04/08/15.
 */
public class TimeCalculatorSamir {

    public static String Time_From ;
    public static String Time_To ;
    public static String Time_Interval ;




    public static int from_hours(){
        String[] parts = Time_From.split(":");
        int hrs = Integer.parseInt(parts[0]);
            return  hrs;
    }


    public static int from_minutes(){
        String[] parts = Time_From.split(":");
        int min = Integer.parseInt(parts[1]);
        return  min;
    }

    public static int to_hours(){
        String[] parts = Time_To.split(":");
        int hrs = Integer.parseInt(parts[0]);
        return  hrs;
    }

    public static int to_min(){
        String[] parts = Time_To.split(":");
        int min = Integer.parseInt(parts[1]);
        return  min;
    }

    public static int time_intervalmin(){
       // String[] parts = Time_Interval.split("");
       // int hrs = Integer.parseInt(parts[0]);
        return  Integer.parseInt(Time_Interval);
    }



    public static boolean validdate() throws ParseException {

        boolean datevalue = false;

        Calendar c = Calendar.getInstance();
        int currenthours = c.get(Calendar.HOUR_OF_DAY);
        int currentmin = c.get(Calendar.MINUTE);


        // Date date_to = simpleDateFormat.parse("08:00 AM"   );
        //Date date_current  = simpleDateFormat.parse("04:00 PM");

         SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
         Date date_to = simpleDateFormat.parse(""+to_hours()+":"+to_min());
         Date date_current = simpleDateFormat.parse(""+currenthours+":"+currentmin);

        Log.e("time to " , ""+date_to);
        Log.e("time current " , ""+date_current);

       if(date_current.getTime() >= date_to.getTime()){
           datevalue = false;
       }else {
           ////check it with ciurrent time
               if(date_current.getTime()<(date_to.getTime()-TimeUnit.MINUTES.toMillis(time_intervalmin()))){
                   datevalue = true;
               }else if(date_current.getTime()>(date_to.getTime()- TimeUnit.MINUTES.toMillis(time_intervalmin()))){
                   datevalue = false;
               }
       }
        return datevalue;
    }


    public static boolean isValidTime() throws ParseException {
        boolean timing = false;

        Calendar c = Calendar.getInstance();
        int currenthours = c.get(Calendar.HOUR_OF_DAY);
        int currentmin = c.get(Calendar.MINUTE);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
        Date time_from = simpleDateFormat.parse("" + from_hours() + ":" + from_minutes());
        Date time_current = simpleDateFormat.parse("" + currenthours + ":" + currentmin);


        if(time_current.getTime()<(time_from.getTime() - TimeUnit.MINUTES.toMillis(time_intervalmin()))){
            timing = false;
        }else if(time_current.getTime()>=(time_from.getTime() - TimeUnit.MINUTES.toMillis(time_intervalmin()))){
            timing = true ;
        }

        return  timing;
    }
}
