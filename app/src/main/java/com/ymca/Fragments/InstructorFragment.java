package com.ymca.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ymca.Adapters.InstructorAdapter;
import com.ymca.AppManager.DataManager;
import com.ymca.ModelClass.InstructorModelClass;
import com.ymca.R;
import com.ymca.UserInterFace.RefreshDataListener;
import com.ymca.UserInterFace.Refreshable;
import com.ymca.WebManager.JsonCaller;

import java.util.LinkedHashMap;
import java.util.Map;


/**
 * Created by Soni on 28-Jul-16.
 */
public class InstructorFragment extends Fragment  {

    private View view;
    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    RecyclerView.Adapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.instructor_fragment,container,false );
//        setData();
        DataManager.getInstance().showProgressMessage(getActivity(),"Progress");
        Map<String,Object> objectMap2 = new LinkedHashMap<>();
        objectMap2.put("type","instructor");
        JsonCaller.getInstance().getScheduleDataInstru(objectMap2);
        mRecyclerView = (RecyclerView)view.findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new GridLayoutManager(getActivity(), 3);
        mRecyclerView.setLayoutManager(mLayoutManager);





        return view;
    }



    public void onRefreshData(Refreshable refreshable, int requestCode) {
        if(requestCode == JsonCaller.REFRESH_CODE_SCHEDULE_DATA_INSTRU) {
            mAdapter = new InstructorAdapter(getActivity(), DataManager.getInstance().getInstructorModelClassArrayList());
            mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
            mRecyclerView.setHasFixedSize(true);
            mLayoutManager = new GridLayoutManager(getActivity(), 3);
            mRecyclerView.setLayoutManager(mLayoutManager);
            mRecyclerView.setAdapter(mAdapter);
            DataManager.getInstance().hideProgressMessage();
        }
    }
}
