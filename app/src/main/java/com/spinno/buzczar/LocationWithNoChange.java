package com.spinno.buzczar;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;

import apiurls.URLAPI;
import location.Locationgetter;
import pojo.LOCATION_RESPONSE;
import pojo.LOCATION_SUCCESS_RESPONSE;
import sessionmanager.LocationSesionManager;
import sessionmanager.SessionManager;
import singleton.VolleySingleton;


public class LocationWithNoChange extends Activity {

    Button selectcity ;
    View noaddress , withaddress  ;

    ListView lswithaddress ;
    LOCATION_RESPONSE lr  = new LOCATION_RESPONSE();

    StringRequest sr ;
    RequestQueue queue ;

    LOCATION_SUCCESS_RESPONSE lsr = new LOCATION_SUCCESS_RESPONSE();
    ArrayList<String> location_id = new ArrayList<String>();
    ArrayList<String> city_id = new ArrayList<String>();
    ArrayList<String> location_name = new ArrayList<String>();

    ProgressWheel pw ;

    Intent addreddfinder ;

    SessionManager sm ;
    LocationSesionManager lsm ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_with_no_chenge);

        queue = VolleySingleton.getInstance(LocationWithNoChange.this).getRequestQueue();
        sm = new SessionManager(getApplicationContext());
        lsm = new LocationSesionManager(getApplicationContext());


        selectcity = (Button) findViewById(R.id.selectcitybutton);

        noaddress = findViewById(R.id.noddress);
        withaddress = findViewById(R.id.withaddress);
        lswithaddress = (ListView) findViewById(R.id.listview_in_withaddress);

        pw = (ProgressWheel) findViewById(R.id.progress_wheel_in_with_address);

        withaddress.setVisibility(View.GONE);
        pw.setVisibility(View.GONE);

        addreddfinder = new Intent(LocationWithNoChange.this , CategoryFetchingActivity.class);




        selectcity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LayoutInflater layoutInflater =
                        (LayoutInflater)LocationWithNoChange.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View addView = layoutInflater.inflate(R.layout.dialogtittle, null);


                AlertDialog.Builder builderSingle = new AlertDialog.Builder(LocationWithNoChange.this);

                builderSingle.setCustomTitle(addView);
                final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                        LocationWithNoChange.this,
                        android.R.layout.select_dialog_singlechoice);

                for(int i= 0 ; i< Locationgetter.cityname.size() ;i++){
                    arrayAdapter.add(Locationgetter.cityname.get(i));
                }



                builderSingle.setNegativeButton("cancel",
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();

                            }
                        });

                builderSingle.setAdapter(arrayAdapter,
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            lsm.createcitystore(Locationgetter.cityname.get(which) ,Locationgetter.cityid.get(which));
                          loadzoneincities(Locationgetter.cityid.get(which));
                            }
                        });
                builderSingle.show();
            }
        });



        lswithaddress.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                lsm.createlocationstore(location_id.get(position) ,location_name.get(position));
                addreddfinder.putExtra("locationid_key" ,location_id.get(position));
                Locationgetter.location = location_name.get(position);
                startActivity(addreddfinder);
                if (ActivityIdentifire.mainactivityopen == 1 ){
                    MainActivity.fa.finish();
                }
                finish();
            }
        });
    }




    private void loadzoneincities(final String city) {
        final String c = city ;
        noaddress.setVisibility(View.GONE);
        withaddress.setVisibility(View.VISIBLE);
        sr = new StringRequest(Request.Method.GET,URLAPI.zonesincity.concat(city), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson = gsonBuilder.create();
                pw.setVisibility(View.GONE);
                lr = gson.fromJson(response, LOCATION_RESPONSE.class);
                if(lr.result.equals("1")){
                    lsr = gson.fromJson(response, LOCATION_SUCCESS_RESPONSE.class);
                    location_id.clear();
                    city_id.clear();
                    location_name.clear();
                    for(int i = 0 ; i<lsr.lm.size() ;i++){
                        location_id.add(lsr.lm.get(i).location_id);
                        location_name.add(lsr.lm.get(i).location_name);
                        city_id.add(lsr.lm.get(i).city_id);
                    }
                    lswithaddress.setVisibility(View.VISIBLE);
                    lswithaddress.setAdapter(new ArrayAdapter(LocationWithNoChange.this , R.layout.item_for_address_search , R.id.addresssearch_id_item ,location_name));
                }if(lr.result.equals("0")){
                    lswithaddress.setVisibility(View.GONE);
                    Toast.makeText(LocationWithNoChange.this , "No store found in this city " , Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(LocationWithNoChange.this, "Please check your internet connection ", Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(sr);
        pw.setVisibility(View.VISIBLE);
    }
}
