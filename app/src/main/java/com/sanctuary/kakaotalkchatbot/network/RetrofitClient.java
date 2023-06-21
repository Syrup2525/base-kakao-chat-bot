package com.sanctuary.kakaotalkchatbot.network;


import java.net.CookieManager;
import java.net.CookiePolicy;

import okhttp3.JavaNetCookieJar;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

class RetrofitClient {
    private static final String API_URL = "https://example_api_site";

    private static RetrofitInterface retrofitInterface;

    static RetrofitInterface getClient() {
        if (retrofitInterface == null) {
            HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            CookieManager manager = new CookieManager();
            manager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);

            OkHttpClient builder = new OkHttpClient
                    .Builder()
                    .addInterceptor(httpLoggingInterceptor)
                    .cookieJar(new JavaNetCookieJar(manager))
                    .build();

            Retrofit retrofit;

            retrofit = new Retrofit.Builder()
                    .baseUrl(API_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(builder)
                    .build();

            retrofitInterface = retrofit.create(RetrofitInterface.class);
        }

        return retrofitInterface;
    }

    public interface RetrofitInterface {

    }
}
