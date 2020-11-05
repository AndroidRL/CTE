package com.example.counttoearn.Service;

import com.example.counttoearn.Model.CommonModel;
import com.example.counttoearn.Model.LoginModel;
import com.example.counttoearn.Model.PaymentModal;
import com.example.counttoearn.Model.QuestionsModel;
import com.example.counttoearn.Model.ReferralCodeModel;
import com.example.counttoearn.Model.SingupModal;
import com.example.counttoearn.Model.TopplayerModel;
import com.example.counttoearn.Model.UserDataDetailModel;
import com.example.counttoearn.Model.WithdrawListModel;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiService {


    @FormUrlEncoded
    @POST("users/login")
    Call<LoginModel> getlogin(@Field("mobile") String mobile);

    @FormUrlEncoded
    @POST("Users/register")
    Call<SingupModal> getsingup(@Field("name") String name,
                                @Field("country_code") String country_code,
                                @Field("mobile") String mobile,
                                @Field("user_referral") String user_referral,
                                @Field("device_token") String device_token);

    @GET("Users/referralcode/{referral_code}")
    Call<ReferralCodeModel> getreferralcode(@Path("referral_code") String referral_code);

    @GET("Users/getUser/{user_data}")
    Call<UserDataDetailModel> getuserdetail(@Path("user_data") String user_data);


    @GET("Questions/getQuestions/{operation}/{user_id}")
    Call<QuestionsModel> getquestions(@Path("operation") String operation,
                                      @Path("user_id") String user_id);


    @FormUrlEncoded
    @POST("Users/profileUpdate")
    Call<CommonModel> updateprofiles(@Field("name") String name,
                                     @Field("email") String email,
                                     @Field("dob") String dob,
                                     @Field("profile_pic") String profile_pic,
                                     @Field("user_id") String user_id);


    @GET("users/getTop10")
    Call<TopplayerModel> gettopten();

    @GET("withdraw/getpaymentmethod")
    Call<PaymentModal> getpayment();


    @FormUrlEncoded
    @POST("withdraw/withdraw")
    Call<CommonModel> getwithdraw(@Field("user_id") String user_id,
                                  @Field("paymet_method_id") String paymet_method_id,
                                  @Field("withdraw_amt") String withdraw_amt,
                                  @Field("transfer_to") String transfer_to);

    @GET("users/checkDeviceLimit/{device_token}")
    Call<ReferralCodeModel> signuplimit(@Path("device_token") String device_token);

    @GET("users/checkMobile/{mobile_check}")
    Call<ReferralCodeModel> mobilenumber_valid(@Path("mobile_check") String mobile_check);


    @GET("Users/getUserWithdrawHistory/{withdraw_list}")
    Call<WithdrawListModel> withdrawlist(@Path("withdraw_list") String withdraw_list);


    @GET("users/watchCoinScreen")
    Call<CommonModel> watchCoinScreen();

//    @FormUrlEncoded
//    @POST("users/watchCoin")
//    Call<CommonModel> watchCoin(@Field("user_id") String user_id);


    @FormUrlEncoded
    @POST("users/submitCoinWallet")
    Call<CommonModel> submitCoinWallet(@Field("user_id") String user_id, @Field("coin") String coin, @Field("wallet") String wallet);


}
