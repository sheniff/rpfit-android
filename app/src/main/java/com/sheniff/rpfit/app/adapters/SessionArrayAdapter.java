package com.sheniff.rpfit.app.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.sheniff.rpfit.app.R;
import com.sheniff.rpfit.app.models.Session;

import java.util.ArrayList;
import java.util.List;

public class SessionArrayAdapter extends ArrayAdapter<Session> {

    public SessionArrayAdapter(Context context, List<Session> sessions) {
        super(context, R.layout.session_item, sessions);
    }

    private static class ViewHolder {
        private TextView mTvName;
        private ArrayList<TextView> mTvDays;
        private TextView mTvSessionNumExercises;
    }
    
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Session session = getItem(position);
        ViewHolder viewHolder;
        
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.session_item, parent, false);
            viewHolder.mTvName = (TextView) convertView.findViewById(R.id.tvSessionName);
            
            viewHolder.mTvDays = new ArrayList<>();
            viewHolder.mTvDays.add((TextView) convertView.findViewById(R.id.tvMonday));
            viewHolder.mTvDays.add((TextView) convertView.findViewById(R.id.tvTuesday));
            viewHolder.mTvDays.add((TextView) convertView.findViewById(R.id.tvWednesday));
            viewHolder.mTvDays.add((TextView) convertView.findViewById(R.id.tvThursday));
            viewHolder.mTvDays.add((TextView) convertView.findViewById(R.id.tvFriday));
            viewHolder.mTvDays.add((TextView) convertView.findViewById(R.id.tvSaturday));
            viewHolder.mTvDays.add((TextView) convertView.findViewById(R.id.tvSunday));
            viewHolder.mTvSessionNumExercises = (TextView) convertView.findViewById(R.id.tvSessionNumExercises);
            
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        
        viewHolder.mTvName.setText(session.getName());
        viewHolder.mTvSessionNumExercises.setText("(" + session.getExercises().size() + " exercises)");
        
        // reset days first
        for(TextView tvDay:viewHolder.mTvDays) {
            tvDay.setEnabled(true);
        }
        // disable the days to be marked as selected
        for(int day:session.getDaysOfWeek()) {
            viewHolder.mTvDays.get(day).setEnabled(false);
        }
        
        return convertView;
    }
}
