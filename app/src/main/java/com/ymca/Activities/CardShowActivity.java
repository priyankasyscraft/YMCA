package com.ymca.Activities;

import android.app.Activity;
import android.content.Intent;
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
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.oned.Code128Writer;
import com.google.zxing.qrcode.QRCodeWriter;
import com.ymca.AppManager.DataManager;
import com.ymca.AppManager.SharedPreference;
import com.ymca.Constants.Constant;
import com.ymca.R;
import com.ymca.UserInterFace.Refreshable;
import com.ymca.WebManager.JsonCaller;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Soni on 03-Aug-16.
 */
public class CardShowActivity extends Activity {

    private View view;
    TextView memberName,barCodeNo;
    ImageView barCodeImg, qrCodeImg,backButton;
    private Bitmap barCodeBitmap, qrBitmap;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 0);
        setContentView(R.layout.card_show_fragment);

        memberName = (TextView) findViewById(R.id.memberName);
        barCodeNo =  (TextView) findViewById(R.id.barCodeNo);
        barCodeImg = (ImageView)findViewById(R.id.barCodeImg);
        qrCodeImg = (ImageView) findViewById(R.id.qrCodeImg);
        backButton = (ImageView) findViewById(R.id.backButton);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                onBackPressed();
                DataManager.getInstance().setFlagMyCardBack(true);
                Intent intent = new Intent(CardShowActivity.this,HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });

        memberName.setText(DataManager.getInstance().getMemberName());
        createEAN13Code(DataManager.getInstance().getMemberCardNumber());
    }


    public void createEAN13Code(String s) {
        if (s == null) return;

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

        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        DataManager.getInstance().setFlagMyCardBack(true);
        Intent intent = new Intent(CardShowActivity.this,HomeActivity.class);
        startActivity(intent);
        finish();
    }
}
