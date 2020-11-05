package com.example.counttoearn.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.loader.content.CursorLoader;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.airbnb.lottie.ImageAssetDelegate;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.counttoearn.Model.CommonModel;
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
import com.google.android.gms.common.internal.Constants;

import net.gotev.uploadservice.MultipartUploadRequest;
import net.gotev.uploadservice.UploadNotificationConfig;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;
import in.mayanknagwanshi.imagepicker.ImageSelectActivity;
import in.mayanknagwanshi.imagepicker.imagePicker.ImagePicker;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.Multipart;

public class UpdateActiovity extends AppCompatActivity {

    EditText et_name, et_email, et_mobile, et_date;
    Button btn_update, btn_date;
    String sname, semail, smobile, sdate, sphoto;
    String aname, aemail, adate;
    String userid;
    CircleImageView img_view;
    int d, m, y;
    Calendar calendar = Calendar.getInstance();
    BitmapFactory.Options options = new BitmapFactory.Options();
    Dialog dialog;
    String imageString;
    int PHOTO_CODE = 12321;
    RelativeLayout btn_picker;


    private AdView adView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_actiovity);

        try {
            et_name = findViewById(R.id.et_name);
            et_email = findViewById(R.id.et_email);
            et_mobile = findViewById(R.id.et_mobile);
            et_date = findViewById(R.id.et_date);
            btn_update = findViewById(R.id.btn_update);
            btn_date = findViewById(R.id.btn_date);
            img_view = findViewById(R.id.img_view);
            btn_picker = findViewById(R.id.btn_picker);

            options.inSampleSize = 4;
            options.inPurgeable = true;


            //stop  roted
            setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


            Bundle bundle = getIntent().getExtras();
            if (bundle != null) {

                userid = bundle.getString("id");
                sname = bundle.getString("name");
                semail = bundle.getString("email");
                smobile = bundle.getString("mobile");
                sdate = bundle.getString("dob");

                sphoto = bundle.getString("profile_pic");

            }
            Uri profilePic = Uri.parse(RestClient.IMAGE_URL + sphoto);
            Glide.with(this).load(profilePic).into(img_view);


            if (semail.equals("null")) {

                et_email.getText().clear();

            } else {

                et_email.setText(semail);

            }

            if (sdate.equals("null")) {
                et_date.getText().clear();
            } else {
                et_date.setText(sdate);
            }

            et_name.setText(sname);
            et_mobile.setText(smobile);




            /*DATE PICKER*/
            btn_picker.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    y = calendar.get(Calendar.YEAR);
                    m = calendar.get(Calendar.MONTH);
                    d = calendar.get(Calendar.DAY_OF_MONTH);


                    DatePickerDialog datePickerDialog = new DatePickerDialog(UpdateActiovity.this, new DatePickerDialog.OnDateSetListener() {
                        @SuppressLint("SetTextI18n")
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {


                            et_date.setText(dayOfMonth + "-" + (month + 1) + "-" + year);

                            adate = "" + year + "-" + (month + 1) + "-" + dayOfMonth;

                        }
                    }, y, m, d);


                    datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis() - 1000);
                    datePickerDialog.show();
                }
            });

            /*IMG PICKER BUTTON*/
            img_view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(UpdateActiovity.this, ImageSelectActivity.class);
                    intent.putExtra(ImageSelectActivity.FLAG_COMPRESS, false);//default is true
                    intent.putExtra(ImageSelectActivity.FLAG_CAMERA, false);//default is true
                    intent.putExtra(ImageSelectActivity.FLAG_GALLERY, true);//default is true
                    startActivityForResult(intent, PHOTO_CODE);


    //                Intent intent = new Intent();
    //                intent.setType("image/*");
    //                intent.setAction(Intent.ACTION_GET_CONTENT);
    //                startActivityForResult(intent, PHOTO_CODE);


                    /*sd card permistin*/
    //                ActivityCompat.requestPermissions(UpdateActiovity.this,
    //                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
    //                        1);


                }
            });

            /*UPDATE PROFILE*/
            btn_update.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    aemail = et_email.getText().toString();
                    aname = et_name.getText().toString();


                    if (sname.equals("")) {

                        et_name.setError("Please Enter Name");

                    } else if (!Util.isValidEmailAddress(aemail)) {

                        et_email.setError("Invalid Email");

                    } else {

                        update(this);

                    }

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

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    /*OK RESULT PHOTO_CODE CAMERA */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PHOTO_CODE && resultCode == Activity.RESULT_OK) {

//            String filePath = data.getStringExtra(ImageSelectActivity.RESULT_FILE_PATH);


            String filePath = data.getStringExtra(ImageSelectActivity.RESULT_FILE_PATH);

//



            Uri uri = Uri.parse(filePath);

//            Uri selectedImageUri = data.getData();
            img_view.setImageURI(uri);


            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            Bitmap bm = BitmapFactory.decodeFile(filePath);
            bm.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
            byte[] imagebytes = byteArrayOutputStream.toByteArray();
            imageString = Base64.encodeToString(imagebytes, Base64.DEFAULT);







        }

    }

    /*IPDATE API CALL*/
    private void update(View.OnClickListener onClickListener) {

        if (!Util.isInternetConnected(this)) {

            Intent intent = new Intent(UpdateActiovity.this, NoInternetActivity.class);
            startActivity(intent);

            return;
        }


        dialog = Util.startLoader(this);

        new RestClient(this).getInstance().get().updateprofiles(aname, aemail, adate, imageString, userid).enqueue(new Callback<CommonModel>() {
            @Override
            public void onResponse(Call<CommonModel> call, Response<CommonModel> response) {
                Util.stopLoader(dialog);
                if (response.body() != null) {
                    startActivity(new Intent(UpdateActiovity.this, MainActivity.class));
                    Toast.makeText(UpdateActiovity.this, "" + response.body().message, Toast.LENGTH_SHORT).show();
                } else {

                    Toast.makeText(UpdateActiovity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CommonModel> call, Throwable t) {
                Util.stopLoader(dialog);
            }
        });


    }

    /*ONBACK PRESS METHOD*/
    @Override
    public void onBackPressed() {
        setResult(RESULT_OK);
        super.onBackPressed();
    }

    public void back(View view) {
        onBackPressed();
    }



    @Override
    protected void onDestroy() {
        if (adView != null) {
            adView.destroy();
        }
        super.onDestroy();

    }

    /* STOREGE PERMISSTIONS*/
//    @Override
//    public void onRequestPermissionsResult(int requestCode,
//                                           String permissions[], int[] grantResults) {
//        switch (requestCode) {
//            case 1: {
//
//                // If request is cancelled, the result arrays are empty.
//                if (grantResults.length > 0
//                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//
//                    // permission was granted, yay! Do the
//                    // contacts-related task you need to do.
//
//
//
//                } else {
//
//                    // permission denied, boo! Disable the
//                    // functionality that depends on this permission.
//                    Toast.makeText(UpdateActiovity.this, "Permission denied to read your External storage", Toast.LENGTH_SHORT).show();
//                }
//                return;
//            }
//
//            // other 'case' lines to check for other
//            // permissions this app might request
//        }
//    }


}


