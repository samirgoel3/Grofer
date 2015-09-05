package com.spinno.buzczar;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
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
import com.orm.SugarRecord;

import java.util.ArrayList;
import java.util.List;

import apiurls.URLAPI;
import networkchecker.NetworkChecker;
import pojo.ADDRESS;
import pojo.FAILED_SIGN_UP;
import pojo.LOGIN;
import pojo.REMOVE_ADDRESS;
import pojo.VIEW_ADDRESS;
import pojo.WHEN_SUCCESSFULL_ADDRESS;
import pojo.WHEN_SUCCESSFULL_LOGIN;
import sessionmanager.SessionManager;
import singleton.VolleySingleton;


public class AddressActivity extends Activity {

    Button orderasguestbtn  , addaddresswhenLoginButNoAddressbtn;
    Button addaddress  , login , signup ;
    EditText phoneedt , passwordedt ;
    String userphonest , userpasswordst ;
    LOGIN loginparseddata = new LOGIN();
    FAILED_SIGN_UP fsp = new FAILED_SIGN_UP();
    WHEN_SUCCESSFULL_LOGIN loginparseddataaftersuccess = new WHEN_SUCCESSFULL_LOGIN();
    Intent starterIntent ;
    LinearLayout parentlayout ;



    View layoutbraforelogin_in_address , layoutafterlogin_in_address , layoutwithnoaddress;
    LinearLayout rowforaddress ;

    TextView noaddresstxt  , checkinternettext ;

    ADDRESS addressresult = new ADDRESS();
    WHEN_SUCCESSFULL_ADDRESS addresssucessresult = new WHEN_SUCCESSFULL_ADDRESS();
    List<VIEW_ADDRESS> addresslist ;

    ProgressDialog progressDialog;

    ImageView backwhenusernotlogin , backbuttonafterlogin  , backwithloginNoaddress;

    String ssss;
    String deleaddress_url ,refid , responseeee ;
    ProgressWheel pw ;


    ArrayList<String> _id_of_address = new ArrayList<String>();
    ArrayList<String> state = new ArrayList<String>();
    ArrayList<String> area = new ArrayList<String>();
    ArrayList<String> house_no = new ArrayList<String>();
    ArrayList<String> street_no = new ArrayList<String>();
    ArrayList<String> address_name = new ArrayList<String>();
    ArrayList<String> pin = new ArrayList<String>();
    ArrayList<String> landmark = new ArrayList<String>();

    REMOVE_ADDRESS postsobjj = new REMOVE_ADDRESS();

    StringRequest sr ;
    RequestQueue queue ;
    public static Activity aa;

    SessionManager sm ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);
        queue = VolleySingleton.getInstance(AddressActivity.this).getRequestQueue();
        sm = new SessionManager(getApplicationContext());
        ActivityIdentifire.addressactivity = 1 ;
        aa = this ;
        starterIntent = getIntent() ;

        Intent in = getIntent();
         ssss = in.getExtras().getString("fromwhichactivity" , "default");


        layoutafterlogin_in_address = findViewById(R.id.after_login_for_address_fragment);
        layoutbraforelogin_in_address = findViewById(R.id.before_login_inaddress_fragment);
        layoutwithnoaddress = findViewById(R.id.login_with_no_address);
        orderasguestbtn = (Button) findViewById(R.id.order_as_guest_in_address_activity);
        rowforaddress = (LinearLayout)findViewById(R.id.linearLayoutforview);
        addaddress = (Button)findViewById(R.id.addaddressid);
        addaddresswhenLoginButNoAddressbtn = (Button) findViewById(R.id.add_address_in_login_with_no_address);
        checkinternettext = (TextView) findViewById(R.id.checkinternettext);
        login = (Button) findViewById(R.id.loginbuttonin_before_login);
        signup = (Button) findViewById(R.id.sign_up_in_before_login);
        phoneedt = (EditText) findViewById(R.id.phone_number_in_before_login);
        passwordedt = (EditText) findViewById(R.id.password_in_before_login);
        parentlayout = (LinearLayout) findViewById(R.id.parentlayout_in_login_activity);


        backwhenusernotlogin = (ImageView) findViewById(R.id.back_button_in_before_login_for_address);
        backbuttonafterlogin = (ImageView) findViewById(R.id.back_button_in_after_login_for_address);
        backwithloginNoaddress = (ImageView) findViewById(R.id.back_button_inlogin_with_no_address);
        pw = (ProgressWheel) findViewById(R.id.progress_wheel_in_addressac_tivity);
        pw.setVisibility(View.GONE);

        noaddresstxt = (TextView) findViewById(R.id.select_address_text);


        noaddresstxt.setVisibility(View.GONE);
             layoutbraforelogin_in_address.setVisibility(View.GONE);
             layoutafterlogin_in_address.setVisibility(View.GONE);
             layoutwithnoaddress.setVisibility(View.GONE);

        checkinternettext.setVisibility(View.GONE);

        setlistenersonbutton();
     }


    @Override
    protected void onResume() {
        super.onResume();
         rowforaddress.removeAllViews();
        SugarRecord.deleteAll(Address_Table.class);
        checksession();
    }

    private void setlistenersonbutton() {


        backwhenusernotlogin.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                     finish();
                 }
             });
        backbuttonafterlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        backwithloginNoaddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        addaddresswhenLoginButNoAddressbtn.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                      Intent in = new Intent(AddressActivity.this , AddAddress.class);
                        startActivity(in);
                 }
             });


        addaddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(AddressActivity.this , AddAddress.class);
                startActivity(in);
              overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_right);
            }
        });


        orderasguestbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent in = new Intent(AddressActivity.this , PaymentActivity.class);
                in.putExtra("key","guest user");
                startActivity(in);
                overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_right);
            }
        });




        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(phoneedt.getText().toString().equals("")||passwordedt.getText().toString().equals("")){
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(parentlayout.getWindowToken(), 0);

                    Toast.makeText(AddressActivity.this , "Please Enter correct phone and password" ,Toast.LENGTH_SHORT).show();

                }else {

                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(parentlayout.getWindowToken(), 0);


                    userphonest = phoneedt.getText().toString();
                    userpasswordst = passwordedt.getText().toString();
                    sr = new StringRequest(Request.Method.GET, URLAPI.loginstart.concat(userphonest).concat(URLAPI.loginmid).concat(userpasswordst), new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            progressDialog.dismiss();
                            GsonBuilder gsonBuilder = new GsonBuilder();
                            Gson gson = gsonBuilder.create();
                            loginparseddata = gson.fromJson(response, LOGIN.class);

                            if(loginparseddata.result.equals("1")){
                                loginparseddataaftersuccess = gson.fromJson(response, WHEN_SUCCESSFULL_LOGIN.class);
                                Toast.makeText(AddressActivity.this, "Welcome " +loginparseddataaftersuccess.maf.first_name, Toast.LENGTH_SHORT).show();
                                new SessionManager(AddressActivity.this).createLoginSession(loginparseddataaftersuccess.maf.id, loginparseddataaftersuccess.maf.first_name, loginparseddataaftersuccess.maf.phone, loginparseddataaftersuccess.maf.email);
                                finish();
                                startActivity(starterIntent);
                            }else if(loginparseddata.result.equals("0")) {
                                fsp = gson.fromJson(response, FAILED_SIGN_UP.class);
                                Toast.makeText(AddressActivity.this , ""+fsp.message,Toast.LENGTH_SHORT).show();
                            }

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            progressDialog.dismiss();
                            Toast.makeText(AddressActivity.this, "Please Check Your internet Connection", Toast.LENGTH_SHORT).show();
                        }
                    });

                    queue.add(sr);
                    progressDialog = new ProgressDialog(AddressActivity.this);
                    progressDialog.setMessage("Loging in ..");
                    progressDialog.show();
                }
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in  = new Intent(AddressActivity.this , SignUpActivity.class);
                in.putExtra("fromwhichactivity","AddressActivity");
                    startActivity(in);
            }
        });

    }










    private void checksession() {

              if(new SessionManager(AddressActivity.this).cheskUseroginStatus()){


                  sr = new StringRequest(Request.Method.GET, URLAPI.viewaddress.concat(new SessionManager(AddressActivity.this).getUserDetails().get(SessionManager.KEY_UserId)), new Response.Listener<String>() {
                      @Override
                      public void onResponse(String response) {

                          pw.setVisibility(View.GONE);
                          checkinternettext.setVisibility(View.GONE);

                          if(!response.equals("null")){

                              GsonBuilder gsonBuilder = new GsonBuilder();
                              Gson gson = gsonBuilder.create();

                              addressresult = gson.fromJson(response, ADDRESS.class);

                              if(addressresult.result.equals("1")){
                                  addresssucessresult = gson.fromJson(response, WHEN_SUCCESSFULL_ADDRESS.class);

                                  addresslist = addresssucessresult.mav;

                                  SugarRecord.deleteAll(Address_Table.class);

                                  for(int i = 0 ; i<addresslist.size() ; i++){
                                      _id_of_address.add(addresssucessresult.mav.get(i).id);
                                      state.add(addresssucessresult.mav.get(i).state);
                                      area.add(addresssucessresult.mav.get(i).area);
                                      house_no.add(addresssucessresult.mav.get(i).houseno);
                                      street_no.add(addresssucessresult.mav.get(i).stree_no);
                                      address_name.add(addresssucessresult.mav.get(i).address_name);
                                      pin.add(addresssucessresult.mav.get(i).pin);
                                      landmark.add(addresssucessresult.mav.get(i).landmark);
                                  }

                                  for (int i = 0 ;i<landmark.size();i++){
                                      new Address_Table(_id_of_address.get(i),
                                              state.get(i),
                                              area.get(i),
                                              house_no.get(i),
                                              street_no.get(i),
                                              address_name.get(i),
                                              pin.get(i),
                                              landmark.get(i)).save();
                                  }
                                  pw.setVisibility(View.GONE);
                                  checkinternettext.setVisibility(View.GONE);

                                  layoutafterlogin_in_address.setVisibility(View.VISIBLE);
                                  layoutwithnoaddress.setVisibility(View.GONE);
                                  loadaddressinlist();

                              }else if(addressresult.result.equals("0")){
                                  layoutafterlogin_in_address.setVisibility(View.GONE);
                                  layoutwithnoaddress.setVisibility(View.VISIBLE);
                              }
                          }else {
                              Toast.makeText(AddressActivity.this , "Problem occured at server side " , Toast.LENGTH_SHORT).show();
                          }
                      }
                  }, new Response.ErrorListener() {
                      @Override
                      public void onErrorResponse(VolleyError error) {
                                checkinternettext.setVisibility(View.VISIBLE);
                          Toast.makeText(AddressActivity.this, "Please check your internet connection " , Toast.LENGTH_SHORT).show();
                      }
                  });

                  queue.add(sr);
                 pw.setVisibility(View.VISIBLE);
              }else {
                  layoutbraforelogin_in_address.setVisibility(View.VISIBLE);
              }
          }











    private void loadaddressinlist() {

        List<Address_Table> templist = Address_Table.listAll(Address_Table.class);
        for(int i =0 ; i<templist.size();i++){
            rowforaddress.addView(triamethod(R.layout.item_for_viewaddress ,
                    templist.get(i).state ,
                    templist.get(i).area,
                    templist.get(i).houseno ,
                    templist.get(i).stree_no,
                    templist.get(i).address_name ,
                    templist.get(i).id_of_particular_address,
                    templist.get(i).pin ,
                    templist.get(i).landmark ));

            SugarRecord.deleteAll(Address_Table.class);
        }

    }














    private View triamethod(int layout_name , String s , String a , String h ,String sn , String addna , String id , String pi , String landmk) {
        LayoutInflater layoutInflater =
                (LayoutInflater)AddressActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View addView = layoutInflater.inflate(layout_name, null);
        final TextView st = (TextView) addView.findViewById(R.id.stateinaddresslayout);
        final TextView  ar = (TextView) addView.findViewById(R.id.areainaddresslayout);
        final TextView hs = (TextView) addView.findViewById(R.id.houseinaddresslayout);
        final TextView stno = (TextView) addView.findViewById(R.id.streetno_inaddresslayout);
        final TextView  addnam = (TextView) addView.findViewById(R.id.addressname_inaddresslayout);
        final TextView pint = (TextView) addView.findViewById(R.id.pin_inaddresslayout);
        final TextView landt = (TextView) addView.findViewById(R.id.landmark_inaddresslayout);

        final TextView idd = (TextView) addView.findViewById(R.id.idinaddresslayout);

        ImageView edit = (ImageView) addView.findViewById(R.id.pencilinviewaddress);
        ImageView delete = (ImageView) addView.findViewById(R.id.dustbininviewaddress);


        st.setText(s);
        ar.setText(a);
        hs.setText(h);
        stno.setText(sn);
        addnam.setText(addna);
        pint.setText(pi);
        landt.setText(landmk);
        idd.setText(id);







            if(ssss.equals("cartactivity")){
                noaddresstxt.setVisibility(View.VISIBLE);
                addView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent in = new Intent(AddressActivity.this , PaymentActivity.class);
                        in.putExtra("key" , "Address");
                        in.putExtra("statekey",st.getText().toString());
                        in.putExtra("areakey", ar.getText().toString());
                        in.putExtra("housenokey", hs.getText().toString());
                        in.putExtra("streetnokey",stno.getText().toString());
                        in.putExtra("addressnamekey", addnam.getText().toString());
                        in.putExtra("pinkey", pint.getText().toString());
                        in.putExtra("landmarkkey", landt.getText().toString());
                        in.putExtra("idofaddresskey",idd.getText().toString());

                        startActivity(in);
                        overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_right);
                        finish();
                    }
                });
            }




        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                refid  = idd.getText().toString();
                deleaddress_url = "http://spinnosolutions.com/ecommerce_api/api/dele_address.php?id=".concat(refid);
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        AddressActivity.this);

                // set title
                alertDialogBuilder.setTitle("delete Address");

                // set dialog message
                alertDialogBuilder
                        .setMessage("Are you sure you want to remove this address")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // if this button is clicked, close
                                // current activity
                                if (new NetworkChecker().isNetworkConnected(AddressActivity.this) == true) {
                                   deleleteaddress();
                                } else {
                                    Toast.makeText(AddressActivity.this, " check your Internet connection", Toast.LENGTH_SHORT).show();
                                }
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // if this button is clicked, just close
                                // the dialog box and do nothing
                                dialog.cancel();
                            }
                        });

                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();

                // show it
                alertDialog.show();
            }
        });







        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(AddressActivity.this , Edit_Address_Activity.class);
                in.putExtra("fromwhichscreen" , "AddressFragment");
                in.putExtra("statekey",st.getText().toString());
                in.putExtra("areakey", ar.getText().toString());
                in.putExtra("housenokey", hs.getText().toString());
                in.putExtra("streetnokey",stno.getText().toString());
                in.putExtra("addressnamekey", addnam.getText().toString());
                in.putExtra("pinkey", pint.getText().toString());
                in.putExtra("landmarkkey", landt.getText().toString());
                in.putExtra("idofaddresskey",idd.getText().toString());

                startActivity(in);
            }
        });

               return  addView ;
    }













    public void deleleteaddress(){
        Toast.makeText(AddressActivity.this , "deleting this id "+refid ,Toast.LENGTH_SHORT).show();
        sr = new StringRequest(Request.Method.GET, deleaddress_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                pw.setVisibility(View.GONE);
                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson = gsonBuilder.create();
                postsobjj = gson.fromJson(response, REMOVE_ADDRESS.class);

                if(postsobjj.response.equals("1")){
                    Toast.makeText(AddressActivity.this,""+postsobjj.message, Toast.LENGTH_SHORT).show();
                         finish();
                          startActivity(getIntent());
                }if(postsobjj.response.equals("0")){
                    Toast.makeText(AddressActivity.this,""+postsobjj.message, Toast.LENGTH_SHORT).show();
                    finish();
                    startActivity(getIntent());
                } else {
                    Toast.makeText(AddressActivity.this,"Problem occurd with the server ", Toast.LENGTH_SHORT).show();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pw.setVisibility(View.GONE);
                Toast.makeText(AddressActivity.this, "Please  check your internet connection ", Toast.LENGTH_SHORT).show();
            }
        });

        queue.add(sr);
        pw.setVisibility(View.VISIBLE);
    }

}
