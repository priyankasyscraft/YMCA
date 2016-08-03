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
import com.ymca.Adapters.HomeClassAdapter;
import com.ymca.AppManager.DataManager;
import com.ymca.Constants.Constant;
import com.ymca.ModelClass.HomeClassesModelClass;
import com.ymca.PullListLoader.XListView;
import com.ymca.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


/**
 * Created by Soni on 28-Jul-16.
 */
public class HomeClassesFragment extends Fragment implements View.OnClickListener {

    private View view;
    ImageView prevButton,forwadButton;
    TextView dateTv,dayTv;
    Calendar c;
    SimpleDateFormat df;
    String formattedDate;
    private SimpleDateFormat outFormat,outFormat1;
    HomeClassAdapter homeClassAdapter;
    XListView homeClassListView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.home_classes_fragment,container,false );
        actionBarUpdate();
        c = Calendar.getInstance();
        df = new SimpleDateFormat("dd-MMM-yyyy");
        outFormat = new SimpleDateFormat("EEE");
        outFormat1 = new SimpleDateFormat("MMM dd");
        formattedDate = df.format(c.getTime());

        homeClassListView = (XListView) view.findViewById(R.id.homeClassListView);
        dateTv = (TextView) view.findViewById(R.id.dateTv);
        dayTv = (TextView) view.findViewById(R.id.dayTv);
        prevButton   = (ImageView)view.findViewById(R.id.prevButton);
        forwadButton = (ImageView)view.findViewById(R.id.forwadButton);
        formattedDate = df.format(c.getTime());
        dateTv.setText(outFormat1.format(new Date(formattedDate)));
        dayTv.setText(outFormat.format(new Date(formattedDate)));
        prevButton.setOnClickListener(this);
        forwadButton.setOnClickListener(this);

        homeClassAdapter = new HomeClassAdapter(getActivity(), DataManager.getInstance().getHomeClassesModelClassArrayList());
        homeClassListView.setAdapter(homeClassAdapter);
        setData();
        return view;
    }

    private void setData() {
        for(int i=0;i<20;i++){
            HomeClassesModelClass modelClass = new HomeClassesModelClass();
            modelClass.setHomeClassesName("Cricket Game Class");
            modelClass.setHomeClassesDayDate("Mon 10 Oct 2016");
            modelClass.setHomeClassesTime("10:30");
            DataManager.getInstance().addHomeClassesModelClassArrayList(modelClass);
        }
        homeClassAdapter.setReloadData(true);
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
    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.prevButton:
                c.add(Calendar.DATE, -1);
                formattedDate = df.format(c.getTime());
                dateTv.setText(outFormat1.format(new Date(formattedDate)));
                dayTv.setText(outFormat.format(new Date(formattedDate)));
                break;

            case R.id.forwadButton:
                c.add(Calendar.DATE, 1);
                formattedDate = df.format(c.getTime());
                dateTv.setText(outFormat1.format(new Date(formattedDate)));
                dayTv.setText(outFormat.format(new Date(formattedDate)));
                break;
        }
    }
}
