package com.sheniff.rpfit.app.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.sheniff.rpfit.app.LoginPagerAdapter;
import com.sheniff.rpfit.app.R;
import com.sheniff.rpfit.app.SessionManager;
import com.sheniff.rpfit.app.view.MessagesBarView;
import com.sheniff.rpfit.app.view.RevealablePasswordEditText;

import org.json.JSONObject;

public class LoginActivity extends Activity implements ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    //region Constants
    private static final String TAG = "LoginActivity";

    /* Request code used to invoke sign in user interactions. */
    private static final int RC_SIGN_IN = 0;
    //endregion

    //region Variables
    /* Client used to interact with Google APIs. */
    private GoogleApiClient mGoogleApiClient;

    /* A flag indicating that a PendingIntent is in progress and prevents
     * us from starting further intents.
     */
    private boolean mIntentInProgress;

    /* Track whether the sign-in button has been clicked so that we know to resolve
     * all issues preventing sign-in without waiting.
     */
    private boolean mSignInClicked;

    /* Store the connection result from onConnectionFailed callbacks so that we can
     * resolve them when the user clicks sign-in.
     */
    private ConnectionResult mConnectionResult;
    private SignInButton gPlusSignInButton;
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
    private View.OnClickListener gPlusSignInButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mSignInClicked = true;
            resolveSignInError();
        }
    };
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
//        mGoogleApiClient = new GoogleApiClient.Builder(this)
//                .addConnectionCallbacks(LoginActivity.this)
//                .addOnConnectionFailedListener(this)
//                .addApi(Plus.API, null)
//                .addScope(Plus.SCOPE_PLUS_LOGIN)
//                .build();
    }

    private void bindUIElements() {
        gPlusSignInButton = (SignInButton) findViewById(R.id.sign_in_button);
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
        gPlusSignInButton.setOnClickListener(gPlusSignInButtonListener);
        loginButton.setOnClickListener(loginButtonListener);
        registerButton.setOnClickListener(registerButtonListener);
    }

    @Override
    protected void onStart() {
        super.onStart();
//        mGoogleApiClient.connect();
    }

    //@Override
    // protected void onStop() {
    //    super.onStop();
    //    if (mGoogleApiClient.isConnected()) {
    //        mGoogleApiClient.disconnect();
    //    }
    //}

    @Override
    public void onConnectionFailed(ConnectionResult result) {
        if (!mIntentInProgress) {
            mConnectionResult = result;

            if (mSignInClicked) {
                resolveSignInError();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int responseCode, Intent intent) {
        if (requestCode == RC_SIGN_IN) {
            if (responseCode != RESULT_OK) {
                mSignInClicked = false;
            }

            mIntentInProgress = false;

            if (!mGoogleApiClient.isConnecting()) {
                mGoogleApiClient.connect();
            }
        }
    }

    @Override
    public void onConnected(Bundle bundle) {
        mSignInClicked = false;
        Toast.makeText(this, "User is connected!", Toast.LENGTH_LONG).show();
    }

    private void resolveSignInError() {
        if (mConnectionResult.hasResolution()) {
            try {
                mIntentInProgress = true;
                mConnectionResult.startResolutionForResult(this, RC_SIGN_IN);
            } catch (IntentSender.SendIntentException e) {
                mIntentInProgress = false;
                mGoogleApiClient.connect();
            }
        } else {
            Toast.makeText(this, "Unresolvable error in SignIn...", Toast.LENGTH_LONG).show();
        }
    }

    public void onConnectionSuspended(int cause) {
        mGoogleApiClient.connect();
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
