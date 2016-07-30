package com.example.ymca.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ymca.R;

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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.date_fragment,container,false );
        c = Calendar.getInstance();
        df = new SimpleDateFormat("dd-MMM-yyyy");

        formattedDate = df.format(c.getTime());

        dateTv = (TextView) view.findViewById(R.id.dateTv);
        dayTv = (TextView) view.findViewById(R.id.dayTv);
        prevButton   = (ImageView)view.findViewById(R.id.prevButton);
        forwadButton = (ImageView)view.findViewById(R.id.forwadButton);

        prevButton.setOnClickListener(this);
        forwadButton.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        SimpleDateFormat outFormat = new SimpleDateFormat("EEE");
        SimpleDateFormat outFormat1 = new SimpleDateFormat("MMM dd");
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
