package com.luisitura.dlymansura.rssgrants.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.luisitura.dlymansura.rssgrants.R;
import com.luisitura.dlymansura.rssgrants.model.Fz44Item;
import com.luisitura.dlymansura.rssgrants.model.RssItem;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by Optima on 09.06.2017.
 */

public class Fz44Adapter extends BaseAdapter {

    private final List<Fz44Item> items;
    private final Context context;

    public Fz44Adapter(Context context, List<Fz44Item> items) {
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
        Fz44Adapter.ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.fz44_adapter_layout, null);
            holder = new Fz44Adapter.ViewHolder();
            holder.itemTitle = (TextView) convertView.findViewById(R.id.fz44_list_item);
            holder.itemDate = (TextView) convertView.findViewById(R.id.fz44_item_date);
            holder.itemRegion = (TextView) convertView.findViewById(R.id.fz44_region);
            holder.itemPrice = (TextView) convertView.findViewById(R.id.fz44_lot_price);
            holder.viewItem = convertView.findViewById(R.id.viewItem);
            convertView.setTag(holder);
        } else {
            holder = (Fz44Adapter.ViewHolder) convertView.getTag();
        }

        holder.itemTitle.setText(items.get(position).getPurchaseObject());
        holder.itemDate.setText("Дедлайн: " + items.get(position).getEndDate());
        holder.itemRegion.setText(items.get(position).getRegion());
        holder.itemPrice.setText(items.get(position).getMaxPrice() + " " + items.get(position).getCurrency());

        return convertView;
    }

    static class ViewHolder {
        TextView itemTitle;
        TextView itemDate;
        TextView itemRegion;
        TextView itemPrice;
        View viewItem;
    }
}
