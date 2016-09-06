package com.ymca.Fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.ymca.Activities.HomeActivity;
import com.ymca.Adapters.NotificationAdapter;
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
 * Created by Soni on 28-Jul-16.
 */
public class NotificationFragment extends Fragment {

    private View view;
    ListView notificationList;
    ImageButton backButton;
    ImageView callButton;
    NotificationAdapter notificationAdapter;
    private Dialog popupView;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        view = inflater.inflate(R.layout.notification_fragment, container, false);

        actionBarUpdate();

        notificationList = (ListView) view.findViewById(R.id.notificationList);
        callButton = (ImageView) view.findViewById(R.id.callButton);

        notificationAdapter = new NotificationAdapter(getActivity(),DataManager.getInstance().getNotificationModelClassArrayList());
        notificationList.setAdapter(notificationAdapter);

        String deviceToken = SharedPreference.getSharedPrefData(getActivity(),Constant.deviceToken);
        DataManager.getInstance().showProgressMessage(getActivity(),"Progress");
        Map<String,Object> objectMap = new LinkedHashMap<>();
        objectMap.put("device_type","1");
        objectMap.put("device_token",deviceToken);
        JsonCaller.getInstance().getAnnouncementList(objectMap);
        callButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                DataManager.getInstance().getAppCompatActivity().runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
                        showPopUp(getActivity());
//                    }
//                });

            }
        });

        return view;
    }


    private void actionBarUpdate() {
        // TODO Auto-generated method stub


        ActionBar actionBar = ((HomeActivity) getActivity()).getSupportActionBar();


//        actionBar = ((HomeActivity) getActivity()).getSupportActionBar();
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

    public void showPopUp(Context context) {

        final PopupWindow popupWindow;
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        final View popupView = layoutInflater.inflate(R.layout.dialoh_layout, null);
          popupWindow = new PopupWindow(popupView,
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT,
                false);
        popupWindow.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.caldroid_transparent)));
        popupWindow.setTouchable(true);
        popupWindow.setFocusable(true);

        popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);

//         popupView = new Dialog(context);
//
//        // Include dialog.xml file
//        popupView.setContentView(R.layout.dialoh_layout);
//        popupView.show();
        LinearLayout callNumer1, callNumer2;
        final TextView nember1, nember2;
//
        callNumer1 = (LinearLayout) popupView.findViewById(R.id.callNumer1);
        callNumer2 = (LinearLayout) popupView.findViewById(R.id.callNumer2);
        nember1 = (TextView) popupView.findViewById(R.id.nember1);
        nember2 = (TextView) popupView.findViewById(R.id.nember2);
        callNumer1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" + nember1.getText().toString()));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
        callNumer2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" + nember2.getText().toString()));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

    }

    public void onRefreshData(Refreshable refreshable, int requestCode) {
        if(requestCode == JsonCaller.REFRESH_CODE_NOTIFY_LIST){
            notificationAdapter = new NotificationAdapter(getActivity(),DataManager.getInstance().getNotificationModelClassArrayList());
            notificationList.setAdapter(notificationAdapter);
            String deviceToken = SharedPreference.getSharedPrefData(getActivity(),Constant.deviceToken);
            Map<String,Object> objectMap = new LinkedHashMap<>();
            objectMap.put("device_type","1");
            objectMap.put("device_token",deviceToken);
            JsonCaller.getInstance().getAnnouncementReset(objectMap);
        }
    }
}
