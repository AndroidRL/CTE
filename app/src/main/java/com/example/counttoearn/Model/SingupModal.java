package com.example.counttoearn.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SingupModal implements Serializable {

    @SerializedName("success")
    @Expose
    public int success;
    @SerializedName("message")
    @Expose
    public String message;

    @SerializedName("user_id")
    @Expose
    public String user_id;

     @SerializedName("level")
    @Expose
    public String level;






}
