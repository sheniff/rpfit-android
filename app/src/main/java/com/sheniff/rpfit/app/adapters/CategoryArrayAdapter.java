package com.sheniff.rpfit.app.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.sheniff.rpfit.app.models.Category;
import com.sheniff.rpfit.app.R;

import java.util.List;

/**
 * Created by sheniff on 6/10/14.
 */
public class CategoryArrayAdapter extends ArrayAdapter<Category> {
    private static final int RESOURCE = R.layout.profile_category;
    private final Context context;
    private final List<Category> values;

    static class ViewHolder {
        public TextView nameView;
        public TextView levelView;
    }

    public CategoryArrayAdapter(Context context, List<Category> objects) {
        super(context, RESOURCE, objects);
        this.context = context;
        this.values = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = convertView;

        // reuse views
        if (rowView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(RESOURCE, null);
            // configure view holder
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.nameView = (TextView) rowView.findViewById(R.id.categoryName);
            viewHolder.levelView = (TextView) rowView.findViewById(R.id.categoryLevel);
            rowView.setTag(viewHolder);
        }

        // fill data
        // Changing only name for now ToDo: Compute Level
        ViewHolder holder = (ViewHolder) rowView.getTag();
        holder.nameView.setText(values.get(position).getName());
        holder.levelView.setText(String.valueOf(values.get(position).getCurrentLevel()));

        return rowView;
    }
}