package com.ymca.Adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.ymca.ModelClass.LocationModelClass;
import com.ymca.R;

import java.util.ArrayList;
import java.util.List;



/**
 * Created by Syscraft on 15-Oct-15.
 */
public class SpinnerAdapter extends ArrayAdapter {


    ArrayList<LocationModelClass> moreOptionList = new ArrayList<>();

    Context ctxt;
    LayoutInflater mInflater;

    public SpinnerAdapter(Context context, int textViewResourceId, ArrayList<LocationModelClass> optionLIst) {
        super(context, textViewResourceId, optionLIst);

        moreOptionList = optionLIst;
        ctxt = context;
        mInflater = (LayoutInflater) ctxt.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
    }

    public View getCustomView(int position, View convertView, ViewGroup parent) {


        View layout_view = mInflater.inflate(R.layout.more_option_item, parent, false);
        //    MoreOptionItem moreOptionItem = ticket_list1.get(position);

        TextView ticket_txt = (TextView) layout_view.findViewById(R.id.ticket_txt);

        ticket_txt.setText(moreOptionList.get(position).getLocationName());

        ticket_txt.setTextColor(Color.BLACK);
        return layout_view;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {

            convertView = mInflater.inflate(R.layout.more_option_item, parent, false);
        }
        //  MoreOptionItem moreOptionItem = ticket_list1.get(position);
        TextView dd_GoText = (TextView) convertView.findViewById(R.id.ticket_txt);
        dd_GoText.setText(moreOptionList.get(position).getLocationName());

        dd_GoText.setTextColor(Color.BLACK);

        return convertView;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }
}
