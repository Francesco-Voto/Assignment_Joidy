package com.assignment.joidy.di.modules;


import android.content.Context;

import com.assignment.joidy.SessionApplication;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class NetModule {

    private static final int CACHE = 10 * 1024 * 1024; // 10 MiB
    private static final String BASE_URL = "http://api.joidy.com/";

    @Provides
    @Singleton
    public Retrofit provideRetrofit(Gson gson, OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient)
                .build();
    }

    @Provides
    @Singleton
    Cache provideOkHttpCache(Context context) {
        return new Cache(SessionApplication.get(context).getCacheDir(), CACHE);
    }

    @Provides
    @Singleton
    Gson provideJsonFactory() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        return gsonBuilder.create();
    }

    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient(Cache cache) {
        return new OkHttpClient
                .Builder()
                .cache(cache)
                .build();
    }

}