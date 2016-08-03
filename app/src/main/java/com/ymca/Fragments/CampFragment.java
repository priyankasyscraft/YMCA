package com.ymca.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.ymca.R;


/**
 * Created by Soni on 28-Jul-16.
 */
public class CampFragment extends Fragment {

    private View view;
    ListView campList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.camp_framgnet,container,false);

        campList = (ListView)view.findViewById(R.id.campList);

        return view;
    }
}