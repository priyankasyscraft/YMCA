package com.ymca.Fragments;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.ymca.Activities.HomeActivity;
import com.ymca.Adapters.ScreenSlidePagerAdapter;
import com.ymca.AppManager.DataManager;
import com.ymca.Constants.Constant;
import com.ymca.R;
import com.ymca.ViewPager.CirclePageIndicator;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Soni on 28-Jul-16.
 */
public class HomeFragment extends Fragment implements View.OnClickListener {

    private View view;
    private ActionBar actionBar;
    LinearLayout scheduleLayout, myCardLayout, locationLayout, programLayout, classLayout, blogLayout;
    private boolean isCheck = false;
    private ScheduleFragment scheduleFragment = new ScheduleFragment();
    private HomeClassesFragment homeClassesFragment = new HomeClassesFragment();
    private LocationFragment locationFragment = new LocationFragment();
    private MyCardsFragment myCardsFragment = new MyCardsFragment();
    private NotificationFragment notificationFragment = new NotificationFragment();
    private ViewPager mPager;
    ScreenSlidePagerAdapter viewPagerAdapter;
    private ArrayList<String> arrrayListImage = new ArrayList<>();
    private CirclePageIndicator mIndicator;
    private Timer timer;
    private int count = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.home_fragment, container, false);

        mPager = (ViewPager) view.findViewById(R.id.frame);
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

        if (arrrayListImage.size() == 0) {
            for (int i = 0; i < 5; i++) {
            arrrayListImage.add("http://connectionsvolunteercenter.org/wp-content/uploads/2015/08/YMCA-Delaware-Ohio.jpg");
            arrrayListImage.add("https://doublethedonation.com/wp-content/uploads/2014/12/cincinnati-ymca.png");

            }
        }
        viewPagerAdapter = new ScreenSlidePagerAdapter(getFragmentManager(), getActivity(), arrrayListImage);
        mPager.setAdapter(viewPagerAdapter);
        mIndicator = (CirclePageIndicator) view.findViewById(R.id.sliderIndicator);
        mIndicator.setViewPager(mPager);


        if (timer == null) {
            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    DataManager.getInstance().getAppCompatActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (count <= arrrayListImage.size()) {
                                mPager.setCurrentItem(count);
                                count++;
                            } else {
                                count = 0;
                                mPager.setCurrentItem(count);
                            }
                        }
                    });
                }
            }, 500, 3000);
        }
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
//                isCheck = DataManager.chkStatus(getActivity());
//                if (isCheck) {
                    getActivity()
                            .getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.content_frame, notificationFragment, Constant.notificationFragment)
                            .commit();
//                }

            }
        });

        actionBar.setCustomView(view, layoutParams);
    }

    @Override
    public void onClick(View view) {
//        isCheck = DataManager.chkStatus(getActivity());
//        if (isCheck) {
            switch (view.getId()) {
                case R.id.scheduleLayout:
                    DataManager.getInstance().setFlagScedule(false);
                    getActivity()
                            .getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.content_frame, scheduleFragment, Constant.scheduleFragment)
                            .addToBackStack(getActivity().getSupportFragmentManager().getClass().getName())
                            .commit();

                    break;
                case R.id.myCardLayout:
                    DataManager.getInstance().setFlagCheckIn(false);
                    getActivity()
                            .getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.content_frame, myCardsFragment, Constant.myCardFragment)
                            .addToBackStack(getActivity().getSupportFragmentManager().getClass().getName())
                            .commit();
                    break;
                case R.id.locationLayout:
                    DataManager.getInstance().setFlagLocation(false);
                    getActivity()
                            .getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.content_frame, locationFragment, Constant.locationFramgnet)
                            .addToBackStack(getActivity().getSupportFragmentManager().getClass().getName())
                            .commit();
                    break;
                case R.id.programLayout:
                    DataManager.getInstance().showIFramePopUp(getActivity());
                    break;
                case R.id.classLayout:
//                    getActivity()
//                            .getSupportFragmentManager()
//                            .beginTransaction()
//                            .replace(R.id.content_frame, homeClassesFragment, Constant.homeClassFragment)
//                            .addToBackStack(getActivity().getSupportFragmentManager().getClass().getName())
//                            .commit();

                    DataManager.getInstance().showIFramePopUp(getActivity());

                    break;
                case R.id.blogLayout:
                    DataManager.getInstance().showIFramePopUp(getActivity());
                    break;
            }
        }
//    }


}
