package com.example.ymca.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.ymca.Constants.Constant;
import com.example.ymca.R;

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

        dateTab = (LinearLayout) view.findViewById(R.id.dateTab);
        classTab = (LinearLayout) view.findViewById(R.id.classTab);
        instructorTab = (LinearLayout) view.findViewById(R.id.instructorTab);

        getChildFragmentManager()
                .beginTransaction()
                .add(R.id.content_child_frame, dateFragment, Constant.dateFragment)
                .commit();

        dateTab.setOnClickListener(this);
        classTab.setOnClickListener(this);
        instructorTab.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.dateTab:

                dateTab.setBackgroundColor(getResources().getColor(R.color.colorScheduleBottomSelect));
                classTab.setBackgroundColor(getResources().getColor(R.color.colorScheduleBottomUnSelect));
                instructorTab.setBackgroundColor(getResources().getColor(R.color.colorScheduleBottomUnSelect));

                getChildFragmentManager()
                        .beginTransaction()
                        .replace(R.id.content_child_frame, dateFragment, Constant.dateFragment)
                        .commit();

                break;
            case R.id.classTab:

                dateTab.setBackgroundColor(getResources().getColor(R.color.colorScheduleBottomUnSelect));
                classTab.setBackgroundColor(getResources().getColor(R.color.colorScheduleBottomSelect));
                instructorTab.setBackgroundColor(getResources().getColor(R.color.colorScheduleBottomUnSelect));
                getChildFragmentManager()
                        .beginTransaction()
                        .replace(R.id.content_child_frame, classFragment, Constant.instructorFragment)
                        .commit();
                break;
            case R.id.instructorTab:

                dateTab.setBackgroundColor(getResources().getColor(R.color.colorScheduleBottomUnSelect));
                classTab.setBackgroundColor(getResources().getColor(R.color.colorScheduleBottomUnSelect));
                instructorTab.setBackgroundColor(getResources().getColor(R.color.colorScheduleBottomSelect));
                getChildFragmentManager()
                        .beginTransaction()
                        .replace(R.id.content_child_frame, instructorFragment, Constant.instructorFragment)
                        .commit();
                break;
        }
    }
}
