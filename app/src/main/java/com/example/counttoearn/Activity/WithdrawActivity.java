package com.example.counttoearn.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.animation.content.Content;
import com.example.counttoearn.Model.CommonModel;
import com.example.counttoearn.Model.PaymentModal;
import com.example.counttoearn.MyPrefrence;
import com.example.counttoearn.R;
import com.example.counttoearn.Service.RestClient;
import com.example.counttoearn.Util.NoInternetActivity;
import com.example.counttoearn.Util.Util;
import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdListener;
import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;
import com.facebook.ads.AudienceNetworkAds;
import com.facebook.ads.RewardedVideoAd;
import com.facebook.ads.RewardedVideoAdListener;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WithdrawActivity extends AppCompatActivity {

    RadioButton googlepay, phonepay, paytm, paypal, btn_600, btn_400, btn_200;
    EditText et_mobileNumber, et_email;
    Button btn_withdrow;
    RadioGroup radioGroup, rbtn_amount;
    TextView tv_wallet, tv_show_withdraw_list;
    String user_id = MyPrefrence.getuserid();
    String withdraw_amt = "300";
    String paytmid, paypal_id;
    String smobilenumber, semail;
    TextView tv_error;
    LinearLayout visible_layout;
    String allpayment_id = "4";
    String email_or_mobilenumber;
    private com.facebook.ads.RewardedVideoAd rewardedVideoAd;
    private AdView adView;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdraw);

        try {

            paymentmethd(this);

            paytm = findViewById(R.id.paytm);
            et_email = findViewById(R.id.et_email);

            et_mobileNumber = findViewById(R.id.et_mobileNumber);
            radioGroup = findViewById(R.id.radioGroup);
            tv_wallet = findViewById(R.id.tv_wallet);
            rbtn_amount = findViewById(R.id.rbtn_amount);
            tv_show_withdraw_list = findViewById(R.id.tv_show_withdraw_list);

            btn_200 = findViewById(R.id.btn_200);
            btn_400 = findViewById(R.id.btn_400);
            btn_600 = findViewById(R.id.btn_600);
            btn_withdrow = findViewById(R.id.btn_withdrow);
            tv_error = findViewById(R.id.tv_error);
            paypal = findViewById(R.id.paypal);
            visible_layout = findViewById(R.id.visible_layout);

            MyPrefrence.setactivitycall("1");

            String wallet = MyPrefrence.getwallet();
            float intwallet = Float.parseFloat(wallet);
            tv_wallet.setText(MyPrefrence.getwallet());

            //stop  roted
            setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


            //time set

            String hh = new SimpleDateFormat("HH").format(Calendar.getInstance().getTime());
            String mm = new SimpleDateFormat("mm").format(Calendar.getInstance().getTime());
            int mmint = Integer.parseInt(mm);
            String weekday_name = new SimpleDateFormat("EEEE", Locale.ENGLISH).format(System.currentTimeMillis());
            if (weekday_name.equals("Thursday")){

                if(hh.equals("9")){
                    if(mmint >= 0 && mmint <= 15){



                        /*IF CONDITIONS 300 500 700 VISIBLE */
                        if (intwallet >= 0 && intwallet < 300) {

//                tv_error.setText("Kindly earn more to withdraw.You need to earn a minimum of Rs.300 to withdraw.");
                            tv_error.setTextColor(Color.BLACK);
                            tv_error.setVisibility(View.VISIBLE);

                        } else if (intwallet >= 300 && intwallet < 500) {

                            btn_200.setEnabled(true);
                            btn_400.setEnabled(false);
                            btn_600.setEnabled(false);

                        } else if (intwallet >= 500 && intwallet < 700) {

                            btn_200.setEnabled(true);
                            btn_400.setEnabled(true);
                            btn_600.setEnabled(false);

                        } else if (intwallet >= 700 && intwallet < 5000) {


                            btn_200.setEnabled(true);
                            btn_400.setEnabled(true);
                            btn_600.setEnabled(true);
                        }

                        /*GREE NAME */
                        if (intwallet >= 200 && btn_200.isEnabled()) {

                            rbtn_amount.check(R.id.btn_200);
                            btn_200.setTextColor(Color.GREEN);
                            visible_layout.setVisibility(View.VISIBLE);

                        }




                    }else {
                        Toast.makeText(this, "Disable", Toast.LENGTH_SHORT).show();
                    }
                }

            }


            tv_show_withdraw_list.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(WithdrawActivity.this,WithdrawListActivity.class));

                        }
                    });



            /*radio btn amount*/
            rbtn_amount.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {


                    if (btn_200.isChecked()) {

                        btn_200.setTextColor(Color.GREEN);
                        btn_400.setTextColor(Color.BLACK);
                        btn_600.setTextColor(Color.BLACK);
                        withdraw_amt = "300";
                        Videoadmethod(WithdrawActivity.this);

                    }
                    if (btn_400.isChecked()) {
                        btn_400.setTextColor(Color.GREEN);
                        btn_200.setTextColor(Color.BLACK);
                        btn_600.setTextColor(Color.BLACK);
                        withdraw_amt = "500";
                        Videoadmethod(WithdrawActivity.this);


                    }
                    if (btn_600.isChecked()) {
                        btn_600.setTextColor(Color.GREEN);
                        btn_200.setTextColor(Color.BLACK);
                        btn_400.setTextColor(Color.BLACK);
                        withdraw_amt = "700";
                        Videoadmethod(WithdrawActivity.this);


                    }

                    if (!btn_200.isEnabled()) {
                        btn_200.setTextColor(Color.GRAY);
                    }
                    if (!btn_400.isEnabled()) {
                        btn_400.setTextColor(Color.GRAY);
                    }
                    if (!btn_600.isEnabled()) {
                        btn_600.setTextColor(Color.GRAY);
                    }
                }
            });

            /*EDIT TEXT*/
            et_mobileNumber.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                    smobilenumber = et_mobileNumber.getText().toString();

                    if (smobilenumber.length() < 10) {
                        btn_withdrow.setEnabled(false);
                    } else if (smobilenumber.length() == 10) {
                        btn_withdrow.setEnabled(true);
                    }

                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

            et_email.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                    semail = et_email.getText().toString();

                    if (Util.isValidEmailAddress(semail)) {

                        btn_withdrow.setEnabled(true);

                    } else {

                        btn_withdrow.setEnabled(false);

                        et_email.setError("Email is not Valid");
                    }

                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

            /*radio button payment app name*/
            radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {

                    if (paytm.isChecked()) {
                        allpayment_id = paytmid;
                        et_mobileNumber.setVisibility(View.VISIBLE);
                        et_email.setVisibility(View.GONE);
                        et_email.getText().clear();


                    }


                    if (paypal.isChecked()) {
                        allpayment_id = paypal_id;

                        et_mobileNumber.setVisibility(View.GONE);
                        et_email.setVisibility(View.VISIBLE);
                        et_mobileNumber.getText().clear();

                    }


                }
            });



            /*WITHDROW BUTTON*/
            btn_withdrow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    email_or_mobilenumber = "" + smobilenumber + "" + semail;
                    withdrawapi(WithdrawActivity.this);
                    Videoadmethod(WithdrawActivity.this);

                }
            });


            //banner ad
            AudienceNetworkAds.initialize(this);
            adView = new AdView(this, "2535402093439162_2535591366753568", AdSize.BANNER_HEIGHT_50);
            LinearLayout adContainer = (LinearLayout) findViewById(R.id.banner_container);
            adContainer.addView(adView);
            adView.loadAd();
            AdListener adListener = new AdListener() {
                @Override
                public void onError(Ad ad, AdError adError) {
                    // Ad error callback
                }

                @Override
                public void onAdLoaded(Ad ad) {
                    // Ad loaded callback
                }

                @Override
                public void onAdClicked(Ad ad) {
                    // Ad clicked callback

                }

                @Override
                public void onLoggingImpression(Ad ad) {
                    // Ad impression logged callback

                }
            };
            adView.loadAd(adView.buildLoadAdConfig().withAdListener(adListener).build());



            Dialog d = Util.startLoader(this);

            // Request an ad
            AudienceNetworkAds.initialize(this);
            rewardedVideoAd = new RewardedVideoAd(this, "2535402093439162_2535592480086790");
            RewardedVideoAdListener rewardedVideoAdListener = new RewardedVideoAdListener() {
                @Override
                public void onError(Ad ad, AdError error) {
                    // Rewarded video ad failed to load
//                    Log.e(TAG, "Rewarded video ad failed to load: " + error.getErrorMessage());
                }

                @Override
                public void onAdLoaded(Ad ad) {
                    // Rewarded video ad is loaded and ready to be displayed
                    //                Log.d(TAG, "Rewarded video ad is loaded and ready to be displayed!");
                    Util.stopLoader(d);
                }

                @Override
                public void onAdClicked(Ad ad) {
                    // Rewarded video ad clicked
                    //      Log.d(TAG, "Rewarded video ad clicked!");

                }

                @Override
                public void onLoggingImpression(Ad ad) {
                    // Rewarded Video ad impression - the event will fire when the
                    // video starts playing
                    //    Log.d(TAG, "Rewarded video ad impression logged!");

//                    Util.stopadloader(adloader);

                }

                @Override
                public void onRewardedVideoCompleted() {
                    // Rewarded Video View Complete - the video has been played to the end.
                    // You can use this event to initialize your reward
                    //   Log.d(TAG, "Rewarded video completed!");

                    // Call method to give reward
                    // giveReward();

                }

                @Override
                public void onRewardedVideoClosed() {
                    // The Rewarded Video ad was closed - this can occur during the video
                    // by closing the app, or closing the end card.
                    //  Log.d(TAG, "Rewarded video ad closed!");

                }
            };

            rewardedVideoAd.loadAd(
                    rewardedVideoAd.buildLoadAdConfig()
                            .withAdListener(rewardedVideoAdListener)
                            .build());


            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {

                    try {
                        Util.stopLoader(d);

                        rewardedVideoAd.show();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }, 2500);



        } catch (NumberFormatException e) {
            e.printStackTrace();
        }


    }

    /*PAYMENT ID AND NAME API */
    private void paymentmethd(Context context) {
        if (!Util.isInternetConnected(this)) {

            Intent intent = new Intent(WithdrawActivity.this, NoInternetActivity.class);
            startActivityForResult(intent, 426);

            return;
        }
        new RestClient(this).getInstance().get().getpayment().enqueue(new Callback<PaymentModal>() {
            @Override
            public void onResponse(Call<PaymentModal> call, Response<PaymentModal> response) {

                if (response.body() != null) {


                    paytm.setText(response.body().paytm.name);
                    paypal.setText(response.body().paypal.name);


                    paytmid = response.body().paytm.id;
                    paypal_id = response.body().paypal.id;


                } else {

                }

            }

            @Override
            public void onFailure(Call<PaymentModal> call, Throwable t) {


            }
        });

    }

    Dialog dialog;
    /*WITHDRAW MONY API*/
    private void withdrawapi(WithdrawActivity withdrawActivity) {

        if (!Util.isInternetConnected(this)) {

            Intent intent = new Intent(WithdrawActivity.this, NoInternetActivity.class);
            startActivityForResult(intent, 426);

            return;
        }
        dialog = Util.startLoader(this);
        new RestClient(this).getInstance().get().getwithdraw(user_id, allpayment_id, withdraw_amt, email_or_mobilenumber).enqueue(new Callback<CommonModel>() {
            @Override
            public void onResponse(Call<CommonModel> call, Response<CommonModel> response) {

                Util.stopLoader(dialog);
                if (response.body() != null) {

                    onBackPressed();

                    Toast.makeText(withdrawActivity, "" + response.body().message, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(withdrawActivity, "Try Again", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CommonModel> call, Throwable t) {
                Util.stopLoader(dialog);


            }
        });
    }

    public void back(View view) {
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_OK);
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        if (adView != null) {
            adView.destroy();
        }

        if (rewardedVideoAd != null) {
            rewardedVideoAd.destroy();
            rewardedVideoAd = null;
        }
        super.onDestroy();
    }


    private void Videoadmethod(WithdrawActivity withdrawActivity) {

        Dialog d = Util.startLoader(this);


    // video ads
            AudienceNetworkAds.initialize(WithdrawActivity.this);
    rewardedVideoAd = new RewardedVideoAd(WithdrawActivity.this, "2535402093439162_2535592480086790");
    RewardedVideoAdListener rewardedVideoAdListener = new RewardedVideoAdListener() {
        @Override
        public void onError(Ad ad, AdError error) {
            // Rewarded video ad failed to load
            //                    Log.e(TAG, "Rewarded video ad failed to load: " + error.getErrorMessage());
        }

        @Override
        public void onAdLoaded(Ad ad) {

        }

        @Override
        public void onAdClicked(Ad ad) {
            // Rewarded video ad clicked
            //      Log.d(TAG, "Rewarded video ad clicked!");

        }

        @Override
        public void onLoggingImpression(Ad ad) {
            // Rewarded Video ad impression - the event will fire when the
            // video starts playing
            //    Log.d(TAG, "Rewarded video ad impression logged!");

            //                    Util.stopadloader(adloader);

        }

        @Override
        public void onRewardedVideoCompleted() {
            // Rewarded Video View Complete - the video has been played to the end.
            // You can use this event to initialize your reward
            //   Log.d(TAG, "Rewarded video completed!");

            // Call method to give reward
            // giveReward();

        }

        @Override
        public void onRewardedVideoClosed() {
            // The Rewarded Video ad was closed - this can occur during the video
            // by closing the app, or closing the end card.
            //  Log.d(TAG, "Rewarded video ad closed!");

            //                                    timestart(QuestionScreenActivity.this);


        }
    };

            rewardedVideoAd.loadAd(
                    rewardedVideoAd.buildLoadAdConfig()
                    .withAdListener(rewardedVideoAdListener)
                            .build());

    Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
        @Override
        public void run() {

            try {
                Util.stopLoader(d);
                rewardedVideoAd.show();

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }, 2000);

            rewardedVideoAd.loadAd();


}


}
