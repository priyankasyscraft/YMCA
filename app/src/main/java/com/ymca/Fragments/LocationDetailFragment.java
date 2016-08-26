package com.ymca.Fragments;

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
import com.ymca.AppManager.DataManager;
import com.ymca.R;


/**
 * Created by Soni on 28-Jul-16.
 */
public class LocationDetailFragment extends Fragment {

    private View view;
    TextView locationDetailHeader, locationDetailAddress, locationOpenTime, openTime1, openTime2, openTime3, openTime4, openTime5, openTime6, openTime7;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.location_detail_fragment, container, false);
        actionBarUpdate();
        locationDetailHeader = (TextView) view.findViewById(R.id.locationDetailHeader);
        locationDetailAddress = (TextView) view.findViewById(R.id.locationDetailAddress);
        locationOpenTime = (TextView) view.findViewById(R.id.locationOpenTime);
        openTime1 = (TextView) view.findViewById(R.id.openTime1);
        openTime2 = (TextView) view.findViewById(R.id.openTime2);
        openTime3 = (TextView) view.findViewById(R.id.openTime3);
        openTime4 = (TextView) view.findViewById(R.id.openTime4);
        openTime5 = (TextView) view.findViewById(R.id.openTime5);
        openTime6 = (TextView) view.findViewById(R.id.openTime6);
        openTime7 = (TextView) view.findViewById(R.id.openTime7);

        locationDetailHeader.setText(DataManager.getInstance().getLocationModelClass().getLocationName());
        locationDetailAddress.setText(DataManager.getInstance().getLocationModelClass().getLocationAddress());
        locationOpenTime.setText(DataManager.getInstance().getLocationModelClass().getLocationOpenCloseTime());
        openTime1.setText(DataManager.getInstance().getLocationModelClass().getLocationOpenCloseTime());
        openTime2.setText(DataManager.getInstance().getLocationModelClass().getLocationOpenCloseTime());
        openTime3.setText(DataManager.getInstance().getLocationModelClass().getLocationOpenCloseTime());
        openTime4.setText(DataManager.getInstance().getLocationModelClass().getLocationOpenCloseTime());
        openTime5.setText(DataManager.getInstance().getLocationModelClass().getLocationOpenCloseTime());
        openTime6.setText(DataManager.getInstance().getLocationModelClass().getLocationOpenCloseTime());
        openTime7.setText(DataManager.getInstance().getLocationModelClass().getLocationOpenCloseTime());


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
}
