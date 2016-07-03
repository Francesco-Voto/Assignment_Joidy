package com.assignment.joidy.di.components;


import android.content.Context;

import com.assignment.joidy.di.modules.AppModule;
import com.assignment.joidy.di.modules.NetModule;

import javax.inject.Singleton;

import dagger.Component;
import retrofit2.Retrofit;

@Singleton
@Component(modules={NetModule.class, AppModule.class})
public interface AppComponent {

    Retrofit provideRetrofit();
    Context provideContext();
}