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
import com.ymca.AppManager.SharedPreference;
import com.ymca.Constants.Constant;
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
public class InstructorFragment extends Fragment {

    private View view;
    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    RecyclerView.Adapter mAdapter;
    InstructorDetailFragment instructorDetailFragment = new InstructorDetailFragment();
    private String locationid;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.instructor_fragment, container, false);
//        setData();
        DataManager.getInstance().showProgressMessage(getActivity(), "Progress");
        Map<String, Object> objectMap2 = new LinkedHashMap<>();
        objectMap2.put("type", "instructor"); if(SharedPreference.getSharedPrefData(getActivity(),Constant.defaultLocationId)!=null) {
            locationid = SharedPreference.getSharedPrefData(getActivity(), Constant.defaultLocationId);
        }else {
            locationid = "1";
        }
        // TODO: 26-Aug-16 Here change location id,Set default location id here.
        objectMap2.put("location_id", locationid);
        objectMap2.put("skiprecords","-1");
        JsonCaller.getInstance().getScheduleDataInstru(objectMap2);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new GridLayoutManager(getActivity(), 3);
        mRecyclerView.setLayoutManager(mLayoutManager);
        return view;
    }


    public void onRefreshData(Refreshable refreshable, int requestCode) {
        if (requestCode == JsonCaller.REFRESH_CODE_SCHEDULE_DATA_INSTRU) {
            mAdapter = new InstructorAdapter(getActivity(), DataManager.getInstance().getInstructorModelClassArrayList());
            mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
            mRecyclerView.setHasFixedSize(true);
            mRecyclerView.setVisibility(View.VISIBLE);
            mLayoutManager = new GridLayoutManager(getActivity(), 3);
            mRecyclerView.setLayoutManager(mLayoutManager);
            mRecyclerView.setAdapter(mAdapter);
            DataManager.getInstance().hideProgressMessage();
        } else if (requestCode == JsonCaller.REFRESH_CODE_INSTRUCTOR_DETAIL) {
            getActivity()
                    .getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.content_frame, instructorDetailFragment, Constant.instructorDetailFrag)
                    .addToBackStack(getActivity().getSupportFragmentManager().getClass().getName())
                    .commit();
//            instructorDetailFragment.onRefreshData(refreshable, requestCode);
        }else if(requestCode == JsonCaller.REFRESH_CODE_INSTRUCT_CLASS_DETAIL){
            instructorDetailFragment.onRefreshData(refreshable, requestCode);
        }else if(requestCode == JsonCaller.REFRESH_CODE_SCHEDULE_DATA_INSTRU_NULL){
            mRecyclerView.setVisibility(View.GONE);
        }
    }
}
