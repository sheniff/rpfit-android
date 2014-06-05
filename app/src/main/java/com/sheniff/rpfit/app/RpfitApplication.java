package com.sheniff.rpfit.app;

import android.app.Application;

public class RpfitApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        // initialize cookie storage context
        RpfitRestClient.initCookieStorage(this);
    }
}
