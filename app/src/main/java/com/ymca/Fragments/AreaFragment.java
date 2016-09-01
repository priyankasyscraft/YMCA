package com.ymca.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ymca.Adapters.AreaAdapter;
import com.ymca.AppManager.DataManager;
import com.ymca.AppManager.SharedPreference;
import com.ymca.Constants.Constant;
import com.ymca.ModelClass.AreaModelClass;
import com.ymca.PullListLoader.XListView;
import com.ymca.R;
import com.ymca.UserInterFace.RefreshDataListener;
import com.ymca.UserInterFace.Refreshable;
import com.ymca.WebManager.JsonCaller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Soni on 08-Aug-16.
 */
public class AreaFragment extends Fragment  {

    private View view;
    XListView areaList;
    AreaAdapter areaAdapter;
    private Calendar c;
    private SimpleDateFormat df;
    private String locationid;
    TextView areaDetailHeader;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.area_fragment,container,false);

        areaList = (XListView)view.findViewById(R.id.areaList);
        areaDetailHeader = (TextView) view.findViewById(R.id.areaDetailHeader);

        areaAdapter = new AreaAdapter(getActivity(), DataManager.getInstance().getAreaModelClassArrayList());
        areaList.setAdapter(areaAdapter);
        c = Calendar.getInstance();
        df = new SimpleDateFormat("yyyy/MM/dd");

        if(SharedPreference.getSharedPrefData(getActivity(), Constant.defaultLocation)!=null) {
            int  position = Integer.parseInt(SharedPreference.getSharedPrefData(getActivity(), Constant.defaultLocation));
            areaDetailHeader.setText(DataManager.getInstance().getLocationModelClasses().get(position).getLocationName());
        }else {
            areaDetailHeader.setText(DataManager.getInstance().getLocationModelClasses().get(0).getLocationName());
        }
        String date = df.format(c.getTime());
        DataManager.getInstance().showProgressMessage(getActivity(),"Progress");
        if(SharedPreference.getSharedPrefData(getActivity(), Constant.defaultLocationId)!=null) {
            locationid = SharedPreference.getSharedPrefData(getActivity(), Constant.defaultLocationId);
        }else {
            locationid = "1";
        }
        Map<String,Object> objectMap = new LinkedHashMap<>();
        objectMap.put("type","area");
        objectMap.put("date",date);
        objectMap.put("location_id",locationid);
        objectMap.put("skiprecords","-1");
        JsonCaller.getInstance().getScheduleDataArea(objectMap);

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



    public void onRefreshData(Refreshable refreshable, int requestCode) {

        if(requestCode == JsonCaller.REFRESH_CODE_SCHEDULE_DATA_AREA){
            areaAdapter.setReloadData(true);
        }
    }
}
