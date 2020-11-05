package com.example.counttoearn.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.counttoearn.Model.CommonModel;
import com.example.counttoearn.Model.UserDataDetailModel;
import com.example.counttoearn.MyPrefrence;
import com.example.counttoearn.R;
import com.example.counttoearn.Service.RestClient;
import com.example.counttoearn.Util.NoInternetActivity;
import com.example.counttoearn.Util.SoundService;
import com.example.counttoearn.Util.Util;
import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdListener;
import com.facebook.ads.AdSettings;
import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;
import com.facebook.ads.AudienceNetworkAds;
import com.facebook.ads.RewardedVideoAd;
import com.facebook.ads.RewardedVideoAdListener;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.google.android.play.core.review.ReviewInfo;
import com.google.android.play.core.review.ReviewManager;
import com.google.android.play.core.review.ReviewManagerFactory;
import com.google.android.play.core.tasks.OnSuccessListener;
import com.google.firebase.dynamiclinks.DynamicLink;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
import com.google.firebase.dynamiclinks.PendingDynamicLinkData;
import com.google.firebase.dynamiclinks.ShortDynamicLink;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {

    LinearLayout all, moreapp, share, btn_update;
    TextView name, refereal, wallet, coin;
    LinearLayout addition, subtraction, multiplication, division;
    CircleImageView profile_img;
    String user_id, apicoin, apiwallet;
    String iid, iname, iemail, imobile, idob, ipaypal, ipaytm, iprofile_pic;
    String sound1, referralCode;


    private AdView adView;

    String level;

    AppUpdateManager appUpdateManager;
    int RequestUpdate = 1;

    private ReviewManager manager;
    ReviewInfo reviewInfo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            all = findViewById(R.id.all);
            moreapp = findViewById(R.id.moreapp);
            share = findViewById(R.id.share);
            name = findViewById(R.id.name);
            refereal = findViewById(R.id.refereal);
            btn_update = findViewById(R.id.btn_update);
            wallet = findViewById(R.id.wallet);
            coin = findViewById(R.id.coin);
            addition = findViewById(R.id.addition);
            subtraction = findViewById(R.id.subtration);
            multiplication = findViewById(R.id.multiplication);
            division = findViewById(R.id.devision);
            profile_img = findViewById(R.id.profile_img);


            user_id = MyPrefrence.getuserid();
            level = MyPrefrence.getgamelevel();

            /*facebook test id set in code*/
//            AdSettings.addTestDevice("343142aa-65ce-4ea8-9d93-580dab825c4f");
//            AdSettings.addTestDevice("78ceff8f-ecdc-41ac-b0ad-7f5831512d15");
//            AdSettings.addTestDevice("bad83a43-0231-4974-a6ba-6df283f7b0ae");

            //stop  roted
            setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

            /*google update code*/
            AppUpdateManager appUpdateManager = AppUpdateManagerFactory.create(MainActivity.this);

            com.google.android.play.core.tasks.Task<AppUpdateInfo> appUpdateInfoTask = appUpdateManager.getAppUpdateInfo();

            appUpdateInfoTask.addOnSuccessListener(new OnSuccessListener<AppUpdateInfo>() {
                @Override
                public void onSuccess(AppUpdateInfo result) {


                    if ((result.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE) && result.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)) {

                        try {

                            appUpdateManager.startUpdateFlowForResult(result, AppUpdateType.IMMEDIATE, MainActivity.this, RequestUpdate);


                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }

                }
            });



            /*google review code*/

            manager = ReviewManagerFactory.create(MainActivity.this);
            com.google.android.play.core.tasks.Task<ReviewInfo> request = manager.requestReviewFlow();
            request.addOnCompleteListener(new com.google.android.play.core.tasks.OnCompleteListener<ReviewInfo>() {
                @Override
                public void onComplete(com.google.android.play.core.tasks.Task<ReviewInfo> task) {

                    if (task.isSuccessful()) {

                        reviewInfo = task.getResult();
                        com.google.android.play.core.tasks.Task<Void> flow
                                = manager.launchReviewFlow(MainActivity.this,reviewInfo);

                        flow.addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void result) {

                            }
                        });


                    } else {

                        Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
                    }


                }
            });




            if (MyPrefrence.getactivitycall().equals("1")) {

                getuserdata(MainActivity.this, user_id);

            } else {

                apicoin = MyPrefrence.getaddcoin();
                apiwallet = MyPrefrence.getaddwallet();


                submitCoinWallet(MainActivity.this, user_id, apicoin, apiwallet);

                MyPrefrence.setactivitycall("1");

            }

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


            /*RATING*/
//            AppRate.with(this)
//                    .setInstallDays(0)     //ONE DAY HOW MANY SHOW DIALOG
//                    .setLaunchTimes(2)        //APP REMOVIWE 2 TIME AND SHOW DIALOG
//                    .setRemindInterval(2)        //CLICK NO THANK ANFTER 2 DAY SHOW DIALOG
//                    .monitor();
//
//            AppRate.showRateDialogIfMeetsConditions(this);    //SHOW DIALOG

            sound1 = "on";
            MyPrefrence.setsound(sound1);
            stopService(new Intent(MainActivity.this, SoundService.class));


            all.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MyPrefrence.setaddwallets((String) wallet.getText());
                    MyPrefrence.setaddcoin((String) coin.getText());

                    Intent in = new Intent(MainActivity.this, QuestionScreenActivity.class);
                    in.putExtra("operation", "combo");
                    in.putExtra("level", "" + level);
                    startActivityForResult(in, 426);

                }
            });


            addition.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MyPrefrence.setaddwallets((String) wallet.getText());
                    MyPrefrence.setaddcoin((String) coin.getText());


                    Intent in = new Intent(MainActivity.this, QuestionScreenActivity.class);
                    in.putExtra("operation", "addition");
                    in.putExtra("level", "" + level);
                    startActivityForResult(in, 426);


                }
            });

            subtraction.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MyPrefrence.setaddwallets((String) wallet.getText());
                    MyPrefrence.setaddcoin((String) coin.getText());

                    Intent in = new Intent(MainActivity.this, QuestionScreenActivity.class);
                    in.putExtra("operation", "subtraction");
                    in.putExtra("level", "" + level);
                    startActivityForResult(in, 426);

                }
            });

            multiplication.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MyPrefrence.setaddwallets((String) wallet.getText());
                    MyPrefrence.setaddcoin((String) coin.getText());

                    Intent in = new Intent(MainActivity.this, QuestionScreenActivity.class);
                    in.putExtra("operation", "multiplication");
                    in.putExtra("level", "" + level);
                    startActivityForResult(in, 426);

                }
            });
            division.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MyPrefrence.setaddwallets((String) wallet.getText());
                    MyPrefrence.setaddcoin((String) coin.getText());


                    Intent in = new Intent(MainActivity.this, QuestionScreenActivity.class);
                    in.putExtra("operation", "division");
                    in.putExtra("level", "" + level);
                    startActivityForResult(in, 426);

                }
            });

            share.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    /*THIS IS DYNAMIC LINK CREAT*/

                    DynamicLink dynamicLink = FirebaseDynamicLinks.getInstance().createDynamicLink()
                            .setLink(Uri.parse("https://www.phoenixlab.in/"))
                            .setDynamicLinkDomain("counttoearn.page.link")
                            // Open links with this app on Android
                            .setAndroidParameters(new DynamicLink.AndroidParameters.Builder().build())
                            // Open links with com.example.ios on iOS
                            .setIosParameters(new DynamicLink.IosParameters.Builder("com.example.ios").build())
                            .buildDynamicLink();

                    Uri dynamicLinkUri = dynamicLink.getUri();


                    /*THIS IS MANUAL SET URL AND OTHER DITAIL*/

                    String shareLinkText = "https://counttoearn.page.link/?" +                  /*FIRE BASE LINK LINK*/
                            "link=https://www.phoenixlab.in/" +                               /*WEBSITE LINK*/
                            "&apn=" + getPackageName() +                                            /*SHARE ANY PRODUT OR APPLICATION LINK*/
                            "&st=" + "Count To Earn" +                                              /*YOUR ANY NAME*/
                            "&sd=" + "Add referral code is " + referralCode + " and earn Upto Rs.10" +  /*ANY DISCREEPCTON LINK*/
                            "&si=" + "http://162.241.174.134/assets/img/logo.png";                   /*PHOTO LINK*/

                    /*SHORT LINK SHARE CODE */
                    Task<ShortDynamicLink> shortLinkTask = FirebaseDynamicLinks.getInstance().createDynamicLink()
                            //                        .setLongLink(dynamicLink.getUri()) /*THIS IS DYNAMIC LINK CREAT*/
                            .setLongLink(Uri.parse(shareLinkText)) /*THIS IS MANUAL SET URL AND OTHER DITAIL*/
                            .buildShortDynamicLink()
                            .addOnCompleteListener(MainActivity.this, new OnCompleteListener<ShortDynamicLink>() {
                                @Override
                                public void onComplete(@NonNull Task<ShortDynamicLink> task) {
                                    if (task.isSuccessful()) {
                                        // Short link created
                                        Uri shortLink = task.getResult().getShortLink();
                                        Uri flowchartLink = task.getResult().getPreviewLink();
                                        //                                    Log.e("short", "" + shortLink);
                                        String msg = "Hey, I earned Rs.200 today. Do you want to earn extra money from any where? Download this and start earning.";
                                        Intent intent = new Intent();
                                        intent.setAction(Intent.ACTION_SEND);
                                        intent.putExtra(Intent.EXTRA_TEXT, shortLink.toString());
                                        //                                    intent.putExtra(Intent.EXTRA_TITLE,msg);
                                        intent.setType("text/plain");
                                        startActivity(intent);


                                    } else {


                                    }
                                }
                            });
                }
            });

            btn_update.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    Intent intent1 = new Intent(MainActivity.this, ProfileActivity.class);
                    intent1.putExtra("id", "" + iid);
                    intent1.putExtra("name", "" + iname);
                    intent1.putExtra("email", "" + iemail);
                    intent1.putExtra("mobile", "" + imobile);
                    intent1.putExtra("dob", "" + idob);
                    intent1.putExtra("paypal", "" + ipaypal);
                    intent1.putExtra("paytm", "" + ipaytm);
                    intent1.putExtra("profile_pic", "" + iprofile_pic);
                    startActivityForResult(intent1, 426);


                }
            });


        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    /*ok result*/
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 426) {
//            getuserdata(MainActivity.this, user_id);

            if (MyPrefrence.getactivitycall().equals("1")) {

                getuserdata(MainActivity.this, user_id);
            } else {

                apicoin = MyPrefrence.getaddcoin();
                apiwallet = MyPrefrence.getaddwallet();
                submitCoinWallet(MainActivity.this, user_id, apicoin, apiwallet);
                getuserdata(MainActivity.this, user_id);

                MyPrefrence.setactivitycall("1");

            }


        }

        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == RequestUpdate) {

            Toast.makeText(this, "Start Download", Toast.LENGTH_SHORT).show();

            if (requestCode != RequestUpdate) {

            }
        }
    }


    private void submitCoinWallet(MainActivity mainActivity, String user_id, String apicoin, String apiwallet) {


        if (!Util.isInternetConnected(this)) {

            Intent intent = new Intent(MainActivity.this, NoInternetActivity.class);
            startActivityForResult(intent, 426);

            return;
        }
        Dialog dialog = Util.startLoader(this);

        new RestClient(this).getInstance().get().submitCoinWallet(user_id, apicoin, apiwallet).enqueue(new Callback<CommonModel>() {
            @Override
            public void onResponse(Call<CommonModel> call, Response<CommonModel> response) {

                Util.stopLoader(dialog);

                if (response.body() != null) {
                    getuserdata(MainActivity.this, user_id);

                } else {


                }

            }

            @Override
            public void onFailure(Call<CommonModel> call, Throwable t) {
                Util.stopLoader(dialog);

            }
        });


    }


    private void getuserdata(MainActivity mainActivity, String user_id) {

        if (!Util.isInternetConnected(this)) {

            Intent intent = new Intent(MainActivity.this, NoInternetActivity.class);
            startActivityForResult(intent, 426);

            return;
        }
        Dialog dialog = Util.startLoader(this);
        new RestClient(this).getInstance().get().getuserdetail(this.user_id).enqueue(new Callback<UserDataDetailModel>() {
            @Override
            public void onResponse(Call<UserDataDetailModel> call, Response<UserDataDetailModel> response) {

                Util.stopLoader(dialog);

                if (response.body() != null) {

                    Uri profilePic = Uri.parse(RestClient.IMAGE_URL + response.body().data.profile_pic);
                    Glide.with(MainActivity.this).load(profilePic).into(profile_img);

                    name.setText(response.body().data.name);
                    coin.setText(response.body().data.coin);
                    wallet.setText(response.body().data.wallet);

                    MyPrefrence.setaddwallets(response.body().data.wallet);
                    MyPrefrence.setaddcoin(response.body().data.coin);

                    MyPrefrence.setwallet(response.body().data.wallet);

                    refereal.setText(response.body().data.referral_code);
                    referralCode = response.body().data.referral_code;
                    iid = response.body().data.id;
                    iname = response.body().data.name;
                    iemail = response.body().data.email;
                    imobile = response.body().data.mobile;
                    idob = response.body().data.dob;
                    ipaypal = response.body().data.paypal;
                    ipaytm = response.body().data.paytm;
                    iprofile_pic = response.body().data.profile_pic;
                    MyPrefrence.setgamelevel(response.body().data.level);
                    MyPrefrence.setperPageAd(response.body().perpageads);
                    MyPrefrence.setshowAds(response.body().showAds);

                    MyPrefrence.setuserid(response.body().data.id);
                    MyPrefrence.sethowtoplay(response.body().howtoplay);
                    MyPrefrence.setwalletmonyaddapi(response.body().data.perQuestionEarn);

                    MyPrefrence.setadsType(response.body().adsType);
                    MyPrefrence.setbannerPerPage(response.body().bannerPerPage);
                    MyPrefrence.setperPageInterstitial(response.body().perPageInterstitial);
                    MyPrefrence.setanswerTime(response.body().answerTime);


                } else {

                }


            }

            @Override
            public void onFailure(Call<UserDataDetailModel> call, Throwable t) {
                Util.stopLoader(dialog);

            }
        });
    }


    /*copy*/
    public void copy(View view) {
        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("Refereal Code", MyPrefrence.getreferralcode());
        clipboard.setPrimaryClip(clip);
        Toast toast = Toast.makeText(MainActivity.this, "Copy Succcess!", Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();

    }

    /*logout*/
    public void logout(View view) {

        MyPrefrence.setulogin(false);
        startActivity(new Intent(MainActivity.this, LoginActivity.class));
        finish();
    }


    /*backpress*/
    boolean staus = false;


    @Override
    public void onBackPressed() {

        if (staus) {
//            super.onBackPressed();

            ActivityCompat.finishAffinity(MainActivity.this);

        } else {

            Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Please press again to exit", Snackbar.LENGTH_LONG);
            snackbar.show();
            staus = true;
        }

    }

    public void howtoplay(View view) {
        startActivity(new Intent(MainActivity.this, HowtoplayActivity.class));
    }

    public void toplayer(View view) {

        startActivity(new Intent(MainActivity.this, TopplayerActivity.class));

    }


    public void withdraw(View view) {

        Intent intent = new Intent(MainActivity.this, WithdrawActivity.class);
        startActivityForResult(intent, 426);

    }

    public void moreapp(View view) {


        startActivity(new Intent(MainActivity.this, MoreAppActivity.class));

    }


    @Override
    protected void onDestroy() {
        if (adView != null) {
            adView.destroy();
        }


        super.onDestroy();
    }

    public void watchearncoin(View view) {
        Intent intent = new Intent(MainActivity.this, WatchCoinActivity.class);
        startActivityForResult(intent, 426);

    }


}