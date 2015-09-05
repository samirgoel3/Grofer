package com.spinno.buzczar;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import apiurls.URLAPI;
import pojo.ADD_ADDRESS_RESPONSE;
import sessionmanager.LocationSesionManager;
import sessionmanager.SessionManager;
import singleton.VolleySingleton;


public class AddAddress extends Activity {

    EditText State , Area , houseno , streetno , homeaddress , pine , landmarke ;
    Button saveaddress  ;
    String statee , areaa , housenoe , streeteeno , homeaddresse  , pin , landmark  , viewaddreslink;

    ImageView backbutton ;

    ADD_ADDRESS_RESPONSE addAddressresponse = new ADD_ADDRESS_RESPONSE();



    StringRequest sr ;
    RequestQueue queue ;

    ProgressDialog progressDialog;

    SessionManager sm ;
    LocationSesionManager lsm ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);

        queue = VolleySingleton.getInstance(AddAddress.this).getRequestQueue();
        sm = new SessionManager(getApplicationContext());
        lsm = new LocationSesionManager(getApplicationContext());
        backbutton = (ImageView) findViewById(R.id.back_button_add_address);

        State = (EditText) findViewById(R.id.editstate);
        Area = (EditText) findViewById(R.id.editarea);
        houseno = (EditText) findViewById(R.id.edithousenumber);
        streetno = (EditText) findViewById(R.id.editstreetno);
        homeaddress = (EditText) findViewById(R.id.edithomeaddress);
        pine = (EditText) findViewById(R.id.pin);
        landmarke = (EditText) findViewById(R.id.landmark);

        State.setEnabled(false);
        Area.setEnabled(false);

        State.setText(lsm.getcity().get(LocationSesionManager.KEY_CITY_NAME));
        Area.setText(lsm.getlocation().get(LocationSesionManager.KEY_LOCATION_NAME));



        saveaddress = (Button) findViewById(R.id.saveaddressinaddaddress);

        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddressActivity.aa.finish();
                Intent in = new Intent(AddAddress.this , AddressActivity.class);
                in.putExtra("fromwhichactivity","Add address");
                startActivity(in);
                finish();
            }
        });


        saveaddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(houseno.getText().toString().equals("")||streetno.getText().toString().equals("")){
                    Toast.makeText(AddAddress.this , "Please fil the mandatory fields" ,Toast.LENGTH_SHORT).show();
                }else {

                    if (State.getText().toString().matches("")){
                        statee = "not mentioned";

                    }else {
                        statee = State.getText().toString();
                    }

                    if(Area.getText().toString().matches("")){
                        areaa = "not mentioned";
                    }
                    else{
                        areaa = Area.getText().toString();
                    }


                    if( houseno.getText().toString().matches("")){
                        housenoe = "not mentioned";
                    }else{
                        housenoe =  houseno.getText().toString();
                    }


                    if(streetno.getText().toString().matches("")){
                        streeteeno = "not mentioned";
                    }else{
                        streeteeno = streetno.getText().toString();
                    }



                    if (homeaddress.getText().toString().matches("")){
                        homeaddresse = "not mentioned";
                    }else {
                        homeaddresse = homeaddress.getText().toString();
                    }


                    if (pine.getText().toString().matches("")){
                        pin = "not mentioned";
                    }else {
                        pin = pine.getText().toString();
                    }




                    if (landmarke.getText().toString().matches("")){
                        landmark = "not mentioned";
                    }else {
                        landmark = landmarke.getText().toString();
                    }


                    viewaddreslink = URLAPI.addaddress.concat(new SessionManager(AddAddress.this).getUserDetails().get(SessionManager.KEY_UserId)).concat("&state=")
                            .concat(statee).concat("&area=").concat(areaa).concat("&houseno=")
                            .concat(housenoe).concat("&streeno=").concat(streeteeno).concat("&addrename=")
                            .concat(homeaddresse)
                            .concat("&pin=")
                            .concat(pin).concat("&landmark=")
                            .concat(landmark);

                    viewaddreslink=viewaddreslink.replace(" ","%20");

                    Log.d("link i am executing for adding addres details ", viewaddreslink);






                    sr = new StringRequest(Request.Method.GET, viewaddreslink, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            progressDialog.dismiss();
                            GsonBuilder gsonBuilder = new GsonBuilder();
                            Gson gson = gsonBuilder.create();
                            addAddressresponse = gson.fromJson(response, ADD_ADDRESS_RESPONSE.class);
                            if(addAddressresponse.result.equals("1")){
                                Toast.makeText(AddAddress.this, "" +addAddressresponse.message, Toast.LENGTH_SHORT).show();
                                AddressActivity.aa.finish();
                                Intent in = new Intent(AddAddress.this , AddressActivity.class);
                                if(ActivityIdentifire.cartactivity == 0 ){
                                    in.putExtra("fromwhichactivity","Add address");
                                }else if (ActivityIdentifire.cartactivity ==  1 ){
                                    in.putExtra("fromwhichactivity","cartactivity");
                                }

                                startActivity(in);
                                finish();
                            }else {
                                Toast.makeText(AddAddress.this, ""+addAddressresponse.message, Toast.LENGTH_SHORT).show();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            progressDialog.dismiss();
                            Toast.makeText(AddAddress.this, "" + error, Toast.LENGTH_SHORT).show();
                        }
                    });

                    queue.add(sr);
                    progressDialog = new ProgressDialog(AddAddress.this);
                    progressDialog.setMessage("Adding Address to server...");
                    progressDialog.show();
                }
            }
        });
    }






    @Override
    public void onBackPressed() {
        super.onBackPressed();
        AddressActivity.aa.finish();
        Intent in = new Intent(AddAddress.this , AddressActivity.class);
        in.putExtra("fromwhichactivity","Add address");
        startActivity(in);
        finish();
    }
}
