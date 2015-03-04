package com.sheniff.rpfit.app;

import android.app.Application;

import com.parse.Parse;
import com.sheniff.rpfit.app.api.RpfitRestClient;

public class RpfitApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "BtaHptJ3A5j7iqClZfdKCcNMTynS8rFewQxOiHOQ", "mK8HXBDqEc4udBBZQw7ktamxalZ7ht5yOBkPlnsI");

        // initialize cookie storage context
        RpfitRestClient.initCookieStorage(this);
    }
}
