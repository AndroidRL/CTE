package com.example.counttoearn.Activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.counttoearn.Model.CommonModel;
import com.example.counttoearn.MyPrefrence;
import com.example.counttoearn.R;
import com.example.counttoearn.Service.RestClient;
import com.example.counttoearn.Util.SoundService;
import com.example.counttoearn.Util.Util;
import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdListener;
import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;
import com.facebook.ads.AudienceNetworkAds;
import com.facebook.ads.InterstitialAd;
import com.facebook.ads.InterstitialAdListener;
import com.facebook.ads.RewardedVideoAd;
import com.facebook.ads.RewardedVideoAdListener;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WatchCoinActivity extends AppCompatActivity {


    TextView textViewCountDown, tv_note, tv_title;
    Button btn_watch;
    private AdView adView;
    LinearLayout linearLayout;
    CountDownTimer countDownTimer;
    int Seconds = 50;
    String currentTime;
    private final String TAG = HowtoplayActivity.class.getSimpleName();
    private RewardedVideoAd rewardedVideoAd;
    int intctime, overTime, endTime = 0;
    int buttonclick = 1;
    private InterstitialAd interstitialAd;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watch_coin);

        textViewCountDown = findViewById(R.id.textViewCountDown);
        tv_title = findViewById(R.id.tv_title);
        tv_note = findViewById(R.id.tv_note);
        btn_watch = findViewById(R.id.btn_watch);
        linearLayout = findViewById(R.id.linearLayout);

        MyPrefrence.setactivitycall("12");
        MyPrefrence.getuserid();
        tv_title.setText("Click on watch & get 10 coin");


        //stop  roted
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

//        Toast.makeText(this, ""+currentTime, Toast.LENGTH_SHORT).show();
//        int currenttimeint = Integer.parseInt(currentTime);
//
//        int plustime = currenttimeint + 10;
//
//        if (currenttimeint == plustime){
//
//
//
//        }


//        int newCurrentTime = Integer.parseInt(currentTime);
//        if (MyPrefrence.getWatchTime().isEmpty()) {
//            endTime = 0;
//        } else {
//            endTime = Integer.parseInt(MyPrefrence.getWatchTime());
//
//        }
//
//        if (newCurrentTime >= endTime) {
//            btn_watch.setEnabled(true);
//            linearLayout.setVisibility(View.GONE);
//        }
//
//
//        Toast.makeText(this, "I"+intctime, Toast.LENGTH_SHORT).show();
//        Toast.makeText(this, "E"+endTime, Toast.LENGTH_SHORT).show();
//
//        if (intctime == endTime) {
//            Toast.makeText(this, "time not over", Toast.LENGTH_SHORT).show();
//            btn_watch.setEnabled(false);
//            linearLayout.setVisibility(View.VISIBLE);
//
//        } else {
//            Toast.makeText(this, "time is over", Toast.LENGTH_SHORT).show();
//            btn_watch.setEnabled(true);
//            linearLayout.setVisibility(View.GONE);
//        }


        Videoadmethod(WatchCoinActivity.this);
            banneradmethod();

        btn_watch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (buttonclick == 1) {

                    interstitialadmethod(this);
                    buttonclick = 2;
                    Addcoin();


                } else if (buttonclick == 2) {

                    buttonclick = 3;

                    Videoadmethod(WatchCoinActivity.this);
                    Addcoin();


                } else if (buttonclick == 3) {

                    buttonclick = 1;
                    btn_watch.setEnabled(false);
                    onBackPressed();


                }


            }
        });


    }

    private void banneradmethod() {

        //banner ad
        AudienceNetworkAds.initialize(this);
        adView = new AdView(this, "2535402093439162_2535591366753568", AdSize.BANNER_HEIGHT_50);
        LinearLayout adContainer = (LinearLayout) findViewById(R.id.banner_container);
        adContainer.addView(adView);
        AdListener adListener = new AdListener() {
            @Override
            public void onError(Ad ad, AdError adError) {
                // Ad error callback
            }

            @Override
            public void onAdLoaded(Ad ad) {
                // Ad loaded callback
//                    Util.stopadloader(adloader);
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


    }

    private void Addcoin() {


        int allcoin = Integer.parseInt(MyPrefrence.getaddcoin());

        int pluscoin = allcoin + 10;

        String pluscoinstring = String.valueOf(pluscoin);
        MyPrefrence.setaddcoin(pluscoinstring);
        Toast.makeText(this, "10 Coin Add Successfully", Toast.LENGTH_SHORT).show();

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

    public void back(View view) {
        onBackPressed();
    }

    private void interstitialadmethod(View.OnClickListener onClickListener) {

        Dialog d = Util.startLoader(this);
        AudienceNetworkAds.initialize(this);

        interstitialAd = new InterstitialAd(this, "2535402093439162_2535592133420158");


        InterstitialAdListener interstitialAdListener = new InterstitialAdListener() {
            @Override
            public void onInterstitialDisplayed(Ad ad) {

            }

            @Override
            public void onInterstitialDismissed(Ad ad) {


            }

            @Override
            public void onError(Ad ad, AdError adError) {
            }

            @Override
            public void onAdLoaded(Ad ad) {


            }

            @Override
            public void onAdClicked(Ad ad) {

            }

            @Override
            public void onLoggingImpression(Ad ad) {

            }
        };


        interstitialAd.loadAd(
                interstitialAd.buildLoadAdConfig()
                        .withAdListener(interstitialAdListener)
                        .build());

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                try {
                    Util.stopLoader(d);
                    interstitialAd.show();

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }, 1500);


    }

    private void Videoadmethod(WatchCoinActivity watchCoinActivity) {

        Dialog d = Util.startLoader(this);


        // video ads
        AudienceNetworkAds.initialize(WatchCoinActivity.this);
        rewardedVideoAd = new RewardedVideoAd(WatchCoinActivity.this, "2535402093439162_2535592480086790");
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

