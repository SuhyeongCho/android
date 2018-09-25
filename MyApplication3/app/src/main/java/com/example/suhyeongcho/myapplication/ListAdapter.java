package com.example.suhyeongcho.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ListAdapter extends BaseAdapter {

    private ArrayList<ReservationItem> mList;

    public ListAdapter(ArrayList<ReservationItem> _mList) {
        mList = _mList;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public ReservationItem getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Context context = parent.getContext();

        /* 'listview_custom' Layout을 inflate하여 convertView 참조 획득 */
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.reservation_listview, parent, false);
        }

        TextView time = convertView.findViewById(R.id.time);
        TextView name = convertView.findViewById(R.id.name);
        TextView phone = convertView.findViewById(R.id.phone);
        TextView cnt = convertView.findViewById(R.id.cnt);
        TextView table = convertView.findViewById(R.id.table);

        ReservationItem item = getItem(position);

        time.setText(item.time);
        name.setText(item.name);
        phone.setText(item.phone);
        cnt.setText(Integer.toString(item.count));
        table.setText(Integer.toString(item.table));

        return convertView;
    }
}
