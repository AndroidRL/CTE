package com.example.counttoearn.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.example.counttoearn.MyPrefrence;
import com.example.counttoearn.R;
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

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import pl.droidsonroids.gif.GifImageView;

public class QuestionScreenActivity extends AppCompatActivity {

    TextView textViewCountDown, mainname, question, coin, wallet, wrong, correct, banner_fb;
    private ColorStateList textColorDefaultCd;
    int finaltime;
    String clickOperation, sans;
    Button btn_next, btn_option1, btn_option2, btn_option3, btn_option4, soundon, soundoff;
    ImageView timer_img, img_coin, img_wallet;
    GifImageView timer_gif;
    CountDownTimer countDownTimer;
    MediaPlayer timefinish, corectans, wrongans;
    String second_time;
    String user_id = MyPrefrence.getuserid();
    String adsnameapi;
    Dialog dialog;
    int screen = 1;
    LottieAnimationView animationView;
    int banner_ads_click = 0;
    int video_ads_click = 0;
    int interstial_ads_click = 0;
    private InterstitialAd interstitialAd;


    //new code
    TextView combo, plus, minus, mul, divQ;
    Double leftMin, leftMax, rightMin, rightMax, answer;
    int opationAMin, opationAMax, opationBMin, opationBMax, opationCMin, opationCMax, optionAValue, optionBValue, optionCValue, optionDValue, operation;
    float opationADivMin, opationADivMax, opationBDivMin, opationBDivMax, opationCDivMin, opationCDivMax, optionADivValue, optionBDivValue, optionCDivValue;
    Integer[] optionsArray;
    List<Integer> optionList;

    //    EditText leval;
    String operator, level;

    private AdView adView;
    private RewardedVideoAd rewardedVideoAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_screen);


        try {


            textViewCountDown = findViewById(R.id.textViewCountDown);
            mainname = findViewById(R.id.mainname);
            question = findViewById(R.id.question);
            btn_option1 = findViewById(R.id.btn_option1);
            btn_option2 = findViewById(R.id.btn_option2);
            btn_option3 = findViewById(R.id.btn_option3);
            btn_option4 = findViewById(R.id.btn_option4);
            btn_next = findViewById(R.id.btn_next);
            timer_img = findViewById(R.id.timer_img);
            timer_gif = findViewById(R.id.timer_gif);
            wallet = findViewById(R.id.wallet);
            coin = findViewById(R.id.coin);
            img_coin = findViewById(R.id.img_coin);
            img_wallet = findViewById(R.id.img_wallet);
            wrong = findViewById(R.id.wrong);
            correct = findViewById(R.id.correct);
            soundoff = findViewById(R.id.belloff);
            soundon = findViewById(R.id.bellon);
            animationView = findViewById(R.id.animationView);

            wrong.setTextColor(Color.RED);
            correct.setTextColor(Color.GREEN);

            //stop  roted
            setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

            //newcode
            Bundle bundle = getIntent().getExtras();
            if (bundle != null) {
                clickOperation = bundle.getString("operation");
                level = bundle.getString("level");
            }

            coin.setText(MyPrefrence.getaddcoin());
            wallet.setText(MyPrefrence.getaddwallet());

            checkOperation(clickOperation);
            mainname.setText(clickOperation);

            MyPrefrence.setactivitycall("12");
//new code over

            /*sound*/
            timefinish = MediaPlayer.create(this, R.raw.timefinissound);
            wrongans = MediaPlayer.create(this, R.raw.wrongans);
            corectans = MediaPlayer.create(this, R.raw.corectans);

            MyPrefrence.setsound("on");
            soundon.setVisibility(View.VISIBLE);
            soundoff.setVisibility(View.GONE);
            startService(new Intent(QuestionScreenActivity.this, SoundService.class));

            soundon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    MyPrefrence.setsound("off");

                    soundoff.setVisibility(View.VISIBLE);
                    soundon.setVisibility(View.GONE);

                    stopService(new Intent(QuestionScreenActivity.this, SoundService.class));

                }
            });

            soundoff.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    MyPrefrence.setsound("on");
                    soundon.setVisibility(View.VISIBLE);
                    soundoff.setVisibility(View.GONE);
                    startService(new Intent(QuestionScreenActivity.this, SoundService.class));

                }
            });

            timestart(QuestionScreenActivity.this);


            /*Buttone*/
            Buttonmethod(this);


            btn_next.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    checkOperation(clickOperation);

                    Buttonmethod(QuestionScreenActivity.this);
                    timestart(QuestionScreenActivity.this);

                    btn_option1.setTextColor(Color.BLACK);
                    btn_option2.setTextColor(Color.BLACK);
                    btn_option3.setTextColor(Color.BLACK);
                    btn_option4.setTextColor(Color.BLACK);

                    btn_option1.setEnabled(true);
                    btn_option2.setEnabled(true);
                    btn_option3.setEnabled(true);
                    btn_option4.setEnabled(true);

                    btn_next.setVisibility(View.GONE);
                    timer_gif.setVisibility(View.VISIBLE);
                    timer_img.setVisibility(View.GONE);
                    wrong.setVisibility(View.GONE);
                    correct.setVisibility(View.GONE);


                    if (MyPrefrence.getshowAds().equals("show")) {


                        if (MyPrefrence.getadsType().equals("interstitial")) {

                            interstitialadmethod(this);

                        } else if (MyPrefrence.getadsType().equals("video")) {

                            Videoadmethod(this);


                        } else if (MyPrefrence.getadsType().equals("both")) {
                            interstitialadmethod(this);
                            Videoadmethod(this);
                        }
                        banneradmethod(this);


                    } else {


                    }


                }
            });

            adsnameapi = MyPrefrence.getadsname();


        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    private void banneradmethod(View.OnClickListener onClickListener) {


        /*banner ads*/
        int bannerAdint = Integer.parseInt(MyPrefrence.getbannerPerPage());
        banner_ads_click++;

        if (banner_ads_click == bannerAdint) {

            //banner ad
            AudienceNetworkAds.initialize(QuestionScreenActivity.this);
            adView = new AdView(QuestionScreenActivity.this, "2535402093439162_2535591366753568", AdSize.BANNER_HEIGHT_50);
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


            banner_ads_click = 0;
        }


    }

    private void interstitialadmethod(View.OnClickListener onClickListener) {

        int intinterstialad = Integer.parseInt(MyPrefrence.getperPageInterstitial());
        interstial_ads_click++;

        if (interstial_ads_click == intinterstialad) {

            AudienceNetworkAds.initialize(this);

            interstitialAd = new InterstitialAd(this, "2535402093439162_2535592133420158");


            InterstitialAdListener interstitialAdListener = new InterstitialAdListener() {
                @Override
                public void onInterstitialDisplayed(Ad ad) {
                    // Interstitial ad displayed callback
//                    Log.e(TAG, "Interstitial ad displayed.");
                }

                @Override
                public void onInterstitialDismissed(Ad ad) {
                    // Interstitial dismissed callback
//                    Log.e(TAG, "Interstitial ad dismissed.");

                    if (MyPrefrence.getsound() == "on") {


                        MyPrefrence.setsound("on");
                        soundon.setVisibility(View.VISIBLE);
                        soundoff.setVisibility(View.GONE);

                        startService(new Intent(QuestionScreenActivity.this, SoundService.class));

                    } else {


                        MyPrefrence.setsound("off");
                        soundoff.setVisibility(View.VISIBLE);
                        soundon.setVisibility(View.GONE);

                        stopService(new Intent(QuestionScreenActivity.this, SoundService.class));
                    }


                }

                @Override
                public void onError(Ad ad, AdError adError) {
                    // Ad error callback
//                    Log.e(TAG, "Interstitial ad failed to load: " + adError.getErrorMessage());
                }

                @Override
                public void onAdLoaded(Ad ad) {
                    // Interstitial ad is loaded and ready to be displayed
//                    Log.d(TAG, "Interstitial ad is loaded and ready to be displayed!");
                    // Show the ad
                    stopService(new Intent(QuestionScreenActivity.this, SoundService.class));
                    countDownTimer.cancel();
                    soundoff.setVisibility(View.VISIBLE);
                    soundon.setVisibility(View.GONE);

                    try {
                        interstitialAd.show();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onAdClicked(Ad ad) {
                    // Ad clicked callback
//                    Log.d(TAG, "Interstitial ad clicked!");
                }

                @Override
                public void onLoggingImpression(Ad ad) {
                    // Ad impression logged callback
//                    Log.d(TAG, "Interstitial ad impression logged!");
                }
            };

            // For auto play video ads, it's recommended to load the ad
            // at least 30 seconds before it is shown
            interstitialAd.loadAd(
                    interstitialAd.buildLoadAdConfig()
                            .withAdListener(interstitialAdListener)
                            .build());

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {

                    try {
                        interstitialAd.show();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }, 1500);

            interstial_ads_click = 0;

        }

    }

    private void Videoadmethod(View.OnClickListener onClickListener) {


        int intvideoads = Integer.parseInt(MyPrefrence.getperPageAd());
        video_ads_click++;

        if (video_ads_click == intvideoads) {

            // video ads
            AudienceNetworkAds.initialize(QuestionScreenActivity.this);
            rewardedVideoAd = new RewardedVideoAd(QuestionScreenActivity.this, "2535402093439162_2535592480086790");
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
                    stopService(new Intent(QuestionScreenActivity.this, SoundService.class));
                    countDownTimer.cancel();
                    soundoff.setVisibility(View.VISIBLE);
                    soundon.setVisibility(View.GONE);
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

                    if (MyPrefrence.getsound() == "on") {


                        MyPrefrence.setsound("on");
                        soundon.setVisibility(View.VISIBLE);
                        soundoff.setVisibility(View.GONE);

                        startService(new Intent(QuestionScreenActivity.this, SoundService.class));

                    } else {


                        MyPrefrence.setsound("off");

                        soundoff.setVisibility(View.VISIBLE);
                        soundon.setVisibility(View.GONE);

                        stopService(new Intent(QuestionScreenActivity.this, SoundService.class));
                    }

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
                        rewardedVideoAd.show();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }, 2500);

            rewardedVideoAd.loadAd();

            video_ads_click = 0;


        }


    }

    private void checkOperation(String clickOperation) {
        switch (clickOperation) {
            case "combo":
                generateComboQuestion();
                break;

            case "addition":
                generateAdditionQuestion();
                break;

            case "subtraction":
                generateSubtractionQuestion();
                break;

            case "multiplication":
                generateMultiplicationQuestion();
                break;

            case "division":
                generateDivisionQuestion();
                break;


        }
    }

    /*time*/
    private void timestart(Context context) {

        textColorDefaultCd = textViewCountDown.getTextColors();


        try {

            int inttime = Integer.parseInt(MyPrefrence.getanswerTime());

            finaltime = inttime * 1000;

        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        countDownTimer = new CountDownTimer(finaltime, 1000) {


            public void onTick(long millisUntilFinished) {

                textViewCountDown.setText("00:" + millisUntilFinished / 1000);


                second_time = "" + millisUntilFinished;

                if (millisUntilFinished < 8000) {

                    textViewCountDown.setTextColor(Color.RED);

                } else {

                    textViewCountDown.setTextColor(Color.BLACK);
                }
            }

            @SuppressLint("ResourceAsColor")
            public void onFinish() {

                textViewCountDown.setText("oops, Time is over..!");
                textViewCountDown.setTextSize(20);
                timer_gif.setVisibility(View.GONE);
                timer_img.setVisibility(View.VISIBLE);
                btn_next.setVisibility(View.VISIBLE);


                if (MyPrefrence.getsound().equals("on")) {


                    timefinish.start();

                }
                if (MyPrefrence.getsound().equals("off")) {

                    timefinish.stop();

                }

                autoans();


            }

        }.start();
    }

    /*back press method*/
    public void back(View view) {
//        onBackPressed();
        stopService(new Intent(QuestionScreenActivity.this, SoundService.class));
        timefinish.stop();
        Intent intent = new Intent(QuestionScreenActivity.this, MainActivity.class);
        startActivity(intent);


    }

    /*back press method*/
    @Override
    public void onBackPressed() {
        stopService(new Intent(QuestionScreenActivity.this, SoundService.class));
        timefinish.stop();
        setResult(RESULT_OK);
        super.onBackPressed();

        Intent intent = new Intent(QuestionScreenActivity.this, MainActivity.class);
        startActivity(intent);


    }


    //new code method
    private void generateDivisionQuestion() {
        String userLevel = level;
        if (userLevel.equals("1")) {
            leftMin = Double.valueOf(1);
            leftMax = Double.valueOf(4);
            rightMin = Double.valueOf(5);
            rightMax = Double.valueOf(9);
            opationAMin = 1;
            opationAMax = 9;
            opationBMin = 1;
            opationBMax = 9;
            opationCMin = 1;
            opationCMax = 9;
            opationADivMin = 1F;
            opationADivMax = 9F;
            opationBDivMin = 1F;
            opationBDivMax = 9F;
            opationCDivMin = 1F;
            opationCDivMax = 9F;
        } else if (userLevel.equals("2")) {
            leftMin = Double.valueOf(10);
            leftMax = Double.valueOf(49);
            rightMin = Double.valueOf(50);
            rightMax = Double.valueOf(99);
            opationAMin = 10;
            opationAMax = 99;
            opationBMin = 10;
            opationBMax = 99;
            opationCMin = 10;
            opationCMax = 99;
            opationADivMin = 10F;
            opationADivMax = 99F;
            opationBDivMin = 10F;
            opationBDivMax = 99F;
            opationCDivMin = 10F;
            opationCDivMax = 99F;
        } else if (userLevel.equals("3")) {
            leftMin = Double.valueOf(100);
            leftMax = Double.valueOf(499);
            rightMin = Double.valueOf(500);
            rightMax = Double.valueOf(999);
            opationAMin = 100;
            opationAMax = 999;
            opationBMin = 100;
            opationBMax = 999;
            opationCMin = 100;
            opationCMax = 999;
            opationADivMin = 100F;
            opationADivMax = 999F;
            opationBDivMin = 100F;
            opationBDivMax = 999F;
            opationCDivMin = 100F;
            opationCDivMax = 999F;
        } else if (userLevel.equals("4")) {
            leftMin = Double.valueOf(1000);
            leftMax = Double.valueOf(4999);
            rightMin = Double.valueOf(5000);
            rightMax = Double.valueOf(9999);
            opationAMin = 1000;
            opationAMax = 9999;
            opationBMin = 1000;
            opationBMax = 9999;
            opationCMin = 1000;
            opationCMax = 9999;
            opationADivMin = 1000F;
            opationADivMax = 9999F;
            opationBDivMin = 1000F;
            opationBDivMax = 9999F;
            opationCDivMin = 1000F;
            opationCDivMax = 9999F;
        }
        operation = 4;
        final Double RightNumber = new Random().nextInt((int) ((leftMax - leftMin) + 1)) + leftMin;
        final Double leftNumber = new Random().nextInt((int) ((rightMax - rightMin) + 1)) + rightMin;
        generateNewQuestion(leftNumber, RightNumber, operation);

    }

    private void generateMultiplicationQuestion() {
        String userLevel = level;
        if (userLevel.equals("1")) {
            leftMin = Double.valueOf(1);
            leftMax = Double.valueOf(4);
            rightMin = Double.valueOf(5);
            rightMax = Double.valueOf(9);
            opationAMin = 1;
            opationAMax = 9;
            opationBMin = 1;
            opationBMax = 9;
            opationCMin = 1;
            opationCMax = 9;
            opationADivMin = 1F;
            opationADivMax = 9F;
            opationBDivMin = 1F;
            opationBDivMax = 9F;
            opationCDivMin = 1F;
            opationCDivMax = 9F;
        } else if (userLevel.equals("2")) {
            leftMin = Double.valueOf(10);
            leftMax = Double.valueOf(49);
            rightMin = Double.valueOf(50);
            rightMax = Double.valueOf(99);
            opationAMin = 10;
            opationAMax = 99;
            opationBMin = 10;
            opationBMax = 99;
            opationCMin = 10;
            opationCMax = 99;
            opationADivMin = 10F;
            opationADivMax = 99F;
            opationBDivMin = 10F;
            opationBDivMax = 99F;
            opationCDivMin = 10F;
            opationCDivMax = 99F;
        } else if (userLevel.equals("3")) {
            leftMin = Double.valueOf(100);
            leftMax = Double.valueOf(499);
            rightMin = Double.valueOf(500);
            rightMax = Double.valueOf(999);
            opationAMin = 100;
            opationAMax = 999;
            opationBMin = 100;
            opationBMax = 999;
            opationCMin = 100;
            opationCMax = 999;
            opationADivMin = 100F;
            opationADivMax = 999F;
            opationBDivMin = 100F;
            opationBDivMax = 999F;
            opationCDivMin = 100F;
            opationCDivMax = 999F;
        } else if (userLevel.equals("4")) {
            leftMin = Double.valueOf(1000);
            leftMax = Double.valueOf(4999);
            rightMin = Double.valueOf(5000);
            rightMax = Double.valueOf(9999);
            opationAMin = 1000;
            opationAMax = 9999;
            opationBMin = 1000;
            opationBMax = 9999;
            opationCMin = 1000;
            opationCMax = 9999;
            opationADivMin = 1000F;
            opationADivMax = 9999F;
            opationBDivMin = 1000F;
            opationBDivMax = 9999F;
            opationCDivMin = 1000F;
            opationCDivMax = 9999F;
        }
        operation = 3;
        final Double leftNumber = new Random().nextInt((int) ((leftMax - leftMin) + 1)) + leftMin;
        final Double RightNumber = new Random().nextInt((int) ((rightMax - rightMin) + 1)) + rightMin;
        generateNewQuestion(leftNumber, RightNumber, operation);

    }

    private void generateSubtractionQuestion() {
        String userLevel = level;
        if (userLevel.equals("1")) {
            leftMin = Double.valueOf(1);
            leftMax = Double.valueOf(4);
            rightMin = Double.valueOf(5);
            rightMax = Double.valueOf(9);
            opationAMin = 1;
            opationAMax = 9;
            opationBMin = 1;
            opationBMax = 9;
            opationCMin = 1;
            opationCMax = 9;
            opationADivMin = 1F;
            opationADivMax = 9F;
            opationBDivMin = 1F;
            opationBDivMax = 9F;
            opationCDivMin = 1F;
            opationCDivMax = 9F;
        } else if (userLevel.equals("2")) {
            leftMin = Double.valueOf(10);
            leftMax = Double.valueOf(49);
            rightMin = Double.valueOf(50);
            rightMax = Double.valueOf(99);
            opationAMin = 10;
            opationAMax = 99;
            opationBMin = 10;
            opationBMax = 99;
            opationCMin = 10;
            opationCMax = 99;
            opationADivMin = 10F;
            opationADivMax = 99F;
            opationBDivMin = 10F;
            opationBDivMax = 99F;
            opationCDivMin = 10F;
            opationCDivMax = 99F;
        } else if (userLevel.equals("3")) {
            leftMin = Double.valueOf(100);
            leftMax = Double.valueOf(499);
            rightMin = Double.valueOf(500);
            rightMax = Double.valueOf(999);
            opationAMin = 100;
            opationAMax = 999;
            opationBMin = 100;
            opationBMax = 999;
            opationCMin = 100;
            opationCMax = 999;
            opationADivMin = 100F;
            opationADivMax = 999F;
            opationBDivMin = 100F;
            opationBDivMax = 999F;
            opationCDivMin = 100F;
            opationCDivMax = 999F;
        } else if (userLevel.equals("4")) {
            leftMin = Double.valueOf(1000);
            leftMax = Double.valueOf(4999);
            rightMin = Double.valueOf(5000);
            rightMax = Double.valueOf(9999);
            opationAMin = 1000;
            opationAMax = 9999;
            opationBMin = 1000;
            opationBMax = 9999;
            opationCMin = 1000;
            opationCMax = 9999;
            opationADivMin = 1000F;
            opationADivMax = 9999F;
            opationBDivMin = 1000F;
            opationBDivMax = 9999F;
            opationCDivMin = 1000F;
            opationCDivMax = 9999F;
        }
        operation = 2;
        final Double RightNumber = new Random().nextInt((int) ((leftMax - leftMin) + 1)) + leftMin;
        final Double leftNumber = new Random().nextInt((int) ((rightMax - rightMin) + 1)) + rightMin;
        generateNewQuestion(leftNumber, RightNumber, operation);

    }

    private void generateAdditionQuestion() {
        String userLevel = level;
        if (userLevel.equals("1")) {
            leftMin = Double.valueOf(1);
            leftMax = Double.valueOf(4);
            rightMin = Double.valueOf(5);
            rightMax = Double.valueOf(9);
            opationAMin = 1;
            opationAMax = 9;
            opationBMin = 1;
            opationBMax = 9;
            opationCMin = 1;
            opationCMax = 9;
            opationADivMin = 1F;
            opationADivMax = 9F;
            opationBDivMin = 1F;
            opationBDivMax = 9F;
            opationCDivMin = 1F;
            opationCDivMax = 9F;
        } else if (userLevel.equals("2")) {
            leftMin = Double.valueOf(10);
            leftMax = Double.valueOf(49);
            rightMin = Double.valueOf(50);
            rightMax = Double.valueOf(99);
            opationAMin = 10;
            opationAMax = 99;
            opationBMin = 10;
            opationBMax = 99;
            opationCMin = 10;
            opationCMax = 99;
            opationADivMin = 10F;
            opationADivMax = 99F;
            opationBDivMin = 10F;
            opationBDivMax = 99F;
            opationCDivMin = 10F;
            opationCDivMax = 99F;
        } else if (userLevel.equals("3")) {
            leftMin = Double.valueOf(100);
            leftMax = Double.valueOf(499);
            rightMin = Double.valueOf(500);
            rightMax = Double.valueOf(999);
            opationAMin = 100;
            opationAMax = 999;
            opationBMin = 100;
            opationBMax = 999;
            opationCMin = 100;
            opationCMax = 999;
            opationADivMin = 100F;
            opationADivMax = 999F;
            opationBDivMin = 100F;
            opationBDivMax = 999F;
            opationCDivMin = 100F;
            opationCDivMax = 999F;
        } else if (userLevel.equals("4")) {
            leftMin = Double.valueOf(1000);
            leftMax = Double.valueOf(4999);
            rightMin = Double.valueOf(5000);
            rightMax = Double.valueOf(9999);
            opationAMin = 1000;
            opationAMax = 9999;
            opationBMin = 1000;
            opationBMax = 9999;
            opationCMin = 1000;
            opationCMax = 9999;
            opationADivMin = 1000F;
            opationADivMax = 9999F;
            opationBDivMin = 1000F;
            opationBDivMax = 9999F;
            opationCDivMin = 1000F;
            opationCDivMax = 9999F;
        }
        operation = 1;
        final Double leftNumber = new Random().nextInt((int) ((leftMax - leftMin) + 1)) + leftMin;
        final Double RightNumber = new Random().nextInt((int) ((rightMax - rightMin) + 1)) + rightMin;
        generateNewQuestion(leftNumber, RightNumber, operation);

    }

    public void generateComboQuestion() {
        String userLevel = level;
        if (userLevel.equals("1")) {
            leftMin = Double.valueOf(1);
            leftMax = Double.valueOf(4);
            rightMin = Double.valueOf(5);
            rightMax = Double.valueOf(9);
            opationAMin = 1;
            opationAMax = 9;
            opationBMin = 1;
            opationBMax = 9;
            opationCMin = 1;
            opationCMax = 9;
            opationADivMin = 1F;
            opationADivMax = 9F;
            opationBDivMin = 1F;
            opationBDivMax = 9F;
            opationCDivMin = 1F;
            opationCDivMax = 9F;
        } else if (userLevel.equals("2")) {
            leftMin = Double.valueOf(10);
            leftMax = Double.valueOf(49);
            rightMin = Double.valueOf(50);
            rightMax = Double.valueOf(99);
            opationAMin = 10;
            opationAMax = 99;
            opationBMin = 10;
            opationBMax = 99;
            opationCMin = 10;
            opationCMax = 99;
            opationADivMin = 10F;
            opationADivMax = 99F;
            opationBDivMin = 10F;
            opationBDivMax = 99F;
            opationCDivMin = 10F;
            opationCDivMax = 99F;
        } else if (userLevel.equals("3")) {
            leftMin = Double.valueOf(100);
            leftMax = Double.valueOf(499);
            rightMin = Double.valueOf(500);
            rightMax = Double.valueOf(999);
            opationAMin = 100;
            opationAMax = 999;
            opationBMin = 100;
            opationBMax = 999;
            opationCMin = 100;
            opationCMax = 999;
            opationADivMin = 100F;
            opationADivMax = 999F;
            opationBDivMin = 100F;
            opationBDivMax = 999F;
            opationCDivMin = 100F;
            opationCDivMax = 999F;
        } else if (userLevel.equals("4")) {
            leftMin = Double.valueOf(1000);
            leftMax = Double.valueOf(4999);
            rightMin = Double.valueOf(5000);
            rightMax = Double.valueOf(9999);
            opationAMin = 1000;
            opationAMax = 9999;
            opationBMin = 1000;
            opationBMax = 9999;
            opationCMin = 1000;
            opationCMax = 9999;
            opationADivMin = 1000F;
            opationADivMax = 9999F;
            opationBDivMin = 1000F;
            opationBDivMax = 9999F;
            opationCDivMin = 1000F;
            opationCDivMax = 9999F;
        }
        operation = 0;
        final Double leftNumber = new Random().nextInt((int) ((leftMax - leftMin) + 1)) + leftMin;
        final Double RightNumber = new Random().nextInt((int) ((rightMax - rightMin) + 1)) + rightMin;
        generateNewQuestion(leftNumber, RightNumber, operation);
    }

    @SuppressLint("SetTextI18n")
    void generateNewQuestion(Double leftSide, Double rightSide, int operation) {
        switch (operation) {
            case 0:
                Integer[] operatorCombo = {1, 2, 3, 4};
                List<Integer> operatorComboArray = Arrays.asList(operatorCombo);
                Collections.shuffle(operatorComboArray);
                operatorComboArray.toArray(operatorCombo);
                if (operatorComboArray.get(0).toString().equals("1")) {
                    getPlusQ(leftSide, rightSide);
                } else if (operatorComboArray.get(0).toString().equals("2")) {
                    getMinusQ(rightSide, leftSide);
                } else if (operatorComboArray.get(0).toString().equals("3")) {
                    getMultiplicatoinQ(leftSide, rightSide);

                } else if (operatorComboArray.get(0).toString().equals("4")) {
                    getDivisionQ(leftSide, rightSide);

                }
                break;
            case 1:
                getPlusQ(leftSide, rightSide);
                break;
            case 2:
                getMinusQ(leftSide, rightSide);
                break;
            case 3:
                getMultiplicatoinQ(leftSide, rightSide);
                break;
            case 4:
                getDivisionQ(leftSide, rightSide);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + operation);
        }
    }

    private void getPlusQ(Double leftSide, Double rightSide) {
        operator = "+";
        answer = leftSide + rightSide;
        question.setText(leftSide.intValue() + " " + operator + " " + rightSide.intValue() + " = ?");
        optionAValue = new Random().nextInt((opationAMax - opationAMin) + 1) + opationAMin;
        optionBValue = new Random().nextInt((opationBMax - opationBMin) + 1) + opationBMin;
        optionCValue = new Random().nextInt((opationCMax - opationCMin) + 1) + opationCMin;
        optionsArray = new Integer[]{optionAValue, optionBValue, optionCValue, answer.intValue()};
        optionList = Arrays.asList(optionsArray);
        Collections.shuffle(optionList);
        optionList.toArray(optionsArray);
        btn_option1.setText(optionList.get(0).toString());
        btn_option2.setText(optionList.get(1).toString());
        btn_option3.setText(optionList.get(2).toString());
        btn_option4.setText(optionList.get(3).toString());

        String ans = String.valueOf(answer.intValue());
        sans = ans;

    }

    private void getMinusQ(Double leftSide, Double rightSide) {
        operator = "-";
        answer = leftSide - rightSide;
        question.setText(leftSide.intValue() + " " + operator + " " + rightSide.intValue() + " = ?");
        optionAValue = new Random().nextInt((opationAMax - opationAMin) + 1) + opationAMin;
        optionBValue = new Random().nextInt((opationBMax - opationBMin) + 1) + opationBMin;
        optionCValue = new Random().nextInt((opationCMax - opationCMin) + 1) + opationCMin;
        optionsArray = new Integer[]{optionAValue, optionBValue, optionCValue, answer.intValue()};
        optionList = Arrays.asList(optionsArray);
        Collections.shuffle(optionList);
        optionList.toArray(optionsArray);
        btn_option1.setText(optionList.get(0).toString());
        btn_option2.setText(optionList.get(1).toString());
        btn_option3.setText(optionList.get(2).toString());
        btn_option4.setText(optionList.get(3).toString());
        String ans = String.valueOf(answer.intValue());
        sans = ans;
    }

    private void getMultiplicatoinQ(Double leftSide, Double rightSide) {
        operator = "*";
        answer = leftSide * rightSide;
        question.setText(leftSide.intValue() + " " + operator + " " + rightSide.intValue() + " = ?");
        optionAValue = new Random().nextInt((opationAMax - opationAMin) + 1) + opationAMin;
        optionBValue = new Random().nextInt((opationBMax - opationBMin) + 1) + opationBMin;
        optionCValue = new Random().nextInt((opationCMax - opationCMin) + 1) + opationCMin;
        optionsArray = new Integer[]{optionAValue, optionBValue, optionCValue, answer.intValue()};
        optionList = Arrays.asList(optionsArray);
        Collections.shuffle(optionList);
        optionList.toArray(optionsArray);
        btn_option1.setText(optionList.get(0).toString());
        btn_option2.setText(optionList.get(1).toString());
        btn_option3.setText(optionList.get(2).toString());
        btn_option4.setText(optionList.get(3).toString());
        String ans = String.valueOf(answer.intValue());
        sans = ans;
    }

    private void getDivisionQ(Double leftSide, Double rightSide) {
        operator = "/";
        answer = leftSide / rightSide;

        if (answer % 2 == 0) {

            question.setText(leftSide.intValue() + " " + operator + " " + rightSide.intValue() + " = ?");
            optionAValue = new Random().nextInt((opationAMax - opationAMin) + 1) + opationAMin;
            optionBValue = new Random().nextInt((opationBMax - opationBMin) + 1) + opationBMin;
            optionCValue = new Random().nextInt((opationCMax - opationCMin) + 1) + opationCMin;
            optionsArray = new Integer[]{optionAValue, optionBValue, optionCValue, answer.intValue()};
            optionList = Arrays.asList(optionsArray);
            Collections.shuffle(optionList);
            optionList.toArray(optionsArray);
            btn_option1.setText(optionList.get(0).toString());
            btn_option2.setText(optionList.get(1).toString());
            btn_option3.setText(optionList.get(2).toString());
            btn_option4.setText(optionList.get(3).toString());
            String ans = String.valueOf(answer.intValue());
            sans = ans;

        } else {

            question.setText(leftSide.intValue() + " " + operator + " " + rightSide.intValue() + " = ?");
            String optionDValue = String.format("%.2f", answer);
            optionADivValue = opationADivMin + new Random().nextFloat() * (opationADivMax - opationADivMin);
            optionBDivValue = opationBDivMin + new Random().nextFloat() * (opationBDivMax - opationBDivMin);
            optionCDivValue = opationCDivMin + new Random().nextFloat() * (opationCDivMax - opationCDivMin);
            String[] optionsStringArray = {String.valueOf(String.format("%.2f", optionADivValue)), String.valueOf(String.format("%.2f", optionBDivValue)), String.valueOf(String.format("%.2f", optionCDivValue)), optionDValue};
            List<String> optionListString = Arrays.asList(optionsStringArray);
            Collections.shuffle(optionListString);
            optionListString.toArray(optionsStringArray);
            btn_option1.setText(optionListString.get(0));
            btn_option2.setText(optionListString.get(1));
            btn_option3.setText(optionListString.get(2));
            btn_option4.setText(optionListString.get(3));
//            String ans = String.valueOf(answer);
            sans = String.format("%.2f", answer);
        }
    }


    /*all butoon moment*/
    private void Buttonmethod(QuestionScreenActivity questionScreenActivity) {

        try {
            btn_option1.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("ResourceAsColor")
                @Override
                public void onClick(View v) {
                    btn_option1.setEnabled(false);
                    btn_option2.setEnabled(false);
                    btn_option3.setEnabled(false);
                    btn_option4.setEnabled(false);
                    btn_option1.setTextColor(R.color.btn);
                    btn_option2.setTextColor(R.color.btn);
                    btn_option3.setTextColor(R.color.btn);
                    btn_option4.setTextColor(R.color.btn);

                    countDownTimer.cancel();
                    timer_gif.setVisibility(View.GONE);
                    timer_img.setVisibility(View.VISIBLE);
                    btn_next.setVisibility(View.VISIBLE);

                    if (btn_option1.getText().equals("" + sans)) {

                        btn_next.setVisibility(View.VISIBLE);
                        btn_option1.setTextColor(Color.GREEN);
                        btn_next.setEnabled(true);

                        try {
                            submitcurrectans();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        correct.setVisibility(View.VISIBLE);
                        wrong.setVisibility(View.GONE);

                        if (MyPrefrence.getsound().equals("on")) {


                            corectans.start();

                        }

                    } else {
                        btn_next.setEnabled(true);
                        btn_option1.setTextColor(Color.RED);
                        correct.setVisibility(View.GONE);
                        wrong.setVisibility(View.VISIBLE);

                        if (MyPrefrence.getsound().equals("on")) {
                            wrongans.start();

                        }

                        if (btn_option2.getText().equals("" + sans)) {
                            btn_option2.setTextColor(Color.GREEN);
                        } else if (btn_option3.getText().equals("" + sans)) {
                            btn_option3.setTextColor(Color.GREEN);
                        } else if (btn_option4.getText().equals("" + sans)) {
                            btn_option4.setTextColor(Color.GREEN);
                        }
                    }


                }
            });

            btn_option2.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("ResourceAsColor")
                @Override
                public void onClick(View v) {
                    btn_option1.setEnabled(false);
                    btn_option2.setEnabled(false);
                    btn_option3.setEnabled(false);
                    btn_option4.setEnabled(false);
                    countDownTimer.cancel();
                    timer_gif.setVisibility(View.GONE);
                    timer_img.setVisibility(View.VISIBLE);
                    btn_next.setVisibility(View.VISIBLE);

                    btn_option1.setTextColor(R.color.btn);
                    btn_option2.setTextColor(R.color.btn);
                    btn_option3.setTextColor(R.color.btn);
                    btn_option4.setTextColor(R.color.btn);

                    if (btn_option2.getText().equals("" + sans)) {
                        btn_next.setVisibility(View.VISIBLE);
                        btn_next.setEnabled(true);
                        btn_option2.setTextColor(Color.GREEN);

                        try {
                            submitcurrectans();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        correct.setVisibility(View.VISIBLE);

                        wrong.setVisibility(View.GONE);
                        if (MyPrefrence.getsound().equals("on")) {


                            corectans.start();

                        }


                    } else {
                        btn_next.setEnabled(true);
                        btn_option2.setTextColor(Color.RED);
                        correct.setVisibility(View.GONE);
                        wrong.setVisibility(View.VISIBLE);
                        if (MyPrefrence.getsound().equals("on")) {

                            wrongans.start();


                        }

                        if (btn_option1.getText().equals("" + sans)) {

                            btn_option1.setTextColor(Color.GREEN);
                        } else if (btn_option3.getText().equals("" + sans)) {
                            btn_option3.setTextColor(Color.GREEN);
                        } else if (btn_option4.getText().equals("" + sans)) {
                            btn_option4.setTextColor(Color.GREEN);
                        }
                    }
                }

            });

            btn_option3.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("ResourceAsColor")
                @Override
                public void onClick(View v) {
                    btn_option1.setEnabled(false);
                    btn_option2.setEnabled(false);
                    btn_option3.setEnabled(false);
                    btn_option4.setEnabled(false);
                    countDownTimer.cancel();
                    timer_gif.setVisibility(View.GONE);
                    timer_img.setVisibility(View.VISIBLE);
                    btn_next.setVisibility(View.VISIBLE);

                    btn_option1.setTextColor(R.color.btn);
                    btn_option2.setTextColor(R.color.btn);
                    btn_option3.setTextColor(R.color.btn);
                    btn_option4.setTextColor(R.color.btn);

                    if (btn_option3.getText().equals("" + sans)) {
                        btn_next.setEnabled(true);

                        try {
                            submitcurrectans();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        correct.setVisibility(View.VISIBLE);
                        wrong.setVisibility(View.GONE);
                        if (MyPrefrence.getsound().equals("on")) {


                            corectans.start();

                        } else {
                            corectans.stop();

                        }
                        btn_next.setVisibility(View.VISIBLE);


                        btn_option3.setTextColor(Color.GREEN);


                    } else {
                        btn_next.setEnabled(true);
                        btn_option3.setTextColor(Color.RED);
                        correct.setVisibility(View.GONE);
                        wrong.setVisibility(View.VISIBLE);
                        if (MyPrefrence.getsound().equals("on")) {

                            wrongans.start();


                        }

                        if (btn_option1.getText().equals("" + sans)) {

                            btn_option1.setTextColor(Color.GREEN);
                        } else if (btn_option2.getText().equals("" + sans)) {
                            btn_option2.setTextColor(Color.GREEN);
                        } else if (btn_option4.getText().equals("" + sans)) {
                            btn_option4.setTextColor(Color.GREEN);
                        }
                    }

                }
            });

            btn_option4.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("ResourceAsColor")
                @Override
                public void onClick(View v) {

                    btn_option1.setEnabled(false);
                    btn_option2.setEnabled(false);
                    btn_option3.setEnabled(false);
                    btn_option4.setEnabled(false);
                    countDownTimer.cancel();
                    timer_gif.setVisibility(View.GONE);
                    timer_img.setVisibility(View.VISIBLE);
                    btn_next.setVisibility(View.VISIBLE);

                    btn_option1.setTextColor(R.color.btn);
                    btn_option2.setTextColor(R.color.btn);
                    btn_option3.setTextColor(R.color.btn);
                    btn_option4.setTextColor(R.color.btn);

                    if (btn_option4.getText().equals("" + sans)) {

                        btn_next.setEnabled(true);
                        btn_option4.setTextColor(Color.GREEN);

                        try {
                            submitcurrectans();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        correct.setVisibility(View.VISIBLE);
                        wrong.setVisibility(View.GONE);

                        if (MyPrefrence.getsound().equals("on")) {

                            corectans.start();

                        }
                        btn_next.setVisibility(View.VISIBLE);

                    } else {

                        btn_next.setEnabled(true);
                        btn_option4.setTextColor(Color.RED);
                        correct.setVisibility(View.GONE);
                        wrong.setVisibility(View.VISIBLE);

                        if (MyPrefrence.getsound().equals("on")) {

                            wrongans.start();


                        }

                        if (btn_option1.getText().equals("" + sans)) {
                            btn_option1.setTextColor(Color.GREEN);
                        } else if (btn_option3.getText().equals("" + sans)) {
                            btn_option3.setTextColor(Color.GREEN);
                        } else if (btn_option2.getText().equals("" + sans)) {
                            btn_option2.setTextColor(Color.GREEN);
                        }


                    }

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    private void submitcurrectans() {

        //wallet
        Double intwallet = Double.parseDouble((String) wallet.getText());    //wallet amount convert to douhble
        Double wallmony = Double.parseDouble(MyPrefrence.getwalletmonyaddapi());  //wallet amount api conver to double

        Double doublewallet = intwallet + wallmony;          //wallet amount + api amount
        String formet = String.format("%.2f", doublewallet); // wallet amount formet set
        MyPrefrence.setaddwallets(formet);                   //wallet amount set myprefrence
        wallet.setText(String.format(MyPrefrence.getaddwallet()));//wallet amount set text


        //coin
        int left_time = Integer.parseInt(second_time);   //left time in ans
        int inttime = left_time / 1000; //time cover to number
        int timeint = inttime / 2;  //time left

        int intcoin = Integer.parseInt((String) coin.getText()); //coin to cocnver integer
        int doublecoin = intcoin + timeint; //coin + left_time
        String formetcoin = String.valueOf(doublecoin); //int cover to string
        MyPrefrence.setaddcoin(formetcoin); //save

        coin.setText(MyPrefrence.getaddcoin());// coin setText


    }


    /*time finish all moments*/
    @SuppressLint("ResourceAsColor")
    private void autoans() {

        if (btn_option1.getText().equals("" + sans)) {

            btn_option1.setTextColor(Color.GREEN);
            btn_option1.setEnabled(false);
            btn_option2.setEnabled(false);
            btn_option3.setEnabled(false);
            btn_option4.setEnabled(false);
            btn_option2.setTextColor(R.color.btn);
            btn_option3.setTextColor(R.color.btn);
            btn_option4.setTextColor(R.color.btn);
            btn_next.setEnabled(true);

        } else if (btn_option2.getText().equals("" + sans)) {

            btn_option2.setTextColor(Color.GREEN);
            btn_option1.setEnabled(false);
            btn_option2.setEnabled(false);
            btn_option3.setEnabled(false);
            btn_option4.setEnabled(false);
            btn_option1.setTextColor(R.color.btn);
            btn_option3.setTextColor(R.color.btn);
            btn_option4.setTextColor(R.color.btn);
            btn_next.setEnabled(true);

        } else if (btn_option3.getText().equals("" + sans)) {

            btn_option3.setTextColor(Color.GREEN);
            btn_option1.setEnabled(false);
            btn_option2.setEnabled(false);
            btn_option3.setEnabled(false);
            btn_option4.setEnabled(false);
            btn_option1.setTextColor(R.color.btn);
            btn_option2.setTextColor(R.color.btn);
            btn_option4.setTextColor(R.color.btn);
            btn_next.setEnabled(true);

        } else if (btn_option4.getText().equals("" + sans)) {

            btn_option4.setTextColor(Color.GREEN);
            btn_option1.setEnabled(false);
            btn_option2.setEnabled(false);
            btn_option3.setEnabled(false);
            btn_option4.setEnabled(false);
            btn_option1.setTextColor(R.color.btn);
            btn_option2.setTextColor(R.color.btn);
            btn_option3.setTextColor(R.color.btn);
            btn_next.setEnabled(true);


        }
    }


    @Override
    protected void onDestroy() {
        if (adView != null) {
            adView.destroy();
        }
        if (interstitialAd != null) {
            interstitialAd.destroy();
        }

        super.onDestroy();

    }

}
