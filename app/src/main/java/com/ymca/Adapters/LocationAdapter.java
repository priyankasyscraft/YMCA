package com.ymca.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ymca.Activities.HomeActivity;
import com.ymca.AppManager.DataManager;
import com.ymca.Constants.Constant;
import com.ymca.Fragments.ClassDetailFragment;
import com.ymca.Fragments.LocationDetailFragment;
import com.ymca.ModelClass.LocationModelClass;
import com.ymca.R;

import java.util.ArrayList;

/**
 * Created by Soni on 03-Aug-16.
 */
public class LocationAdapter extends BaseAdapter {
    private ArrayList<LocationModelClass> locationModelClasses = new ArrayList<LocationModelClass>();
    private LayoutInflater inflater;
    Context context;
    ViewHolder viewHolder;

    public LocationAdapter(Context context, ArrayList<LocationModelClass> classesModelClassArrayList) {
        this.locationModelClasses = classesModelClassArrayList;
        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    public void setReloadData(boolean shouldReload) {
        locationModelClasses = DataManager.getInstance().getLocationModelClasses();
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
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.location_item, null);
            viewHolder.locationName = (TextView) convertView.findViewById(R.id.locationName);
            viewHolder.locationDistance = (TextView) convertView.findViewById(R.id.locationDistance);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.locationName.setText(locationModelClasses.get(position).getLocationName());
        viewHolder.locationDistance.setText(locationModelClasses.get(position).getLocationMiles());

        viewHolder.locationDistance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ((HomeActivity) context)
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.content_frame, new LocationDetailFragment(), Constant.locationDetailFragment)
                        .commit();
            }
        });

        return convertView;
    }

    public static class ViewHolder {
        TextView locationName, locationDistance;
    }
}
