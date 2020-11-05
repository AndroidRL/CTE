package com.example.counttoearn.Util;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;


import com.example.counttoearn.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Util {


    public static boolean isValidEmailAddress(String emailAddress) {

        String expression = "[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})";
        CharSequence inputStr = emailAddress;
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        return matcher.matches();
    }

    public static boolean isInternetConnected(Context mContext) {

        try {
            ConnectivityManager connect = null;
            connect = (ConnectivityManager) mContext
                    .getSystemService(Context.CONNECTIVITY_SERVICE);

            if (connect != null) {
                NetworkInfo resultMobile = connect
                        .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

                NetworkInfo resultWifi = connect
                        .getNetworkInfo(ConnectivityManager.TYPE_WIFI);

                if ((resultMobile != null && resultMobile
                        .isConnectedOrConnecting())
                        || (resultWifi != null && resultWifi
                        .isConnectedOrConnecting())) {
                    return true;
                } else {
                    return false;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public static void hideSoftKeyword(Activity activity) {

        if (activity.getCurrentFocus() != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
        }

    }

    public static void showSoftKeyword(Activity activity, View view) {

        if (activity.getCurrentFocus() != null) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
        }

    }

    public static Dialog startLoader(final Context context) {
        final Dialog d = new Dialog(context);
        d.requestWindowFeature(Window.FEATURE_NO_TITLE);
        d.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        d.setContentView(R.layout.pogresbar);
        d.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        d.setCancelable(false);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(d.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        d.show();
        d.getWindow().setAttributes(lp);

        return d;
    }

    public static void stopLoader(final Dialog d) {
        if (d != null && d.isShowing()) {
            d.cancel();
            //d.hide();
        }
    }







    public static boolean isEmpty(String str) {

        if (!TextUtils.isEmpty(str))

            return TextUtils.isEmpty(str.trim());

        return TextUtils.isEmpty(str);

    }





}
