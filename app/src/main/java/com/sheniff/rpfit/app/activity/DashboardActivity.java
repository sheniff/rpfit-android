package com.sheniff.rpfit.app.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.sheniff.rpfit.app.R;
import com.sheniff.rpfit.app.SessionManager;
import com.sheniff.rpfit.app.view.MessagesBarView;

import org.json.JSONObject;

/**
 * Created by sheniff on 6/9/14.
 */
public class DashboardActivity extends Activity {

    // region Constants
    private static final String TAG = "DashboardActivity";
    // endregion

    // region Variables
    SessionManager sessionManager = new SessionManager();
    private MessagesBarView messagesBar;
    // endregion

    // region Listeners
    // endregion


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        sessionManager.getUserInfo(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(JSONObject response) {
                Log.d(TAG, "USER DATA");
            }

            @Override
            public void onFailure(Throwable e, JSONObject errorResponse) {
                showError(e, errorResponse);
                if(sessionManager.isLoggedIn()) {
                    logout();
                }
            }
        });

        bindUIElements();
        setUpListeners();
    }

    private void bindUIElements() {
        messagesBar = (MessagesBarView) findViewById(R.id.messages_barView);
    }

    private void setUpListeners() {
    }

    private void logout() {
        sessionManager.logout(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(JSONObject response) {
                goToLogin();
            }

            @Override
            public void onFailure(Throwable e, JSONObject errorResponse) {
                showError(e, errorResponse);
            }
        });
    }

    private void goToLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private void showError(Throwable e, JSONObject errorResponse) {
        String errorMessage;
        try {
            errorMessage = (String) errorResponse.get("error");
        } catch (Exception e1) {
            errorMessage = e.getMessage();
        }
        Log.d(TAG, "ERROR! " + errorMessage);
        messagesBar.show(errorMessage);
    }
}
