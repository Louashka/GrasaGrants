package com.luisitura.dlymansura.rssgrants.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.luisitura.dlymansura.rssgrants.R;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Optima on 13.06.2017.
 */

public class FilterAdapter extends BaseAdapter {

    private final List<String> items;
    private final Context context;

    public FilterAdapter(Context context) {
        this.context = context;
        this.items = Arrays.asList(context.getResources().getStringArray(R.array.array_filter_items));
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        FilterAdapter.ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.filter_layout, null);
            holder = new FilterAdapter.ViewHolder();
            holder.textViewTitle = (TextView) convertView.findViewById(R.id.textViewTitle);
            holder.textViewFilter = (TextView) convertView.findViewById(R.id.textViewFilter);
            convertView.setTag(holder);
        } else {
            holder = (FilterAdapter.ViewHolder) convertView.getTag();
        }

        holder.textViewTitle.setText(items.get(position));
        if(position == 0){
            holder.textViewFilter.setText("По дате добавления");
        } else {
            holder.textViewFilter.setText("Все");
        }

        return convertView;
    }

    static class ViewHolder {
        TextView textViewTitle;
        TextView textViewFilter;
    }
}
