package com.sheniff.rpfit.app.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.sheniff.rpfit.app.R;
import com.sheniff.rpfit.app.adapters.LoginPagerAdapter;
import com.sheniff.rpfit.app.api.SessionManager;
import com.sheniff.rpfit.app.views.MessagesBarView;
import com.sheniff.rpfit.app.views.RevealablePasswordEditText;

import org.json.JSONObject;

public class LoginActivity extends Activity {

    //region Constants
    private static final String TAG = "LoginActivity";
    //endregion

    //region Variables
    private ViewPager pager;
    private Button loginButton;
    private Button registerButton;
    private EditText loginEmail;
    private EditText registerEmail;
    private RevealablePasswordEditText loginPassword;
    private RevealablePasswordEditText registerPassword;
    private SessionManager sessionManager = new SessionManager();
    private MessagesBarView messagesBar;
    //endregion

    //region Listeners
    private View.OnClickListener loginButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            login();
        }
    };
    private View.OnClickListener registerButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            register();
        }
    };
    //endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // if logged in, jump to dashboard activity
        if (sessionManager.isLoggedIn()) {
            this.goToDashboard();
        }

        bindUIElements();
        setUpListeners();

        populateLocalFormsViewPager();
    }

    private void bindUIElements() {
        loginButton = (Button) findViewById(R.id.login_button);
        registerButton = (Button) findViewById(R.id.register_button);
        loginEmail = (EditText) findViewById(R.id.login_emailInput);
        registerEmail = (EditText) findViewById(R.id.register_emailInput);
        loginPassword = (RevealablePasswordEditText) findViewById(R.id.login_passwordView);
        registerPassword = (RevealablePasswordEditText) findViewById(R.id.register_passwordView);
        pager = (ViewPager) findViewById(R.id.login_viewPager);
        messagesBar = (MessagesBarView) findViewById(R.id.messages_barView);
    }

    private void setUpListeners() {
        loginButton.setOnClickListener(loginButtonListener);
        registerButton.setOnClickListener(registerButtonListener);
    }

    private void populateLocalFormsViewPager() {
        LoginPagerAdapter adapter = new LoginPagerAdapter();
        pager.setAdapter(adapter);
    }

    private void goToDashboard() {
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
        LoginActivity.this.finish();
    }

    private void login() {
        String email = "";
        String password = loginPassword.getPassword();

        if (loginEmail.getText() != null) {
            email = loginEmail.getText().toString();
        }

        sessionManager.login(email, password, new JsonHttpResponseHandler() {
            @Override
            public void onStart() {
                // TODO: show spinner
                // messagesBar.show("Logging in...");
            }

            @Override
            public void onFinish() {
                // TODO: hide spinner
            }

            @Override
            public void onSuccess(JSONObject response) {
                goToDashboard();
            }

            @Override
            public void onFailure(Throwable e, JSONObject errorResponse) {
                String errorMessage;
                try {
                    errorMessage = (String) errorResponse.get("error");
                } catch (Exception e1) {
                    errorMessage = e.getMessage();
                }
                Log.d(TAG, "ERROR! " + errorMessage);
                messagesBar.show(errorMessage);
            }
        });
    }

    private void register() {
        String email = "";
        String password = registerPassword.getPassword();

        if (registerEmail.getText() != null) {
            email = registerEmail.getText().toString();
        }

        Log.d(TAG, "Signing up");
        Log.d(TAG, email);
        Log.d(TAG, password);
    }
}
