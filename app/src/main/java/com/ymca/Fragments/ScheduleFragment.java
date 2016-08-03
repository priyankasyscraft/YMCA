package com.ymca.Fragments;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ymca.Activities.HomeActivity;
import com.ymca.AppManager.DataManager;
import com.ymca.Constants.Constant;
import com.ymca.Fragments.*;
import com.ymca.Fragments.ClassFragment;
import com.ymca.R;

/**
 * Created by Soni on 28-Jul-16.
 */
public class ScheduleFragment extends Fragment implements View.OnClickListener {

    private View view;
    LinearLayout dateTab, classTab, instructorTab;

    private DateFragment dateFragment = new DateFragment();
    private InstructorFragment instructorFragment = new InstructorFragment();
    private ClassFragment classFragment = new ClassFragment();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.schedule_fragment, container, false);
        actionBarUpdate();
        dateTab = (LinearLayout) view.findViewById(R.id.dateTab);
        classTab = (LinearLayout) view.findViewById(R.id.classTab);
        instructorTab = (LinearLayout) view.findViewById(R.id.instructorTab);

        if (DataManager.getInstance().isFlagClassList()) {
            DataManager.getInstance().setFlagClassList(false);
            dateTab.setBackgroundResource(R.drawable.schedule_tabmenu_selected);
            classTab.setBackgroundResource(R.drawable.schedule_tabmenu);
            instructorTab.setBackgroundResource(R.drawable.schedule_tabmenu_selected);
            getChildFragmentManager()
                    .beginTransaction()
                    .replace(R.id.content_child_frame, classFragment, Constant.classFragment)
                    .commit();
        } else if (DataManager.getInstance().isFlagInstructorList()) {
            DataManager.getInstance().setFlagInstructorList(false);
            dateTab.setBackgroundResource(R.drawable.schedule_tabmenu_selected);
            classTab.setBackgroundResource(R.drawable.schedule_tabmenu_selected);
            instructorTab.setBackgroundResource(R.drawable.schedule_tabmenu);
            getChildFragmentManager()
                    .beginTransaction()
                    .replace(R.id.content_child_frame, instructorFragment, Constant.instructorFragment)
                    .commit();
        } else {
            dateTab.setBackgroundResource(R.drawable.schedule_tabmenu);
            classTab.setBackgroundResource(R.drawable.schedule_tabmenu_selected);
            instructorTab.setBackgroundResource(R.drawable.schedule_tabmenu_selected);
            getChildFragmentManager()
                    .beginTransaction()
                    .replace(R.id.content_child_frame, dateFragment, Constant.dateFragment)
                    .commit();

        }

        dateTab.setOnClickListener(this);
        classTab.setOnClickListener(this);
        instructorTab.setOnClickListener(this);
        return view;
    }

    private void actionBarUpdate() {
        // TODO Auto-generated method stub


        ActionBar actionBar = ((HomeActivity) getActivity()).getSupportActionBar();


        actionBar = ((HomeActivity) getActivity()).getSupportActionBar();
        actionBar.setHomeButtonEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
//        actionBar.setTitle("");
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setDisplayShowHomeEnabled(false);
//        actionBar.setHomeAsUpIndicator(R.drawable.menu_icon);
        actionBar.setDisplayShowTitleEnabled(false);

//        Drawable actionBar_bg = getResources().getDrawable(
//                R.drawable.tool_bar_bg);
//        actionBar.setBackgroundDrawable(actionBar_bg);

//        actionBar.setDisplayShowCustomEnabled(true);
        ActionBar.LayoutParams layoutParams = new ActionBar.LayoutParams(
                ActionBar.LayoutParams.MATCH_PARENT,
                ActionBar.LayoutParams.MATCH_PARENT);
        layoutParams.gravity = Gravity.START;
        layoutParams.leftMargin = -50;

        LayoutInflater inflator = getActivity().getLayoutInflater();
        View v = inflator.inflate(R.layout.custom_layout_back_button, null);
        ImageView image_action = (ImageView) v.findViewById(R.id.custom_img_action_profile);
//        TextView logoText = (TextView) v.findViewById(R.id.logoText);
//        logoText.setVisibility(View.VISIBLE);
//        logoText.setText(getResources().getString(R.string.schedule));
        image_action.setImageResource(R.drawable.bt_back_white);
        image_action.setVisibility(View.VISIBLE);
        image_action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });
        actionBar.setCustomView(v, layoutParams);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.dateTab:

                dateTab.setBackgroundResource(R.drawable.schedule_tabmenu);
                classTab.setBackgroundResource(R.drawable.schedule_tabmenu_selected);
                instructorTab.setBackgroundResource(R.drawable.schedule_tabmenu_selected);

                getChildFragmentManager()
                        .beginTransaction()
                        .replace(R.id.content_child_frame, dateFragment, Constant.dateFragment)
                        .commit();

                break;
            case R.id.classTab:

                dateTab.setBackgroundResource(R.drawable.schedule_tabmenu_selected);
                classTab.setBackgroundResource(R.drawable.schedule_tabmenu);
                instructorTab.setBackgroundResource(R.drawable.schedule_tabmenu_selected);
                getChildFragmentManager()
                        .beginTransaction()
                        .replace(R.id.content_child_frame, classFragment, Constant.classFragment)
                        .commit();
                break;
            case R.id.instructorTab:

                dateTab.setBackgroundResource(R.drawable.schedule_tabmenu_selected);
                classTab.setBackgroundResource(R.drawable.schedule_tabmenu_selected);
                instructorTab.setBackgroundResource(R.drawable.schedule_tabmenu);
                getChildFragmentManager()
                        .beginTransaction()
                        .replace(R.id.content_child_frame, instructorFragment, Constant.instructorFragment)
                        .commit();
                break;
        }
    }
}
