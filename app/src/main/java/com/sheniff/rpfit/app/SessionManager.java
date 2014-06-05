package com.sheniff.rpfit.app;

import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by sheniff on 6/4/14.
 */
public class SessionManager {

    //region Constants
    private static final String TAG = "SessionManager";
    //endregion

    // region Variables
    private JsonHttpResponseHandler responseHandler;
    private JsonHttpResponseHandler loginResponseHandler = new JsonHttpResponseHandler() {
        @Override
        public void onSuccess(JSONObject response) {
            //ToDo: Add real cookie
            //cacheCookie();
            super.onSuccess(response);
            responseHandler.onSuccess(response);
            // ToDO: Move it out of here, only for testing purposes...
            getUserInfo();
        }

        @Override
        public void onFailure(Throwable e, JSONObject errorResponse) {
            super.onFailure(e, errorResponse);
            responseHandler.onFailure(e, errorResponse);
        }

        @Override
        public void onStart() {
            super.onStart();
            responseHandler.onStart();
        }

        @Override
        public void onFinish() {
            super.onFinish();
            responseHandler.onFinish();
        }
    };
    private AsyncHttpClient http = new AsyncHttpClient();
    // endregion

    //ToDo
    public boolean isLoggedIn() {
        return false;
    }

    //ToDo
    public void cacheCookie(String loginCookie) {

    }

    public void login(String email, String pass, JsonHttpResponseHandler responseHandler) {
        this.responseHandler = responseHandler;
        RequestParams params = new RequestParams();
        params.put("email", email);
        params.put("password", pass);

        http.post("http://192.168.1.149:1337/login.json", params, loginResponseHandler);
    }

    public void getUserInfo() {
        http.get("http://192.168.1.149:1337/user/current", new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(JSONObject response) {
                Log.d(TAG, "USER DATA");
            }

            @Override
            public void onFailure(Throwable e, JSONObject errorResponse) {
                Log.d(TAG, "ERROR");
                try {
                    Log.d(TAG, errorResponse.getString("message"));
                } catch (JSONException e1) {
                    Log.d(TAG, e.getMessage());
                }
            }
        });
    }
}
