package com.assignment.joidy.di.modules;


import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    private final Context mContext;

    public AppModule(Application app) {
        this.mContext = app.getApplicationContext();
    }

    @Provides
    @Singleton
    Context providesContext() {
        return mContext;
    }
}