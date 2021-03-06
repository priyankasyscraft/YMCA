package com.ymca.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ymca.Activities.HomeActivity;
import com.ymca.AppManager.DataManager;
import com.ymca.Constants.Constant;
import com.ymca.Fragments.LocationDetailFragment;
import com.ymca.ModelClass.FacilityModelClass;
import com.ymca.ModelClass.LocationModelClass;
import com.ymca.R;

import java.util.ArrayList;

/**
 * Created by Soni on 03-Aug-16.
 */
public class FacilityAdapter extends BaseAdapter {
    private ArrayList<FacilityModelClass> locationModelClasses = new ArrayList<>();
    private LayoutInflater inflater;
    Context context;
    ViewHolder viewHolder;

    public FacilityAdapter(Context context, ArrayList<FacilityModelClass> classesModelClassArrayList) {
        this.locationModelClasses = classesModelClassArrayList;
        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    public void setReloadData(boolean shouldReload) {
        locationModelClasses = DataManager.getInstance().getFacilityModelClassArrayList();
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
        return locationModelClasses.size();
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
            convertView = inflater.inflate(R.layout.facility_item, null);
            viewHolder.statusImg = (ImageView) convertView.findViewById(R.id.statusImg);
            viewHolder.locationLayout = (LinearLayout) convertView.findViewById(R.id.locationLayout);
            viewHolder.locationName = (TextView) convertView.findViewById(R.id.locationName);
            viewHolder.locationDistance = (TextView) convertView.findViewById(R.id.locationDistance);
            viewHolder.facilityAddress = (TextView) convertView.findViewById(R.id.facilityAddress);
            viewHolder.facilityStatus = (TextView) convertView.findViewById(R.id.facilityStatus);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.locationName.setText(locationModelClasses.get(position).getFacilityName());
        viewHolder.locationDistance.setText(locationModelClasses.get(position).getFacilityMiles());
        viewHolder.facilityAddress.setText(locationModelClasses.get(position).getFacilityAddress());
        viewHolder.facilityStatus.setText(locationModelClasses.get(position).getFacilityOpenCloseTime());

        if (locationModelClasses.get(position).getFacilityOpenCloseStatus().equals("1")) {
            viewHolder.statusImg.setImageResource(R.mipmap.open);
        } else {
            viewHolder.statusImg.setImageResource(R.mipmap.close);
        }

        viewHolder.locationLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DataManager.getInstance().setFacilityModelClass(locationModelClasses.get(position));

                ((HomeActivity) context)
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.content_frame, new LocationDetailFragment(), Constant.locationDetailFragment)
                        .addToBackStack(((HomeActivity) context).getSupportFragmentManager().getClass().getName())
                        .commit();
            }
        });

        return convertView;
    }

    public static class ViewHolder {
        TextView locationName, locationDistance, facilityAddress,facilityStatus;
        ImageView statusImg;
        LinearLayout locationLayout;
    }
}
