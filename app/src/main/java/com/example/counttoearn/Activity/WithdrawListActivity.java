package com.example.counttoearn.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.counttoearn.Adepter.ToppayerAdapter;
import com.example.counttoearn.Adepter.WithdrawAdapter;
import com.example.counttoearn.Model.WithdrawListModel;
import com.example.counttoearn.MyPrefrence;
import com.example.counttoearn.R;
import com.example.counttoearn.Service.RestClient;
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

public class WithdrawListActivity extends AppCompatActivity {

    TextView tv_totalamount;
    RecyclerView rv;
    WithdrawAdapter withdrawAdapter;
    private AdView adView;
    private com.facebook.ads.RewardedVideoAd rewardedVideoAd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdraw_list);

        try {


            tv_totalamount = findViewById(R.id.tv_totalamount);
            rv = findViewById(R.id.rv);

            MyPrefrence.getuserid();

            //stop  roted
            setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
            rv.setLayoutManager(linearLayoutManager);

            withdrawAdapter = new WithdrawAdapter(this);
            rv.setAdapter(withdrawAdapter);

            withdrawlist(this);



            //banner ad
            AudienceNetworkAds.initialize(this);
            adView = new AdView(this, "2535402093439162_2535591366753568", AdSize.BANNER_HEIGHT_50);
            LinearLayout adContainer = (LinearLayout) findViewById(R.id.banner_container);
            adContainer.addView(adView);
            adView.loadAd();




            AdListener adListener = new AdListener() {
                @Override
                public void onError(Ad ad1, AdError adError) {
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


            Videoadmethod(WithdrawListActivity.this);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void withdrawlist(WithdrawListActivity withdrawListActivity) {

        new RestClient(this).getInstance().get().withdrawlist(MyPrefrence.getuserid()).enqueue(new Callback<WithdrawListModel>() {
            @Override
            public void onResponse(Call<WithdrawListModel> call, Response<WithdrawListModel> response) {

                if (response.body() != null) {

                    tv_totalamount.setText(response.body().total_amount);

                    if (response.body().history != null){

                        withdrawAdapter.addAll(response.body().history);


                    }else {


                    }



                }else {

                    Toast.makeText(withdrawListActivity, "Something went wrong", Toast.LENGTH_SHORT).show();

                }


            }

            @Override
            public void onFailure(Call<WithdrawListModel> call, Throwable t) {
                Toast.makeText(withdrawListActivity, ""+t, Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public void onBackPressed() {
        setResult(RESULT_OK);
        super.onBackPressed();
    }




    private void Videoadmethod(WithdrawListActivity withdrawListActivity) {

        Dialog d = Util.startLoader(this);


        // video ads
        AudienceNetworkAds.initialize(WithdrawListActivity.this);
        rewardedVideoAd = new RewardedVideoAd(WithdrawListActivity.this, "2535402093439162_2535592480086790");
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
