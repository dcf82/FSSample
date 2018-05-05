package com.dcf82.fs.sample.modules;

import com.dcf82.fs.sample.activities.HomeActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules={AppModule.class, NetModule.class})
public interface NetComponent {
    void inject(HomeActivity activity);
}
