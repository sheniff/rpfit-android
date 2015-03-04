package com.sheniff.rpfit.app.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sheniff.rpfit.app.R;

/**
 * Created by sheniff on 6/5/14.
 */
public class MessagesBarView extends LinearLayout {

    // region Variables
    TextView message;
    Context context;
    CircularTimerView timer;
    // endregion

    // region Constants
    // endregion

    // region Listeners
    private Animation.AnimationListener animationShowListener = new Animation.AnimationListener() {
        @Override
        public void onAnimationStart(Animation animation) {}

        @Override
        public void onAnimationEnd(Animation animation) {
            int duration = context.getResources().getInteger(R.integer.messageBarCircularTimerDuration);
            timer.start(duration);
            timer.invalidate();
            postDelayed(new Runnable() {
                @Override
                public void run() {
                    hide();
                }
            }, duration);
        }

        @Override
        public void onAnimationRepeat(Animation animation) {}
    };
    private Animation.AnimationListener animationHideListener = new Animation.AnimationListener() {
        @Override
        public void onAnimationStart(Animation animation) {}

        @Override
        public void onAnimationEnd(Animation animation) {
            MessagesBarView.this.message.setText(null);
        }

        @Override
        public void onAnimationRepeat(Animation animation) {}
    };
    // endregion

    public MessagesBarView(Context context) {
        this(context, null);
    }

    public MessagesBarView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MessagesBarView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        this.context = context;

        LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.messages_bar, this, true);

        bindUIElements();
    }

    private void bindUIElements() {
        message = (TextView) findViewById(R.id.messagesBarMessage);
        timer = (CircularTimerView) findViewById(R.id.messageBarCirclularTimerView);
    }

    public void show(String message) {
        this.message.setText(message);
        this.setVisibility(View.VISIBLE);
        Animation animationShow = AnimationUtils.loadAnimation(this.context, R.anim.in_from_top);
        animationShow.setAnimationListener(animationShowListener);
        this.setAnimation(animationShow);
    }

    public void hide() {
        Animation animationHide = AnimationUtils.loadAnimation(this.context, R.anim.out_to_top);
        animationHide.setAnimationListener(animationHideListener);
        this.setAnimation(animationHide);
        MessagesBarView.this.setVisibility(View.GONE);
    }
}
