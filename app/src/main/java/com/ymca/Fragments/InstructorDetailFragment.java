package com.ymca.Fragments;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.ymca.Activities.HomeActivity;
import com.ymca.Adapters.InstructorDetailAdapter;
import com.ymca.AppManager.DataManager;
import com.ymca.Constants.Constant;
import com.ymca.ModelClass.ClassesModelClass;
import com.ymca.ModelClass.CustomTextModelClass;
import com.ymca.R;
import com.ymca.UserInterFace.Refreshable;
import com.ymca.WebManager.JsonCaller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;


/**
 * Created by Soni on 28-Jul-16.
 */
public class InstructorDetailFragment extends Fragment {

    private View view;
    LinearLayout experienceLayout, certificateLayout;
    ListView instructorList;
    ImageView circleInstructorImg;
    TextView instructorName;
    private InstructorDetailAdapter instructorDetailAdapter;
    private TextView textView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.instructor_detail_fragment, container, false);
        DataManager.getInstance().setFlagInstructorList(true);
        actionBarUpdate();

        instructorName = (TextView) view.findViewById(R.id.instructorName);
        circleInstructorImg = (ImageView) view.findViewById(R.id.circleInstructorImg);
        instructorList = (ListView) view.findViewById(R.id.instructorList);
        certificateLayout = (LinearLayout) view.findViewById(R.id.certificateLayout);
        experienceLayout = (LinearLayout) view.findViewById(R.id.experienceLayout);
        textView = new TextView(getActivity());
        DataManager.getInstance().clearCustomTextModelClassArrayList();


        instructorDetailAdapter = new InstructorDetailAdapter(getActivity(), DataManager.getInstance().getInstructorDetailModelArrayList());
        instructorList.setAdapter(instructorDetailAdapter);

        instructorList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                DataManager.getInstance().showProgressMessage(DataManager.getInstance().getAppCompatActivity(), "Progress");

                Map<String, Object> objectMap = new LinkedHashMap<String, Object>();
                objectMap.put("class_id", DataManager.getInstance().getInstructorDetailModelArrayList().get(0).getModelClasses().get(i).getClassesId());

                JsonCaller.getInstance().getClassDetail(objectMap);
            }
        });
        setListViewHeightBasedOnChildren(instructorList);
        textView.setText(DataManager.getInstance().getInstructorDetailModelArrayList().get(0).getInstructorInfo());
        textView.setTextColor(getResources().getColor(R.color.colorPrimary));
        textView.setPadding(5, 2, 0, 2);
        experienceLayout.addView(textView);


        instructorName.setText(DataManager.getInstance().getInstructorDetailModelArrayList().get(0).getInstructorName());
        ImageLoader imageLoader = ImageLoader.getInstance();

        imageLoader.init(ImageLoaderConfiguration.createDefault(getActivity()));

       DisplayImageOptions options = new DisplayImageOptions.Builder().displayer(new RoundedBitmapDisplayer(1000)).cacheInMemory(true)
                .cacheOnDisc(true).resetViewBeforeLoading(true)
                .showImageForEmptyUri(R.mipmap.user_default)
                .showImageOnFail(R.mipmap.user_default)
                .showImageOnLoading(R.mipmap.user_default).bitmapConfig(Bitmap.Config.RGB_565).build();

        imageLoader.displayImage(DataManager.getInstance().getInstructorDetailModelArrayList().get(0).getInstructorImgUrl(),circleInstructorImg,options);
//        Glide.with(getActivity()).load(DataManager.getInstance().getInstructorDetailModelArrayList().get(0).getInstructorImgUrl()).into(circleInstructorImg);
        instructorDetailAdapter.setReloadData(true);
        DataManager.getInstance().hideProgressMessage();


        return view;
    }

    private void setData() {
        DataManager.getInstance().clearClassesModelClassArrayList();

        for (int i = 0; i < 5; i++) {
            ClassesModelClass classesModelClass = new ClassesModelClass();
            classesModelClass.setClassesName("Yoga Class");
            DataManager.getInstance().addClassesModelClassArrayList(classesModelClass);
        }
        instructorDetailAdapter.setReloadData(true);

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


        actionBar.setHomeButtonEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
//        actionBar.setTitle("");
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
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




    public void onRefreshData(Refreshable refreshable, int requestCode) {
        if (requestCode == JsonCaller.REFRESH_CODE_INSTRUCT_CLASS_DETAIL) {
            getActivity()
                    .getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.content_frame,new ClassDetailFragment(), Constant.classDetailFragment)
                    .addToBackStack(getActivity().getSupportFragmentManager().getClass().getName())
                    .commit();
        }
    }
}
