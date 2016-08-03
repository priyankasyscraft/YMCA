package com.ymca.Fragments;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ymca.Activities.HomeActivity;
import com.ymca.Adapters.MyCardAdapter;
import com.ymca.AppManager.DataManager;
import com.ymca.BarcodeGenerator.BarQrCodeGenerator;
import com.ymca.Constants.Constant;
import com.ymca.ModelClass.MyCardModelClass;
import com.ymca.PullListLoader.XListView;
import com.ymca.R;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.oned.Code128Writer;
import com.google.zxing.qrcode.QRCodeWriter;

/**
 * Created by Soni on 30-Jul-16.
 */
public class MyCardsFragment extends Fragment {

    private View view;
    private Bitmap BarCodeBitmap;
    private Bitmap QrBitmap;
    private MyCardAdapter myCardAdapter;
    RecyclerView recyclerCardList;
    TextView addCard;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.my_card_fragment, container, false);

        actionBarUpdate();
        recyclerCardList = (RecyclerView) view.findViewById(R.id.recyclerCardList);
        addCard = (TextView) view.findViewById(R.id.addCard);

        setData();

        addCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.content_frame, new AddCardFragment(), Constant.addMyCardFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });
        return view;
    }

    private void setData() {
        for (int i = 0; i < 10; i++) {
            MyCardModelClass myCardModelClass = new MyCardModelClass();
            myCardModelClass.setUserName("Amit");
            myCardModelClass.setUserBarCodeNumber("123456789012");
            DataManager.getInstance().addMyCardModelClasses(myCardModelClass);
        }

        myCardAdapter = new MyCardAdapter(getActivity(),DataManager.getInstance().getMyCardModelClasses());

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerCardList.setLayoutManager(mLayoutManager);
        recyclerCardList.setItemAnimator(new DefaultItemAnimator());
        recyclerCardList.setAdapter(myCardAdapter);
    }


    private void actionBarUpdate() {
        // TODO Auto-generated method stub
        ActionBar actionBar = ((HomeActivity) getActivity()).getSupportActionBar();


        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setTitle("");
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.icon_menu);
        actionBar.setDisplayShowTitleEnabled(true);


        // TODO: 28-Jul-16 set action bar background
        Drawable actionBar_bg = getResources().getDrawable(R.drawable.header_bg);
        actionBar.setBackgroundDrawable(actionBar_bg);
        actionBar.setDisplayShowCustomEnabled(true);
        ActionBar.LayoutParams layoutParams = new ActionBar.LayoutParams(
                ActionBar.LayoutParams.MATCH_PARENT,
                ActionBar.LayoutParams.MATCH_PARENT);

        // layoutParams.rightMargin = 20;

        LayoutInflater inflator = getActivity().getLayoutInflater();
        View view = inflator.inflate(R.layout.custom_layout_actionbar, null);

        ImageView notificationBell = (ImageView) view.findViewById(R.id.notificationBell);


        notificationBell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isCheck = DataManager.chkStatus();
                if (isCheck) {

                    getActivity()
                            .getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.content_frame, new NotificationFragment(), Constant.notificationFragment)
                            .commit();
                }

            }
        });

        actionBar.setCustomView(view, layoutParams);


    }
}