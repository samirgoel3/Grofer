package com.spinno.buzczar;

import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import apiurls.URLAPI;
import location.Locationgetter;
import pojo.CITIES;
import sessionmanager.LocationSesionManager;
import sessionmanager.SessionManager;
import singleton.VolleySingleton;


public class LocatoinActivity extends Activity  {

    Location ll ;
    protected LocationManager locationManager;
    protected LocationListener locationListener;


    private static int SPLASH_TIME_OUT = 3000;
    SessionManager sm ;
    LocationSesionManager lsm ;


    StringRequest sr ;
    RequestQueue queue ;


    List<CITIES> citieslist = new ArrayList<CITIES>();

    ArrayList<String> cityid = new ArrayList<String>();
    ArrayList<String> cityname = new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locatoin);
        queue = VolleySingleton.getInstance(LocatoinActivity.this).getRequestQueue();
        sm = new SessionManager(getApplicationContext());
        lsm = new LocationSesionManager(getApplicationContext());

        //   GPD should be on high accuracy for using this logic
      // ll  =   new LocationService().getBestLocation(LocatoinActivity.this);
       // LocationAddress.getAddressFromLocation(ll.getLatitude(), ll.getLongitude(), getApplicationContext(), new GeocoderHandler());


        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                if(lsm.getlocation().get(LocationSesionManager.KEY_LOCATION_ID).isEmpty()){
                    loadcities();
                }else {
                    loadcitiessecongtime();
                }
            }
        }, SPLASH_TIME_OUT);
    }

    private void loadcitiessecongtime() {
        sr = new StringRequest(Request.Method.GET, URLAPI.allcities, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson = gsonBuilder.create();
                citieslist = Arrays.asList(gson.fromJson(response, CITIES[].class));

                for(int i = 0 ; i<citieslist.size() ; i++){
                    cityid.add(citieslist.get(i).city_id);
                    cityname.add(citieslist.get(i).city);
                }

                Locationgetter.cityid = cityid ;
                Locationgetter.cityname = cityname ;

                Intent ii=new Intent(LocatoinActivity.this,CategoryFetchingActivity.class);
                ii.putExtra("locationid_key", lsm.getlocation().get(LocationSesionManager.KEY_LOCATION_ID));
                startActivity(ii);
                finish();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
               // Toast.makeText(LocatoinActivity.this, "" + error, Toast.LENGTH_SHORT).show();
                Intent ii = new Intent(LocatoinActivity.this,NoInternetActivity.class);
                startActivity(ii);
                finish();
            }
        });

        queue.add(sr);

    }

    private void loadcities() {

        sr = new StringRequest(Request.Method.GET, URLAPI.allcities, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson = gsonBuilder.create();
                citieslist = Arrays.asList(gson.fromJson(response, CITIES[].class));

                for(int i = 0 ; i<citieslist.size() ; i++){
                    cityid.add(citieslist.get(i).city_id);
                    cityname.add(citieslist.get(i).city);
                }

                Locationgetter.cityid = cityid ;
                Locationgetter.cityname = cityname ;

               // Toast.makeText(LocatoinActivity.this, "" + Locationgetter.cityname.size(), Toast.LENGTH_SHORT).show();
                Intent ii=new Intent(LocatoinActivity.this,LocationWithNoChange.class);
                startActivity(ii);
                  finish();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(LocatoinActivity.this, "Please check Your Internet Connection", Toast.LENGTH_SHORT).show();
                Log.d("error response in Location Activity #######" , ""+error);

                Intent ii = new Intent(LocatoinActivity.this,NoInternetActivity.class);
                startActivity(ii);
                finish();
            }
        });

        sr.setRetryPolicy(new DefaultRetryPolicy(
                2500,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        queue.add(sr);

    }


    private class GeocoderHandler extends Handler {
        @Override

        public void handleMessage(Message message) {
            String locationAddress;
            switch (message.what) {
                case 1:
                    Bundle bundle = message.getData();
                    locationAddress = bundle.getString("address");
                    break;
                default:
                    locationAddress = null;
            }
            Toast.makeText(LocatoinActivity.this, "" + locationAddress, Toast.LENGTH_SHORT).show();
            Locationgetter.location = locationAddress;
            Intent  in  = new Intent(LocatoinActivity.this , SplashActivity.class );
                     startActivity(in);
                      finish();
        }
    }

}
