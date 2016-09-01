package com.ymca.Fragments;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ymca.Adapters.DateAdapter;
import com.ymca.Adapters.SpinnerAdapter;
import com.ymca.AppManager.DataManager;
import com.ymca.AppManager.SharedPreference;
import com.ymca.Constants.Constant;
import com.ymca.ModelClass.DateModelClass;
import com.ymca.PullListLoader.XListView;
import com.ymca.R;
import com.ymca.UserInterFace.RefreshDataListener;
import com.ymca.UserInterFace.Refreshable;
import com.ymca.WebManager.JsonCaller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;


/**
 * Created by Soni on 28-Jul-16.
 */
public class DateFragment extends Fragment implements View.OnClickListener , XListView.IXListViewListener{

    private View view;
    ImageView prevButton, forwadButton;
    TextView dateTv, dayTv, dateText;
    Calendar c;
    SimpleDateFormat df, df1;
    String formattedDate;
    private SimpleDateFormat newOutFormat, outFormat, outFormat1;
    XListView dateScheduleListView;
    DateAdapter dateAdapter;
    EditText searchEditText;
    ArrayList<DateModelClass> dateModelArrayList = new ArrayList<>();
    ArrayList<DateModelClass> dateModelClassArrayList = new ArrayList<>();
    Button buttonClick;
    Spinner spinnerLocation;
    SpinnerAdapter spinnerAdapter;
    TextView searchButton;
    int offsetValue = 0;
    String searchKey = "";
    private String locationid;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.date_fragment, container, false);



        c = Calendar.getInstance();
        df = new SimpleDateFormat("dd-MMM-yyyy");
        df1 = new SimpleDateFormat("yyyy-MM-dd");
        outFormat = new SimpleDateFormat("EEE");
        outFormat1 = new SimpleDateFormat("MMM dd");
        newOutFormat = new SimpleDateFormat("EEE, MMM dd,yyyy");
        formattedDate = df.format(c.getTime());

        buttonClick = (Button) view.findViewById(R.id.buttonClick);

        buttonClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        spinnerLocation = (Spinner) view.findViewById(R.id.spinnerLocation);
        dateScheduleListView = (XListView) view.findViewById(R.id.dateScheduleListView);
        searchEditText = (EditText) view.findViewById(R.id.searchEditText);
        searchEditText.setText("");
        searchButton = (TextView) view.findViewById(R.id.searchButton);
        dateText = (TextView) view.findViewById(R.id.dateText);
        dateTv = (TextView) view.findViewById(R.id.dateTv);
        dayTv = (TextView) view.findViewById(R.id.dayTv);
        prevButton = (ImageView) view.findViewById(R.id.prevButton);
        forwadButton = (ImageView) view.findViewById(R.id.forwadButton);
        formattedDate = df.format(c.getTime());
        dateTv.setText(outFormat1.format(new Date(formattedDate)));
        dayTv.setText(outFormat.format(new Date(formattedDate)));
        dateText.setText(newOutFormat.format(new Date(formattedDate)));
        prevButton.setOnClickListener(this);
        forwadButton.setOnClickListener(this);
        searchKey = searchEditText.getText().toString().trim();
        spinnerAdapter = new SpinnerAdapter(getActivity(),android.R.layout.test_list_item,DataManager.getInstance().getLocationModelClasses());
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerLocation.setAdapter(spinnerAdapter);


        final String date = df1.format(c.getTime());
        DataManager.getInstance().showProgressMessage(getActivity(), "Progress");
        if(SharedPreference.getSharedPrefData(getActivity(),Constant.defaultLocationId)!=null) {
             locationid = SharedPreference.getSharedPrefData(getActivity(), Constant.defaultLocationId);
        }else {
            locationid = "1";
        }
        Map<String, Object> objectMap = new LinkedHashMap<>();
        objectMap.put("type", "date");
        objectMap.put("date", date);
        objectMap.put("location_id",locationid) ;
        objectMap.put("skiprecords","0");
        objectMap.put("search_keyword",searchKey);
        offsetValue = 0;
        JsonCaller.getInstance().getScheduleData(objectMap);


        dateAdapter = new DateAdapter(getActivity(), DataManager.getInstance().getDateModelClasses());
        dateScheduleListView.setAdapter(dateAdapter);
        dateScheduleListView.setPullLoadEnable(true);
        dateScheduleListView.setXListViewListener(this);
        spinnerLocation.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                spinnerLocation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                        searchEditText.setText("");
                        dateScheduleListView.setPullLoadEnable(true);
                        dateScheduleListView.setPullRefreshEnable(true);
                        SharedPreference.setDataInSharedPreference(getActivity(), Constant.defaultLocation, "" + position);
                        SharedPreference.setDataInSharedPreference(getActivity(), Constant.defaultLocationId, DataManager.getInstance().getLocationModelClasses().get(position).getLocationId());
                        DataManager.getInstance().showProgressMessage(getActivity(), "Progress");
                        String date2 = df1.format(new Date(dateText.getText().toString()));
                        Map<String, Object> objectMap = new LinkedHashMap<>();
                        objectMap.put("type", "date");
                        objectMap.put("date", date2);
                        objectMap.put("location_id", DataManager.getInstance().getLocationModelClasses().get(position).getLocationId());
                        objectMap.put("skiprecords","0");
                        objectMap.put("search_keyword",searchKey);
                        offsetValue = 0;
                        JsonCaller.getInstance().getScheduleData(objectMap);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
                return false;
            }
        });
        searchButton.setOnClickListener(this);
//        setData();

        return view;
    }



    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.prevButton:
                searchEditText.setText("");
                dateScheduleListView.setPullLoadEnable(true);
                dateScheduleListView.setPullRefreshEnable(true);
                c.add(Calendar.DATE, -1);
                formattedDate = df.format(c.getTime());
                dateTv.setText(outFormat1.format(new Date(formattedDate)));
                dayTv.setText(outFormat.format(new Date(formattedDate)));
                dateText.setText(newOutFormat.format(new Date(formattedDate)));
                DataManager.getInstance().showProgressMessage(getActivity(), "Progress");
                if(SharedPreference.getSharedPrefData(getActivity(),Constant.defaultLocationId)!=null) {
                    locationid = SharedPreference.getSharedPrefData(getActivity(), Constant.defaultLocationId);
                }else {
                    locationid = "1";
                }
                String date = df1.format(c.getTime());
                Map<String, Object> objectMap = new LinkedHashMap<>();
                objectMap.put("type", "date");
                objectMap.put("date", date);
                objectMap.put("location_id", locationid);
                objectMap.put("skiprecords","0");
                objectMap.put("search_keyword",searchKey);
                offsetValue = 0;
                JsonCaller.getInstance().getScheduleData(objectMap);

                break;

            case R.id.forwadButton:
                searchEditText.setText("");
                dateScheduleListView.setPullLoadEnable(true);
                dateScheduleListView.setPullRefreshEnable(true);
                c.add(Calendar.DATE, 1);
                formattedDate = df.format(c.getTime());
                dateTv.setText(outFormat1.format(new Date(formattedDate)));
                dayTv.setText(outFormat.format(new Date(formattedDate)));
                dateText.setText(newOutFormat.format(new Date(formattedDate)));
                DataManager.getInstance().showProgressMessage(getActivity(), "Progress");
                if(SharedPreference.getSharedPrefData(getActivity(),Constant.defaultLocationId)!=null) {
                    locationid = SharedPreference.getSharedPrefData(getActivity(), Constant.defaultLocationId);
                }else {
                    locationid = "1";
                }
                String date1 = df1.format(c.getTime());
                Map<String, Object> objectMap1 = new LinkedHashMap<>();
                objectMap1.put("type", "date");
                objectMap1.put("date", date1);
                objectMap1.put("location_id", locationid);
                objectMap1.put("skiprecords","0");
                objectMap1.put("search_keyword",searchKey);
                JsonCaller.getInstance().getScheduleData(objectMap1);
                break;
            case R.id.searchButton:
                searchKey = searchEditText.getText().toString();
                DataManager.getInstance().showProgressMessage(getActivity(), "Progress");
                String date2 = df1.format(new Date(dateText.getText().toString()));
                Map<String, Object> objectMap2 = new LinkedHashMap<>();
                objectMap2.put("type", "date");
                objectMap2.put("date", date2);
                if(SharedPreference.getSharedPrefData(getActivity(),Constant.defaultLocationId)!=null) {
                    locationid = SharedPreference.getSharedPrefData(getActivity(), Constant.defaultLocationId);
                }else {
                    locationid = "1";
                }
                objectMap2.put("location_id", locationid);
                objectMap2.put("skiprecords","0");
                objectMap2.put("search_keyword",searchKey);
                JsonCaller.getInstance().getScheduleData(objectMap2);
                break;
        }
    }


    public void onRefreshData(Refreshable refreshable, int requestCode) {
        if (requestCode == JsonCaller.REFRESH_CODE_SCHEDULE_DATA) {
            if(offsetValue==0) {
                dateAdapter = new DateAdapter(getActivity(), DataManager.getInstance().getDateModelClasses());
                dateScheduleListView.setAdapter(dateAdapter);
//                dateAdapter.notifyDataSetChanged();
            }else {
                dateAdapter.notifyDataSetChanged();
            }
            Map<String,Object> objectMap = new LinkedHashMap<>();
            JsonCaller.getInstance().getLocationList(objectMap);
        }else if(requestCode == JsonCaller.REFRESH_CODE_LOCATION_LIST){
            spinnerAdapter.notifyDataSetChanged();
            if(SharedPreference.getSharedPrefData(getActivity(), Constant.defaultLocation)!=null) {
                int position = Integer.parseInt(SharedPreference.getSharedPrefData(getActivity(), Constant.defaultLocation));
                Log.e("onRefreshData: ", "" + position);
                spinnerLocation.setSelection(position);
            }
            DataManager.getInstance().hideProgressMessage();
        }else if(requestCode == JsonCaller.REFRESH_CODE_SCHEDULE_DATA_DATE_NULL){
            dateScheduleListView.setPullLoadEnable(false);
            dateScheduleListView.setPullRefreshEnable(false);
            Map<String,Object> objectMap = new LinkedHashMap<>();
            JsonCaller.getInstance().getLocationList(objectMap);
        }
    }

    private void onLoad() {
        dateScheduleListView.stopRefresh();
        dateScheduleListView.stopLoadMore();
        dateScheduleListView.setRefreshTime("just");
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onLoadMore() {
        Handler mHandler = new Handler();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
//                DataManager.getInstance().showProgressMessage(getActivity(), "Progress");
                int size = DataManager.getInstance().getDateModelClasses().size();

                if(SharedPreference.getSharedPrefData(getActivity(),Constant.defaultLocationId)!=null) {
                    locationid = SharedPreference.getSharedPrefData(getActivity(), Constant.defaultLocationId);
                }else {
                    locationid = "1";
                }
                String date1 = df1.format(c.getTime());
                Map<String, Object> objectMap1 = new LinkedHashMap<>();
                objectMap1.put("type", "date");
                objectMap1.put("date", date1);
                objectMap1.put("location_id", locationid);
                objectMap1.put("skiprecords",size);
                objectMap1.put("search_keyword",searchKey);
                JsonCaller.getInstance().getScheduleData(objectMap1);
                onLoad();
            }
        }, 2000);
    }
}
