package com.spinno.buzczar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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

import apiurls.URLAPI;
import pojo.CATEGORY_AFTER_SUCCESS;
import pojo.CATEGORY_RESPONSE;
import singleton.VolleySingleton;


public class CategoryFetchingActivity extends Activity {

    String location ;

    StringRequest sr ;
    RequestQueue queue ;

    CATEGORY_RESPONSE lr = new CATEGORY_RESPONSE();
    CATEGORY_AFTER_SUCCESS cas  = new CATEGORY_AFTER_SUCCESS();

    ArrayList<String> namearr = new ArrayList<String>();
    ArrayList<String> idarr = new ArrayList<String>();
    ArrayList<String> locationarr = new ArrayList<String>();
    ArrayList<String> imagearr = new ArrayList<String>();

    Intent inee ,   norecordintent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_fetching);

        queue = VolleySingleton.getInstance(CategoryFetchingActivity.this).getRequestQueue();

        Intent in = getIntent();
         location = in.getExtras().getString("locationid_key" , "");

        inee = new Intent(CategoryFetchingActivity.this , MainActivity.class);
        norecordintent = new Intent(CategoryFetchingActivity.this ,LocationWithNoChange.class);

        sr = new StringRequest(Request.Method.GET, URLAPI.locationbasedcategory.concat(location), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {




                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson = gsonBuilder.create();
                lr = gson.fromJson(response, CATEGORY_RESPONSE.class);

                if(lr.result.equals("0")){
                    startActivity(norecordintent);

                    finish();
                }
                else {

                    cas = gson.fromJson(response, CATEGORY_AFTER_SUCCESS.class);
                    for(int i=0 ; i<cas.mac.size() ; i++){
                        namearr.add(cas.mac.get(i).name);
                        idarr.add(cas.mac.get(i).id);
                        imagearr.add(URLAPI.imageurlcategory.concat(cas.mac.get(i).image));
                        locationarr.add(cas.mac.get(i).location);
                    }
                    MyArrayListForcategory.name = namearr;
                    MyArrayListForcategory.id = idarr ;
                    MyArrayListForcategory.images = imagearr ;

                    Log.d("images array of category", "" + MyArrayListForcategory.images);

                    startActivity(inee);
                    finish();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(CategoryFetchingActivity.this, "" + error, Toast.LENGTH_SHORT).show();
            }
        });

        queue.add(sr);

        sr.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }

}
