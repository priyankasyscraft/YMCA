package com.ymca.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ymca.Adapters.ClassAdapter;
import com.ymca.AppManager.DataManager;
import com.ymca.ModelClass.ClassesModelClass;
import com.ymca.PullListLoader.XListView;
import com.ymca.R;
import com.ymca.UserInterFace.RefreshDataListener;
import com.ymca.UserInterFace.Refreshable;
import com.ymca.WebManager.JsonCaller;

import java.util.LinkedHashMap;
import java.util.Map;


/**
 * Created by Soni on 28-Jul-16.
 */
public class ClassFragment extends Fragment {

    private View view;
    XListView classScheduleListView;
    ClassAdapter classAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.class_fragment, container, false);

        DataManager.getInstance().showProgressMessage(getActivity(), "Progress");
        Map<String, Object> objectMap = new LinkedHashMap<>();
        objectMap.put("type", "class");
        JsonCaller.getInstance().getScheduleDataClass(objectMap);
        classScheduleListView = (XListView) view.findViewById(R.id.classScheduleListView);
        return view;
    }

    public void onRefreshData(Refreshable refreshable, int requestCode) {
        if (requestCode == JsonCaller.REFRESH_CODE_SCHEDULE_DATA_CLASS) {
            classScheduleListView = (XListView) view.findViewById(R.id.classScheduleListView);
            classAdapter = new ClassAdapter(getActivity(), DataManager.getInstance().getClassesModelClassArrayList());
            classScheduleListView.setAdapter(classAdapter);
        }
    }
}
