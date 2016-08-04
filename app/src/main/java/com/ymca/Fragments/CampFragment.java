package com.ymca.Fragments;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import com.ymca.Activities.HomeActivity;
import com.ymca.Adapters.CampAdapter;
import com.ymca.AppManager.DataManager;
import com.ymca.ModelClass.CampModelClass;
import com.ymca.PullListLoader.XListView;
import com.ymca.R;


/**
 * Created by Soni on 28-Jul-16.
 */
public class CampFragment extends Fragment {

    private View view;
    XListView campList;
    CampAdapter campAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.camp_framgnet, container, false);
        actionBarUpdate();
        campList = (XListView) view.findViewById(R.id.campList);
        campAdapter = new CampAdapter(getActivity(), DataManager.getInstance().getCampModelClassArrayList());
        campList.setAdapter(campAdapter);

        setData();

        return view;
    }

    private void setData() {
        for (int i = 0; i < 20; i++) {
            CampModelClass campModelClass = new CampModelClass();
            campModelClass.setCampCounter("" + (i + 1));
            campModelClass.setCampName("SUMMER CAMP");
            campModelClass.setCampAddress("31 Street down track New York");
            DataManager.getInstance().addCampModelClassArrayList(campModelClass);
        }
        campAdapter.setReloadData(true);
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
