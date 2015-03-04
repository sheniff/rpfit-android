package com.sheniff.rpfit.app.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.sheniff.rpfit.app.R;

/**
 * Created by sheniff on 6/6/14.
 */
public class CircularTimerView extends View {

    // region Constants
    private static final int DEFAULT_ANIMATION_TIME = 3000;
    private static final String TAG = CircularTimerView.class.getSimpleName();
    // endregion

    // region Variables
    Context context;
    int time = DEFAULT_ANIMATION_TIME;
    Paint paint;
    private long startTime;
    private RectF timerBounds;
    // endregion

    public CircularTimerView(Context context) {
        this(context, null);
    }

    public CircularTimerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircularTimerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        int timerSize = (int) context.getResources().getDimension(R.dimen.messageBarCircularTimerSize);
        this.context = context;
        this.timerBounds = new RectF(0, 0, timerSize, timerSize);

        bindUIElements();
        setUpListeners();

        paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(context.getResources().getColor(android.R.color.white));
        paint.setAntiAlias(true);
    }

    private void bindUIElements() {
    }

    private void setUpListeners() {
    }

    public void start(int duration) {
        time = duration;
        startTime = System.currentTimeMillis();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        long currentTime = System.currentTimeMillis();
        int angle = (int) ((currentTime - startTime) / (time * 1.0) * 360);

        if (angle < 360) {
            canvas.drawArc(timerBounds, -90 + angle, 360 - angle, true, paint);
            invalidate();
        }
    }
}
