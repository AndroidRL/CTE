package com.example.counttoearn.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class TopplayerModel implements Serializable {

    @SerializedName("status")
    @Expose
    public String status;

    @SerializedName("data")
    @Expose
    public List<Topplayer> data;

    public class Topplayer implements Serializable {

        @SerializedName("name")
        @Expose
        public String name;

        @SerializedName("profile_pic")
        @Expose
        public String profile_pic;

        @SerializedName("coin")
        @Expose
        public String coin;

    }


}
