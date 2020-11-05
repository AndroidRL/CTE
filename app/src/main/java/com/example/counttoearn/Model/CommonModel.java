package com.example.counttoearn.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CommonModel implements Serializable {

    @SerializedName("success")
    @Expose
    public int success;
    @SerializedName("message")
    @Expose
    public String message;

}
