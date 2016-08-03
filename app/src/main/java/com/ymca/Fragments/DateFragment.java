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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


/**
 * Created by Soni on 28-Jul-16.
 */
public class DateFragment extends Fragment implements View.OnClickListener {

    private View view;
    ImageView prevButton,forwadButton;
    TextView dateTv,dayTv;
    Calendar c;
    SimpleDateFormat df;
    String formattedDate;
    private SimpleDateFormat outFormat,outFormat1;
    XListView dateScheduleListView;
    DateAdapter dateAdapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.date_fragment,container,false );
        c = Calendar.getInstance();
        df = new SimpleDateFormat("dd-MMM-yyyy");
        outFormat = new SimpleDateFormat("EEE");
        outFormat1 = new SimpleDateFormat("MMM dd");
        formattedDate = df.format(c.getTime());

        dateScheduleListView = (XListView) view.findViewById(R.id.dateScheduleListView);
        dateTv = (TextView) view.findViewById(R.id.dateTv);
        dayTv = (TextView) view.findViewById(R.id.dayTv);
        prevButton   = (ImageView)view.findViewById(R.id.prevButton);
        forwadButton = (ImageView)view.findViewById(R.id.forwadButton);
        formattedDate = df.format(c.getTime());
        dateTv.setText(outFormat1.format(new Date(formattedDate)));
        dayTv.setText(outFormat.format(new Date(formattedDate)));
        prevButton.setOnClickListener(this);
        forwadButton.setOnClickListener(this);

        dateAdapter = new DateAdapter(getActivity(), DataManager.getInstance().getDateModelClasses());
        dateScheduleListView.setAdapter(dateAdapter);

        setData();

        return view;
    }

    private void setData() {
        for(int i = 0;i<20;i++){
            DateModelClass dateModelClass = new DateModelClass();
            dateModelClass.setScheduleDateAppointmentTime("2016-08-03 15:23:00");
            dateModelClass.setScheduleDateName("CYCLE + TRX - Beginner");
            dateModelClass.setScheduleDateNameWith("with James MacMann");
            dateModelClass.setScheduleDateTime("Th Tu @07:40 AM (50 min)");
            dateModelClass.setScheduleDatePlace("Group Exercise studio");
            DataManager.getInstance().addDateModelClasses(dateModelClass);
        }
        dateAdapter.setReloadData(true);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.prevButton:
                c.add(Calendar.DATE, -1);
                formattedDate = df.format(c.getTime());
                dateTv.setText(outFormat1.format(new Date(formattedDate)));
                dayTv.setText(outFormat.format(new Date(formattedDate)));
                break;

            case R.id.forwadButton:
                c.add(Calendar.DATE, 1);
                formattedDate = df.format(c.getTime());
                dateTv.setText(outFormat1.format(new Date(formattedDate)));
                dayTv.setText(outFormat.format(new Date(formattedDate)));
                break;
        }
    }
}
