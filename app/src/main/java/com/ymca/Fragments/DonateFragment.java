package com.ymca.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ymca.AppManager.DataManager;
import com.ymca.Constants.Constant;
import com.ymca.R;

/**
 * Created by Soni on 03-Aug-16.
 */
public class DonateFragment extends Fragment {

    private View view;
    ImageView donateButton;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.donate_fragment,container,false);

        donateButton = (ImageView)view.findViewById(R.id.donateButton);
        donateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DataManager.getInstance().setFlagWebView(false);
                getActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.content_frame, new WebViewFragment(), Constant.webViewFragment)
                        .addToBackStack(getActivity().getSupportFragmentManager().getClass().getName())
                        .commit();
            }
        });

        return view;
    }
}
