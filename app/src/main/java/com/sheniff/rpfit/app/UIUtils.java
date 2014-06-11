package com.sheniff.rpfit.app;

import android.content.Context;

/**
 * Created by sheniff on 6/6/14.
 */
public class UIUtils {
    public static int[] LEVELS = {50, 150, 450, 1350, 1800, 2250};

    public static int getPixels(Context context, float dps) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dps * scale + 0.5f);
    }

}
