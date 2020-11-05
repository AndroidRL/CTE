package com.example.counttoearn.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.counttoearn.MyPrefrence;
import com.example.counttoearn.R;
import com.example.counttoearn.Util.Util;
import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdListener;
import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;
import com.facebook.ads.AudienceNetworkAds;
import com.facebook.ads.RewardedVideoAd;
import com.facebook.ads.RewardedVideoAdListener;

public class MoreAppActivity extends AppCompatActivity {

    private AdView adView;

    ImageView img, colorcrustimg, gitaimg, swipeimg;

    String imgURL = "https://lh3.googleusercontent.com/Gm4S0MaJ3oOrHpJ9azHhn8GkciH8tvteqU_CDvW3z4IBU-7-SkxyolH__a2UIcis2DPh=s180-rw";
    String link = "https://play.google.com/store/apps/details?id=com.phoenix.jewelmagic";

    String colorcrusturl = "https://lh3.googleusercontent.com/7RJMdhB6cVrTiL9ZicpuDbQ44LcIZEa1yIyLzfXuyP5GgOVXkUaNDD0wxmNUGTQeCcBl=s180-rw";
    String colorcrustlink = "https://play.google.com/store/apps/details?id=com.game.colorcrush";

    String swipelink = "https://play.google.com/store/apps/details?id=com.game.swipe2048";
    String swipeurl = "https://lh3.googleusercontent.com/uGkvEBTky-BzuVxiL8wJQ4c_GuhgX9v4_8-HB7HEeraiktdGigTxpo9L3MW-EKcTNZKT=s180-rw";


    String gitaurl = "https://lh3.googleusercontent.com/SD4NDbXK-f4GV0Ovg2U1gG9AxAJopz-cipOQpMfteHrJfRt3DBJ0gKr_8-r2bh5bl7LO=s180-rw";
    String gitalink = "https://play.google.com/store/apps/details?id=com.gita.bhagwatgita";


    private RewardedVideoAd rewardedVideoAd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moreapp);

        try {
            img = findViewById(R.id.img);
            colorcrustimg = findViewById(R.id.colorcrustimg);
            swipeimg = findViewById(R.id.swipeimg);
            gitaimg = findViewById(R.id.gitaimg);


            Glide.with(this).load(imgURL).into(img);
            Glide.with(this).load(colorcrusturl).into(colorcrustimg);
            Glide.with(this).load(swipeurl).into(swipeimg);
            Glide.with(this).load(gitaurl).into(gitaimg);

            img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Uri uriUrl = Uri.parse(link);
                    Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
                    startActivity(launchBrowser);

                }
            });


            //stop  roted
            setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

            colorcrustimg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Uri uriUrl = Uri.parse(colorcrustlink);
                    Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
                    startActivity(launchBrowser);

                }
            });

            swipeimg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    Uri uriUrl = Uri.parse(swipelink);
                    Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
                    startActivity(launchBrowser);

                }
            });
            gitaimg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    Uri uriUrl = Uri.parse(gitalink);
                    Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
                    startActivity(launchBrowser);

                }
            });


            MyPrefrence.setactivitycall("1");

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

            // Request an ad
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
        } catch (Exception e) {
            e.printStackTrace();
        }


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
