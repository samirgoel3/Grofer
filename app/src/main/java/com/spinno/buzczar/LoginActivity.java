package com.spinno.buzczar;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
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
import pojo.FAILED_SIGN_UP;
import pojo.FORGOT_PASSWORD;
import pojo.LOGIN;
import pojo.MESSAGE_AFTER_LOGIN;
import pojo.WHEN_SUCCESSFULL_LOGIN;
import sessionmanager.SessionManager;
import singleton.VolleySingleton;


public class LoginActivity extends Activity {

    EditText usernameedt , userpasswordedt ;
    Button  loginbtn , signupbtn ;

    String userphonest , userpasswordst ;
    TextView forgotpassword ;

    RelativeLayout Parentlayout ;


    StringRequest sr ;
    RequestQueue queue ;

    ProgressDialog progressDialog;

    LOGIN loginparseddata = new LOGIN();
    FAILED_SIGN_UP fsp = new FAILED_SIGN_UP();
    WHEN_SUCCESSFULL_LOGIN loginparseddataaftersuccess = new WHEN_SUCCESSFULL_LOGIN();
    MESSAGE_AFTER_LOGIN     messageafterlogin = new MESSAGE_AFTER_LOGIN();

    FORGOT_PASSWORD fp = new FORGOT_PASSWORD();


     Dialog dialog ;

    LinearLayout parentof_forgot ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        dialog = new Dialog(LoginActivity.this);

        Parentlayout = (RelativeLayout) findViewById(R.id.parentlayout_in_login_activity);
        parentof_forgot = (LinearLayout) findViewById(R.id.parent_of_forgot_password);


        if(new SessionManager(LoginActivity.this).isLoggedIn()){
            finish();
        } else

        queue = VolleySingleton.getInstance(LoginActivity.this).getRequestQueue();

        usernameedt = (EditText) findViewById(R.id.phone_number_in_before_login);
        userpasswordedt = (EditText) findViewById(R.id.password_in_before_login);
        loginbtn = (Button) findViewById(R.id.loginbuttonin_before_login);
        signupbtn = (Button) findViewById(R.id.sign_up_in_before_login);
        forgotpassword = (TextView) findViewById(R.id.forgetpassword);


        forgotpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 // custom dialog

              //  dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.activity_forgot_password);
                final EditText edt = (EditText)dialog.findViewById(R.id.forgotpassemail);

                  dialog.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
                      @Override
                      public void onClick(View v) {
                          dialog.dismiss();
                      }
                  });
                dialog.findViewById(R.id.request).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                     String email =  edt.getText().toString();
                        if(email.equals("")){
                            Toast.makeText(LoginActivity.this ,"Please Enter your Registered email" , Toast.LENGTH_SHORT).show();
                        }else if(!email.equals("")){
                           if( checkemailaddress(email)){
                               String forgorurl = URLAPI.forgetpass.concat(email);
                               forgorurl = forgorurl.replace(" ", "%20");
                               makerequest(forgorurl);

                           }else{
                               Toast.makeText(LoginActivity.this ,"Please Enter your correct email" , Toast.LENGTH_SHORT).show();
                           }
                        }
                    }

                    private boolean checkemailaddress(String Email) {
                        String pttn = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                        Pattern p = Pattern.compile(pttn);
                        Matcher m = p.matcher(Email);

                        if (m.matches()) {
                            return true;
                        }
                        return false;
                    }
                });

                dialog.show();
            }
        });

        signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(LoginActivity.this , SignUpActivity.class );
                in.putExtra("fromwhichactivity","LoginActivity");
                startActivity(in);
                overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
            }
        });



            loginbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(usernameedt.getText().toString().equals("")||userpasswordedt.getText().toString().equals("")){
                        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(Parentlayout.getWindowToken(), 0);
                        Toast.makeText(LoginActivity.this , "Please Enter correct phone and password" ,Toast.LENGTH_SHORT).show();
                    }
                    else {
                        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(Parentlayout.getWindowToken(), 0);
                        userphonest = usernameedt.getText().toString();
                        userpasswordst = userpasswordedt.getText().toString();
                        sr = new StringRequest(Request.Method.GET, URLAPI.loginstart.concat(userphonest).concat(URLAPI.loginmid).concat(userpasswordst), new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                progressDialog.dismiss();
                                GsonBuilder gsonBuilder = new GsonBuilder();
                                Gson gson = gsonBuilder.create();
                                loginparseddata = gson.fromJson(response, LOGIN.class);
                                if(loginparseddata.result.equals("1")){
                                    loginparseddataaftersuccess = gson.fromJson(response, WHEN_SUCCESSFULL_LOGIN.class);
                                    Toast.makeText(LoginActivity.this, "Welcome " +loginparseddataaftersuccess.maf.first_name, Toast.LENGTH_SHORT).show();
                                    new SessionManager(LoginActivity.this).createLoginSession(loginparseddataaftersuccess.maf.id, loginparseddataaftersuccess.maf.first_name, loginparseddataaftersuccess.maf.phone, loginparseddataaftersuccess.maf.email);
                                    finish();
                                }else if(loginparseddata.result.equals("0")) {
                                    fsp = gson.fromJson(response, FAILED_SIGN_UP.class);
                                    Toast.makeText(LoginActivity.this , ""+fsp.message,Toast.LENGTH_SHORT).show();
                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                progressDialog.dismiss();
                                Toast.makeText(LoginActivity.this, "Check your internet connection" , Toast.LENGTH_SHORT).show();
                            }
                        });

                        queue.add(sr);
                        progressDialog = new ProgressDialog(LoginActivity.this);
                        progressDialog.setMessage("Loging in ..");
                        progressDialog.show();
                    }
                }
            });
    }

    private void makerequest(String forgorurl) {

       // InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
       //  imm.hideSoftInputFromWindow(Parentlayout.getWindowToken(), 0);

        sr = new StringRequest(Request.Method.GET, forgorurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson = gsonBuilder.create();
                fp = gson.fromJson(response, FORGOT_PASSWORD.class);
                if(fp.result.equals("1")){
                    dialog.dismiss();
                }
                Toast.makeText(LoginActivity.this ," "+fp.message , Toast.LENGTH_LONG).show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(LoginActivity.this, "Check your internet connection" , Toast.LENGTH_SHORT).show();
            }
        });

        queue.add(sr);
        progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setMessage("requesting your password...");
        progressDialog.show();
    }


    @Override
    protected void onResume() {
        super.onResume();
        if(new SessionManager(LoginActivity.this).isLoggedIn()){
            finish();
        }
    }
}
