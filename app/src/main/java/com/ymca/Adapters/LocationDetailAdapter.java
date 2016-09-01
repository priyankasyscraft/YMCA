package com.ymca.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ymca.AppManager.DataManager;
import com.ymca.ModelClass.ClassesModelClass;
import com.ymca.R;
import com.ymca.WebManager.JsonCaller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Soni on 03-Aug-16.
 */
public class LocationDetailAdapter extends BaseAdapter {
    private ArrayList<String> classesModelClassArrayList = new ArrayList<String>();
    private LayoutInflater inflater;
    Context context;
    ViewHolder viewHolder;
    String facilityOpenCloseTime;
    public LocationDetailAdapter(Context context, ArrayList<String> classesModelClassArrayList,String facilityOpenCloseTime) {
        this.classesModelClassArrayList = classesModelClassArrayList;
        this.facilityOpenCloseTime = facilityOpenCloseTime;
        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        return classesModelClassArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.location_detail_list_item, null);
            viewHolder.weekDays = (TextView) convertView.findViewById(R.id.weekDays);
            viewHolder.weekTime = (TextView) convertView.findViewById(R.id.weekTime);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.weekDays.setText(classesModelClassArrayList.get(position));
        viewHolder.weekTime.setText(facilityOpenCloseTime);

        DataManager.getInstance().hideProgressMessage();


        return convertView;
    }

    public static class ViewHolder {
        TextView weekDays,weekTime;
    }
}
