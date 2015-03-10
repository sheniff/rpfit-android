package com.sheniff.rpfit.app.activities;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.sheniff.rpfit.app.R;
import com.sheniff.rpfit.app.adapters.CategoryArrayAdapter;
import com.sheniff.rpfit.app.api.SessionManager;
import com.sheniff.rpfit.app.models.Category;
import com.sheniff.rpfit.app.views.MessagesBarView;
import com.sheniff.rpfit.app.views.RoundedImageView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

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
    private ArrayList<Category> categoryList = new ArrayList<>();
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

    // ToDo: modify to use ParseUser...
    private void fetchUser() {
        sessionManager.getUserInfo(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(JSONObject response) {
                try {
                    tmpTitle.setText(response.getString("name"));
                    Picasso.with(ProfileActivity.this).load("https://pbs.twimg.com/profile_images/2677851365/21b86d4edc88250e6e9ecd9fcff29443.jpeg").into(profilePicture);
                    printCategories(response.getJSONArray("xps"));
                    categoryAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    tmpTitle.setText("<no name>");
                }
            }

            @Override
            public void onFailure(Throwable e, JSONObject errorResponse) {
                showError(e, errorResponse);
            }
        });
    }

    private void printCategories(JSONArray categories) throws JSONException {
        for (int i = 0; i < categories.length(); i++) {
            categoryList.add(new Category(categories.getJSONObject(i)));
        }
    }
}
