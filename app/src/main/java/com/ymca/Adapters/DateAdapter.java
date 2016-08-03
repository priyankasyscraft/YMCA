package com.ymca.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ymca.AppManager.DataManager;
import com.ymca.ModelClass.DateModelClass;
import com.ymca.R;

import java.util.ArrayList;

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

    @Override
    public int getCount() {
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

        if (convertView == null) {
            viewHolder = new ViewHolder();
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

        String time = DataManager.getInstance().getNotifyDate(dateModelClasses.get(position).getScheduleDateAppointmentTime());
        if(!time.equals("") && time!=null && !time.equals("null")) {
            viewHolder.dateTimeTv.setText(time);
        }else {
            viewHolder.dateTimeTv.setText("");
        }
        viewHolder.dateName.setText(dateModelClasses.get(position).getScheduleDateName());
        viewHolder.dateSubName.setText(dateModelClasses.get(position).getScheduleDateNameWith());
        viewHolder.dateTTimeWithDays.setText(dateModelClasses.get(position).getScheduleDateAppointmentTime());
        viewHolder.dateGroupName.setText(dateModelClasses.get(position).getScheduleDatePlace());

        return convertView;
    }

    public static class ViewHolder {
        TextView dateTimeTv, dateName, dateSubName, dateTTimeWithDays, dateGroupName;
    }
}
