package com.spinno.buzczar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import networkchecker.NetworkChecker;


public class NoInternetActivity extends Activity {

    Button retrybtn ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_internet);

        retrybtn = (Button) findViewById(R.id.retrybtn);

        retrybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(new NetworkChecker().isNetworkConnected(NoInternetActivity.this)){
                    Intent in = new Intent(NoInternetActivity.this , LocatoinActivity.class);
                      startActivity(in);
                      finish();
                }
            }
        });
    }
}
