package com.sheniff.rpfit.app.fragments;


import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.sheniff.rpfit.app.R;
import com.sheniff.rpfit.app.models.Session;

import java.util.ArrayList;

public class NewSessionFragment extends Fragment {

    // region Variables
    private TextView mTvSessionName;
    private Button mBtnCreateSession;
    private Session mSession;
    private ArrayList<TextView> tvDays;
    // endregion
    
    // region Listeners
    private View.OnClickListener onDayClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            v.setActivated(!v.isActivated());
        }
    };
    private View.OnClickListener onCreateListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // create session
            mSession.setName(mTvSessionName.getText().toString());
            
            // selected DoW
            ArrayList<Integer> dows = new ArrayList<>();
            for(int i = 0; i < tvDays.size(); i++) {
                if(tvDays.get(i).isActivated()) {
                    dows.add(i);
                }
            }
            mSession.setDaysOfWeek(dows);
            
            // finish activity and return session
            Intent response = new Intent();
            // ToDo: Implement Parcelable for Session
//            response.putExtra("session", mSession);
            getActivity().setResult(Activity.RESULT_OK, response);
            getActivity().finish();
        }
    };
    // endregion
    
    public NewSessionFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSession = new Session();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_new_session, container, false);

        bindUIElements(v);
        setUpListeners();
        
        return v;
    }

    private void bindUIElements(View v) {
        mTvSessionName = (TextView) v.findViewById(R.id.etSessionName);
        mBtnCreateSession = (Button) v.findViewById(R.id.btnCreateSession);
        tvDays = new ArrayList<>();
        tvDays.add((TextView) v.findViewById(R.id.tvMonday));
        tvDays.add((TextView) v.findViewById(R.id.tvTuesday));
        tvDays.add((TextView) v.findViewById(R.id.tvWednesday));
        tvDays.add((TextView) v.findViewById(R.id.tvThursday));
        tvDays.add((TextView) v.findViewById(R.id.tvFriday));
        tvDays.add((TextView) v.findViewById(R.id.tvSaturday));
        tvDays.add((TextView) v.findViewById(R.id.tvSunday));
    }

    private void setUpListeners() {
        mBtnCreateSession.setOnClickListener(onCreateListener);
        for(TextView tv:tvDays) {
            tv.setOnClickListener(onDayClickListener);
        }
    }

}
