package com.example.counttoearn;

import android.app.Application;
import android.content.SharedPreferences;

public class MyPrefrence extends Application {

    public static SharedPreferences sharedPreferences;
    public static SharedPreferences.Editor editor;


    @Override
    public void onCreate() {
        super.onCreate();
        sharedPreferences = getSharedPreferences("my", MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public static void setulogin(boolean ulogin) {
        editor.putBoolean("ulogin", ulogin).commit();
    }

    public static boolean getulogin() {
        return sharedPreferences.getBoolean("ulogin", false);
    }

    public static void setname(String name) {
        editor.putString("name", name).commit();
    }

    public static String getname() {
        return sharedPreferences.getString("name", "");
    }

    public static void setcountrycode(String countrycode) {
        editor.putString("countrycode", countrycode).commit();
    }

    public static String getcountrycode() {
        return sharedPreferences.getString("countrycode", "");
    }

    public static void setreferralcode(String referralcode) {
        editor.putString("referralcode", referralcode).commit();
    }

    public static String getreferralcode() {
        return sharedPreferences.getString("referralcode", "");
    }

    public static void setmobile(String mobile) {
        editor.putString("mobile", mobile).commit();
    }

    public static String getmobile() {
        return sharedPreferences.getString("mobile", "");
    }

    public static void settimelimit(String timelimit) {
        editor.putString("timelimit", timelimit).commit();
    }

    public static String gettimelimit() {
        return sharedPreferences.getString("timelimit", "");
    }


    public static void setuserid(String userid) {
        editor.putString("userid", userid).commit();
    }

    public static String getuserid() {
        return sharedPreferences.getString("userid", "");
    }


    public static void setsound(String sound) {
        editor.putString("sound", sound).commit();
    }

    public static String getsound() {
        return sharedPreferences.getString("sound", "");
    }


    public static void setadsname(String adsname) {
        editor.putString("adsname", adsname).commit();
    }

    public static String getadsname() {
        return sharedPreferences.getString("adsname", "");
    }

    public static void setbannerid(String bannerid) {
        editor.putString("bannerid", bannerid).commit();
    }

    public static String getbannerid() {
        return sharedPreferences.getString("bannerid", "");
    }

    public static void setadsappid(String adsappid) {
        editor.putString("adsappid", adsappid).commit();
    }

    public static String getadsappid() {
        return sharedPreferences.getString("adsappid", "");
    }

    public static void setperPageAd(String perPageAd) {
        editor.putString("perPageAd", perPageAd).commit();
    }

    public static String getperPageAd() {
        return sharedPreferences.getString("perPageAd", "");
    }

    public static void setwatchVideo(String watchVideo) {
        editor.putString("watchVideo", watchVideo).commit();
    }

    public static String getwatchVideo() {
        return sharedPreferences.getString("watchVideo", "");
    }



    public static void setadcountquestion(String adcountquestion) {
        editor.putString("adcountquestion", adcountquestion).commit();
    }

    public static String getadcountquestion() {
        return sharedPreferences.getString("adcountquestion", "");
    }

    public static void setwallet(String wallet) {
        editor.putString("wallet", wallet).commit();
    }

    public static String getwallet() {
        return sharedPreferences.getString("wallet", "");
    }

    public static void sethowtoplay(String howtoplay) {
        editor.putString("howtoplay", howtoplay).commit();
    }

    public static String gethowtoplay() {
        return sharedPreferences.getString("howtoplay", "");
    }

    public static void setotpsendclick(String otpsendclick) {
        editor.putString("otpsendclick", otpsendclick).commit();
    }

    public static String getotpsendclick() {
        return sharedPreferences.getString("otpsendclick", "");
    }


    public static void setWatchTime(String watchTime) {
        editor.putString("watchTime", watchTime).commit();
    }

    public static String getWatchTime() {
        return sharedPreferences.getString("watchTime", "");
    }


    public static void setgamelevel(String gamelevel) {
        editor.putString("gamelevel", gamelevel).commit();
    }

    public static String getgamelevel() {
        return sharedPreferences.getString("gamelevel", "");
    }


    public static void setshowAds(String showAds) {
        editor.putString("showAds", showAds).commit();
    }

    public static String getshowAds() {
        return sharedPreferences.getString("showAds", "");
    }

    public static void setaddwallets(String addwallet) {
        editor.putString("addwallet", addwallet).commit();
    }

    public static String getaddwallet() {
        return sharedPreferences.getString("addwallet", "");
    }

    public static void setaddcoin(String addcoin) {
        editor.putString("addcoin", addcoin).commit();
    }

    public static String getaddcoin() {
        return sharedPreferences.getString("addcoin", "");
    }

    public static void setwalletmonyaddapi(String walletmonyaddapi) {
        editor.putString("walletmonyaddapi", walletmonyaddapi).commit();
    }

    public static String getwalletmonyaddapi() {
        return sharedPreferences.getString("walletmonyaddapi", "");
    }

    public static void setactivitycall(String activitycall) {
        editor.putString("activitycall", activitycall).commit();
    }

    public static String getactivitycall() {
        return sharedPreferences.getString("activitycall", "");
    }

    public static void setadsType(String adsType) {
        editor.putString("adsType", adsType).commit();
    }

    public static String getadsType() {
        return sharedPreferences.getString("adsType", "");
    }

    public static void setbannerPerPage(String bannerPerPage) {
        editor.putString("bannerPerPage", bannerPerPage).commit();
    }

    public static String getbannerPerPage() {
        return sharedPreferences.getString("bannerPerPage", "");
    }

  public static void setperPageInterstitial(String perPageInterstitial) {
        editor.putString("perPageInterstitial", perPageInterstitial).commit();
    }

    public static String getperPageInterstitial() {
        return sharedPreferences.getString("perPageInterstitial", "");
    }
 public static void setanswerTime(String answerTime) {
        editor.putString("answerTime", answerTime).commit();
    }

    public static String getanswerTime() {
        return sharedPreferences.getString("answerTime", "");
    }


}




