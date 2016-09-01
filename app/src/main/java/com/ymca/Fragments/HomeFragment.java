package com.ymca.Fragments;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.ymca.Activities.HomeActivity;
import com.ymca.Adapters.ScreenSlidePagerAdapter;
import com.ymca.AppManager.DataManager;
import com.ymca.AppManager.SharedPreference;
import com.ymca.Constants.Constant;
import com.ymca.R;
import com.ymca.UserInterFace.RefreshDataListener;
import com.ymca.UserInterFace.Refreshable;
import com.ymca.ViewPager.CirclePageIndicator;
import com.ymca.WebManager.JsonCaller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Soni on 28-Jul-16.
 */
public class HomeFragment extends Fragment implements View.OnClickListener, LocationListener,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

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
    TextView badgeCount;
    LocationRequest mLocationRequest;
    GoogleApiClient mGoogleApiClient;
    Location mCurrentLocation;
    private String TAG = "Home Activity";

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


        viewPagerAdapter = new ScreenSlidePagerAdapter(getFragmentManager(), getActivity(), DataManager.getInstance().getSliderModelClasses());
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
                            if (count <= DataManager.getInstance().getSliderModelClasses().size()) {
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
        badgeCount = (TextView) view.findViewById(R.id.badgeCount);

        String deviceToken = SharedPreference.getSharedPrefData(getActivity(), Constant.deviceToken);
        Map<String, Object> objectMap = new LinkedHashMap<>();
        objectMap.put("device_type", "1");
        objectMap.put("device_token", deviceToken);
        JsonCaller.getInstance().getBadgeCount(objectMap);
        notificationBell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                isCheck = DataManager.chkStatus(getActivity());
//                if (isCheck) {
                DataManager.getInstance().setFlagNotification(false);
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
        isCheck = DataManager.chkStatus(getActivity());
        if (isCheck) {
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
//                    DataManager.getInstance().showProgressMessage(getActivity(), "Progress");
//                    String deviceToken = SharedPreference.getSharedPrefData(getActivity(), Constant.deviceToken);
//                    Map<String, Object> params = new LinkedHashMap<>();
//                    params.put("device_type","1");
//                    params.put("device_token", deviceToken);
//                    JsonCaller.getInstance().getAllCard(params);
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
//                    DataManager.getInstance().showIFramePopUp(getActivity());
                    DataManager.getInstance().setFlagWebView(false);
                    getActivity()
                            .getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.content_frame, new WebViewFragment(), Constant.webViewFragment)
                            .addToBackStack(getActivity().getSupportFragmentManager().getClass().getName())
                            .commit();
                    break;
                case R.id.classLayout:
//                    getActivity()
//                            .getSupportFragmentManager()
//                            .beginTransaction()
//                            .replace(R.id.content_frame, homeClassesFragment, Constant.homeClassFragment)
//                            .addToBackStack(getActivity().getSupportFragmentManager().getClass().getName())
//                            .commit();
                    DataManager.getInstance().setFlagWebView(false);
                    getActivity()
                            .getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.content_frame, new WebViewFragment(), Constant.webViewFragment)
                            .addToBackStack(getActivity().getSupportFragmentManager().getClass().getName())
                            .commit();
//                    DataManager.getInstance().showIFramePopUp(getActivity());

                    break;
                case R.id.blogLayout:
                    DataManager.getInstance().setFlagWebView(false);
                    getActivity()
                            .getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.content_frame, new WebViewFragment(), Constant.webViewFragment)
                            .addToBackStack(getActivity().getSupportFragmentManager().getClass().getName())
                            .commit();
//                    DataManager.getInstance().showIFramePopUp(getActivity());
                    break;
            }
        }
    }


    public void onRefreshData(Refreshable refreshable, int requestCode) {
        if (requestCode == JsonCaller.REFRESH_CODE_ALL_CARDS) {
            if (DataManager.getInstance().isFlagCardShowBack()) {
                DataManager.getInstance().hideProgressMessage();
                getActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.content_frame, myCardsFragment, Constant.myCardFragment)
                        .commit();

            } else {
//                getActivity()
//                        .getSupportFragmentManager()
//                        .beginTransaction()
//                        .replace(R.id.content_frame, myCardsFragment, Constant.myCardFragment)
//                        .addToBackStack(getActivity().getSupportFragmentManager().getClass().getName())
//                        .commit();
                myCardsFragment.onRefreshData(refreshable, requestCode);
            }

        } else if (requestCode == JsonCaller.REFRESH_CODE_ADD_CARD_NULL) {
            getActivity()
                    .getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.content_frame, myCardsFragment, Constant.myCardFragment)
                    .addToBackStack(getActivity().getSupportFragmentManager().getClass().getName())
                    .commit();
        } else if (requestCode == JsonCaller.REFRESH_CODE_DELETE_CARDS) {
            myCardsFragment.onRefreshData(refreshable, requestCode);
        } else if (requestCode == JsonCaller.REFRESH_CODE_SCHEDULE_DATA_INSTRU) {
            scheduleFragment.onRefreshData(refreshable, requestCode);
        } else if (requestCode == JsonCaller.REFRESH_CODE_SCHEDULE_DATA_CLASS) {
            scheduleFragment.onRefreshData(refreshable, requestCode);
        } else if (requestCode == JsonCaller.REFRESH_CODE_SCHEDULE_DATA_AREA) {
            scheduleFragment.onRefreshData(refreshable, requestCode);
        } else if (requestCode == JsonCaller.REFRESH_CODE_SCHEDULE_DATA) {
            scheduleFragment.onRefreshData(refreshable, requestCode);
        } else if (requestCode == JsonCaller.REFRESH_CODE_SCHEDULE_DATA_DATE_NULL) {
            scheduleFragment.onRefreshData(refreshable, requestCode);
        } else if (requestCode == JsonCaller.REFRESH_CODE_INSTRUCTOR_DETAIL) {
            scheduleFragment.onRefreshData(refreshable, requestCode);
        } else if (requestCode == JsonCaller.REFRESH_CODE_CLASS_DETAIL) {
            scheduleFragment.onRefreshData(refreshable, requestCode);
        } else if (requestCode == JsonCaller.REFRESH_CODE_LOCATION_LIST) {
            scheduleFragment.onRefreshData(refreshable, requestCode);
        } else if (requestCode == JsonCaller.REFRESH_CODE_FACILITY_LIST) {
            locationFragment.onRefreshData(refreshable, requestCode);
        } else if (requestCode == JsonCaller.REFRESH_CODE_INSTRUCT_CLASS_DETAIL) {
            scheduleFragment.onRefreshData(refreshable, requestCode);
        } else if (requestCode == JsonCaller.REFRESH_CODE_NOTIFY_LIST) {
            notificationFragment.onRefreshData(refreshable, requestCode);
        } else if (requestCode == JsonCaller.REFRESH_CODE_BADGE_COUNT) {
            if (DataManager.getInstance().getBadgeCount().equals("0")) {
                badgeCount.setVisibility(View.GONE);
            } else {
                badgeCount.setVisibility(View.VISIBLE);
                badgeCount.setText(DataManager.getInstance().getBadgeCount());
            }
        }
    }

    @Override
    public void onConnected(Bundle bundle) {
        startLocationUpdates();
    }

    protected void startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        PendingResult<Status> pendingResult = LocationServices.FusedLocationApi.requestLocationUpdates(
                mGoogleApiClient, mLocationRequest, this);
        Log.e(TAG, "Location update started ..............: ");
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.e(TAG, "Connection failed: " + connectionResult.toString());
    }


    @Override
    public void onLocationChanged(Location location) {
        Log.e(TAG, "Firing onLocationChanged..............................................");
        mCurrentLocation = location;
        String lat = "" + mCurrentLocation.getLatitude();
        String lon = "" + mCurrentLocation.getLongitude();
        Log.e(TAG, "Firing Lat/Longs........" + lat + "........" + lon);
        SharedPreference.setDataInSharedPreference(getActivity(), Constant.lati, lat);
        SharedPreference.setDataInSharedPreference(getActivity(), Constant.longi, lon);
    }
//    }


}
