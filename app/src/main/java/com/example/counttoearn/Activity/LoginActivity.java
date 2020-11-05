package com.example.counttoearn.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.counttoearn.Model.LoginModel;
import com.example.counttoearn.MyPrefrence;
import com.example.counttoearn.R;
import com.example.counttoearn.Service.RestClient;
import com.example.counttoearn.Util.NoInternetActivity;
import com.example.counttoearn.Util.Util;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.rilixtech.widget.countrycodepicker.Country;
import com.rilixtech.widget.countrycodepicker.CountryCodePicker;

import java.util.concurrent.TimeUnit;

import in.aabhasjindal.otptextview.OtpTextView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    Button btn_singin;
    EditText et_mobile;
    String smobilenumber;
    Dialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        try {
            btn_singin = findViewById(R.id.btn_singin);
            et_mobile = findViewById(R.id.et_mobiles);

            //stop  roted
            setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

            if (MyPrefrence.getulogin()) {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }

            et_mobile.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                    smobilenumber = et_mobile.getText().toString();


                    if (smobilenumber.length() == 10) {

                        btn_singin.setEnabled(true);
                    }


                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

            btn_singin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    login(LoginActivity.this, smobilenumber);


                }


            });

        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    /*LOGIN API CALL*/
    public void login(Context context, String smobilenumber) {


        if (!Util.isInternetConnected(this)) {

            Intent intent = new Intent(LoginActivity.this, NoInternetActivity.class);
            startActivity(intent);

            return;
        }
        dialog = Util.startLoader(context);

        new RestClient(this).getInstance().get().getlogin(smobilenumber).enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                Util.stopLoader(dialog);

                if (response.body() != null) {

                    MyPrefrence.setname(response.body().data.name);
                    MyPrefrence.setcountrycode(response.body().data.country_code);
                    MyPrefrence.setreferralcode(response.body().data.referral_code);
                    MyPrefrence.setmobile(response.body().data.mobile);
                    MyPrefrence.setuserid(response.body().data.id);
                    MyPrefrence.setgamelevel(response.body().data.level);

                    btn_singin.setEnabled(true);

                    startActivity(new Intent(LoginActivity.this, OtpLoginActivity.class));
                    MyPrefrence.setactivitycall("1");
                } else {

                    et_mobile.setError("Invalid Mobile Number");


                }

            }

            @Override
            public void onFailure(Call<LoginModel> call, Throwable t) {
                Util.stopLoader(dialog);

            }
        });


    }

    public void singup(View view) {
        startActivity(new Intent(LoginActivity.this, SingUpActivity.class));
    }

}
