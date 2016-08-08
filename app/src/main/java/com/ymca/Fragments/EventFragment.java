package com.ymca.Fragments;

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
import android.widget.TextView;

import com.ymca.Activities.HomeActivity;
import com.ymca.Adapters.EventAdapter;
import com.ymca.AppManager.DataManager;
import com.ymca.ModelClass.EventModelClass;
import com.ymca.PullListLoader.XListView;
import com.ymca.R;


/**
 * Created by Soni on 28-Jul-16.
 */
public class EventFragment extends Fragment {

    private View view;
    XListView eventListView;
    EventAdapter eventAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.event_fragment, container, false);
        actionBarUpdate();
        eventListView = (XListView) view.findViewById(R.id.eventListView);
        eventAdapter = new EventAdapter(getActivity(), DataManager.getInstance().getEventModelClasses());
        eventListView.setAdapter(eventAdapter);

        setData();

        return view;
    }

    private void setData() {
        for (int i = 0; i < 20; i++) {
            EventModelClass eventModelClass = new EventModelClass();
            eventModelClass.setEventName("YMCA Trivia Night");
            eventModelClass.setEventdate("01");
            eventModelClass.setEventMonth("AUG");
            DataManager.getInstance().addEventModelClasses(eventModelClass);
        }
        eventAdapter.setReloadData(true);
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
        TextView badgeCount = (TextView) view.findViewById(R.id.badgeCount);

        notificationBell.setVisibility(View.GONE);
        badgeCount.setVisibility(View.GONE);


        actionBar.setCustomView(view, layoutParams);
    }
}
