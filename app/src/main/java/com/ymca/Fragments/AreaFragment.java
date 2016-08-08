package com.ymca.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ymca.Adapters.AreaAdapter;
import com.ymca.AppManager.DataManager;
import com.ymca.ModelClass.AreaModelClass;
import com.ymca.PullListLoader.XListView;
import com.ymca.R;

/**
 * Created by Soni on 08-Aug-16.
 */
public class AreaFragment extends Fragment {

    private View view;
    XListView areaList;
    AreaAdapter areaAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.area_fragment,container,false);

        areaList = (XListView)view.findViewById(R.id.areaList);

        areaAdapter = new AreaAdapter(getActivity(), DataManager.getInstance().getAreaModelClassArrayList());
        areaList.setAdapter(areaAdapter);

        setData();

        return view;
    }

    private void setData() {
        for(int i = 0;i<20;i++){
            AreaModelClass areaModelClass = new AreaModelClass();
            areaModelClass.setAreaName("Yoga Class");
            areaModelClass.setAreaInstructor("with James Mac Mann");
            areaModelClass.setAreaTime("07:30 am");
            DataManager.getInstance().addAreaModelClassArrayList(areaModelClass);
        }
        areaAdapter.setReloadData(true);
    }
}
