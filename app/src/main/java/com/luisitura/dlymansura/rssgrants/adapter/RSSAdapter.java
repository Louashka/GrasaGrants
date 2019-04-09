package com.luisitura.dlymansura.rssgrants.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.luisitura.dlymansura.rssgrants.R;
import com.luisitura.dlymansura.rssgrants.model.RssItem;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by Admin on 22.03.2017.
 */

public class RSSAdapter extends BaseAdapter {

    private final List<RssItem> items;
    private final Context context;
    private Date dateFrom;
    private String targetDate = "";

    public RSSAdapter(Context context, List<RssItem> items) {
        this.items = items;
        this.context = context;
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
    public long getItemId(int id) {
        return id;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.list_item, null);
            holder = new ViewHolder();
            holder.itemTitle = (TextView) convertView.findViewById(R.id.rss_list_item);
            holder.itenDate = (TextView) convertView.findViewById(R.id.rss_item_date);
            holder.viewItem = convertView.findViewById(R.id.viewItem);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        SimpleDateFormat formatnow = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss zzzz", Locale.US);
        SimpleDateFormat formatneeded=new SimpleDateFormat("dd-MM-yyyy");
        try {
            dateFrom = formatnow.parse(items.get(position).getPubDate());
            targetDate = formatneeded.format(dateFrom);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        holder.itemTitle.setText(items.get(position).getTitle());
        if (items.get(position).getResource().equals("grantist")) {
            holder.itenDate.setText("Дедлайн: " + targetDate);
        } else {
            holder.itenDate.setText(targetDate);
        }


        return convertView;
    }

    static class ViewHolder {
        TextView itemTitle;
        TextView itenDate;
        View viewItem;
    }
}
