package com.example.counttoearn.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.counttoearn.Adepter.ToppayerAdapter;
import com.example.counttoearn.Model.TopplayerModel;
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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TopplayerActivity extends AppCompatActivity {

    RecyclerView rv;
    ToppayerAdapter toppayerAdapter;
    private AdView adView;
    private RewardedVideoAd rewardedVideoAd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topplayer);

        try {
            rv = findViewById(R.id.rv);

            //stop  roted
            setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
            rv.setLayoutManager(linearLayoutManager);

            toppayerAdapter = new ToppayerAdapter(this);
            rv.setAdapter(toppayerAdapter);

            gettopplayerlist(this);
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



    private void gettopplayerlist(TopplayerActivity topplayerActivity) {
        if (!Util.isInternetConnected(this)) {

            Intent intent = new Intent(TopplayerActivity.this, NoInternetActivity.class);
            startActivity(intent);

            return;
        }
        new RestClient(this).getInstance().get().gettopten().enqueue(new Callback<TopplayerModel>() {
            @Override
            public void onResponse(Call<TopplayerModel> call, Response<TopplayerModel> response) {

                if (response.body() != null) {

                    if (response.body().data != null) {

                        toppayerAdapter.addAll(response.body().data);

                    }else {


                    }




                }else {
                    Intent intent = new Intent(TopplayerActivity.this, NoInternetActivity.class);
                    startActivity(intent);


                }
            }

            @Override
            public void onFailure(Call<TopplayerModel> call, Throwable t) {

            }
        });
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
