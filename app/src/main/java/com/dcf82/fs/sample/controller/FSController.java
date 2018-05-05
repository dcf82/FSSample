package com.dcf82.fs.sample.controller;

import android.content.SharedPreferences;
import android.support.multidex.MultiDexApplication;

import com.dcf82.fs.sample.helpers.ErrorHandler;
import com.dcf82.fs.sample.modules.AppModule;
import com.dcf82.fs.sample.modules.DaggerNetComponent;
import com.dcf82.fs.sample.modules.NetComponent;
import com.dcf82.fs.sample.modules.NetModule;

import io.reactivex.plugins.RxJavaPlugins;

public class FSController extends MultiDexApplication {

    private static FSController thiz = null;

    private SharedPreferences mSharedPreferences;

    private NetComponent mNetComponent;

    public void onCreate() {
        super.onCreate();

        // The current app context
        thiz = this;

        // Setting error handler for RxJava calls
        RxJavaPlugins.setErrorHandler(ErrorHandler.getSingleton());

        // Proving a singleton instance
        mSharedPreferences = getSharedPreferences("MyPreferences", MODE_PRIVATE);

        mNetComponent = DaggerNetComponent.builder()
                .appModule(new AppModule(this))
                .netModule(new NetModule())
                .build();
    }

    public static FSController getApp() {
        return thiz;
    }

    public SharedPreferences getMyPreferences() {
        return mSharedPreferences;
    }

    public NetComponent getNetComponent() {
        return mNetComponent;
    }
}
