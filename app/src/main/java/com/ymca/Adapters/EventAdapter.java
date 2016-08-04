package com.ymca.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ymca.Activities.HomeActivity;
import com.ymca.AppManager.DataManager;
import com.ymca.Constants.Constant;
import com.ymca.Fragments.ClassDetailFragment;
import com.ymca.Fragments.EventDetailFragment;
import com.ymca.ModelClass.EventModelClass;
import com.ymca.R;

import java.util.ArrayList;

/**
 * Created by Soni on 03-Aug-16.
 */
public class EventAdapter extends BaseAdapter {
    private ArrayList<EventModelClass> eventModelClassArrayList = new ArrayList<EventModelClass>();
    private LayoutInflater inflater;
    Context context;
    ViewHolder viewHolder;

    public EventAdapter(Context context, ArrayList<EventModelClass> eventModelClasses) {
        this.eventModelClassArrayList = eventModelClasses;
        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    public void setReloadData(boolean shouldReload) {
        eventModelClassArrayList = DataManager.getInstance().getEventModelClasses();
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
        return eventModelClassArrayList.size();
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
            convertView = inflater.inflate(R.layout.event_items, null);
            viewHolder.eventLayout = (RelativeLayout) convertView.findViewById(R.id.eventLayout);
            viewHolder.eventName = (TextView) convertView.findViewById(R.id.eventName);
            viewHolder.eventDate = (TextView) convertView.findViewById(R.id.eventDate);
            viewHolder.eventMonth = (TextView) convertView.findViewById(R.id.eventMonth);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.eventName.setText(eventModelClassArrayList.get(position).getEventName());
        viewHolder.eventDate.setText(eventModelClassArrayList.get(position).getEventdate());
        viewHolder.eventMonth.setText(eventModelClassArrayList.get(position).getEventMonth());

        viewHolder.eventLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ((HomeActivity) context)
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.content_frame, new EventDetailFragment(), Constant.eventDetailFragment)
                        .addToBackStack(((HomeActivity) context).getFragmentManager().getClass().getName())
                        .commit();
            }
        });

        return convertView;
    }

    public static class ViewHolder {
        TextView eventName, eventDate, eventMonth;
        RelativeLayout eventLayout;
    }
}
