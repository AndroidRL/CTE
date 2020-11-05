package com.example.counttoearn.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class PaymentModal implements Serializable {


    @SerializedName("status")
    @Expose
    public String status;

    @SerializedName("message")
    @Expose
    public String message;

    @SerializedName("paypal")
    @Expose
    public Paypal paypal;

    public class Paypal implements Serializable {

        @SerializedName("id")
        @Expose
        public String id;

        @SerializedName("name")
        @Expose
        public String name;

        @SerializedName("type")
        @Expose
        public String type;

    }


    @SerializedName("googlepay")
    @Expose
    public Googlepay googlepay;

    public class Googlepay implements Serializable {

        @SerializedName("id")
        @Expose
        public String id;

        @SerializedName("name")
        @Expose
        public String name;

        @SerializedName("type")
        @Expose
        public String type;

    }

    @SerializedName("phonepay")
    @Expose
    public PhonePay phonepay;

    public class PhonePay implements Serializable {

        @SerializedName("id")
        @Expose
        public String id;

        @SerializedName("name")
        @Expose
        public String name;

        @SerializedName("type")
        @Expose
        public String type;

    }

    @SerializedName("paytm")
    @Expose
    public PayTm paytm;

    public class PayTm implements Serializable {

        @SerializedName("id")
        @Expose
        public String id;

        @SerializedName("name")
        @Expose
        public String name;

        @SerializedName("type")
        @Expose
        public String type;

    }

}

