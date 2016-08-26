package com.ymca.Fragments;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.ymca.Activities.HomeActivity;
import com.ymca.AppManager.DataManager;
import com.ymca.ModelClass.CustomTextModelClass;
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
public class TrainerDetailFragment extends Fragment {

    private View view;
    LinearLayout experienceLayout, certificateLayout,funFactLayout;
    private TextView instructorName;
    ImageView circleInstructorImg;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.trainer_detail_fragment, container, false);

        actionBarUpdate();

        instructorName = (TextView) view.findViewById(R.id.instructorName);
        certificateLayout = (LinearLayout) view.findViewById(R.id.certificateLayout);
        experienceLayout = (LinearLayout) view.findViewById(R.id.experienceLayout);
        funFactLayout = (LinearLayout) view.findViewById(R.id.funFactLayout);
        circleInstructorImg = (ImageView) view.findViewById(R.id.circleInstructorImg);

        TextView textView = new TextView(getActivity());
        textView.setText(DataManager.getInstance().getTrainerDetailModelClassArrayList().get(0).getTrainerExperience());
        textView.setTextColor(Color.BLACK);
        textView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.img_check, 0, 0, 0);
        textView.setPadding(5, 2, 0, 2);
        experienceLayout.addView(textView);

        TextView textView1 = new TextView(getActivity());
        textView1.setText(DataManager.getInstance().getTrainerDetailModelClassArrayList().get(0).getTrainerFunFact());
        textView1.setTextColor(Color.BLACK);
        textView1.setCompoundDrawablesWithIntrinsicBounds(R.drawable.img_check, 0, 0, 0);
        textView1.setPadding(5, 2, 0, 2);
        funFactLayout.addView(textView1);

        instructorName.setText(DataManager.getInstance().getTrainerDetailModelClassArrayList().get(0).getTrainerName());

        ImageLoader imageLoader = ImageLoader.getInstance();

        imageLoader.init(ImageLoaderConfiguration.createDefault(getActivity()));
        DisplayImageOptions options = new DisplayImageOptions.Builder().displayer(new RoundedBitmapDisplayer(1000)).cacheInMemory(true)
                .cacheOnDisc(true).resetViewBeforeLoading(true)
                .showImageForEmptyUri(R.mipmap.user_default)
                .showImageOnFail(R.mipmap.user_default)
                .showImageOnLoading(R.mipmap.user_default).bitmapConfig(Bitmap.Config.RGB_565).build();

        imageLoader.displayImage(DataManager.getInstance().getTrainerDetailModelClassArrayList().get(0).getTrainerImg(),circleInstructorImg,options);

        DataManager.getInstance().hideProgressMessage();

        DataManager.getInstance().clearCustomTextModelClassArrayList();
        for (int i = 0; i < 10; i++) {
            CustomTextModelClass customTextModelClass = new CustomTextModelClass();
            customTextModelClass.setTextViewString("Dummy");
            DataManager.getInstance().addCustomTextModelClassArrayList(customTextModelClass);
        }

        populateLinks(certificateLayout, DataManager.getInstance().getTrainerDetailModelClassArrayList().get(0).getCertificateList(), "sample");
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


    private void populateLinks(LinearLayout ll, ArrayList<CustomTextModelClass> collection, String header) {

        Display display = getActivity().getWindowManager().getDefaultDisplay();
        int maxWidth = display.getWidth() - 10;

        if (collection.size() > 0) {
            LinearLayout llAlso = new LinearLayout(getActivity());
            llAlso.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
            llAlso.setOrientation(LinearLayout.HORIZONTAL);

            TextView txtSample = new TextView(getActivity());
            txtSample.setText(header);
            txtSample.setVisibility(View.GONE);

            llAlso.addView(txtSample);
            txtSample.measure(0, 0);

            int widthSoFar = txtSample.getMeasuredWidth();
            for (CustomTextModelClass customTextModelClass : collection) {
                TextView txtSamItem = new TextView(getActivity(), null, android.R.attr.textColorLink);
                txtSamItem.setText(customTextModelClass.getTextViewString());
                txtSamItem.setPadding(10, 0, 0, 0);
                txtSamItem.setTag(customTextModelClass);
                txtSamItem.setCompoundDrawablesWithIntrinsicBounds(R.drawable.img_check, 0, 0, 0);
                txtSamItem.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // TODO: 12-Aug-16 textview click listener
//                        TextView self = (TextView) v;
//                        Sample ds = (Sample) self.getTag();
//
//                        Intent myIntent = new Intent();
//                        myIntent.putExtra("link_info", ds.Sample);
//                        setResult("link_clicked", myIntent);
//                        finish();
                    }
                });

                txtSamItem.measure(0, 0);
                widthSoFar += txtSamItem.getMeasuredWidth();

                if (widthSoFar >= maxWidth) {
                    ll.addView(llAlso);

                    llAlso = new LinearLayout(getActivity());
                    llAlso.setLayoutParams(new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.FILL_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT));
                    llAlso.setOrientation(LinearLayout.HORIZONTAL);

                    llAlso.addView(txtSamItem);
                    widthSoFar = txtSamItem.getMeasuredWidth();
                } else {
                    llAlso.addView(txtSamItem);
                }
            }

            ll.addView(llAlso);
        }
    }



}
