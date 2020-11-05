package com.example.counttoearn.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
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

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {

    CircleImageView profile_imgs;
    TextView tv_name, tv_email, tv_dob, tv_mobile;
    String sname, semail, smobile, sdate, spaypal, spaytm, sphoto, userid;
    Button btn_update;
    private com.facebook.ads.RewardedVideoAd rewardedVideoAd;



    private AdView adView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        try {
            profile_imgs = findViewById(R.id.profile_img);

            tv_name = findViewById(R.id.tv_name);
            tv_email = findViewById(R.id.tv_email);
            tv_mobile = findViewById(R.id.tv_mobile);
            tv_dob = findViewById(R.id.tv_dob);
            btn_update = findViewById(R.id.btn_update);


            //stop  roted
            setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

            MyPrefrence.setactivitycall("1");

            Bundle bundle = getIntent().getExtras();

            if (bundle != null) {

                userid = bundle.getString("id");
                sname = bundle.getString("name");
                semail = bundle.getString("email");
                smobile = bundle.getString("mobile");
                sdate = bundle.getString("dob");
                spaypal = bundle.getString("paypal");
                spaytm = bundle.getString("paytm");
                sphoto = bundle.getString("profile_pic");

            }

            if (semail.equals("null")) {

                tv_email.setText("N/A");


            } else {
                tv_email.setText(semail);


            }

            if (sdate.equals("null")) {

                tv_dob.setText("N/A");


            } else {
                tv_dob.setText(sdate);


            }


            tv_name.setText(sname);
            tv_mobile.setText(smobile);


            Uri profile_img = Uri.parse(RestClient.IMAGE_URL + sphoto);
            Glide.with(this).load(profile_img).into(profile_imgs);


            btn_update.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    Intent intent1 = new Intent(ProfileActivity.this, UpdateActiovity.class);
                    intent1.putExtra("id", "" + userid);
                    intent1.putExtra("name", "" + sname);
                    intent1.putExtra("email", "" + semail);
                    intent1.putExtra("mobile", "" + smobile);
                    intent1.putExtra("dob", "" + sdate);
                    intent1.putExtra("paypal", "" + spaypal);
                    intent1.putExtra("paytm", "" + spaytm);
                    intent1.putExtra("profile_pic", "" + sphoto);

                    startActivityForResult(intent1, 426);


                }
            });


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

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    /*ok result*/
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 426) {
            if (resultCode == RESULT_OK) {

            }
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
