package com.ymca.Fragments;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.ymca.Activities.HomeActivity;
import com.ymca.Adapters.PopUpLocationAdapter;
import com.ymca.Adapters.SpinnerAdapter;
import com.ymca.AppManager.DataManager;
import com.ymca.AppManager.SharedPreference;
import com.ymca.Constants.Constant;
import com.ymca.Fragments.*;
import com.ymca.Fragments.ClassFragment;
import com.ymca.ModelClass.PopUpLocationModel;
import com.ymca.R;
import com.ymca.UserInterFace.RefreshDataListener;
import com.ymca.UserInterFace.Refreshable;
import com.ymca.WebManager.JsonCaller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Soni on 28-Jul-16.
 */
public class ScheduleFragment extends Fragment implements View.OnClickListener, LocationListener,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    private View view;
    private LinearLayout dateTab, classTab, instructorTab, areasTab;
    private DateFragment dateFragment = new DateFragment();
    private InstructorFragment instructorFragment = new InstructorFragment();
    private ClassFragment classFragment = new ClassFragment();
    private AreaFragment areaFragment = new AreaFragment();
    LocationRequest mLocationRequest;
    GoogleApiClient mGoogleApiClient;
    Location mCurrentLocation;
    private String TAG = "ScheduleFragment";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        try {
            view = inflater.inflate(R.layout.schedule_fragment, container, false);
            if (DataManager.getInstance().isFlagScedule()) {
                actionBarUpdate();
            } else {
                actionBarUpdateBack();
            }
            dateTab = (LinearLayout) view.findViewById(R.id.dateTab);
            classTab = (LinearLayout) view.findViewById(R.id.classTab);
            areasTab = (LinearLayout) view.findViewById(R.id.areasTab);
            instructorTab = (LinearLayout) view.findViewById(R.id.instructorTab);

            if (DataManager.getInstance().isFlagClassList()) {
                DataManager.getInstance().setFlagClassList(false);
                dateTab.setBackgroundResource(R.drawable.schedule_tabmenu_selected);
                classTab.setBackgroundResource(R.drawable.schedule_tabmenu);
                instructorTab.setBackgroundResource(R.drawable.schedule_tabmenu_selected);
                areasTab.setBackgroundResource(R.drawable.schedule_tabmenu_selected);


                getChildFragmentManager()
                        .beginTransaction()
                        .replace(R.id.content_child_frame, classFragment, Constant.classFragment)
                        .addToBackStack(getActivity().getSupportFragmentManager().getClass().getName())
                        .commit();
            } else if (DataManager.getInstance().isFlagInstructorList()) {
                DataManager.getInstance().setFlagInstructorList(false);
                dateTab.setBackgroundResource(R.drawable.schedule_tabmenu_selected);
                classTab.setBackgroundResource(R.drawable.schedule_tabmenu_selected);
                instructorTab.setBackgroundResource(R.drawable.schedule_tabmenu);
                areasTab.setBackgroundResource(R.drawable.schedule_tabmenu_selected);


                getChildFragmentManager()
                        .beginTransaction()
                        .replace(R.id.content_child_frame, instructorFragment, Constant.instructorFragment)
                        .addToBackStack(getActivity().getSupportFragmentManager().getClass().getName())
                        .commit();
            } else {
                dateTab.setBackgroundResource(R.drawable.schedule_tabmenu);
                classTab.setBackgroundResource(R.drawable.schedule_tabmenu_selected);
                instructorTab.setBackgroundResource(R.drawable.schedule_tabmenu_selected);
                areasTab.setBackgroundResource(R.drawable.schedule_tabmenu_selected);


                getChildFragmentManager()
                        .beginTransaction()
                        .replace(R.id.content_child_frame, dateFragment, Constant.dateFragment)
                        .commit();

            }

            dateTab.setOnClickListener(this);
            classTab.setOnClickListener(this);
            areasTab.setOnClickListener(this);
            instructorTab.setOnClickListener(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return view;
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
        ImageView filterImg = (ImageView) view.findViewById(R.id.filterImg);
        filterImg.setVisibility(View.GONE);
        filterImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showAlertPopUp();
//                showPopUp();
            }
        });

        actionBar.setCustomView(view, layoutParams);
    }


    private void actionBarUpdateBack() {
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

        ImageView filterImg = (ImageView) v.findViewById(R.id.filterImg);
        filterImg.setVisibility(View.GONE);
        filterImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showAlertPopUp();
//                showPopUp();
            }
        });
        image_action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });
        actionBar.setCustomView(v, layoutParams);
    }
    public static void startInstalledAppDetailsActivity(final Activity context) {
        if (context == null) {
            return;
        }
        final Intent i = new Intent();
        i.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        i.addCategory(Intent.CATEGORY_DEFAULT);
        i.setData(Uri.parse("package:" + context.getPackageName()));
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        i.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        context.startActivity(i);
    }
    @Override
    public void onClick(View view) {
        try {
            if(SharedPreference.getSharedPrefData(getActivity(),Constant.lati)!=null) {
                String lat = SharedPreference.getSharedPrefData(getActivity(), Constant.lati);
                if(lat!=null || lat.length()!=0 || lat.equals("")) {
                    switch (view.getId()) {
                        case R.id.dateTab:

                            dateTab.setBackgroundResource(R.drawable.schedule_tabmenu);
                            classTab.setBackgroundResource(R.drawable.schedule_tabmenu_selected);
                            instructorTab.setBackgroundResource(R.drawable.schedule_tabmenu_selected);
                            areasTab.setBackgroundResource(R.drawable.schedule_tabmenu_selected);

                            getChildFragmentManager()
                                    .beginTransaction()
                                    .replace(R.id.content_child_frame, dateFragment, Constant.dateFragment)
                                    .addToBackStack(getActivity().getSupportFragmentManager().getClass().getName())
                                    .commit();

                            break;
                        case R.id.classTab:

                            dateTab.setBackgroundResource(R.drawable.schedule_tabmenu_selected);
                            classTab.setBackgroundResource(R.drawable.schedule_tabmenu);
                            instructorTab.setBackgroundResource(R.drawable.schedule_tabmenu_selected);
                            areasTab.setBackgroundResource(R.drawable.schedule_tabmenu_selected);


                            getChildFragmentManager()
                                    .beginTransaction()
                                    .replace(R.id.content_child_frame, classFragment, Constant.classFragment)
                                    .addToBackStack(getActivity().getSupportFragmentManager().getClass().getName())
                                    .commit();
                            break;
                        case R.id.instructorTab:

                            dateTab.setBackgroundResource(R.drawable.schedule_tabmenu_selected);
                            classTab.setBackgroundResource(R.drawable.schedule_tabmenu_selected);
                            instructorTab.setBackgroundResource(R.drawable.schedule_tabmenu);
                            areasTab.setBackgroundResource(R.drawable.schedule_tabmenu_selected);

    //                DataManager.getInstance().showProgressMessage(getActivity(),"Progress");
    //                Map<String,Object> objectMap2 = new LinkedHashMap<>();
    //                objectMap2.put("type","instructor");
    //                JsonCaller.getInstance().getScheduleDataInstru(objectMap2);

                            getChildFragmentManager()
                                    .beginTransaction()
                                    .replace(R.id.content_child_frame, instructorFragment, Constant.instructorFragment)
                                    .addToBackStack(getActivity().getSupportFragmentManager().getClass().getName())
                                    .commit();
                            break;
                        case R.id.areasTab:

                            dateTab.setBackgroundResource(R.drawable.schedule_tabmenu_selected);
                            classTab.setBackgroundResource(R.drawable.schedule_tabmenu_selected);
                            instructorTab.setBackgroundResource(R.drawable.schedule_tabmenu_selected);
                            areasTab.setBackgroundResource(R.drawable.schedule_tabmenu);
                            getChildFragmentManager()
                                    .beginTransaction()
                                    .replace(R.id.content_child_frame, areaFragment, Constant.areaFragment)
                                    .addToBackStack(getActivity().getSupportFragmentManager().getClass().getName())
                                    .commit();
                            break;
                    }
                }else {
                    startInstalledAppDetailsActivity(getActivity());
                }
            }else {

                startInstalledAppDetailsActivity(getActivity());
            }
        } catch (Exception e) {
            e.printStackTrace();
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

    public void showPopUp() {
        LayoutInflater layoutInflater = (LayoutInflater) getActivity()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View popupView = layoutInflater.inflate(R.layout.location_pop_up, null);
        final PopupWindow popupWindow = new PopupWindow(popupView,
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT,
                true);
        popupWindow.setTouchable(true);
        popupWindow.setFocusable(true);
        popupWindow.setFocusable(true);

        popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);

        DataManager.getInstance().clearPopUpLocationModelArrayList();
        for (int i = 0; i < 3; i++) {
            PopUpLocationModel popUpLocationModel = new PopUpLocationModel();
            popUpLocationModel.setLocationName("DownTown");
            DataManager.getInstance().addPopUpLocationModelArrayList(popUpLocationModel);
        }


        ListView popUpListView = (ListView) popupView.findViewById(R.id.popUpListView);
        PopUpLocationAdapter popUpLocationAdapter = new PopUpLocationAdapter(getActivity(), DataManager.getInstance().getPopUpLocationModelArrayList());

        popUpListView.setAdapter(popUpLocationAdapter);
    }

    public void showAlertPopUp() {

        // TODO: 22-Aug-16 set data into arraylist
        DataManager.getInstance().clearPopUpLocationModelArrayList();
        for (int i = 0; i < 3; i++) {
            PopUpLocationModel popUpLocationModel = new PopUpLocationModel();
            popUpLocationModel.setLocationName("DownTown");
            DataManager.getInstance().addPopUpLocationModelArrayList(popUpLocationModel);
        }


        ArrayList<String> stringArrayList = new ArrayList<>();
        for (int i = 0; i < DataManager.getInstance().getPopUpLocationModelArrayList().size(); i++) {
            stringArrayList.add(DataManager.getInstance().getPopUpLocationModelArrayList().get(i).getLocationName());
        }

        CharSequence[] array = stringArrayList.toArray(new String[stringArrayList.size()]);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Select Location");

        builder.setItems(array, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int position) {
                dialog.dismiss();
                Toast.makeText(getActivity(), "item position" + position, Toast.LENGTH_SHORT).show();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }


    public void onRefreshData(Refreshable refreshable, int requestCode) {
        if (requestCode == JsonCaller.REFRESH_CODE_SCHEDULE_DATA_INSTRU) {
            instructorFragment.onRefreshData(refreshable, requestCode);
        } else if (requestCode == JsonCaller.REFRESH_CODE_SCHEDULE_DATA_CLASS) {
            classFragment.onRefreshData(refreshable, requestCode);
        }else if (requestCode == JsonCaller.REFRESH_CODE_SCHEDULE_DATA_AREA) {
            areaFragment.onRefreshData(refreshable, requestCode);
        }else if (requestCode == JsonCaller.REFRESH_CODE_SCHEDULE_DATA) {
            dateFragment.onRefreshData(refreshable, requestCode);
        }else if (requestCode == JsonCaller.REFRESH_CODE_SCHEDULE_DATA_DATE_NULL) {
            dateFragment.onRefreshData(refreshable, requestCode);
        }else if (requestCode == JsonCaller.REFRESH_CODE_SCHEDULE_AREA_NULL) {
            areaFragment.onRefreshData(refreshable, requestCode);
        }else if (requestCode == JsonCaller.REFRESH_CODE_SCHEDULE_DATA_CLASS_NULL) {
            classFragment.onRefreshData(refreshable, requestCode);
        }else if (requestCode == JsonCaller.REFRESH_CODE_SCHEDULE_DATA_INSTRU_NULL) {
            instructorFragment.onRefreshData(refreshable, requestCode);
        }else if (requestCode == JsonCaller.REFRESH_CODE_INSTRUCTOR_DETAIL) {
            instructorFragment.onRefreshData(refreshable, requestCode);
        }else if (requestCode == JsonCaller.REFRESH_CODE_CLASS_DETAIL) {
            classFragment.onRefreshData(refreshable, requestCode);
        }else if (requestCode == JsonCaller.REFRESH_CODE_INSTRUCT_CLASS_DETAIL) {
            instructorFragment.onRefreshData(refreshable, requestCode);
        }else if(requestCode == JsonCaller.REFRESH_CODE_LOCATION_LIST){
            dateFragment.onRefreshData(refreshable, requestCode);
        }
    }
}
