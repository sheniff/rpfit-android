package com.sheniff.rpfit.app.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseUser;
import com.sheniff.rpfit.app.R;

public class DashboardActivity extends ActionBarActivity {

    // region Variables
    ParseUser user;
    Toolbar toolbar;
    TextView mNewWorkout;
    // region Constants

    // region Listeners

    // endregion
    // endregion
    private View.OnClickListener onNewWorkoutClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(new Intent(DashboardActivity.this, NewWorkoutActivity.class));
        }
    };
    // endregion


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        bindUIElements();
        setUpListeners();
        
        user = ParseUser.getCurrentUser();
        toolbar.setTitle(user.getUsername());
        setSupportActionBar(toolbar);
    }

    private void bindUIElements() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        mNewWorkout = (TextView) findViewById(R.id.tvNewWorkout);
    }

    private void setUpListeners() {
        mNewWorkout.setOnClickListener(onNewWorkoutClickListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_dashboard, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void activityProfile(MenuItem item) {
        Toast.makeText(this, "Coming soon...", Toast.LENGTH_SHORT).show();
//        startActivity(new Intent(this, ProfileActivity.class));
    }

    public void logout(MenuItem item) {
        ParseUser.logOut();
        Intent intent = new Intent(this, DispatchActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
