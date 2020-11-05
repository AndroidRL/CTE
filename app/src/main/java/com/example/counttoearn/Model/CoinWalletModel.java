package com.example.counttoearn.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CoinWalletModel implements Serializable {

    @SerializedName("success")
    @Expose
    public int success;
    @SerializedName("message")
    @Expose
    public String message;



    @SerializedName("data")
    @Expose
    public coinwallet data;

    public class coinwallet implements Serializable {

        @SerializedName("wallet")
        @Expose
        public String wallet;

        @SerializedName("coin")
        @Expose
        public String coin;
    }
}
