package com.example.adapter;

import android.content.Context;
import android.print.PrinterId;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.entity.PopItem;
import com.example.mobilesafe.R;

import java.util.ArrayList;

public class PopWindowAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<PopItem> list =  new ArrayList<>();
    private TextView tv;

    public PopWindowAdapter(Context context, ArrayList<PopItem> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public PopItem getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        PopItem item = getItem(position);
        convertView = LayoutInflater.from(context).inflate(R.layout.pop_item, null);
        tv = convertView.findViewById(R.id.text);
        tv.setText(item.name);
        return convertView;
    }
}
