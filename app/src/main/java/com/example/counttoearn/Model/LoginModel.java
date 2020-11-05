package com.example.counttoearn.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class LoginModel implements Serializable {


    @SerializedName("status")
    @Expose
    public String status;

    @SerializedName("message")
    @Expose
    public String message;

    @SerializedName("data")
    @Expose
    public login data;

    public class login implements Serializable {

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



        @SerializedName("level")
        @Expose
        public String level;





        @SerializedName("device_token")
        @Expose
        public String device_token;

        @SerializedName("status")
        @Expose
        public String status;

        @SerializedName("created")
        @Expose
        public String created;

        @SerializedName("modified")
        @Expose
        public String modified;


    }
}
