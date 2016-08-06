package com.ymca.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ymca.AppManager.DataManager;
import com.ymca.ModelClass.FacilityModelClass;
import com.ymca.R;

import java.util.ArrayList;

/**
 * Created by Soni on 03-Aug-16.
 */
public class FacilityAdapter extends BaseAdapter {
    private ArrayList<FacilityModelClass> facilityModelClassArrayList = new ArrayList<FacilityModelClass>();
    private LayoutInflater inflater;
    Context context;
    ViewHolder viewHolder;

    public FacilityAdapter(Context context, ArrayList<FacilityModelClass> facilityModelClassArrayList) {
        this.facilityModelClassArrayList = facilityModelClassArrayList;
        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    public void setReloadData(boolean shouldReload) {
        facilityModelClassArrayList = DataManager.getInstance().getFacilityModelClassArrayList();
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
        return facilityModelClassArrayList.size();
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
            convertView = inflater.inflate(R.layout.facility_item, null);
            viewHolder.facilityName = (TextView) convertView.findViewById(R.id.facilityName);
            viewHolder.facilityAddress = (TextView) convertView.findViewById(R.id.facilityAddress);
            viewHolder.facilityStatus = (TextView) convertView.findViewById(R.id.facilityStatus);
            viewHolder.statusImg = (ImageView) convertView.findViewById(R.id.statusImg);
            viewHolder.facilityLayout = (LinearLayout) convertView.findViewById(R.id.facilityLayout);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.facilityName.setText(facilityModelClassArrayList.get(position).getFacilityName());
        viewHolder.facilityAddress.setText(facilityModelClassArrayList.get(position).getFacilityAddress());
        viewHolder.facilityStatus.setText(facilityModelClassArrayList.get(position).getFacilityOpenClose());
        if (facilityModelClassArrayList.get(position).isFacilityStatus()) {
            viewHolder.statusImg.setImageResource(R.mipmap.open);
        } else {
            viewHolder.statusImg.setImageResource(R.mipmap.close);
        }

        return convertView;
    }

    public static class ViewHolder {
        TextView facilityName, facilityAddress, statusText, facilityStatus;
        ImageView statusImg;
        LinearLayout facilityLayout;
    }
}
