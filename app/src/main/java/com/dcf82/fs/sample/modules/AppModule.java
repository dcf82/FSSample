package com.dcf82.fs.sample.modules;

import android.app.Application;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;

@Module
public class AppModule {

    Application mApplication;

    public AppModule(Application application) {
        mApplication = application;
    }

    @Provides
    @Singleton
    Application providesApplication() {
        return mApplication;
    }

    @Provides
    @Singleton
    CompositeDisposable providesCompositeDisposable() {
        return new CompositeDisposable();
    }
}
