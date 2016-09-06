package com.ymca.Activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.ymca.AppManager.DataManager;
import com.ymca.AppManager.SharedPreference;
import com.ymca.Constants.Constant;
import com.ymca.Fragments.CardShowFragment;
import com.ymca.R;
import com.ymca.UserInterFace.RefreshDataListener;
import com.ymca.UserInterFace.Refreshable;
import com.ymca.WebManager.JsonCaller;

import java.util.LinkedHashMap;
import java.util.Map;


/**
 * Created by Soni on 28-Jul-16.
 */
public class AddCardActivity extends Activity implements View.OnClickListener {

    private View view;
    EditText memberName, memberBarCodeNumber;
    TextView cancelButton, addCardButton;
    ImageView backButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 0);
        setContentView(R.layout.add_my_card_fragment);

        backButton = (ImageView) findViewById(R.id.backButton);
        memberName = (EditText) findViewById(R.id.memberName);
        memberBarCodeNumber = (EditText) findViewById(R.id.memberBarCodeNumber);
        cancelButton = (TextView) findViewById(R.id.cancelButton);
        addCardButton = (TextView) findViewById(R.id.addCardButton);

        Typeface myTypeface = Typeface.createFromAsset(getAssets(), "fonts/cachet-std-bold.ttf");
        memberName.setTypeface(myTypeface);
        memberBarCodeNumber.setTypeface(myTypeface);

        cancelButton.setOnClickListener(this);
        addCardButton.setOnClickListener(this);
        backButton.setOnClickListener(this);
    }

//    @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        view = inflater.inflate(R.layout.add_my_card_fragment, container, false);
//
//        actionBarUpdate();
//        memberName = (EditText) view.findViewById(R.id.memberName);
//        memberBarCodeNumber = (EditText) view.findViewById(R.id.memberBarCodeNumber);
//        cancelButton = (TextView) view.findViewById(R.id.cancelButton);
//        addCardButton = (TextView) view.findViewById(R.id.addCardButton);
//
//        Typeface myTypeface = Typeface.createFromAsset(getAssets(), "fonts/cachet-std-bold.ttf");
//        memberName.setTypeface(myTypeface);
//        memberBarCodeNumber.setTypeface(myTypeface);
//
//        cancelButton.setOnClickListener(this);
//        addCardButton.setOnClickListener(this);
//
//        return view;
//    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cancelButton:
//                getActivity().getSupportFragmentManager().beginTransaction()
//                        .replace(R.id.content_frame,new MyCardsFragment(),Constant.myCardFragment)
//                        .commit();
//                getActivity().onBackPressed();
                DataManager.getInstance().setFlagMyCardBack(true);
                Intent intent = new Intent(AddCardActivity.this,HomeActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.addCardButton:

                if (memberName.getText().toString().trim().equals("")) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("Alert");
                    builder.setMessage("Please enter Member Name");
                    builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    builder.show();
                } else if (memberBarCodeNumber.getText().toString().trim().equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("Alert");
                    builder.setMessage("Please enter  member card number");
                    builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    builder.show();
                } else if (memberBarCodeNumber.getText().toString().trim().length()< 4) {


                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("Alert");
                    builder.setMessage("Please enter minimum 4 digit member card number");
                    builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    builder.show();
                } else {

                    DataManager.getInstance().showProgressMessage(this, "Progress");
                    DataManager.getInstance().setMemberName(memberName.getText().toString().trim());
                    DataManager.getInstance().setMemberCardNumber(memberBarCodeNumber.getText().toString().trim());
                    DataManager.getInstance().setFlagCardShow(false);
                    String deviceToken = SharedPreference.getSharedPrefData(this, Constant.deviceToken);
                    Map<String, Object> params = new LinkedHashMap<>();

                    params.put("device_token", deviceToken);
                    params.put("card_name", DataManager.getInstance().getMemberName());
                    params.put("barcode_no", DataManager.getInstance().getMemberCardNumber());
                    params.put("device_type","1");
                    DataManager.getInstance().setFlagCardShowBack(true);
                    JsonCaller.getInstance().getAddCard(params);
                }
                break;
            case R.id.backButton:
                DataManager.getInstance().setFlagMyCardBack(true);
                Intent intent1 = new Intent(AddCardActivity.this,HomeActivity.class);
                startActivity(intent1);
                finish();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        DataManager.getInstance().setFlagMyCardBack(true);
        Intent intent = new Intent(AddCardActivity.this,HomeActivity.class);
        startActivity(intent);
        finish();
    }



}
