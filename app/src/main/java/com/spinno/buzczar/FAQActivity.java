package com.spinno.buzczar;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
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

import apiurls.URLAPI;
import pojo.QAResponse;
import pojo.RESULT;
import singleton.VolleySingleton;

public class FAQActivity extends Activity {

    TextView question , answer ;
    LinearLayout qalayout  , noquestionlayout;
    StringRequest sr ;
    RequestQueue queue ;
    ProgressDialog progressDialog;

    RESULT rspojo  = new RESULT() ;
    QAResponse qares = new QAResponse();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq);
        queue = VolleySingleton.getInstance(FAQActivity.this).getRequestQueue();

        question = (TextView) findViewById(R.id.questoin_in_faq_activity);
        answer = (TextView) findViewById(R.id.answer_in_faq_activity);
        qalayout = (LinearLayout) findViewById(R.id.qa_layout);
        noquestionlayout = (LinearLayout) findViewById(R.id.no_question_layout);
        qalayout.setVisibility(View.GONE);
        noquestionlayout.setVisibility(View.GONE);




        loaddata();
    }

    private void loaddata() {
        sr = new StringRequest(Request.Method.GET, URLAPI.faq, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson = gsonBuilder.create();
                rspojo = gson.fromJson(response, RESULT.class);
                if(rspojo.result.equals("1")){
                    qalayout.setVisibility(View.VISIBLE);
                    noquestionlayout.setVisibility(View.GONE);
                    qares = gson.fromJson(response, QAResponse.class);
                    question.setText(qares.qa.Question);
                    answer.setText(qares.qa.Answer);
                }else if(rspojo.result.equals("0")) {
                    qalayout.setVisibility(View.GONE);
                    noquestionlayout.setVisibility(View.VISIBLE);
                   Toast.makeText(FAQActivity.this,"doest contain data " ,Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(FAQActivity.this, "Check your internet connection" , Toast.LENGTH_SHORT).show();
            }
        });


        queue.add(sr);
        progressDialog = new ProgressDialog(FAQActivity.this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
    }

}
