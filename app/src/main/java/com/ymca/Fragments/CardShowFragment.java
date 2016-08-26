package com.ymca.Fragments;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.oned.Code128Writer;
import com.google.zxing.qrcode.QRCodeWriter;
import com.ymca.Activities.HomeActivity;
import com.ymca.AppManager.DataManager;
import com.ymca.AppManager.SharedPreference;
import com.ymca.BarcodeGenerator.BarQrCodeGenerator;
import com.ymca.Constants.Constant;
import com.ymca.R;
import com.ymca.UserInterFace.Refreshable;
import com.ymca.WebManager.JsonCaller;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Soni on 03-Aug-16.
 */
public class CardShowFragment extends Fragment {

    private View view;
    TextView memberName,barCodeNo;
    ImageView barCodeImg, qrCodeImg;
    private Bitmap barCodeBitmap, qrBitmap;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if(!DataManager.getInstance().isFlagCardShow()) {
            DataManager.getInstance().showProgressMessage(getActivity(), "Progress");
            String deviceToken = SharedPreference.getSharedPrefData(getActivity(), Constant.deviceToken);
            Map<String, Object> params = new LinkedHashMap<>();

            params.put("device_token", deviceToken);
            params.put("card_name", DataManager.getInstance().getMemberName());
            params.put("barcode_no", DataManager.getInstance().getMemberCardNumber());
            DataManager.getInstance().setFlagCardShowBack(true);
            JsonCaller.getInstance().getAddCard(params);
        }

        view = inflater.inflate(R.layout.card_show_fragment, container, false);

        memberName = (TextView) view.findViewById(R.id.memberName);
        barCodeNo = (TextView) view.findViewById(R.id.barCodeNo);
        barCodeImg = (ImageView) view.findViewById(R.id.barCodeImg);
        qrCodeImg = (ImageView) view.findViewById(R.id.qrCodeImg);
        actionBarUpdate();
        memberName.setText(DataManager.getInstance().getMemberName());
        createEAN13Code(DataManager.getInstance().getMemberCardNumber());
        return view;
    }


    public void onRefreshData(Refreshable refreshable, int requestCode) {

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

    public void createEAN13Code(String s) {
        if (s == null) return;

//        BarQrCodeGenerator bb = new BarQrCodeGenerator(s);

//        double miles = distance(22.719568,75.857727,22.9612,76.0514);
//        String milesDouble = String.format("%.2f", miles);
//        barCodeNo.setText(milesDouble);
        barCodeNo.setText(s);
        generateBarCode(s);
        generateQRCode(s);
    }


    //Change the writers as per your need QR code
    private void generateQRCode(String data) {
        com.google.zxing.Writer writer = new QRCodeWriter();
        String finaldata = Uri.encode(data, "ISO-8859-1");
        try {
            BitMatrix bm = writer.encode(finaldata, BarcodeFormat.QR_CODE, 350, 350);
            qrBitmap = Bitmap.createBitmap(350, 350, Bitmap.Config.ARGB_8888);
            for (int i = 0; i < 350; i++) {
                for (int j = 0; j < 350; j++) {
                    qrBitmap.setPixel(i, j, bm.get(i, j) ? Color.BLACK : Color.WHITE);
                }
            }
        } catch (WriterException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (qrBitmap != null) {
            qrCodeImg.setImageBitmap(qrBitmap);
        }
    }

    public void generateBarCode(String data) {
        com.google.zxing.Writer c9 = new Code128Writer();
        try {

            BitMatrix bm = c9.encode(data, BarcodeFormat.CODE_128, 350, 350);
            barCodeBitmap = Bitmap.createBitmap(350, 350, Bitmap.Config.ARGB_8888);

            for (int i = 0; i < 350; i++) {
                for (int j = 0; j < 350; j++) {

                    barCodeBitmap.setPixel(i, j, bm.get(i, j) ? Color.BLACK : Color.WHITE);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (barCodeBitmap != null) {
            barCodeImg.setImageBitmap(barCodeBitmap);
            DataManager.getInstance().hideProgressMessage();
        }
    }
}
