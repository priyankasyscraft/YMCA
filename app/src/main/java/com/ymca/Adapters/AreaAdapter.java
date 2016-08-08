package com.ymca.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ymca.Activities.HomeActivity;
import com.ymca.AppManager.DataManager;
import com.ymca.Constants.Constant;
import com.ymca.Fragments.CampDetailFragment;
import com.ymca.ModelClass.AreaModelClass;
import com.ymca.R;

import java.util.ArrayList;

/**
 * Created by Soni on 08-Aug-16.
 */
public class AreaAdapter extends BaseAdapter {
    private ArrayList<AreaModelClass> areaModelClassArrayList = new ArrayList<AreaModelClass>();
    private LayoutInflater inflater;
    Context context;
    ViewHolder viewHolder;

    public AreaAdapter(Context context, ArrayList<AreaModelClass> campModelClasses) {
        this.areaModelClassArrayList = campModelClasses;
        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    public void setReloadData(boolean shouldReload) {
        areaModelClassArrayList = DataManager.getInstance().getAreaModelClassArrayList();
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
        return areaModelClassArrayList.size();
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
            convertView = inflater.inflate(R.layout.area_item, null);
            viewHolder.areaInstructor = (TextView) convertView.findViewById(R.id.areaInstructor);
            viewHolder.areaName = (TextView) convertView.findViewById(R.id.areaName);
            viewHolder.areaTime = (TextView) convertView.findViewById(R.id.areaTime);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.areaName.setText(areaModelClassArrayList.get(position).getAreaName());
        viewHolder.areaInstructor.setText(areaModelClassArrayList.get(position).getAreaInstructor());
        viewHolder.areaTime.setText(areaModelClassArrayList.get(position).getAreaTime());


        return convertView;
    }

    public static class ViewHolder {
        TextView areaInstructor, areaName, areaTime;
    }
}