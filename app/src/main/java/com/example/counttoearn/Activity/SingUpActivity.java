package com.example.counttoearn.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.counttoearn.Model.CommonModel;
import com.example.counttoearn.Model.ReferralCodeModel;
import com.example.counttoearn.Model.SingupModal;
import com.example.counttoearn.MyPrefrence;
import com.example.counttoearn.R;
import com.example.counttoearn.Service.RestClient;
import com.example.counttoearn.Util.NoInternetActivity;
import com.example.counttoearn.Util.Util;
import com.rilixtech.widget.countrycodepicker.Country;
import com.rilixtech.widget.countrycodepicker.CountryCodePicker;

import in.aabhasjindal.otptextview.OtpTextView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SingUpActivity extends AppCompatActivity {

    EditText et_name, et_mobile, et_rcode;
    Button btn_singup;
    CountryCodePicker ccp;
    String country_code, referral_code, smobile;
    ImageView true_tick, false_tick;
    TextView referralcode_error, referralcode_length;
    String MobilePattern = "[0-9]{10}";
    String sname;
    String android_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singup);

        try {
            et_name = findViewById(R.id.et_name);
            et_mobile = findViewById(R.id.et_mobile);
            et_rcode = findViewById(R.id.et_code);
            btn_singup = findViewById(R.id.btn_singup);
            true_tick = findViewById(R.id.true_tick);
            false_tick = findViewById(R.id.false_tick);
            referralcode_error = findViewById(R.id.referralcode_error);
            referralcode_length = findViewById(R.id.referralcode_length);

            //stop  roted
            setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

            /*contry Code*/
            ccp = (CountryCodePicker) findViewById(R.id.ccp);
            ccp.setOnCountryChangeListener(new CountryCodePicker.OnCountryChangeListener() {
                @Override
                public void onCountrySelected(Country selectedCountry) {


                    country_code = "" + selectedCountry.getPhoneCode();

                }
            });
            if (ccp.isSelected()) {
            } else {

                country_code = "91";

            }
            false_tick.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    et_rcode.getText().clear();
                }
            });

            android_id = Settings.Secure.getString(SingUpActivity.this.getContentResolver(),
                    Settings.Secure.ANDROID_ID);

            et_rcode.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {


                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                    referral_code = et_rcode.getText().toString();


                    if (referral_code.length() == 0) {

                        btn_singup.setEnabled(true);

                    }


                    if (referral_code.length() == 6) {

                        referralcode(SingUpActivity.this, referral_code);
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (referral_code.length() < 6) {

                        referralcode_length.setVisibility(View.VISIBLE);
                        referralcode_error.setVisibility(View.GONE);

                    }
                }
            });

            btn_singup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    sname = et_name.getText().toString();


                    if (sname.equals("")) {

                        et_name.setError("Add Name");
                        btn_singup.setEnabled(false);

                    } else if (smobile.equals(MobilePattern)) {

                        et_mobile.setError("Add Mobile Number");

                    } else {

                        singuplimit(this);
                    }


                }
            });

            et_mobile.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                    smobile = et_mobile.getText().toString();


                    if ((smobile.length() >= 10 && smobile.length() <= 13)) {


                        mobilenumbechech(this);

                    } else {

                        btn_singup.setEnabled(false);

                    }

                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });


        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private void mobilenumbechech(TextWatcher textWatcher) {

        new RestClient(this).getInstance().get().mobilenumber_valid(smobile).enqueue(new Callback<ReferralCodeModel>() {
            @Override
            public void onResponse(Call<ReferralCodeModel> call, Response<ReferralCodeModel> response) {


                if (response.body() != null) {

                    btn_singup.setEnabled(true);
                    MyPrefrence.setmobile(smobile);

                } else {

                    btn_singup.setEnabled(false);
                    et_mobile.setError("Already exist");

                }

            }

            @Override
            public void onFailure(Call<ReferralCodeModel> call, Throwable t) {

            }
        });
    }

    Dialog dialog;

    private void singuplimit(View.OnClickListener onClickListener) {


        new RestClient(this).getInstance().get().signuplimit(android_id).enqueue(new Callback<ReferralCodeModel>() {
            @Override
            public void onResponse(Call<ReferralCodeModel> call, Response<ReferralCodeModel> response) {

                if (response.body() != null) {

                    MyPrefrence.setcountrycode(country_code);
                    MyPrefrence.setmobile(smobile);


                    singup(SingUpActivity.this, sname, country_code, smobile, referral_code, android_id);


                } else {

                    new AlertDialog.Builder(SingUpActivity.this)
                            .setTitle("Sign Limit finish")
                            .setMessage("Your registration limit has exceed using this device or phone. Kindly use new device or phone.")

                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    onBackPressed();
                                }
                            })

                            .setNegativeButton(android.R.string.no, null)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();


                }


            }

            @Override
            public void onFailure(Call<ReferralCodeModel> call, Throwable t) {

                Toast.makeText(SingUpActivity.this, "" + t, Toast.LENGTH_SHORT).show();


            }
        });


    }

    private void referralcode(Context context, String referral_code) {


        if (!Util.isInternetConnected(this)) {

            Intent intent = new Intent(SingUpActivity.this, NoInternetActivity.class);
            startActivity(intent);

            return;
        }

        new RestClient(this).getInstance().get().getreferralcode(referral_code).enqueue(new Callback<ReferralCodeModel>() {
            @Override
            public void onResponse(Call<ReferralCodeModel> call, Response<ReferralCodeModel> response) {


                if (response.body() != null) {

                    true_tick.setVisibility(View.VISIBLE);
                    false_tick.setVisibility(View.GONE);
                    referralcode_error.setVisibility(View.GONE);
                    et_rcode.setTextColor(Color.parseColor("#3BB54A"));
                    referralcode_length.setVisibility(View.GONE);
                    btn_singup.setEnabled(true);

                } else {
                    false_tick.setVisibility(View.VISIBLE);
                    true_tick.setVisibility(View.GONE);
                    referralcode_error.setVisibility(View.VISIBLE);
                    referralcode_length.setVisibility(View.GONE);
                    et_rcode.setTextColor(Color.parseColor("#D7443E"));
                    btn_singup.setEnabled(false);

                }

            }

            @Override
            public void onFailure(Call<ReferralCodeModel> call, Throwable t) {

            }
        });
    }

    private void singup(Context context, String sname, String country_code, String smobile, String referral_code, String android_id) {

        if (!Util.isInternetConnected(this)) {

            Intent intent = new Intent(SingUpActivity.this, NoInternetActivity.class);
            startActivity(intent);

            return;
        }
        dialog = Util.startLoader(context);

        new RestClient(context).getInstance().get().getsingup(sname, country_code, smobile, referral_code, android_id).enqueue(new Callback<SingupModal>() {
            @Override
            public void onResponse(Call<SingupModal> call, Response<SingupModal> response) {

                Util.stopLoader(dialog);

                if (response.body() != null) {


                    MyPrefrence.setcountrycode(country_code);
                    MyPrefrence.setmobile(smobile);
                    MyPrefrence.setname(sname);

                    MyPrefrence.setactivitycall("1");


                    MyPrefrence.setuserid(response.body().user_id);

                    Intent intent = new Intent(SingUpActivity.this, MainActivity.class);
                    startActivityForResult(intent, 426);
                    finish();
                    MyPrefrence.setulogin(true);


                } else {


                }

            }

            @Override
            public void onFailure(Call<SingupModal> call, Throwable t) {
                Util.stopLoader(dialog);

            }
        });

    }

    public void backs(View view) {
        onBackPressed();
    }
}
