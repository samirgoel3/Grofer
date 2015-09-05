package com.spinno.buzczar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class RecordNotFoundActivity extends Activity {

    Button changelocality ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_not_found);

        changelocality = (Button) findViewById(R.id.change_locality_button_in_no_record_activity);

           changelocality.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   Intent in = new Intent(RecordNotFoundActivity.this , AddressFinderActivity.class);
                       startActivity(in);
                      finish();
               }
           });
    }

}
