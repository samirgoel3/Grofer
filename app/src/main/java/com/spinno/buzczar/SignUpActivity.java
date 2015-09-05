package com.spinno.buzczar;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import apiurls.URLAPI;
import networkchecker.NetworkChecker;
import pojo.LOGIN;
import pojo.REGISTERING_USER;
import pojo.WHEN_SUCCESSFULL_LOGIN;
import sessionmanager.SessionManager;
import singleton.VolleySingleton;


public class SignUpActivity extends Activity {


    EditText firstnameedt , lastnameedt  , phoneedt , passwordedt , confirmpasswordedt , emailedt ;
    Button createaccount ;
    String storing_phone , storing_password ;
    String furl_for_registration ;

    StringRequest sr ;
    RequestQueue queue ;
    ProgressDialog progressDialog;

    REGISTERING_USER ru = new REGISTERING_USER();
    LOGIN loginparseddata = new LOGIN();
    WHEN_SUCCESSFULL_LOGIN loginparseddataaftersuccess = new WHEN_SUCCESSFULL_LOGIN();

    String fromwhichactivity ;

    LinearLayout parentlayout ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        Intent in = getIntent();
        fromwhichactivity = in.getExtras().getString("fromwhichactivity" , "default");


        queue = VolleySingleton.getInstance(SignUpActivity.this).getRequestQueue();


        firstnameedt = (EditText) findViewById(R.id.firstname_in_regiration_activity);
        lastnameedt = (EditText) findViewById(R.id.lastname_in_regiration_activity);
        emailedt = (EditText) findViewById(R.id.email_in_regiration_activity);
        phoneedt = (EditText) findViewById(R.id.phone_in_regiration_activity);
        passwordedt = (EditText) findViewById(R.id.password_in_regiration_activity);
        confirmpasswordedt = (EditText) findViewById(R.id.confirm_password_in_regiration_activity);

        createaccount = (Button) findViewById(R.id.create_account_in_registration_activity);

        parentlayout = (LinearLayout) findViewById(R.id.parent_of_edit_text);

        creatinglisteneronedittext();




        createaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ///   checking first name
                if(isfirstnamelastnameisvalid(firstnameedt.getText().toString())){
                    /// checking last name
                    if (isfirstnamelastnameisvalid(lastnameedt.getText().toString())){
                        /// checing email
                        if(checkEmailCorrect(emailedt.getText().toString())){
                            ////// checking phone number
                            if( isnumbervalid(phoneedt.getText().toString())){

                                if(!passwordedt.getText().toString().equals(confirmpasswordedt.getText().toString())){
                                    Toast.makeText(SignUpActivity.this, "Password Not Matches", Toast.LENGTH_LONG).show();

                                    passwordedt.setText("");
                                    confirmpasswordedt.setText("");

                                } else{

                                    storing_phone = phoneedt.getText().toString();
                                    storing_phone = storing_phone.replace(" ","%20");
                                    storing_password = passwordedt.getText().toString();
                                    storing_password = storing_password.replace(" ","%20");



                                    furl_for_registration = URLAPI.registrationstart.concat(firstnameedt.getText().toString()).concat("&last_name=").concat(lastnameedt.getText().toString())
                                            .concat("&email=").concat(emailedt.getText().toString()).concat("&phone=").concat(phoneedt.getText().toString())
                                            .concat("&password=").concat(passwordedt.getText().toString());

                                    furl_for_registration = furl_for_registration.replace(" ", "%20");

                                    if(new NetworkChecker().isNetworkConnected(SignUpActivity.this)){
                                       resgisteruser();
                                    }else {
                                        Toast.makeText(SignUpActivity.this , "Please check your internet Connectivity" , Toast.LENGTH_SHORT).show();
                                    }

                                }

                            }else {
                                //  phoneedt.setError("number is not valid");
                            }
                        }else{
                            // emailedt.setError("email is not valid");
                        }
                    }else {
                        //     lastnameedt.setError("required atleast three alphabet");
                    }
                }else {
                    //  firstnameedt.setError("required atleast three alphabet");
                }

            }

            private void resgisteruser() {
                sr = new StringRequest(Request.Method.GET,furl_for_registration, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(parentlayout.getWindowToken(), 0);


                        GsonBuilder gsonBuilder = new GsonBuilder();
                        Gson gson = gsonBuilder.create();
                        ru = gson.fromJson(response, REGISTERING_USER.class);

                        if(ru.result.equals("0")){
                            progressDialog.dismiss();
                                 clearalledittext();
                            Toast.makeText(SignUpActivity.this, ""+ru.message, Toast.LENGTH_SHORT).show();

                        }else if(ru.result.equals("1")){
                            Toast.makeText(SignUpActivity.this, ""+ru.message, Toast.LENGTH_SHORT).show();
                              loginrequest(storing_phone , storing_password);
                        }
                   }

                    private void clearalledittext() {
                        firstnameedt.setText("");
                        lastnameedt.setText("");
                        phoneedt.setText("");
                        passwordedt.setText("");
                        confirmpasswordedt.setText("");
                        emailedt.setText("");
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();

                        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(parentlayout.getWindowToken(), 0);

                        Toast.makeText(SignUpActivity.this, "Please check your internet connection", Toast.LENGTH_SHORT).show();
                    }
                });

                queue.add(sr);
                Log.e("link i am eexecuting for registering user " ,furl_for_registration);
                progressDialog = new ProgressDialog(SignUpActivity.this);
                progressDialog.setMessage("Loging in ..");
                progressDialog.show();
            }
        });
    }

    private void loginrequest(String storing_phone, String storing_password) {
        sr = new StringRequest(Request.Method.GET, URLAPI.loginstart.concat(storing_phone).concat(URLAPI.loginmid).concat(storing_password), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson = gsonBuilder.create();
                loginparseddata = gson.fromJson(response, LOGIN.class);
                if(loginparseddata.result.equals("1")){
                    loginparseddataaftersuccess = gson.fromJson(response, WHEN_SUCCESSFULL_LOGIN.class);
                    Toast.makeText(SignUpActivity.this, "Welcome " +loginparseddataaftersuccess.maf.first_name, Toast.LENGTH_SHORT).show();
                    new SessionManager(SignUpActivity.this).createLoginSession(loginparseddataaftersuccess.maf.id, loginparseddataaftersuccess.maf.first_name, loginparseddataaftersuccess.maf.phone, loginparseddataaftersuccess.maf.email);

                    if(fromwhichactivity.equals("LoginActivity")){
                        finish();
                    }else if (fromwhichactivity.equals("AddressActivity")){
                        if(ActivityIdentifire.addressactivity == 1 ){
                            Intent in = new Intent(SignUpActivity.this , AddressActivity.class);
                                in.putExtra("fromwhichactivity" , "SignUpActivity");
                            AddressActivity.aa.finish();
                               finish();
                              startActivity(in);
                        }
                    }

                }else {
                    Toast.makeText(SignUpActivity.this, "Please Enter Correct username/password", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(SignUpActivity.this, " Please check your internet connection ", Toast.LENGTH_SHORT).show();
            }
        });

        queue.add(sr);
    }

    private void creatinglisteneronedittext() {


        firstnameedt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {

                if(firstnameedt.getText().toString().equals("")){
                }else if(firstnameedt.getText().toString().length()<3){
                    firstnameedt.setError("Atleast 3 alphabet");
                }else if(firstnameedt.getText().toString().length()>=3){
                    if(alphabetchecker(firstnameedt.getText().toString())){
                        firstnameedt.setError(null);
                    }else {
                        firstnameedt.setError("Enter alphabet only");
                    }

                }
            }
        });




        lastnameedt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {

                if(lastnameedt.getText().toString().equals("")){
                }else if(lastnameedt.getText().toString().length()<3){
                    lastnameedt.setError("Atleast 3 alphabet");
                }else if(lastnameedt.getText().toString().length()>=3){
                    lastnameedt.setError(null);
                }
            }
        });


        emailedt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(emailedt.getText().toString().equals("")){
                }else if(checkEmailCorrect(emailedt.getText().toString()) == false){
                    emailedt.setError("Email is not valid");
                }else  if (checkEmailCorrect(emailedt.getText().toString()) == true){
                    emailedt.setError(null);
                }
            }
        });


        phoneedt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(phoneedt.getText().toString().equals("")){
                }else if(isnumbervalid(phoneedt.getText().toString()) == false){
                    phoneedt.setError("Phone is not valid");
                }else  if (checkEmailCorrect(phoneedt.getText().toString()) == true){
                    phoneedt.setError(null);
                }
            }
        });

        passwordedt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(passwordedt.getText().toString().equals("")){

                }else if(passwordedt.getText().toString().length()<6){
                    passwordedt.setError("Enter Atleast 6 digit");
                    createaccount.setClickable(false);
                }else if(passwordedt.getText().toString().length()>=6){
                    passwordedt.setError(null);
                    createaccount.setClickable(true);
                }
            }
        });
    }

    private Boolean alphabetchecker(String s) {
        boolean check;
        Pattern ps = Pattern.compile("^[a-zA-Z ]+$");
        Matcher ms = ps.matcher(s);
        check = ms.matches();
        return check ;
    }

    private Boolean isnumbervalid(String s) {
        boolean check;
        //  if(s.length() < 2 || s.length() > 13)
        if(s.length() < 10)
        {
            check = false;

            phoneedt.setError("Number is not valid phone");
        }
        else
        {
            check = true;

        }
        return check;
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

    private Boolean isfirstnamelastnameisvalid(String s) {
        boolean check;
        //  if(s.length() < 2 || s.length() > 13)
        if(s.length() < 3)
        {
            check = false;

            phoneedt.setError("Number is not valid phone");
        }
        else
        {
            check = true;

        }
        return check;
    }


}