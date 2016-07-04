package com.assignment.joidy.di.modules;


import com.assignment.joidy.data.network.APIMain;
import com.assignment.joidy.di.scopes.Flow;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class FlowModule {

    @Flow
    @Provides
    public APIMain provideMainAPI(Retrofit retrofit) {
        return retrofit.create(APIMain.class);
    }

}
