package com.sheniff.rpfit.app.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.sheniff.rpfit.app.R;
import com.sheniff.rpfit.app.activities.NewSessionActivity;
import com.sheniff.rpfit.app.adapters.SessionArrayAdapter;
import com.sheniff.rpfit.app.models.Session;

import java.util.ArrayList;

public class NewWorkoutFragment extends Fragment {

    // region Constants
    private static final int NEW_SESSION_REQUEST = 111;
    // endregion
    
    // region Variables
    private Button mBtnNewSession;
    private ListView mLvSessions;
    private SessionArrayAdapter aSessions;
    private ArrayList<Session> sessions;
    // endregion
    
    // region Listeners
    private View.OnClickListener onNewSessionClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivityForResult(new Intent(getActivity(), NewSessionActivity.class), NEW_SESSION_REQUEST);
        }
    };
    // endregion
    
    public static NewWorkoutFragment newInstance() {
        NewWorkoutFragment fragment = new NewWorkoutFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public NewWorkoutFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sessions = new ArrayList<>();
        aSessions = new SessionArrayAdapter(getActivity(), sessions);

        // region TMP
        Session s = new Session("fake Session #1");
        s.getDaysOfWeek().add(1);
        s.getDaysOfWeek().add(3);
        s.getDaysOfWeek().add(6);
        sessions.add(s);
        aSessions.notifyDataSetChanged();
        // endregion
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_new_workout, container, false);
        bindUIElements(v);
        setUpListeners();
        mLvSessions.setAdapter(aSessions);

        return v;
    }

    private void bindUIElements(View v) {
        mBtnNewSession = (Button) v.findViewById(R.id.btnNewSession);
        mLvSessions = (ListView) v.findViewById(R.id.lvSessions);
    }

    private void setUpListeners() {
        mBtnNewSession.setOnClickListener(onNewSessionClickListener);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == Activity.RESULT_OK && requestCode == NEW_SESSION_REQUEST) {
            // ToDo: add Session to the list
        }
    }
}
