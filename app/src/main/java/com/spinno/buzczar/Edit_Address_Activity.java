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
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import networkchecker.NetworkChecker;
import pojo.EDIT_ADDRESS_RESPONSE;
import pojo.VIEW_ADDRESS;
import pojo.WHEN_SUCCESSFULL_ADDRESS;
import singleton.VolleySingleton;


public class Edit_Address_Activity extends Activity {

    String statefrompreviousactivity , areafrompreviousactivity , housenofrompreviousactivity , streetnofrompreviousactivity , addressnamefrompreviousactivity ,pinfrompreviousactivity , landmarkfrompreviousactivity , idofaddressfrompreviousactivity;

    EditText stateedt , areaedt , housenoedt , streetnoedt , addressnameedt , pinedt , landmarkedt  ;

    TextView idofaddress ;

    Button update ;

    String starturl_for_edit_address  = "http://spinnosolutions.com/ecommerce_api/api/update_address.php?id=";
    String  final_url_for_edit_adrress ;

    ProgressDialog progressDialog;
    EDIT_ADDRESS_RESPONSE editpost = new EDIT_ADDRESS_RESPONSE();






    String starturl_for_retrive_address ="http://spinnosolutions.com/ecommerce_api/api/view_address.php?user_id=";
    WHEN_SUCCESSFULL_ADDRESS aftersuccesresult = new WHEN_SUCCESSFULL_ADDRESS();
    List<VIEW_ADDRESS> addresslist ;
    ArrayList<String> _id_of_address = new ArrayList<String>();
    ArrayList<String> state = new ArrayList<String>();
    ArrayList<String> area = new ArrayList<String>();
    ArrayList<String> house_no = new ArrayList<String>();
    ArrayList<String> street_no = new ArrayList<String>();
    ArrayList<String> address_name = new ArrayList<String>();
    ArrayList<String> pinl = new ArrayList<String>();
    ArrayList<String> landmarkl = new ArrayList<String>();

    String fromwhichscreen ;

    ImageView backbutton ;


    StringRequest sr ;
    RequestQueue queue ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit__address_);

        queue = VolleySingleton.getInstance(Edit_Address_Activity.this).getRequestQueue();



        stateedt = (EditText) findViewById(R.id.editstate_in_edit_address);
        areaedt = (EditText) findViewById(R.id.editarea_in_edit_address);
        housenoedt = (EditText) findViewById(R.id.edithousenumber_in_edit_address);
        streetnoedt = (EditText) findViewById(R.id.editstreetno_in_edit_address);
        addressnameedt = (EditText) findViewById(R.id.edithomeaddress_in_edit_address);
        pinedt = (EditText) findViewById(R.id.pin_in_edit_address);
        landmarkedt = (EditText) findViewById(R.id.landmark_in_edit_address);

        idofaddress = (TextView) findViewById(R.id.id_in_edit_address);

        update = (Button) findViewById(R.id.update_button_in_edit_address);
        backbutton = (ImageView) findViewById(R.id.back_button_edit_address);

        stateedt.setEnabled(false);
        areaedt.setEnabled(false);




        Intent intent = getIntent();
        fromwhichscreen =  intent.getStringExtra("fromwhichscreen");

        statefrompreviousactivity = intent.getStringExtra("statekey");
        areafrompreviousactivity = intent.getStringExtra("areakey");
        housenofrompreviousactivity = intent.getStringExtra("housenokey");
        streetnofrompreviousactivity = intent.getStringExtra("streetnokey");
        addressnamefrompreviousactivity = intent.getStringExtra("addressnamekey");
        pinfrompreviousactivity = intent.getStringExtra("pinkey");
        landmarkfrompreviousactivity = intent.getStringExtra("landmarkkey");
        idofaddressfrompreviousactivity = intent.getStringExtra("idofaddresskey");

        stateedt.setText(statefrompreviousactivity);
        areaedt.setText(areafrompreviousactivity);
        housenoedt.setText(housenofrompreviousactivity);
        streetnoedt.setText(streetnofrompreviousactivity);
        addressnameedt.setText(addressnamefrompreviousactivity);
        pinedt.setText(pinfrompreviousactivity);
        landmarkedt.setText(landmarkfrompreviousactivity);
        idofaddress.setText(idofaddressfrompreviousactivity);




        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginIntent = new Intent(Edit_Address_Activity.this, AddressActivity.class);
                AddressActivity.aa.finish();
                loginIntent.putExtra("fromwhichactivity" ,"editaddress");
                startActivity(loginIntent);
                finish();
            }
        });


        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final_url_for_edit_adrress = starturl_for_edit_address.concat(idofaddress.getText().toString())
                        .concat("&state=").concat(stateedt.getText().toString())
                        .concat("&area=").concat(areaedt.getText().toString())
                        .concat("&houseno=").concat(housenoedt.getText().toString())
                        .concat("&streeno=").concat(streetnoedt.getText().toString())
                        .concat("&addrename=").concat(addressnameedt.getText().toString())
                        .concat("&pin=").concat(pinedt.getText().toString())
                        .concat("&landmark=").concat(landmarkedt.getText().toString());

                final_url_for_edit_adrress=final_url_for_edit_adrress.replace(" ","%20");

                Log.d("Link for editing address  ", "" + final_url_for_edit_adrress);
                if(new NetworkChecker().isNetworkConnected(Edit_Address_Activity.this) == true){

                    editaddress();

                }else {
                    Toast.makeText(Edit_Address_Activity.this, "No internet Connection available ", Toast.LENGTH_SHORT).show();
                }


            }
        });


    }

    private void editaddress() {
        sr = new StringRequest(Request.Method.GET, final_url_for_edit_adrress, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson = gsonBuilder.create();
                editpost = gson.fromJson(response, EDIT_ADDRESS_RESPONSE.class);

                if(editpost.response.equals("1")){
                    Intent loginIntent = new Intent(Edit_Address_Activity.this, AddressActivity.class);
                    AddressActivity.aa.finish();
                    loginIntent.putExtra("fromwhichactivity" ,"editaddress");
                    startActivity(loginIntent);
                    finish();
                }else {
                    Toast.makeText(Edit_Address_Activity.this, ""+editpost.message, Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(Edit_Address_Activity.this, "" + error, Toast.LENGTH_SHORT).show();
            }
        });

        queue.add(sr);
        progressDialog = new ProgressDialog(Edit_Address_Activity.this);
        progressDialog.setMessage("Adding Address to server...");
        progressDialog.show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent loginIntent = new Intent(Edit_Address_Activity.this, AddressActivity.class);
        AddressActivity.aa.finish();
        loginIntent.putExtra("fromwhichactivity" ,"editaddress");
        startActivity(loginIntent);
        finish();
    }
}
