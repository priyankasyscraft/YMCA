package com.ymca.Fragments;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ymca.Activities.HomeActivity;
import com.ymca.Adapters.InstructorAdapter;
import com.ymca.Adapters.TraineeAdapter;
import com.ymca.AppManager.DataManager;
import com.ymca.ModelClass.InstructorModelClass;
import com.ymca.ModelClass.TraineeModelClass;
import com.ymca.R;

/**
 * Created by Soni on 12-Aug-16.
 */
public class TrainerFragment extends Fragment {

    private View view;
    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    RecyclerView.Adapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.trainee_fragment,container,false);
        actionBarUpdate();
        mRecyclerView = (RecyclerView)view.findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new GridLayoutManager(getActivity(), 3);
        mRecyclerView.setLayoutManager(mLayoutManager);
        setData();
        return view;
    }

    private void setData() {

        for(int i=0; i < 19; i++) {
            TraineeModelClass traineeModelClass = new TraineeModelClass();
            traineeModelClass.setTraineeName("Amit");
            traineeModelClass.setTraineeImg("http://img.rtve.es/i/?w=400&crop=no&o=no&i=1435058352516.png");
            DataManager.getInstance().addTraineeModelClasses(traineeModelClass);
        }
        mAdapter = new TraineeAdapter(getActivity(), DataManager.getInstance().getTraineeModelClasses());
        mRecyclerView.setAdapter(mAdapter);
    }

    private void actionBarUpdate() {
        // TODO Auto-generated method stub


        ActionBar actionBar = ((HomeActivity) getActivity()).getSupportActionBar();


        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setTitle("");
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.icon_menu);
        actionBar.setDisplayShowTitleEnabled(true);


        // TODO: 28-Jul-16 set action bar background
        Drawable actionBar_bg = getResources().getDrawable(R.drawable.header_bg);
        actionBar.setBackgroundDrawable(actionBar_bg);
        actionBar.setDisplayShowCustomEnabled(true);
        ActionBar.LayoutParams layoutParams = new ActionBar.LayoutParams(
                ActionBar.LayoutParams.MATCH_PARENT,
                ActionBar.LayoutParams.MATCH_PARENT);

        // layoutParams.rightMargin = 20;

        LayoutInflater inflator = getActivity().getLayoutInflater();
        View view = inflator.inflate(R.layout.custom_layout_actionbar, null);

        ImageView notificationBell = (ImageView) view.findViewById(R.id.notificationBell);

        view.setVisibility(View.GONE);


        actionBar.setCustomView(view, layoutParams);
    }
}
