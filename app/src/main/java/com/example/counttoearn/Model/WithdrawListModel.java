package com.example.counttoearn.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class WithdrawListModel implements Serializable {

    @SerializedName("status")
    @Expose
    public String status;

     @SerializedName("message")
    @Expose
    public String message;

 @SerializedName("total_amount")
    @Expose
    public String total_amount;




    @SerializedName("history")
    @Expose
    public List<Withdrawlist> history;

    public class Withdrawlist implements Serializable {

        @SerializedName("wamount")
        @Expose
        public String wamount;

        @SerializedName("pname")
        @Expose
        public String pname;

        @SerializedName("wdate")
        @Expose
        public String wdate;

    }


}
