package com.sheniff.rpfit.app.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.sheniff.rpfit.app.R;
import com.sheniff.rpfit.app.SessionManager;
import com.sheniff.rpfit.app.view.MessagesBarView;

import org.json.JSONException;
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
    private TextView tmpTitle;
    // endregion

    // region Listeners
    // endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        fetchUser();

        bindUIElements();
        setUpListeners();
    }

    private void bindUIElements() {
        messagesBar = (MessagesBarView) findViewById(R.id.messages_barView);
        tmpTitle = (TextView) findViewById(R.id.tmpTitleView);
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

    private void fetchUser() {
        sessionManager.getUserInfo(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(JSONObject response) {
                try {
                    tmpTitle.setText(response.getString("name"));
                } catch (JSONException e) {
                    tmpTitle.setText("<no name>");
                }
            }

            @Override
            public void onFailure(Throwable e, JSONObject errorResponse) {
                showError(e, errorResponse);
                if(sessionManager.isLoggedIn()) {
                    logout();
                }
            }
        });
    }
}
