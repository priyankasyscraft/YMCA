package com.ymca.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.TextView;

import com.ymca.AppManager.DataManager;
import com.ymca.ModelClass.DateModelClass;
import com.ymca.R;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by Soni on 03-Aug-16.
 */
public class DateAdapter extends BaseAdapter {
    private ArrayList<DateModelClass> dateModelClasses = new ArrayList<DateModelClass>();
    private LayoutInflater inflater;
    Context context;
    ViewHolder viewHolder;

    public DateAdapter(Context context, ArrayList<DateModelClass> dateModelClasses) {
        this.dateModelClasses = dateModelClasses;
        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    public void setReloadData(boolean shouldReload) {
        dateModelClasses = DataManager.getInstance().getDateModelClasses();
        if (shouldReload) {
            DataManager.getInstance().getAppCompatActivity().runOnUiThread(new Runnable() {
                public void run() {
                    notifyDataSetChanged();
                    DataManager.getInstance().hideProgressMessage();
                }
            });
        }
    }

//    public void loadData(ArrayList<DateModelClass> dateModelArrayList) {
//        dateModelClasses.clear();
//        dateModelClasses.addAll(dateModelArrayList);
//        DataManager.getInstance().getAppCompatActivity().runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                notifyDataSetChanged();
//            }
//        });
//    }

    @Override
    public int getCount() {

        if(dateModelClasses.size()==0)
            return 0;
        return dateModelClasses.size();
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
    public View getView(int position, View convertView, ViewGroup parent) {
        viewHolder = new ViewHolder();
        if (convertView == null) {

            convertView = inflater.inflate(R.layout.date_list_item, null);
            viewHolder.dateTimeTv = (TextView) convertView.findViewById(R.id.dateTimeTv);
            viewHolder.dateName = (TextView) convertView.findViewById(R.id.dateName);
            viewHolder.dateSubName = (TextView) convertView.findViewById(R.id.dateSubName);
            viewHolder.dateTTimeWithDays = (TextView) convertView.findViewById(R.id.dateTTimeWithDays);
            viewHolder.dateGroupName = (TextView) convertView.findViewById(R.id.dateGroupName);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.dateTimeTv.setText(dateModelClasses.get(position).getScheduleDateTime());
        viewHolder.dateName.setText(dateModelClasses.get(position).getScheduleDateName());
        viewHolder.dateSubName.setText(dateModelClasses.get(position).getScheduleDateNameWith());
        viewHolder.dateTTimeWithDays.setText(dateModelClasses.get(position).getScheduleDateWeekDays());
        viewHolder.dateGroupName.setText(dateModelClasses.get(position).getScheduleDatePlace());

        return convertView;
    }


    public static class ViewHolder {
       private TextView dateTimeTv, dateName, dateSubName, dateTTimeWithDays, dateGroupName;
    }


    // Filter Class
//    public void filter(String charText) {
//        charText = charText.toLowerCase(Locale.getDefault());
//        dateModelClassArrayList.clear();
//        if (charText.length() == 0) {
//            dateModelClassArrayList.addAll(dateModelClasses);
//        } else {
//            for (DateModelClass wp : dateModelClasses) {
//                if (wp.getScheduleDateName().toLowerCase(Locale.getDefault())
//                        .contains(charText)) {
//                    dateModelClassArrayList.add(wp);
//                }
//            }
//        }
//        notifyDataSetChanged();
//    }
}
