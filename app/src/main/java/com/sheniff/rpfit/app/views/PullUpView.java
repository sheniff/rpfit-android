package com.sheniff.rpfit.app.views;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class PullUpView extends LinearLayout implements View.OnTouchListener {

    private static final String TAG = "PULL_UP_VIEW";
    private float mStartY;
    private float mTranslateY;

    public PullUpView(Context context) {
        this(context, null);
    }

    public PullUpView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PullUpView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        
        if (getChildCount() > 1) {
            throw new IllegalArgumentException("Only one child allowed");
        }
        
        setOnTouchListener(this);
    }


    @Override
    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        super.addView(child, index, params);
        if (getChildCount() > 1) {
            throw new IllegalArgumentException("Only one child allowed");
        }
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        canvas.translate(0, mTranslateY);
        super.dispatchDraw(canvas);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if(event.getY() > getChildAt(0).getTop() || event.getAction() == MotionEvent.ACTION_MOVE) {
            processTouch(event);
            return true;
        } else {
            return false;
        }
    }

    private void processTouch(MotionEvent event) {
        Log.d(TAG, String.valueOf(event.getAction()));
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mStartY = event.getY();
                break;
            case MotionEvent.ACTION_UP:
                LinearLayout.LayoutParams lParams = (LayoutParams) getChildAt(0).getLayoutParams();
                lParams.topMargin = 0;
                getChildAt(0).setLayoutParams(lParams);
                break;
            case MotionEvent.ACTION_MOVE:
                mTranslateY = event.getY() - mStartY;
                Log.d(TAG, "mtranslate is " + String.valueOf(mTranslateY));
                invalidate();
                break;
        }
    }
}
