package com.sheniff.rpfit.app.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;

import com.sheniff.rpfit.app.R;

public class NewWorkoutActivity extends ActionBarActivity {
    // region Variables
    Toolbar toolbar;
    // endregion

    // region Constants
    // endregion

    // region Listeners
    // endregion


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_workout);

        bindUIElements();
        setUpListeners();

        toolbar.setTitle("New Workout");
        setSupportActionBar(toolbar);
    }

    private void bindUIElements() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
    }

    private void setUpListeners() {
    }
}
