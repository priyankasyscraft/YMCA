package com.ymca.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ymca.Adapters.LocationAdapter;
import com.ymca.AppManager.DataManager;
import com.ymca.ModelClass.LocationModelClass;
import com.ymca.PullListLoader.XListView;
import com.ymca.R;


/**
 * Created by Soni on 28-Jul-16.
 */
public class LocationFragment extends Fragment {

    private View view;
    XListView locationListView;
    LocationAdapter locationAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.location_fragment,container,false );

        locationListView = (XListView)view.findViewById(R.id.locationListView);
        locationAdapter = new LocationAdapter(getActivity(), DataManager.getInstance().getLocationModelClasses());
        locationListView.setAdapter(locationAdapter);
        setData();

        return view;
    }

    private void setData() {
        for(int i = 0; i<20; i++){
            LocationModelClass locationModelClass = new LocationModelClass();
            locationModelClass.setLocationName("Gordon family YMCA");

            locationModelClass.setLocationMiles(String.format("%.2f",DataManager.getInstance().distance(22.719569,75.857726,22.962267,76.050795)));

            DataManager.getInstance().addLocationModelClasses(locationModelClass);
        }

        locationAdapter.setReloadData(true);
    }
}
