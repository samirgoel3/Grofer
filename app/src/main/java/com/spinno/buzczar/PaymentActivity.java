package com.spinno.buzczar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.orm.SugarRecord;
import com.spinno.buzczar.time.RangeTimePickerDialog;
import com.spinno.buzczar.time.TimeCalculatorSamir;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import apiurls.URLAPI;
import cn.pedant.SweetAlert.SweetAlertDialog;
import networkchecker.NetworkChecker;
import pojo.PLACE_ORDER;
import pojo.TIMING;
import sessionmanager.LocationSesionManager;
import sessionmanager.SessionManager;
import singleton.VolleySingleton;


public class PaymentActivity extends Activity {

    TextView  tosavedetailstext  , totalamounttobepaid  , deliverydatetext ,deliverytimetext ;

    EditText customername , customerphone , customeemail ,
            statee , areanamee , housenoee , strbldne , addnamee ,
            pine , landmarke   ;
    Button paybutton  , jumptoaddAddressActivity , editaddress;


    String dateofsystem  , timeofsystem , userid , userloginstatus ,
            finalorderurl ,stateget , areanameget , housenoget , streetnoget ,
            homeaddressget ,pinnoget , landmarkget, customernameget , customerphoneget ,
            customeralterphoneget  ,restUrl , time_from ,time_to , min_interval  ,Gap_Time ;

    String orderurlstart = "http://spinnosolutions.com/ecommerce_api/api/orders.php?user_id=" ;

    PLACE_ORDER placeorderpost = new PLACE_ORDER();    TIMING tm = new TIMING();





    View noaddress_found_frame, add_address_layout_frame ;
    SessionManager sm ;
    LocationSesionManager lsm ;
    LinearLayout progresslayout  , main_layout  , Deliverydate_layout ,DeliveryTime_layout;

    StringRequest sr ;
    RequestQueue queue ;
    ProgressDialog progress;



    private DatePicker datePicker;
    private Calendar calendar;
    private int year, month, day;
    private int hour , minute;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        sm = new SessionManager(getApplicationContext());
        lsm = new LocationSesionManager(getApplicationContext());
        queue = VolleySingleton.getInstance(PaymentActivity.this).getRequestQueue();


        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        hour = calendar.get(Calendar.HOUR_OF_DAY);
        minute = calendar.get(Calendar.MINUTE);

        Intent in = getIntent();
        String s = in.getExtras().getString("key" , "default");




        List<Address_Table> tablesize = Address_Table.listAll(Address_Table.class);


        noaddress_found_frame =  findViewById(R.id.no_address_found_in_payment_form_activity);
        add_address_layout_frame =  findViewById(R.id.add_address_layout_frame_in_payment_form_activity);

        tosavedetailstext = (TextView) findViewById(R.id.tosaveyoudetails_in_payment_form_activity);
        totalamounttobepaid = (TextView) findViewById(R.id.total_amount_in_payment_form_activity);
        paybutton = (Button) findViewById(R.id.final_pay_in_payment_form_activity);
        jumptoaddAddressActivity = (Button) findViewById(R.id.add_address_in_payment_form_activity_jump_to_next_activity);
        editaddress = (Button) findViewById(R.id.edit_address_paymnet_form);


        customername = (EditText) findViewById(R.id.username_in_payment_form_activity);
        customerphone = (EditText) findViewById(R.id.phone_in_payment_form_activity);
        customeemail = (EditText) findViewById(R.id.email_in_payment_form_activity);

        progresslayout = (LinearLayout) findViewById(R.id.progress_layout_in_payment_activity);
        main_layout = (LinearLayout) findViewById(R.id.main_layout_in_payment_activity);
        Deliverydate_layout = (LinearLayout) findViewById(R.id.delivery_date_layout_in_place_order_activity);
        DeliveryTime_layout = (LinearLayout) findViewById(R.id.delivery_time_layout_in_place_order_activity);

        deliverydatetext = (TextView) findViewById(R.id.delivery_date_text);
        deliverytimetext = (TextView) findViewById(R.id.delivery_time_text);


        editaddress.setVisibility(View.GONE);

        progresslayout.setVisibility(View.GONE);
        main_layout.setVisibility(View.GONE);

        loadtiming();



        statee = (EditText) findViewById(R.id.editstate);
        areanamee = (EditText) findViewById(R.id.editarea);
        housenoee = (EditText) findViewById(R.id.edithousenumber);
        strbldne = (EditText) findViewById(R.id.editstreetno);
        addnamee = (EditText) findViewById(R.id.edithomeaddress);
        pine = (EditText) findViewById(R.id.pin);
        landmarke = (EditText) findViewById(R.id.landmark);

        statee.setEnabled(false);
        areanamee.setEnabled(false);

        areanamee.setText(lsm.getlocation().get(LocationSesionManager.KEY_LOCATION_NAME));
        statee.setText(lsm.getcity().get(LocationSesionManager.KEY_CITY_NAME));





        paybleAmount();

        Log.d("User loged in ", sm.getUserDetails().get(SessionManager.KEY_UserName));
        Log.d("login status ", "" + sm.cheskUseroginStatus());














        if(!sm.getUserDetails().get(SessionManager.KEY_UserName).equals("guest user")){
            Log.d("under the details condition","under the details condition");

            tosavedetailstext.setVisibility(View.GONE);
            noaddress_found_frame.setVisibility(View.GONE);
            add_address_layout_frame.setVisibility(View.GONE);
            customerphone.setEnabled(false);
            customerphone.setText(sm.getUserDetails().get(SessionManager.KEY_UsephoneNumber));
            customeemail.setText(sm.getUserDetails().get(SessionManager.KEY_UserEmail));


            statee.setEnabled(false);
            areanamee.setEnabled(false);
            housenoee.setEnabled(false);
            strbldne.setEnabled(false);
            addnamee.setEnabled(false);
            pine.setEnabled(false);
            landmarke.setEnabled(false);
            customeemail.setEnabled(false);
            ///////////  check that adress is exist of not ////////////////

            if(s.equals("guest user")){
                //       Toast.makeText(PayementFormActivity.this , "From main activity " , Toast.LENGTH_SHORT).show();
                if(tablesize.size() >0){
                    editaddress.setVisibility(View.VISIBLE);
                    add_address_layout_frame.setVisibility(View.VISIBLE);
                    statee.setText(tablesize.get(0).state);
                    areanamee.setText(tablesize.get(0).area);
                    housenoee.setText(tablesize.get(0).houseno);
                    strbldne.setText(tablesize.get(0).stree_no);
                    addnamee.setText(tablesize.get(0).address_name);
                    pine.setText(tablesize.get(0).pin);
                    landmarke.setText(tablesize.get(0).landmark);

                    editaddress.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent in  =  new Intent(PaymentActivity.this ,AddressActivity.class);
                            startActivity(in);
                        }
                    });

                }else{
                    noaddress_found_frame.setVisibility(View.VISIBLE);
                    //// button of add new address , new activity
                    jumptoaddAddressActivity.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent in = new Intent(PaymentActivity.this , AddressActivity.class);
                            startActivity(in);
                        }
                    });
                }
            }else if (s.equals("Address")){
                editaddress.setVisibility(View.VISIBLE);
                add_address_layout_frame.setVisibility(View.VISIBLE);

                if(!in.getExtras().getString("statekey").equals(lsm.getlocation().get(LocationSesionManager.KEY_CITY_NAME))){
                }

                    statee.setText(in.getExtras().getString("statekey"));
                    areanamee.setText(in.getExtras().getString("areakey"));
                    housenoee.setText(in.getExtras().getString("housenokey"));
                    strbldne.setText(in.getExtras().getString("streetnokey"));
                    addnamee.setText(in.getExtras().getString("addressnamekey"));
                    pine.setText(in.getExtras().getString("pinkey"));
                    landmarke.setText(in.getExtras().getString("landmarkkey"));


                editaddress.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent in  =  new Intent(PaymentActivity.this ,AddressActivity.class);
                        startActivity(in);
                        finish();
                    }
                });

            }
        }


        else {
            noaddress_found_frame.setVisibility(View.GONE);

        }




////////////////// Delivery Date
        Deliverydate_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(1111);
            }
        });


//////////////////////////////////// Delivery Time

        DeliveryTime_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(2222);
            }
        });




        paybutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ActivityIdentifire.cartactivity = 0 ;

                if (customername.getText().toString().equals("")
                        || customerphone.getText().toString().equals("")
                        || statee.getText().toString().equals("")
                        || areanamee.getText().toString().equals("")
                        || housenoee.getText().toString().equals("")
                        || strbldne.getText().toString().equals("")
                        || customeemail.getText().toString().equals("")
                        || deliverytimetext.getText().toString().equals("Delivery Time")
                        ||deliverydatetext.getText().toString().equals("Delivery Date")) {


                    Toast.makeText(PaymentActivity.this, "Please enter the missing fields", Toast.LENGTH_SHORT).show();

                } else {
                      if(checkEmailCorrect(customeemail.getText().toString())){



                        /////////////////////////// if user order as login user / /////////////////////////////////////////
                        if (!sm.getUserDetails().get(SessionManager.KEY_UserName).equals("guest user")) {
                            userid = sm.getUserDetails().get(SessionManager.KEY_UserId);
                            userloginstatus = "1";
                            if (customername.getText().length() == 0) {
                                customernameget = "----------";
                            } else {
                                customernameget = customername.getText().toString();
                            }

                            if (customerphone.getText().length() == 0) {
                                customerphoneget = "----------";
                            } else {
                                customerphoneget = customerphone.getText().toString();
                            }

                            if (customeemail.getText().length() == 0) {
                                customeralterphoneget = "----------";
                            } else {
                                customeralterphoneget = customeemail.getText().toString();
                            }

                            if (statee.getText().length() == 0) {
                                stateget = "----------";
                            } else {
                                stateget = statee.getText().toString();
                            }

                            if (areanamee.getText().length() == 0) {
                                areanameget = "----------";
                            } else {
                                areanameget = areanamee.getText().toString();
                            }

                            if (housenoee.getText().length() == 0) {
                                housenoget = "----------";
                            } else {
                                housenoget = housenoee.getText().toString();
                            }

                            if (strbldne.getText().length() == 0) {
                                streetnoget = "----------";
                            } else {
                                streetnoget = strbldne.getText().toString();
                            }

                            if (addnamee.getText().length() == 0) {
                                homeaddressget = "----------";
                            } else {
                                homeaddressget = addnamee.getText().toString();
                            }

                            if (pine.getText().length() == 0) {
                                pinnoget = "----------";
                            } else {
                                pinnoget = pine.getText().toString();
                            }


                            if (landmarke.getText().length() == 0) {
                                landmarkget = "----------";
                            } else {
                                landmarkget = landmarke.getText().toString();
                            }
                        }


                        /////////////////////////// if user order as guest user / /////////////////////////////////////////
                        else if (sm.getUserDetails().get(SessionManager.KEY_UserName).equals("guest user")) {
                            userid = " ";
                            userloginstatus = "0";
                            if (customername.getText().length() == 0) {
                                customernameget = "----------";
                            } else {
                                customernameget = customername.getText().toString();
                            }

                            if (customerphone.getText().length() == 0) {
                                customerphoneget = "----------";
                            } else {
                                customerphoneget = customerphone.getText().toString();
                            }

                            if (customeemail.getText().length() == 0) {
                                customeralterphoneget = "----------";
                            } else {
                                customeralterphoneget = customeemail.getText().toString();
                            }

                            if (statee.getText().length() == 0) {
                                stateget = "----------";
                            } else {
                                stateget = statee.getText().toString();
                            }

                            if (areanamee.getText().length() == 0) {
                                areanameget = "----------";
                            } else {
                                areanameget = areanamee.getText().toString();
                            }

                            if (housenoee.getText().length() == 0) {
                                housenoget = "----------";
                            } else {
                                housenoget = housenoee.getText().toString();
                            }

                            if (strbldne.getText().length() == 0) {
                                streetnoget = "----------";
                            } else {
                                streetnoget = strbldne.getText().toString();
                            }

                            if (addnamee.getText().length() == 0) {
                                homeaddressget = "----------";
                            } else {
                                homeaddressget = addnamee.getText().toString();
                            }

                            if (pine.getText().length() == 0) {
                                pinnoget = "----------";
                            } else {
                                pinnoget = pine.getText().toString();
                            }


                            if (landmarke.getText().length() == 0) {
                                landmarkget = "----------";
                            } else {
                                landmarkget = landmarke.getText().toString();
                            }
                        }


                        //////// prepraring script for product //////////////////////////////
                        List<Producttable> productttct = Producttable.listAll(Producttable.class);
                        JSONArray myarray = new JSONArray();


                        for (int i = 0; i < productttct.size(); i++) {
                            try {
                                JSONObject j = new JSONObject();
                                j.put("product_id", productttct.get(i).getProductId());
                                j.put("price", productttct.get(i).getSalePrice());
                                j.put("product_name", productttct.get(i).getProductName());
                                j.put("product_image", productttct.get(i).getProductimage());
                                j.put("no_of_unit_booked", productttct.get(i).getNumberOfUnits());
                                j.put("unit", productttct.get(i).getNumberOfUnits());
                                j.put("unit_type", productttct.get(i).getUnitType());

                                myarray.put(j);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        try {
                            restUrl = URLEncoder.encode(myarray.toString(), "UTF-8");
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }

                          Log.e("jaosn script " ,myarray.toString());


                          Calendar c = Calendar.getInstance();

                        SimpleDateFormat dfdate = new SimpleDateFormat("dd MMM yyyy");
                        SimpleDateFormat dftime = new SimpleDateFormat("HH:mm:ss");

                        dateofsystem = dfdate.format(c.getTime());
                        timeofsystem = dftime.format(c.getTime());


//                          Log.e("jaosn script " ,restUrl);

                        finalorderurl = orderurlstart.concat(userid)
                                .concat("&user_name=").concat(customernameget)
                                .concat("&phone=").concat(customerphoneget)
                                .concat("&email=").concat(customeralterphoneget)
                                .concat("&state=").concat(stateget)
                                .concat("&area_name=").concat(areanameget)
                                .concat("&house_number=").concat(housenoget)
                                .concat("&street_number=").concat(streetnoget)
                                .concat("&pin=").concat(pinnoget)
                                .concat("&address=").concat(homeaddressget)
                                .concat("&landmark=").concat(landmarkget)
                                .concat("&product_detail=").concat(restUrl)
                                .concat("&time_of_delivery=").concat(deliverytimetext.getText().toString())
                                .concat("&date_of_delivery=").concat(deliverydatetext.getText().toString())
                                .concat("&guest_or_login=").concat(userloginstatus)
                                .concat("&city_id=").concat(lsm.getcity().get(LocationSesionManager.KEY_CITY_ID))
                                .concat("&location_id=").concat(lsm.getlocation().get(LocationSesionManager.KEY_LOCATION_ID));
                           Log.e("link before placing order" ,finalorderurl);

                        finalorderurl = finalorderurl.replace(" ", "%20");
                        Log.e("link execute for placing order " ,finalorderurl);

                        if (new NetworkChecker().isNetworkConnected(PaymentActivity.this) == true) {

                            postorder();

                        } else {
                            Toast.makeText(PaymentActivity.this, "No  Internet Connection ", Toast.LENGTH_SHORT).show();
                        }
                    }else {
                          Toast.makeText(PaymentActivity.this , "Email is invalid" , Toast.LENGTH_SHORT).show();
                      }

            }
             }
        });

    }





    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case 1111:
                DatePickerDialog dpd = new DatePickerDialog(this, pickerListener,year,month,day);
                try {
                   // Toast.makeText(PaymentActivity.this , ""+TimeCalculatorSamir.validdate() ,Toast.LENGTH_SHORT).show();
                    if(TimeCalculatorSamir.validdate()){
                        dpd.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                    }else {
                        GregorianCalendar gc = new GregorianCalendar();
                        gc.add(Calendar.DATE, 1);
                        dpd.getDatePicker().setMinDate(gc.getTime().getTime());
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                return dpd;
            case 2222:
             //   TimePickerDialog tdp = new TimePickerDialog(this, timePickerListener, hour, minute, false);
                RangeTimePickerDialog btp = new RangeTimePickerDialog(this, timePickerListener, hour, minute, false);

                try {
                    if(TimeCalculatorSamir.isValidTime()){
                        Toast.makeText(PaymentActivity.this , ""+true  , Toast.LENGTH_SHORT).show();
                        Calendar now = Calendar.getInstance();
                        now.add(Calendar.MINUTE,TimeCalculatorSamir.time_intervalmin());
                        btp.setMin(now.get(Calendar.HOUR ),now.get(Calendar.MINUTE));
                    }else {
                        Toast.makeText(PaymentActivity.this , ""+false  , Toast.LENGTH_SHORT).show();
                        btp.setMin(TimeCalculatorSamir.from_hours(), TimeCalculatorSamir.from_minutes());
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }



                return btp;
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener pickerListener = new DatePickerDialog.OnDateSetListener() {

        // when dialog box is closed, below method will be called.
        @Override
        public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {

            year  = selectedYear;
            month = selectedMonth;
            day   = selectedDay;
            deliverydatetext.setText(""+day+"/"+month+"/"+year);
        }
    };

    private TimePickerDialog.OnTimeSetListener timePickerListener = new TimePickerDialog.OnTimeSetListener() {


        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minutes) {
            // TODO Auto-generated method stub
            hour   = hourOfDay;
            minute = minutes;

            deliverytimetext.setText(""+hour+":"+minute);
        }

    };

    private void loadtiming() {
        sr = new StringRequest(Request.Method.GET, URLAPI.timeFrom_To, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progresslayout.setVisibility(View.GONE);
                if(!response.equals("null")){
                    GsonBuilder gsonBuilder = new GsonBuilder();
                    Gson gson = gsonBuilder.create();
                    tm = gson.fromJson(response, TIMING.class);
                    TimeCalculatorSamir.Time_From = tm.tp.working_time_from;
                    TimeCalculatorSamir.Time_To  = tm.tp.working_time_to;
                    TimeCalculatorSamir.Time_Interval = tm.tp.delivery_minutes;

                    main_layout.setVisibility(View.VISIBLE);
                }else {
                    Toast.makeText(PaymentActivity.this , "Problem occured at server side " , Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progress.dismiss();
                Toast.makeText(PaymentActivity.this, "No internet connection available", Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(sr);
        progresslayout.setVisibility(View.VISIBLE);
    }

    boolean checkEmailCorrect(String Email) {
        String pttn = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        Pattern p = Pattern.compile(pttn);
        Matcher m = p.matcher(Email);

        if (m.matches()) {
            customeemail.setError(null);
            return true;
        }
        customeemail.setError("Email is not valid");
        return false;
    }


    private void postorder() {

        sr = new StringRequest(Request.Method.GET, finalorderurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progress.dismiss();

                if(!response.equals("null")){
                    GsonBuilder gsonBuilder = new GsonBuilder();
                    Gson gson = gsonBuilder.create();
                    placeorderpost = gson.fromJson(response, PLACE_ORDER.class);
                    if(placeorderpost.result.equals("1")){
                        SugarRecord.deleteAll(Producttable.class);

                        new SweetAlertDialog(PaymentActivity.this, SweetAlertDialog.WARNING_TYPE)
                                .setTitleText("Order successfuly placed")
                                .setContentText("Click Continue to shop more")
                                .setConfirmText("Continue")
                                .setCancelText("Exit")
                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sDialog) {
                                        sDialog.dismiss();
                                        Intent loginIntent = new Intent(PaymentActivity.this, MainActivity.class);
                                        loginIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(loginIntent);
                                        finish();
                                    }
                                })
                                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                                        sweetAlertDialog.dismiss();
                                        finish();

                                    }
                                })
                                .show();
                    }else if(placeorderpost.result.equals("0")) {
                        Toast.makeText(PaymentActivity.this , ""+placeorderpost.msg , Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(PaymentActivity.this , "Problem occured at server side " , Toast.LENGTH_SHORT).show();

                }



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progress.dismiss();
                Toast.makeText(PaymentActivity.this, "No internet connection available", Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(sr);
        progress = ProgressDialog.show(PaymentActivity.this, "",
                "Sending your order details...", true);
    }


    private void paybleAmount() {
        int temp1 , temp2  ,multiplier, result_to_show_on_cart =0 ;
        List<Producttable> templist = Producttable.listAll(Producttable.class);

        for(int i = 0 ; i<templist.size() ; i++){
            temp1 = Integer.parseInt(templist.get(i).getSalePrice());
            temp2 = templist.get(i).getNumberOfUnits();
            multiplier = temp1 * temp2 ;
            result_to_show_on_cart = result_to_show_on_cart +multiplier ;
        }
        totalamounttobepaid.setText(""+result_to_show_on_cart);
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
