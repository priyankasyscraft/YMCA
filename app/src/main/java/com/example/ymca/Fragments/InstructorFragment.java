package com.example.ymca.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ymca.Adapters.GridAdapter;
import com.example.ymca.AppManager.DataManager;
import com.example.ymca.ModelClass.InstructorModelClass;
import com.example.ymca.R;


/**
 * Created by Soni on 28-Jul-16.
 */
public class InstructorFragment extends Fragment {

    private View view;
    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    RecyclerView.Adapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.instructor_fragment,container,false );

        mRecyclerView = (RecyclerView)view.findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new GridLayoutManager(getActivity(), 3);
        mRecyclerView.setLayoutManager(mLayoutManager);

        setData();


        return view;
    }

    private void setData() {

        for(int i=0; i < 19; i++) {
            InstructorModelClass instructorModelClass = new InstructorModelClass();
            instructorModelClass.setInstructorName("Amit");
            instructorModelClass.setInstructorImg("http://img.rtve.es/i/?w=400&crop=no&o=no&i=1435058352516.png");
            DataManager.getInstance().addInstructorModelClassArrayList(instructorModelClass);
        }
        mAdapter = new GridAdapter(getActivity(), DataManager.getInstance().getInstructorModelClassArrayList());
        mRecyclerView.setAdapter(mAdapter);
    }
}