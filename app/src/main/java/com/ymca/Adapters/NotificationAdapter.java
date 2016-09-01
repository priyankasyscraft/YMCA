package com.ymca.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ymca.AppManager.DataManager;
import com.ymca.ModelClass.NotificationModelClass;
import com.ymca.R;

import java.util.ArrayList;

/**
 * Created by Soni on 03-Aug-16.
 */
public class NotificationAdapter extends BaseAdapter {
    private ArrayList<NotificationModelClass> notificationModelClassArrayList = new ArrayList<>();
    private LayoutInflater inflater;
    Context context;
    ViewHolder viewHolder;

    public NotificationAdapter(Context context, ArrayList<NotificationModelClass> notificationModelClassArrayList) {
        this.notificationModelClassArrayList = notificationModelClassArrayList;
        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    public void setReloadData(boolean shouldReload) {
        notificationModelClassArrayList = DataManager.getInstance().getNotificationModelClassArrayList();
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
        return notificationModelClassArrayList.size();
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
            convertView = inflater.inflate(R.layout.notification_item, null);
            viewHolder.notifyDate = (TextView) convertView.findViewById(R.id.notifyDate);
            viewHolder.notifyText = (TextView) convertView.findViewById(R.id.notifyText);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.notifyText.setText(notificationModelClassArrayList.get(position).getNotiMessage());
        viewHolder.notifyDate.setText(notificationModelClassArrayList.get(position).getNotiDate());

        DataManager.getInstance().hideProgressMessage();


        return convertView;
    }

    public static class ViewHolder {
        TextView notifyDate,notifyText;
    }
}
