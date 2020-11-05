package com.example.counttoearn.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class UserDataDetailModel implements Serializable {

    @SerializedName("status")
    @Expose
    public String status;

    @SerializedName("howtoplay")
    @Expose
    public String howtoplay;

    @SerializedName("perpageads")
    @Expose
    public String perpageads;

    @SerializedName("showAds")
    @Expose
    public String showAds;

    @SerializedName("adsType")
    @Expose
    public String adsType;

    @SerializedName("bannerPerPage")
    @Expose
    public String bannerPerPage;

    @SerializedName("perPageInterstitial")
    @Expose
    public String perPageInterstitial;

 @SerializedName("answerTime")
    @Expose
    public String answerTime;


    @SerializedName("data")
    @Expose
    public userdata data;

    public class userdata implements Serializable {

        @SerializedName("id")
        @Expose
        public String id;

        @SerializedName("name")
        @Expose
        public String name;

        @SerializedName("email")
        @Expose
        public String email;

        @SerializedName("dob")
        @Expose
        public String dob;

        @SerializedName("country_code")
        @Expose
        public String country_code;

        @SerializedName("mobile")
        @Expose
        public String mobile;

        @SerializedName("profile_pic")
        @Expose
        public String profile_pic;


        @SerializedName("paypal")
        @Expose
        public String paypal;


        @SerializedName("paytm")
        @Expose
        public String paytm;

        @SerializedName("referral_code")
        @Expose
        public String referral_code;

        @SerializedName("wallet")
        @Expose
        public String wallet;

        @SerializedName("coin")
        @Expose
        public String coin;

        @SerializedName("level")
        @Expose
        public String level;


        @SerializedName("perQuestionEarn")
        @Expose
        public String perQuestionEarn;


    }
}
