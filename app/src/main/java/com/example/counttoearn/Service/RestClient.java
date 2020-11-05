package com.example.counttoearn.Service;

import android.content.Context;
import android.util.Base64;

import com.example.counttoearn.Util.Debug;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestClient {


    private static ApiService api;
    private static Context mContext;
    private static final RestClient restClient = new RestClient(mContext);
    private String error = "Something went wrong.. Please try again later";
    private static OkHttpClient okHttpClient;
    private static final String AUTH = "Base " + Base64.encodeToString(("countToEarnAdmin:pgwZ3L58").getBytes(), Base64.NO_WRAP);

    public static String BASE_URL = "http://162.241.174.134/api/v4/";
    public static String IMAGE_URL = "http://162.241.174.134/";

    static {
        initRestClient();
    }

    public RestClient(Context context) {
        this.mContext = context;
    }

    private static void initRestClient() {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(Debug.DEBUG ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE);

        OkHttpClient.Builder client = new OkHttpClient.Builder();
        client.addInterceptor(interceptor)
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request original = chain.request();

                        Request request = original.newBuilder()
                                .header("Authorization", AUTH)
                                .header("x-api-key", "COUNTTOEARNV1")
//                                .header("Content-Type", "multipart/form-data")
                                .header("Content-Type", "application/json")
                                .header("Accept", "application/json")
                                .method(original.method(), original.body())
                                .build();

                        return chain.proceed(request);
                    }
                });


        okHttpClient = client.readTimeout(2, TimeUnit.MINUTES)
                .connectTimeout(2, TimeUnit.MINUTES)
                .retryOnConnectionFailure(true)
                .build();

//        Gson gson = new GsonBuilder()
//                .setLenient()
//                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        api = retrofit.create(ApiService.class);
    }

    public RestClient getInstance() {
        return restClient;
    }


    public ApiService get() {
        return api;
    }

    public OkHttpClient getOkHttpClient() {
        return okHttpClient;
    }

}

