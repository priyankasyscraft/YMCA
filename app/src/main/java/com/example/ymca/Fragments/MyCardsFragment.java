package com.example.ymca.Fragments;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ymca.BarcodeGenerator.BarQrCodeGenerator;
import com.example.ymca.R;
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
    EditText nameMyCard,numberMyCard;
    ImageView barCodeImg;
    TextView barCodeNumber;
    private Bitmap BarCodeBitmap;
    private Bitmap QrBitmap;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.my_card_fragment,container,false);

        nameMyCard = (EditText)view.findViewById(R.id.nameMyCard);
        numberMyCard = (EditText)view.findViewById(R.id.numberMyCard);
        barCodeImg = (ImageView) view.findViewById(R.id.barCodeImg);
        barCodeNumber = (TextView) view.findViewById(R.id.barCodeNumber);

        return  view;
    }

    public void createEAN13Code() {


        String s = numberMyCard.getText().toString().trim();
        if (s == null || s.length() != 12) return;

        BarQrCodeGenerator bb = new BarQrCodeGenerator(s);


        barCodeNumber.setText(bb.getCode());
        generateBarCode(s);
//        generateQRCode(s);
    }


    //Change the writers as per your need QR code
//    private void generateQRCode(String data) {
//        com.google.zxing.Writer writer = new QRCodeWriter();
//        String finaldata = Uri.encode(data, "ISO-8859-1");
//        try {
//            BitMatrix bm = writer.encode(finaldata, BarcodeFormat.QR_CODE, 350, 350);
//            QrBitmap = Bitmap.createBitmap(350, 350, Bitmap.Config.ARGB_8888);
//            for (int i = 0; i < 350; i++) {
//                for (int j = 0; j < 350; j++) {
//                    QrBitmap.setPixel(i, j, bm.get(i, j) ? Color.BLACK : Color.WHITE);
//                }
//            }
//        } catch (WriterException e) {
//            e.printStackTrace();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        if (QrBitmap != null) {
//            mImageView.setImageBitmap(QrBitmap);
//        }
//    }

    public void generateBarCode(String data) {
        com.google.zxing.Writer c9 = new Code128Writer();
        try {
            BitMatrix bm = c9.encode(data, BarcodeFormat.CODE_128, 350, 350);
            BarCodeBitmap = Bitmap.createBitmap(350, 350, Bitmap.Config.ARGB_8888);

            for (int i = 0; i < 350; i++) {
                for (int j = 0; j < 350; j++) {

                    BarCodeBitmap.setPixel(i, j, bm.get(i, j) ? Color.BLACK : Color.WHITE);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (BarCodeBitmap != null) {
            barCodeImg.setImageBitmap(BarCodeBitmap);
        }
    }
}
