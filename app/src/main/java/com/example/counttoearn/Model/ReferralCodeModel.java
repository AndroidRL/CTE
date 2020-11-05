package com.example.counttoearn.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ReferralCodeModel implements Serializable {

    @SerializedName("valid")
    @Expose
    public Boolean valid;
    @SerializedName("message")
    @Expose
    public String message;

}
