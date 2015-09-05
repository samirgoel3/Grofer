package com.spinno.buzczar;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import apiurls.URLAPI;
import networkchecker.NetworkChecker;
import pojo.CONTACT_RESULT;
import pojo.CONTACT_SUCCESS_RESULT;
import pojo.ENQUIRY;
import sessionmanager.LocationSesionManager;
import singleton.VolleySingleton;


public class EnquiryActivity extends Activity {

    LinearLayout Parentlayout  ,addressslayout , officephonelayout ,mobilephonelayout  , emaillayout;
    ImageView backbutton ;
    TextView sendbutton   , officeaddress , officephone , officemobile  , officemail ;

    FrameLayout contactframe ;
    EditText nameedt , emailedt , addresseedt  , enquiryedt ;

    String namestr , emailstr , addressstr , enquirystr ;

    StringRequest sr ;
    RequestQueue queue ;
    ProgressDialog progress;
    ProgressWheel pwheel ;
    ENQUIRY enparsed = new ENQUIRY();

    CONTACT_RESULT cr = new CONTACT_RESULT();
    CONTACT_SUCCESS_RESULT csr = new CONTACT_SUCCESS_RESULT();

    LocationSesionManager lsm ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enquiry);
        queue = VolleySingleton.getInstance(EnquiryActivity.this).getRequestQueue();
          lsm = new LocationSesionManager(getApplicationContext());

        Parentlayout = (LinearLayout) findViewById(R.id.parent_layout_in_enquiry);
        addressslayout = (LinearLayout) findViewById(R.id.address_layout_in_enquiry);
        officephonelayout = (LinearLayout) findViewById(R.id.office_phone_layout);
        mobilephonelayout = (LinearLayout) findViewById(R.id.mobile_phone_layout);
        emaillayout = (LinearLayout) findViewById(R.id.email_layout);

             addressslayout.setVisibility(View.GONE);
             officephonelayout.setVisibility(View.GONE);
             mobilephonelayout.setVisibility(View.GONE);
             emaillayout.setVisibility(View.GONE);

        officeaddress = (TextView) findViewById(R.id.officeaddresstextt);
        officephone = (TextView) findViewById(R.id.office_phone);
        officemobile = (TextView) findViewById(R.id.mobile_phone);
        officemail = (TextView) findViewById(R.id.officeemail);

        contactframe = (FrameLayout) findViewById(R.id.contactframeeee);
              contactframe.setVisibility(View.GONE);



        backbutton = (ImageView) findViewById(R.id.back_button_in_cart);
        sendbutton = (TextView) findViewById(R.id.send_button_in_enquiry);


        nameedt = (EditText) findViewById(R.id.nameinenquiry);
        emailedt = (EditText) findViewById(R.id.emailinenquiry);
        addresseedt  = (EditText) findViewById(R.id.addressinenquiry);
        enquiryedt = (EditText) findViewById(R.id.queryinenquiry);


        pwheel = (ProgressWheel) findViewById(R.id.progress_wheel_in_enquiry);
           pwheel.setVisibility(View.GONE);


           loadAdminAddress();

        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(Parentlayout.getWindowToken(), 0);
                finish();
            }
        });



        sendbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(Parentlayout.getWindowToken(), 0);

                namestr = nameedt.getText().toString();
             emailstr = emailedt.getText().toString();
             addressstr = addresseedt.getText().toString() ;
             enquirystr = enquiryedt.getText().toString();

                Calendar c = Calendar.getInstance();
                SimpleDateFormat dfdate = new SimpleDateFormat("dd MMM yyyy");
                SimpleDateFormat dftime = new SimpleDateFormat("HH:mm:ss");
                if(new NetworkChecker().isNetworkConnected(EnquiryActivity.this)){
                    if(checkEmailCorrect(emailstr)){
                        doenquiry(namestr , emailstr , addressstr , enquirystr , dfdate.format(c.getTime()) ,dftime.format(c.getTime()) );
                    }else {
                        Toast.makeText(EnquiryActivity.this , "Please Enter correct email" ,Toast.LENGTH_SHORT).show();
                    }
                }else {
              Toast.makeText(EnquiryActivity.this , "Please check your internet connection" ,Toast.LENGTH_SHORT).show();
                }
            }
        });


      /*  callbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String posted_by = callingtext.getText().toString();
                String uri = "tel:" + posted_by.trim() ;
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse(uri));
                startActivity(intent);
            }
        });   */



    }

    private void loadAdminAddress() {
        sr = new StringRequest(Request.Method.GET, URLAPI.adminaddress, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                pwheel.setVisibility(View.GONE);
                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson = gsonBuilder.create();
                cr = gson.fromJson(response ,CONTACT_RESULT.class);

                if(cr.result.equals("1")){
                    csr = gson.fromJson(response ,CONTACT_SUCCESS_RESULT.class);
                  //  Toast.makeText(EnquiryActivity.this , ""+csr.ca.Contact_Person_Name , Toast.LENGTH_SHORT).show();
                 lsm.createadminaddress(csr.ca.Contact_Person_Name ,csr.ca.Email_Id , csr.ca.Mobile_No ,csr.ca.Phone_No , csr.ca.Address);
                    loadAdminInView();
                }else if(enparsed.result.equals("0")){
                    Toast.makeText(EnquiryActivity.this , "Admin Problem" , Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pwheel.setVisibility(View.GONE);
                Toast.makeText(EnquiryActivity.this, "Internet Problem Occurred", Toast.LENGTH_SHORT).show();

            }
        });

        queue.add(sr);
            pwheel.setVisibility(View.VISIBLE);


    }

    private void loadAdminInView() {
        String contactperson = lsm.getadminaddress().get(LocationSesionManager.KEY_CONTACT_PERSON_NAME);
        String email = lsm.getadminaddress().get(LocationSesionManager.KEY_EMAIL);
        String mobile = lsm.getadminaddress().get(LocationSesionManager.KEY_MOBILE_No);
        String phone = lsm.getadminaddress().get(LocationSesionManager.KEY_PHONE_NO);
        String address = lsm.getadminaddress().get(LocationSesionManager.KEY_ADDRESS);



        if(!address.equals("")){
            contactframe.setVisibility(View.VISIBLE);
            addressslayout.setVisibility(View.VISIBLE);
            officeaddress.setText(address);
        }if(!mobile.equals("")){
            contactframe.setVisibility(View.VISIBLE);
            mobilephonelayout.setVisibility(View.VISIBLE);
            officemobile.setText(mobile);
            mobilephonelayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String posted_by = officemobile.getText().toString();
                    String uri = "tel:" + posted_by.trim() ;
                    Intent intent = new Intent(Intent.ACTION_CALL);
                    intent.setData(Uri.parse(uri));
                    startActivity(intent);
                }
            });
        }if(!phone.equals("")){
            contactframe.setVisibility(View.VISIBLE);
            officephonelayout.setVisibility(View.VISIBLE);
            officephone.setText(phone);
            officephonelayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String posted_by = officephone.getText().toString();
                    String uri = "tel:" + posted_by.trim() ;
                    Intent intent = new Intent(Intent.ACTION_CALL);
                    intent.setData(Uri.parse(uri));
                    startActivity(intent);
                }
            });
        }if(!email.equals("")){
            contactframe.setVisibility(View.VISIBLE);
            emaillayout.setVisibility(View.VISIBLE);
            officemail.setText(email);
        }
    }

    private void doenquiry(String naem , String email , String phone , String enquiry , String date , String timing ) {

        String enurl = URLAPI.enquiry+naem+"&email="+email+"&address="+phone+"&comment="+enquiry+"&date="+date+"&time="+timing;
        enurl = enurl.replace(" ", "%20");

        Log.e("Lionk i m executing for enquiry " ,enurl );
        sr = new StringRequest(Request.Method.GET, enurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progress.dismiss();
                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson = gsonBuilder.create();
                   enparsed = gson.fromJson(response ,ENQUIRY.class);

                if(enparsed.result.equals("1")){
                    finish();
                    Toast.makeText(EnquiryActivity.this , ""+enparsed.message , Toast.LENGTH_SHORT).show();
                }else if(enparsed.result.equals("0")){
                    Toast.makeText(EnquiryActivity.this , ""+enparsed.message , Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progress.dismiss();
             Toast.makeText(EnquiryActivity.this, "Internet Problem Occurred", Toast.LENGTH_SHORT).show();

            }
        });

        queue.add(sr);
        progress = ProgressDialog.show(EnquiryActivity.this, "",
                "Sending your enquiry...", true);

    }

    boolean checkEmailCorrect(String Email) {
        String pttn = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        Pattern p = Pattern.compile(pttn);
        Matcher m = p.matcher(Email);

        if (m.matches()) {
            return true;
        }
        return false;
    }
}
