package com.ymca.Fragments;

import android.content.Intent;
import android.net.Uri;
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
import com.ymca.Constants.Constant;
import com.ymca.R;


/**
 * Created by Soni on 28-Jul-16.
 */
public class EventDetailFragment extends Fragment {

    private View view;
    TextView showMapButton,eventName,eventDate,eventDescr;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.event_detail_fragment,container,false );
        actionBarUpdate();

        showMapButton = (TextView)view.findViewById(R.id.showMapButton);
        eventName = (TextView)view.findViewById(R.id.eventName);
        eventDate = (TextView)view.findViewById(R.id.eventDate);
        eventDescr = (TextView)view.findViewById(R.id.eventDescr);

        eventName.setText(DataManager.getInstance().getEventModelClass().getEventName());
        eventDate.setText(DataManager.getInstance().getEventDetailModelClassArrayList().get(0).getDateTime());
        showMapButton.setText(DataManager.getInstance().getEventDetailModelClassArrayList().get(0).getLocationAddress());
        eventDescr.setText(DataManager.getInstance().getEventDetailModelClassArrayList().get(0).getDescription());

        DataManager.getInstance().hideProgressMessage();
        showMapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: 09-Aug-16 Open Map in app
//                getActivity()
//                        .getSupportFragmentManager()
//                        .beginTransaction()
//                        .replace(R.id.content_frame,new EventMapFragment(), Constant.eventMapFragment)
//                        .addToBackStack(getActivity().getSupportFragmentManager().getClass().getName())
//                        .commit();

                // TODO: 09-Aug-16 Redirect to the Map app
                Uri gmmIntentUri = Uri.parse("geo:"+""+ DataManager.getInstance().getEventDetailModelClassArrayList().get(0).getLati()+","+DataManager.getInstance().getEventDetailModelClassArrayList().get(0).getLongi()+"?q=<"+DataManager.getInstance().getEventDetailModelClassArrayList().get(0).getLati()+">,<"+DataManager.getInstance().getEventDetailModelClassArrayList().get(0).getLongi()+">"+"Event Name");
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
            }
        });

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
