package com.example.counttoearn.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

import okio.Options;

public class QuestionsModel implements Serializable {


    @SerializedName("timeLimit")
    @Expose
    public String timeLimit;

    @SerializedName("question")
    @Expose
    public String question;

    @SerializedName("answer")
    @Expose
    public String answer;


    @SerializedName("option1")
    @Expose
    public String option1;
    @SerializedName("option2")
    @Expose
    public String option2;

    @SerializedName("option3")
    @Expose
    public String option3;

    @SerializedName("option4")
    @Expose
    public String option4;

     @SerializedName("coin")
    @Expose
    public String coin;

    @SerializedName("wallet")
    @Expose
    public String wallet;






}
