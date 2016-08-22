package com.ymca.Fragments;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ToggleButton;

import com.ymca.Activities.HomeActivity;
import com.ymca.AppManager.DataManager;
import com.ymca.AppManager.SharedPreference;
import com.ymca.Constants.Constant;
import com.ymca.R;
import com.ymca.WebManager.JsonCaller;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Soni on 04-Aug-16.
 */
public class SettingFragment extends Fragment {

    private View view;

    SwitchCompat notifyToggle;
    LinearLayout settingButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.setting_fragment, container, false);
        actionBarUpdate();

        notifyToggle = (SwitchCompat) view.findViewById(R.id.notifyToggle);
        settingButton = (LinearLayout) view.findViewById(R.id.settingButton);

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
                        .replace(R.id.content_frame, new SettingPuchInFragment(), Constant.settingPunchFragm)
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
}
