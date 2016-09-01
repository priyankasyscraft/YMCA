package com.ymca.Fragments;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.ToggleButton;

import com.ymca.Activities.HomeActivity;
import com.ymca.Adapters.SpinnerAdapter;
import com.ymca.AppManager.DataManager;
import com.ymca.AppManager.SharedPreference;
import com.ymca.Constants.Constant;
import com.ymca.R;
import com.ymca.UserInterFace.RefreshDataListener;
import com.ymca.UserInterFace.Refreshable;
import com.ymca.WebManager.JsonCaller;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Soni on 04-Aug-16.
 */
public class SettingFragment extends Fragment {

    private View view;

    SwitchCompat notifyToggle;
    LinearLayout settingButton,reportProblem;
    Spinner spinnerLocation;
    SpinnerAdapter spinnerAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.setting_fragment, container, false);
        actionBarUpdate();

        notifyToggle = (SwitchCompat) view.findViewById(R.id.notifyToggle);
        settingButton = (LinearLayout) view.findViewById(R.id.settingButton);
        reportProblem = (LinearLayout) view.findViewById(R.id.reportProblem);
        spinnerLocation = (Spinner) view.findViewById(R.id.spinnerLocation);

        spinnerAdapter = new SpinnerAdapter(getActivity(), android.R.layout.test_list_item, DataManager.getInstance().getLocationModelClasses());
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerLocation.setAdapter(spinnerAdapter);


        DataManager.getInstance().setFlagSettingLocation(true);
        DataManager.getInstance().showProgressMessage(getActivity(), "Progress");
        Map<String, Object> objectMap = new LinkedHashMap<>();
        JsonCaller.getInstance().getLocationList(objectMap);


        spinnerLocation.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                spinnerLocation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                        SharedPreference.setDataInSharedPreference(getActivity(), Constant.defaultLocation, "" + position);
                        SharedPreference.setDataInSharedPreference(getActivity(), Constant.defaultLocationId, DataManager.getInstance().getLocationModelClasses().get(position).getLocationId());
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
                return false;
            }
        });




        String notiStatus = SharedPreference.getSharedPrefData(getActivity(), Constant.notificationStatus);

        if (notiStatus != null) {
            if (notiStatus.equals("1")) {
                notifyToggle.setChecked(true);
            } else {
                notifyToggle.setChecked(false);
            }
        }


        notifyToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton arg0, boolean isChecked) {
                String status;
                if (isChecked) {
                    status = "1";
                    SharedPreference.setDataInSharedPreference(getActivity(), Constant.notificationStatus, "1");
                } else {
                    SharedPreference.setDataInSharedPreference(getActivity(), Constant.notificationStatus, "2");
                    status = "2";
                }
                boolean isNetwrkCheck = DataManager.chkStatus(getActivity());

                if (isNetwrkCheck) {
                    DataManager.getInstance().showProgressMessage(getActivity(), "Progress");
                    String deviceToken = SharedPreference.getSharedPrefData(DataManager.getInstance().getAppCompatActivity(), Constant.deviceToken);

                    Map<String, Object> objectMap = new LinkedHashMap<String, Object>();

                    objectMap.put("device_token", deviceToken);
                    objectMap.put("device_type","1");
                    objectMap.put("notification_status", status);
                    JsonCaller.getInstance().getSettingNotification(objectMap);
                }
            }
        });

        settingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.content_frame, new WebViewFragment(), Constant.webViewFragment)
                        .addToBackStack(getActivity().getSupportFragmentManager().getClass().getName())
                        .commit();
            }
        });

        reportProblem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.content_frame, new WebViewFragment(), Constant.webViewFragment)
                        .addToBackStack(getActivity().getSupportFragmentManager().getClass().getName())
                        .commit();
            }
        });


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

        view.setVisibility(View.GONE);


        actionBar.setCustomView(view, layoutParams);
    }


    public void onRefreshData(Refreshable refreshable, int requestCode) {
        if (requestCode == JsonCaller.REFRESH_CODE_LOCATION_LIST) {
            spinnerAdapter.notifyDataSetChanged();

            if(SharedPreference.getSharedPrefData(getActivity(), Constant.defaultLocation)!=null) {
                int position = Integer.parseInt(SharedPreference.getSharedPrefData(getActivity(), Constant.defaultLocation));
                Log.e("onRefreshData: ", "" + position);
                spinnerLocation.setSelection(position);
            }
            DataManager.getInstance().hideProgressMessage();
            DataManager.getInstance().setFlagSettingLocation(false);
            DataManager.getInstance().hideProgressMessage();
        }
    }
}
