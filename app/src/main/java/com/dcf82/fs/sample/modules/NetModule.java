package com.dcf82.fs.sample.modules;

import com.dcf82.fs.sample.network.RetrofitFSInterface;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class NetModule {

    @Provides
    @Singleton
    RetrofitFSInterface providesRetrofitFSInterface() {

        // Logging support for debugging purposes
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);

        // OkHttp client
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        // Retrofit instance for making queries
        Retrofit retrofit = new Retrofit.Builder().client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://api.foursquare.com/v2/venues/")
                .build();

        return retrofit.create(RetrofitFSInterface.class);
    }

}
