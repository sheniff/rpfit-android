package com.sheniff.rpfit.app.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.sheniff.rpfit.app.Category;
import com.sheniff.rpfit.app.R;
import com.sheniff.rpfit.app.RetrieveImage;
import com.sheniff.rpfit.app.adapter.CategoryArrayAdapter;
import com.sheniff.rpfit.app.api.RpfitRestClient;
import com.sheniff.rpfit.app.api.SessionManager;
import com.sheniff.rpfit.app.view.MessagesBarView;
import com.sheniff.rpfit.app.view.RoundedImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by sheniff on 6/9/14.
 */
public class ProfileActivity extends Activity {

    // region Constants
    private static final String TAG = "ProfileActivity";
    // endregion

    // region Variables
    SessionManager sessionManager = new SessionManager();
    private MessagesBarView messagesBar;
    private TextView tmpTitle;
    private RoundedImageView profilePicture;
    private ListView categoryListView;
    private CategoryArrayAdapter categoryAdapter;
    private ArrayList<Category> categoryList = new ArrayList<Category>();
    // endregion

    // region Listeners
    // endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        fetchUser();

        bindUIElements();
        setUpListeners();
        setUpAdapter();
    }

    private void bindUIElements() {
        messagesBar = (MessagesBarView) findViewById(R.id.messages_barView);
        tmpTitle = (TextView) findViewById(R.id.tmpTitleView);
        profilePicture = (RoundedImageView) findViewById(R.id.profile_pictureView);
        categoryListView = (ListView) findViewById(R.id.categoryList);
    }

    private void setUpListeners() {
    }

    private void setUpAdapter() {
        categoryAdapter = new CategoryArrayAdapter(this, categoryList);
        categoryListView.setAdapter(categoryAdapter);
        categoryListView.setDivider(null);
    }

    private void logout() {
        sessionManager.logout(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(JSONObject response) {
                RpfitRestClient.clearCookies();
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
                    printPicture("https://pbs.twimg.com/profile_images/2677851365/21b86d4edc88250e6e9ecd9fcff29443.jpeg");
                    printCategories(response.getJSONArray("xps"));
                    categoryAdapter.notifyDataSetChanged();
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

    private void printCategories(JSONArray categories) throws JSONException {
        for (int i = 0; i < categories.length(); i++) {
            categoryList.add(new Category(categories.getJSONObject(i)));
        }
    }

    private void printPicture(String src) {
        new RetrieveImage(profilePicture).execute(src);
    }
}
