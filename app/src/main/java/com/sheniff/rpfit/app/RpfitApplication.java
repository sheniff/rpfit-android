package com.sheniff.rpfit.app;

import android.app.Application;

import com.sheniff.rpfit.app.api.RpfitRestClient;

public class RpfitApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        // initialize cookie storage context
        RpfitRestClient.initCookieStorage(this);
    }
}
