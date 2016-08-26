package com.ymca.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ymca.Adapters.DateAdapter;
import com.ymca.AppManager.DataManager;
import com.ymca.ModelClass.DateModelClass;
import com.ymca.PullListLoader.XListView;
import com.ymca.R;
import com.ymca.UserInterFace.RefreshDataListener;
import com.ymca.UserInterFace.Refreshable;
import com.ymca.WebManager.JsonCaller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;


/**
 * Created by Soni on 28-Jul-16.
 */
public class DateFragment extends Fragment implements View.OnClickListener {

    private View view;
    ImageView prevButton, forwadButton;
    TextView dateTv, dayTv;
    Calendar c;
    SimpleDateFormat df, df1;
    String formattedDate;
    private SimpleDateFormat outFormat, outFormat1;
    XListView dateScheduleListView;
    DateAdapter dateAdapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.date_fragment, container, false);
        c = Calendar.getInstance();
        df = new SimpleDateFormat("dd-MMM-yyyy");
        df1 = new SimpleDateFormat("yyyy-MM-dd");
        outFormat = new SimpleDateFormat("EEE");
        outFormat1 = new SimpleDateFormat("MMM dd");
        formattedDate = df.format(c.getTime());

        dateScheduleListView = (XListView) view.findViewById(R.id.dateScheduleListView);
        dateTv = (TextView) view.findViewById(R.id.dateTv);
        dayTv = (TextView) view.findViewById(R.id.dayTv);
        prevButton = (ImageView) view.findViewById(R.id.prevButton);
        forwadButton = (ImageView) view.findViewById(R.id.forwadButton);
        formattedDate = df.format(c.getTime());
        dateTv.setText(outFormat1.format(new Date(formattedDate)));
        dayTv.setText(outFormat.format(new Date(formattedDate)));
        prevButton.setOnClickListener(this);
        forwadButton.setOnClickListener(this);

        String date = df1.format(c.getTime());
        DataManager.getInstance().showProgressMessage(getActivity(), "Progress");
        Map<String, Object> objectMap = new LinkedHashMap<>();
        objectMap.put("type", "date");
        objectMap.put("date", date);
        JsonCaller.getInstance().getScheduleData(objectMap);
        dateAdapter = new DateAdapter(getActivity(), DataManager.getInstance().getDateModelClasses());
        dateScheduleListView.setAdapter(dateAdapter);

//        setData();

        return view;
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.prevButton:

                c.add(Calendar.DATE, -1);
                formattedDate = df.format(c.getTime());
                dateTv.setText(outFormat1.format(new Date(formattedDate)));
                dayTv.setText(outFormat.format(new Date(formattedDate)));
                DataManager.getInstance().showProgressMessage(getActivity(), "Progress");
                String date = df1.format(c.getTime());
                Map<String, Object> objectMap = new LinkedHashMap<>();
                objectMap.put("type", "date");
                objectMap.put("date", date);
                JsonCaller.getInstance().getScheduleData(objectMap);

                break;

            case R.id.forwadButton:
                c.add(Calendar.DATE, 1);
                formattedDate = df.format(c.getTime());
                dateTv.setText(outFormat1.format(new Date(formattedDate)));
                dayTv.setText(outFormat.format(new Date(formattedDate)));
                DataManager.getInstance().showProgressMessage(getActivity(), "Progress");
                String date1 = df1.format(c.getTime());
                Map<String, Object> objectMap1 = new LinkedHashMap<>();
                objectMap1.put("type", "date");
                objectMap1.put("date", date1);
                JsonCaller.getInstance().getScheduleData(objectMap1);
                break;
        }
    }


    public void onRefreshData(Refreshable refreshable, int requestCode) {
        if (requestCode == JsonCaller.REFRESH_CODE_SCHEDULE_DATA) {
            dateAdapter.setReloadData(true);
        }
    }
}
