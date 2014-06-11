package com.sheniff.rpfit.app.api;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.sheniff.rpfit.app.api.RpfitRestClient;

import org.json.JSONObject;

/**
 * Created by sheniff on 6/4/14.u
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
            super.onSuccess(response);
            responseHandler.onSuccess(response);
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
    // endregion

    public boolean isLoggedIn() {
        String cookie = RpfitRestClient.getCookie("sails.sid");
        return cookie != null && !cookie.equals("");
    }

    public void login(String email, String pass, JsonHttpResponseHandler responseHandler) {
        this.responseHandler = responseHandler;
        RequestParams params = new RequestParams();
        params.put("email", email);
        params.put("password", pass);

        RpfitRestClient.post("/login.json", params, loginResponseHandler);
    }

    public void logout(JsonHttpResponseHandler responseHandler) {
        RpfitRestClient.get("/logout.json", responseHandler);
    }

    // ToDo: This should go to User Class?
    public void getUserInfo(JsonHttpResponseHandler responseHandler) {
        RpfitRestClient.get("/user/current", responseHandler);
    }
}
