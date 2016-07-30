package com.example.ymca.Fragments;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.ymca.Activities.HomeActivity;
import com.example.ymca.AppManager.DataManager;
import com.example.ymca.Constants.Constant;
import com.example.ymca.R;

/**
 * Created by Soni on 28-Jul-16.
 */
public class HomeFragment extends Fragment implements View.OnClickListener {

    private View view;
    private ActionBar actionBar;
    LinearLayout scheduleLayout, myCardLayout, locationLayout, programLayout, classLayout, blogLayout;
    private boolean isCheck = false;
    private ScheduleFragment scheduleFragment = new ScheduleFragment();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.home_fragment, container, false);

        scheduleLayout = (LinearLayout) view.findViewById(R.id.scheduleLayout);
        myCardLayout = (LinearLayout) view.findViewById(R.id.myCardLayout);
        locationLayout = (LinearLayout) view.findViewById(R.id.locationLayout);
        programLayout = (LinearLayout) view.findViewById(R.id.programLayout);
        classLayout = (LinearLayout) view.findViewById(R.id.classLayout);
        blogLayout = (LinearLayout) view.findViewById(R.id.blogLayout);

        scheduleLayout.setOnClickListener(this);
        myCardLayout.setOnClickListener(this);
        locationLayout.setOnClickListener(this);
        programLayout.setOnClickListener(this);
        classLayout.setOnClickListener(this);
        blogLayout.setOnClickListener(this);
        actionBarUpdate();
        return view;
    }

    private void actionBarUpdate() {
        // TODO Auto-generated method stub


        actionBar = ((HomeActivity) getActivity()).getSupportActionBar();


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


        notificationBell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isCheck = DataManager.chkStatus();
                if (isCheck) {
                    Toast.makeText(getActivity(), "NotificationBell", Toast.LENGTH_SHORT).show();
                }

            }
        });

        actionBar.setCustomView(view, layoutParams);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.scheduleLayout:

                getActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.content_frame,scheduleFragment, Constant.scheduleFragment)
                        .addToBackStack(getFragmentManager().getClass().getName())
                        .commit();

                break;
            case R.id.myCardLayout:
                break;
            case R.id.locationLayout:
                break;
            case R.id.programLayout:
                break;
            case R.id.classLayout:
                break;
            case R.id.blogLayout:
                break;
        }
    }
}
