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

        classScheduleListView = (XListView) view.findViewById(R.id.classScheduleListView);
        classAdapter = new ClassAdapter(getActivity(), DataManager.getInstance().getClassesModelClassArrayList());
        classScheduleListView.setAdapter(classAdapter);


        setData();
        return view;
    }

    private void setData() {
        for (int i = 0; i < 50; i++) {
            ClassesModelClass classesModelClass = new ClassesModelClass();
            classesModelClass.setClassesName("Yoga Class");
            DataManager.getInstance().addClassesModelClassArrayList(classesModelClass);
        }
        classAdapter.setReloadData(true);
    }


}
