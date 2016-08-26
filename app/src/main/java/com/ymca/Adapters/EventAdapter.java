package com.ymca.Adapters;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
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
import com.ymca.ModelClass.EventNewModelClass;
import com.ymca.R;
import com.ymca.WebManager.JsonCaller;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Soni on 03-Aug-16.
 */
public class EventAdapter extends BaseAdapter {
    private ArrayList<EventNewModelClass> eventModelClassArrayList = new ArrayList<>();
    private LayoutInflater inflater;
    Context context;
    ViewHolder viewHolder;
    String date;

    public EventAdapter(Context context, ArrayList<EventNewModelClass> eventNewModelClassArrayList) {
        this.eventModelClassArrayList = eventNewModelClassArrayList;
        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }




//    public void setReloadData(boolean shouldReload) {
//        eventModelClassArrayList = DataManager.getInstance().getEventModelClasses();
//        if (shouldReload) {
//            DataManager.getInstance().getAppCompatActivity().runOnUiThread(new Runnable() {
//                public void run() {
//                    notifyDataSetChanged();
//                    DataManager.getInstance().hideProgressMessage();
//                }
//            });
//        }
//    }

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
    public View getView(final int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.event_items, null);
            viewHolder.eventLayout = (RelativeLayout) convertView.findViewById(R.id.eventLayout);
            viewHolder.eventDay = (TextView) convertView.findViewById(R.id.eventDay);
            viewHolder.eventName = (TextView) convertView.findViewById(R.id.eventName);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

//        if (eventModelClassArrayList.get(position).getEventStartDates().equals(date)) {
            viewHolder.eventName.setText(eventModelClassArrayList.get(position).getEventName());
//        } else if (eventModelClassArrayList.get(position).getEventEndDates().equals(date)) {
//            viewHolder.eventName.setText(eventModelClassArrayList.get(position).getEventName());
//        }else {
//            viewHolder.eventLayout.setVisibility(View.GONE);
//        }


        viewHolder.eventLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isCheck = DataManager.chkStatus(context);
                if (isCheck) {
                    DataManager.getInstance().setEventModelClass(eventModelClassArrayList.get(position));
                    DataManager.getInstance().showProgressMessage(DataManager.getInstance().getAppCompatActivity(),"Progress");
                    Map<String,Object> objectMap = new LinkedHashMap<String, Object>();
                    objectMap.put("event_id",eventModelClassArrayList.get(position).getEventId());
                    JsonCaller.getInstance().getEventDetail(objectMap);

//                    ((HomeActivity) context)
//                            .getSupportFragmentManager()
//                            .beginTransaction()
//                            .replace(R.id.content_frame, new EventDetailFragment(), Constant.eventDetailFragment)
//                            .addToBackStack(((HomeActivity) context).getFragmentManager().getClass().getName())
//                            .commit();
                }
            }
        });

        return convertView;
    }

    public static class ViewHolder {
        TextView eventName, eventDate, eventMonth, eventDay;
        RelativeLayout eventLayout;
    }
}
