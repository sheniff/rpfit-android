package com.sheniff.rpfit.app.view;

import android.content.Context;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.sheniff.rpfit.app.R;

/**
 * Created by sheniff on 5/15/14.
 */
public class RevealablePasswordEditText extends LinearLayout {

    //region Variables
    private EditText password;
    private Button toggleButton;
    //endregion

    //region Listeners
    private OnClickListener toggleButtonListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            if(password.getInputType() == 129) {
                password.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
            } else {
                password.setInputType(129); // === InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD
            }
        }
    };
    //endregion

    public RevealablePasswordEditText(Context context) {
        this(context, null);
    }

    public RevealablePasswordEditText(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RevealablePasswordEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.discoverable_password, this, true);

        bindUIElements();
        setUpListeners();
    }

    private void bindUIElements() {
        password = (EditText) findViewById(R.id.passwordInput);
        toggleButton = (Button) findViewById(R.id.reveal_password_button);
    }

    private void setUpListeners() {
        toggleButton.setOnClickListener(toggleButtonListener);
    }

    public String getPassword() {
        return password.getText().toString();
    }
}
