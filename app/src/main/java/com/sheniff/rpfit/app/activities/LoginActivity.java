package com.sheniff.rpfit.app.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;
import com.sheniff.rpfit.app.R;
import com.sheniff.rpfit.app.UIUtils;
import com.sheniff.rpfit.app.adapters.LoginPagerAdapter;
import com.sheniff.rpfit.app.api.SessionManager;
import com.sheniff.rpfit.app.views.MessagesBarView;
import com.sheniff.rpfit.app.views.RevealablePasswordEditText;

public class LoginActivity extends Activity {

    //region Constants
    private static final String TAG = "LoginActivity";
    //endregion

    //region Variables
    private ViewPager pager;
    private Button loginButton;
    private Button registerButton;
    private EditText loginUsername;
    private EditText registerEmail;
    private EditText registerUsername;
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

        bindUIElements();
        setUpListeners();

        populateLocalFormsViewPager();
    }

    private void bindUIElements() {
        loginButton = (Button) findViewById(R.id.login_button);
        registerButton = (Button) findViewById(R.id.register_button);
        loginUsername = (EditText) findViewById(R.id.login_usernameInput);
        registerEmail = (EditText) findViewById(R.id.register_emailInput);
        loginPassword = (RevealablePasswordEditText) findViewById(R.id.login_passwordView);
        registerPassword = (RevealablePasswordEditText) findViewById(R.id.register_passwordView);
        registerUsername = (EditText) findViewById(R.id.register_nameInput);
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

    private void login() {
        String username = loginUsername.getText().toString().trim();
        String password = loginPassword.getPassword().trim();

        if (validateLogin(username, password)) {
            messagesBar.show("Logging in...");
            ParseUser.logInInBackground(username, password, new LogInCallback() {
                @Override
                public void done(ParseUser parseUser, ParseException e) {
                    if (e != null) {
                        messagesBar.show(e.getMessage());
                    } else {
                        Intent i = new Intent(LoginActivity.this, DispatchActivity.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(i);
                    }
                }
            });
        }
    }

    private void register() {
        String email = registerEmail.getText().toString().trim();
        String password = registerPassword.getPassword().trim();
        String username = registerUsername.getText().toString().trim();

        // ToDo: Show a loader
        messagesBar.show("Creating user...");
        registerButton.setEnabled(false);

        // validate
        if (validate(email, password, username)) {
            ParseUser user = new ParseUser();
            user.setEmail(email);
            user.setPassword(password);
            user.setUsername(username);

            user.signUpInBackground(new SignUpCallback() {
                @Override
                public void done(ParseException e) {
                    registerButton.setEnabled(true);

                    if (e != null) {
                        messagesBar.show(e.getMessage());
                    } else {
                        Intent i = new Intent(LoginActivity.this, DispatchActivity.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(i);
                    }
                }
            });
        } else {
            registerButton.setEnabled(true);
        }
    }

    private boolean validate(String email, String password, String username) {
        boolean valid = true;

        if (email.equals("")) {
            messagesBar.show(getString(R.string.email_required));
            valid = false;
        } else if (!UIUtils.isEmailValid(email)) {
            messagesBar.show(getString(R.string.email_not_valid));
            valid = false;
        } else if (password.equals("")) {
            messagesBar.show(getString(R.string.password_required));
            valid = false;
        } else if (username.equals("")) {
            messagesBar.show("Username can't be blank");
            valid = false;
        }

        return valid;
    }

    private boolean validateLogin(String username, String password) {
        boolean valid = true;

        if (password.equals("")) {
            messagesBar.show(getString(R.string.password_required));
            valid = false;
        } else if (username.equals("")) {
            messagesBar.show("Username can't be blank");
            valid = false;
        }

        return valid;
    }
}
