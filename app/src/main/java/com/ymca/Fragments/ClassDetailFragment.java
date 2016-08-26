package com.ymca.Fragments;

import android.graphics.Bitmap;
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

import com.bumptech.glide.Glide;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.ymca.Activities.HomeActivity;
import com.ymca.AppManager.DataManager;
import com.ymca.R;


/**
 * Created by Soni on 28-Jul-16.
 */
public class ClassDetailFragment extends Fragment {

    private View view;
    TextView classDetailHeader,trainerName, classDetailDay, classDetailDate, classDetailWeekDays, intensityLevel, classDetailDescription, classDetailAddress;
    ImageView circleInstructorImg, classBgImg;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.class_detail_fragment, container, false);

        classBgImg = (ImageView) view.findViewById(R.id.classBgImg);
        circleInstructorImg = (ImageView) view.findViewById(R.id.circleInstructorImg);
        classDetailHeader = (TextView) view.findViewById(R.id.classDetailHeader);
        classDetailDay = (TextView) view.findViewById(R.id.classDetailDay);
        classDetailDate = (TextView) view.findViewById(R.id.classDetailDate);
        classDetailWeekDays = (TextView) view.findViewById(R.id.classDetailWeekDays);
        intensityLevel = (TextView) view.findViewById(R.id.intensityLevel);
        classDetailDescription = (TextView) view.findViewById(R.id.classDetailDescription);
        classDetailAddress = (TextView) view.findViewById(R.id.classDetailAddress);
        trainerName = (TextView) view.findViewById(R.id.trainerName);

        classDetailHeader.setText(DataManager.getInstance().getClassDetailModelClassArrayList().get(0).getClassDetailName());
        classDetailDay.setText(DataManager.getInstance().getClassDetailModelClassArrayList().get(0).getClassDetailDate());
        classDetailDate.setText(DataManager.getInstance().getClassDetailModelClassArrayList().get(0).getClassDetailDay());
        classDetailWeekDays.setText(DataManager.getInstance().getClassDetailModelClassArrayList().get(0).getClassDetailWeekDays());
        classDetailDescription.setText(DataManager.getInstance().getClassDetailModelClassArrayList().get(0).getClassDetailDescription());
        classDetailAddress.setText(DataManager.getInstance().getClassDetailModelClassArrayList().get(0).getClassDetailLocationName());
        intensityLevel.setText(DataManager.getInstance().getClassDetailModelClassArrayList().get(0).getClassDetailIntensityLevel());
        trainerName.setText(DataManager.getInstance().getClassDetailModelClassArrayList().get(0).getClassDetailInstrName());


        ImageLoader imageLoader = ImageLoader.getInstance();

        imageLoader.init(ImageLoaderConfiguration.createDefault(getActivity()));

        DisplayImageOptions options = new DisplayImageOptions.Builder().displayer(new RoundedBitmapDisplayer(1000)).cacheInMemory(true)
                .cacheOnDisc(true).resetViewBeforeLoading(true)
                .showImageForEmptyUri(R.drawable.bg_dot)
                .showImageOnFail(R.drawable.bg_dot)
                .showImageOnLoading(R.drawable.bg_dot).bitmapConfig(Bitmap.Config.RGB_565).build();

        imageLoader.displayImage(DataManager.getInstance().getClassDetailModelClassArrayList().get(0).getClassDetailInstrImg(), circleInstructorImg, options);



        Glide.with(getActivity()).load(DataManager.getInstance().getClassDetailModelClassArrayList().get(0).getClassDetailBgImg()).into(classBgImg);
        DataManager.getInstance().hideProgressMessage();


        actionBarUpdate();

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
