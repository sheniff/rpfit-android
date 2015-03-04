package com.sheniff.rpfit.app.adapters;

import android.support.v4.view.PagerAdapter;
import android.view.View;

import com.sheniff.rpfit.app.R;

/**
 * Created by sheniff on 5/13/14.
 */
public class LoginPagerAdapter extends PagerAdapter {

    public Object instantiateItem(View collection, int position) {

        int resId = 0;
        switch (position) {
            case 0:
                resId = R.id.login_form;
                break;
            case 1:
                resId = R.id.register_form;
                break;
        }
        return collection.findViewById(resId);
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }
}
