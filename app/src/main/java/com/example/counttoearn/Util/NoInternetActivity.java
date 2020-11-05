package com.example.counttoearn.Util;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.counttoearn.R;
import com.google.android.material.snackbar.Snackbar;

public class NoInternetActivity extends AppCompatActivity {

    TextView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_internet);
        back = findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Util.isInternetConnected(NoInternetActivity.this)) {

                    onBackPressed();

                } else {

                    Snackbar snackbar =  Snackbar.make(findViewById(android.R.id.content),"Please on internet connection",Snackbar.LENGTH_LONG);
                    snackbar.show();
                    return;
                }
            }
        });

    }
    @Override
    public void onBackPressed() {
        setResult(RESULT_OK);
        super.onBackPressed();
    }

}
