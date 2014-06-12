package com.sheniff.rpfit.app.activity;

import android.app.Activity;
import android.os.Bundle;

import com.sheniff.rpfit.app.R;

/**
 * Created by sheniff on 6/11/14.
 */
public class DashboardActivity extends Activity {

    // region Variables
    // endregion

    // region Constants
    // endregion

    // region Listeners
    // endregion


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        bindUIElements();
        setUpListeners();
    }

    private void bindUIElements() {

    }

    private void setUpListeners() {

    }
}
