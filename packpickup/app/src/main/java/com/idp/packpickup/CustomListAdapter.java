package com.idp.packpickup;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class CustomListAdapter extends BaseAdapter {
    private ArrayList<Offer> listData;
    private LayoutInflater layoutInflater;

    public CustomListAdapter(Context aContext, ArrayList<Offer> listData) {
        this.listData = listData;
        layoutInflater = LayoutInflater.from(aContext);
    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int position) {
        return listData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.table_item, null);
            holder = new ViewHolder();
            holder.thumbnail = (ImageView) convertView.findViewById(R.id.thumbnail);
            holder.name_user = (TextView) convertView.findViewById(R.id.name_user);
            holder.phone = (TextView) convertView.findViewById(R.id.phone);
            holder.departure = (TextView) convertView.findViewById(R.id.departure);
            holder.arrival = (TextView) convertView.findViewById(R.id.arrival);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        //holder.headlineView.setText(listData.get(position).getHeadline());
        //holder.reporterNameView.setText("By, " + listData.get(position).getReporterName());
        //holder.reportedDateView.setText(listData.get(position).getDate());
        holder.thumbnail.setImageBitmap(listData.get(position).getImage());
        holder.name_user.setText(listData.get(position).getName_user());
        holder.phone.setText(listData.get(position).getPhone());
        holder.departure.setText(listData.get(position).getDeparture());
        holder.arrival.setText(listData.get(position).getArrival());
        return convertView;
    }

    static class ViewHolder {
        ImageView thumbnail;
        TextView name_user;
        TextView phone;
        TextView departure;
        TextView arrival;
    }
}