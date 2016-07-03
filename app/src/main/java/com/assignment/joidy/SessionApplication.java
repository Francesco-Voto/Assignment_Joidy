package com.assignment.joidy;


import android.app.Application;
import android.content.Context;

import com.assignment.joidy.di.components.AppComponent;
import com.assignment.joidy.di.components.DaggerAppComponent;
import com.assignment.joidy.di.modules.AppModule;
import com.assignment.joidy.di.modules.NetModule;

public class SessionApplication extends Application {

    public AppComponent mAppComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        mAppComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .netModule(new NetModule())
                .build();

    }

    public static SessionApplication get(Context context) {
        return (SessionApplication) context.getApplicationContext();
    }
}