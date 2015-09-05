package com.spinno.buzczar;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
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
import java.util.List;

import adapters.OrderAdapter;
import apiurls.URLAPI;
import pojo.AFTER_ORDER_RESPINSE;
import pojo.CHECK_ORDER_RESPOPNSE;
import pojo.PREVIOUS_ORDERS;
import pojo.PRODUCT_RECEIVED_IN_PREVOIUS_ORDERS;
import sessionmanager.SessionManager;
import singleton.VolleySingleton;


public class MyPreviousOrdersActivity extends Activity {


    ListView orderlist ;
    TextView no_order_found ;
    ImageView addusericon  , backbutton ;

    StringRequest sr ;
    RequestQueue queue ;

    String userid ;

    ProgressDialog progressDialog;

    CHECK_ORDER_RESPOPNSE cor = new CHECK_ORDER_RESPOPNSE();
    AFTER_ORDER_RESPINSE aor = new AFTER_ORDER_RESPINSE();

    List<PREVIOUS_ORDERS> po = new ArrayList<>();
    ///////// Attribute that i will send to the adapter //////////
    List<String> order_status  = new ArrayList<>();
    List<String> timing_of_order = new ArrayList<>();
    List<String> date_of_order = new ArrayList<>();
    List <String> order_id = new ArrayList<>();
    List <String> status = new ArrayList<>();


    ////////// list for receiving all products in one order  ///////////
    List<List<PRODUCT_RECEIVED_IN_PREVOIUS_ORDERS>> prepo = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_previous_orders);

        queue = VolleySingleton.getInstance(MyPreviousOrdersActivity.this).getRequestQueue();


        orderlist = (ListView) findViewById(R.id.listview_in_order_fragment);
        no_order_found = (TextView) findViewById(R.id.no_order_foundin_my_order_fragment);
        addusericon = (ImageView) findViewById(R.id.add_user_icon);
        backbutton = (ImageView) findViewById(R.id.back_button_in_myprevious_orders);

        no_order_found.setVisibility(View.GONE);
        addusericon.setVisibility(View.GONE);
              checkloginlogoutstatus();


        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

    private void checkloginlogoutstatus() {
        userid = new SessionManager(MyPreviousOrdersActivity.this).getUserDetails().get(SessionManager.KEY_UserId);
        if (userid.equals("koi value nahi hai")||userid.equals("guest user")){
            no_order_found.setVisibility(View.VISIBLE);
            addusericon.setVisibility(View.VISIBLE);
            no_order_found.setText("You need to Create Account to view your previous orders ");
        }else{
                   no_order_found.setVisibility(View.GONE);
                   addusericon.setVisibility(View.GONE);


            sr = new StringRequest(Request.Method.GET, URLAPI.previousorders.concat(userid), new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    progressDialog.dismiss();
                    GsonBuilder gsonBuilder = new GsonBuilder();
                    Gson gson = gsonBuilder.create();
                    cor = gson.fromJson(response, CHECK_ORDER_RESPOPNSE.class);
                    if(cor.result.equals("1")){
                        aor = gson.fromJson(response, AFTER_ORDER_RESPINSE.class);
                     //   Toast.makeText(MyPreviousOrdersActivity.this, "" + aor.message.get(0).order_id, Toast.LENGTH_SHORT).show();
                        po = aor.message;
                        for(int i = 0 ; i<po.size() ; i++){
                            timing_of_order.add(po.get(i).ordering_time);
                            date_of_order.add(po.get(i).ordering_date);
                            order_status.add(po.get(i).order_status);
                            order_id.add(po.get(i).order_id);
                            prepo.add(po.get(i).product_detail);
                            status.add(po.get(i).status);
                        }
                        orderlist.setAdapter(new OrderAdapter(MyPreviousOrdersActivity.this , order_status , timing_of_order , date_of_order , order_id  , prepo , status ));
                    }else {
                        no_order_found.setVisibility(View.VISIBLE);
                        no_order_found.setText("No previous Orders");
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progressDialog.dismiss();
                    Toast.makeText(MyPreviousOrdersActivity.this, "Please check your internet connection", Toast.LENGTH_SHORT).show();
                }
            });
            sr.setRetryPolicy(new DefaultRetryPolicy(
                    2500,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            queue.add(sr);
            progressDialog = new ProgressDialog(MyPreviousOrdersActivity.this);
            progressDialog.setMessage("Looking for previous orders ..");
            progressDialog.show();
        }
    }
}




