package com.spinno.buzczar;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import apiurls.URLAPI;
import pojo.AboutresponseResponse;
import pojo.RESULT;
import singleton.VolleySingleton;

public class AboutUsActivity extends Activity {


    TextView   tittle , description ;



    StringRequest sr ;
    RequestQueue queue ;
    ProgressDialog progressDialog;

    RESULT rspojo  = new RESULT() ;
    AboutresponseResponse  aur = new AboutresponseResponse();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        queue = VolleySingleton.getInstance(AboutUsActivity.this).getRequestQueue();

        tittle = (TextView) findViewById(R.id.tittle_about_us_activity);
        description = (TextView) findViewById(R.id.description_about_us_activity);


        loaddata();

    }

    private void loaddata() {
        sr = new StringRequest(Request.Method.GET, URLAPI.aboutus, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson = gsonBuilder.create();
                rspojo = gson.fromJson(response, RESULT.class);
                if(rspojo.result.equals("1")){

                    aur = gson.fromJson(response, AboutresponseResponse.class);
                    tittle.setText(aur.aus.Title);
                    description.setText(aur.aus.Description);
                }else if(rspojo.result.equals("0")) {

                    Toast.makeText(AboutUsActivity.this,"doest contain data " ,Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(AboutUsActivity.this, "Check your internet connection" , Toast.LENGTH_SHORT).show();
            }
        });


        queue.add(sr);
        progressDialog = new ProgressDialog(AboutUsActivity.this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
    }


}
