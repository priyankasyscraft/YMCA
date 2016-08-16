package com.ymca.Fragments;

import android.content.Context;
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
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ymca.Activities.HomeActivity;
import com.ymca.Adapters.InstructorDetailAdapter;
import com.ymca.AppManager.DataManager;
import com.ymca.ModelClass.ClassesModelClass;
import com.ymca.ModelClass.CustomTextModelClass;
import com.ymca.R;

import java.util.ArrayList;


/**
 * Created by Soni on 28-Jul-16.
 */
public class InstructorDetailFragment extends Fragment {

    private View view;
    LinearLayout experienceLayout,certificateLayout;
    ListView instructorList;
    private InstructorDetailAdapter instructorDetailAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.instructor_detail_fragment, container, false);
        DataManager.getInstance().setFlagInstructorList(true);
        actionBarUpdate();

        instructorList = (ListView) view.findViewById(R.id.instructorList);
        certificateLayout = (LinearLayout) view.findViewById(R.id.certificateLayout);
        experienceLayout = (LinearLayout) view.findViewById(R.id.experienceLayout);
        TextView textView = new TextView(getActivity());
        textView.setText("15 year");
        textView.setTextColor(getResources().getColor(R.color.colorPrimary));
        textView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.img_check, 0, 0, 0);
        textView.setPadding(5, 2, 0, 2);
        experienceLayout.addView(textView);
        DataManager.getInstance().clearCustomTextModelClassArrayList();
        for (int i = 0; i < 20; i++) {
            CustomTextModelClass customTextModelClass = new CustomTextModelClass();
            customTextModelClass.setTextViewString("Dummy text ");
            DataManager.getInstance().addCustomTextModelClassArrayList(customTextModelClass);
        }

         instructorDetailAdapter = new InstructorDetailAdapter(getActivity(),DataManager.getInstance().getClassesModelClassArrayList());
        instructorList.setAdapter(instructorDetailAdapter);

        setData();
        populateLinks(certificateLayout, DataManager.getInstance().getCustomTextModelClassArrayList(), "sample");


        return view;
    }

    private void setData() {
        DataManager.getInstance().clearClassesModelClassArrayList();

        for(int i = 0;i<5;i++){
            ClassesModelClass classesModelClass = new ClassesModelClass();
            classesModelClass.setClassesName("Yoga Class");
            DataManager.getInstance().addClassesModelClassArrayList(classesModelClass);
        }
        instructorDetailAdapter.setReloadData(true);
        setListViewHeightBasedOnChildren(instructorList);
    }

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null)
            return;

        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.UNSPECIFIED);
        int totalHeight = 0;
        View view = null;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            view = listAdapter.getView(i, view, listView);
            if (i == 0)
                view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, ViewGroup.LayoutParams.WRAP_CONTENT));

            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
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

            llAlso.addView(txtSample);
            txtSample.measure(0, 0);

            int widthSoFar = txtSample.getMeasuredWidth();
            for (CustomTextModelClass customTextModelClass : collection) {
                TextView txtSamItem = new TextView(getActivity(), null, android.R.attr.textColorLink);
                txtSamItem.setText(customTextModelClass.getTextViewString());
                txtSamItem.setPadding(5, 0, 0, 0);
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
