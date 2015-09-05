package com.spinno.buzczar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
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


public class AddressFinderActivity extends Activity {

    ListView addresslist  ;
    EditText addressedt ;


    StringRequest sr ;
    RequestQueue queue ;
    ProgressWheel prwheel ;

    LOCATION_RESPONSE lr = new LOCATION_RESPONSE();
    LOCATION_SUCCESS_RESPONSE lsr = new LOCATION_SUCCESS_RESPONSE();


    ArrayList<String> location_id = new ArrayList<String>();
    ArrayList<String> city_id = new ArrayList<String>();
    ArrayList<String> location_name = new ArrayList<String>();

    Intent addreddfinder ;

    SessionManager sm ;
    LocationSesionManager lsm ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_finder);

        sm = new SessionManager(getApplicationContext());
        lsm = new LocationSesionManager(getApplicationContext());


        addressedt = (EditText) findViewById(R.id.edit_text_in_address_finder_activity);
        addresslist = (ListView) findViewById(R.id.listview_in_addres_finder_activity);
         prwheel = (ProgressWheel) findViewById(R.id.progress_wheel);
            prwheel.setVisibility(View.GONE);

        queue = VolleySingleton.getInstance(AddressFinderActivity.this).getRequestQueue();

        addreddfinder = new Intent(AddressFinderActivity.this , CategoryFetchingActivity.class);



        addressedt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                sr = new StringRequest(Request.Method.GET,URLAPI.searchLocation.concat(""+s), new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        prwheel.setVisibility(View.GONE);
                        addresslist.setVisibility(View.VISIBLE);
                        GsonBuilder gsonBuilder = new GsonBuilder();
                        Gson gson = gsonBuilder.create();

                        lr = gson.fromJson(response, LOCATION_RESPONSE.class);

                        if(lr.result.equals("0")){

                            addresslist.setVisibility(View.GONE);
                        } if (lr.result.equals("1")){
                            lsr = gson.fromJson(response, LOCATION_SUCCESS_RESPONSE.class);

                            location_id.clear();
                            city_id.clear();
                            location_name.clear();

                             for(int i = 0 ; i<lsr.lm.size() ;i++){
                                 location_id.add(lsr.lm.get(i).location_id);
                                 location_name.add(lsr.lm.get(i).location_name);
                                 city_id.add(lsr.lm.get(i).city_id);
                             }
                            addresslist.setAdapter(new ArrayAdapter(AddressFinderActivity.this , R.layout.item_for_address_search , R.id.addresssearch_id_item ,location_name));
                        }


                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(AddressFinderActivity.this, "" + error, Toast.LENGTH_SHORT).show();
                    }
                });

                queue.add(sr);
                prwheel.setVisibility(View.VISIBLE);
                addresslist.setVisibility(View.GONE);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


       addresslist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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


}
