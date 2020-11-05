package com.example.counttoearn.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.counttoearn.MyPrefrence;
import com.example.counttoearn.R;
import com.example.counttoearn.Util.SoundService;
import com.example.counttoearn.Util.Util;
import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdListener;
import com.facebook.ads.AdSettings;
import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;
import com.facebook.ads.AudienceNetworkAds;
import com.facebook.ads.InterstitialAd;
import com.facebook.ads.InterstitialAdListener;
import com.facebook.ads.RewardedVideoAd;
import com.facebook.ads.RewardedVideoAdListener;
import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.google.android.play.core.tasks.OnSuccessListener;
import com.google.android.play.core.tasks.Task;

public class HowtoplayActivity extends AppCompatActivity {

    WebView webview;


    private AdView adView;
    TextView mainname;
    private InterstitialAd interstitialAd;

    private final String TAG = HowtoplayActivity.class.getSimpleName();
    private RewardedVideoAd rewardedVideoAd;
    AdListener adListener;


    AppUpdateManager appUpdateManager;
    int RequestUpdate = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_howtoplay);

        webview = findViewById(R.id.webview);
        mainname = findViewById(R.id.mainname);

        webview.loadUrl(MyPrefrence.gethowtoplay());
        MyPrefrence.setactivitycall("1");

        //stop  roted
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

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





}
