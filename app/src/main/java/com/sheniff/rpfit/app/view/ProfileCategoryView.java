package com.sheniff.rpfit.app.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sheniff.rpfit.app.R;

/**
 * Created by sheniff on 6/9/14.
 */
public class ProfileCategoryView extends LinearLayout {

    // region Variables
    Context context;
    TextView categoryName;
    TextView categoryLevel;
    // endregion

    public ProfileCategoryView(Context context) {
        this(context, null);
    }

    public ProfileCategoryView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ProfileCategoryView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        this.context = context;

        LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.profile_category, this, true);

        bindUIElements();
    }

    private void bindUIElements() {
        categoryName = (TextView) findViewById(R.id.categoryName);
        categoryLevel = (TextView) findViewById(R.id.categoryLevel);
    }

    public void setName(String name) {
        categoryName.setText(name);
    }

    public void setLevel(String level) {
        categoryLevel.setText(level);
    }
}
