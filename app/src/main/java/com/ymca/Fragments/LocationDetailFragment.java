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
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.ymca.Activities.HomeActivity;
import com.ymca.Adapters.LocationDetailAdapter;
import com.ymca.AppManager.DataManager;
import com.ymca.R;


/**
 * Created by Soni on 28-Jul-16.
 */
public class LocationDetailFragment extends Fragment {

    private View view;
    TextView openCloseText,locationDetailHeader, locationDetailAddress, locationOpenTime;
    ListView listViewWeekDays;
    LocationDetailAdapter locationDetailAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.location_detail_fragment, container, false);
        actionBarUpdate();
        locationDetailHeader = (TextView) view.findViewById(R.id.locationDetailHeader);
        locationDetailAddress = (TextView) view.findViewById(R.id.locationDetailAddress);
        locationOpenTime = (TextView) view.findViewById(R.id.locationOpenTime);
        openCloseText = (TextView) view.findViewById(R.id.openCloseText);
        listViewWeekDays = (ListView) view.findViewById(R.id.listViewWeekDays);

        locationDetailAdapter = new LocationDetailAdapter(getActivity(),DataManager.getInstance().getFacilityModelClass().getFacilityWeekDays(),DataManager.getInstance().getFacilityModelClass().getFacilityOpenCloseTime());
        listViewWeekDays.setAdapter(locationDetailAdapter);

        locationDetailHeader.setText(DataManager.getInstance().getFacilityModelClass().getFacilityName());
        locationDetailAddress.setText(DataManager.getInstance().getFacilityModelClass().getFacilityAddress());
        locationOpenTime.setText(DataManager.getInstance().getFacilityModelClass().getFacilityOpenCloseTime());


        if(DataManager.getInstance().getFacilityModelClass().getFacilityOpenCloseStatus().equals("1")){
            openCloseText.setText("Open");
            openCloseText.setTextColor(getResources().getColor(R.color.colorTextGreeen));
        }else {
            openCloseText.setText("Close");
            openCloseText.setTextColor(getResources().getColor(R.color.colorTextRed));
        }


        return view;
    }

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null)
            return;

        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.UNSPECIFIED);
        int totalHeight = 0;
        View view = null;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            view = listAdapter.getView(i, view, listView);
            if (i == 0)
                view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, ViewGroup.LayoutParams.WRAP_CONTENT));

            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
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
