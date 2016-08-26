package com.ymca.Fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ymca.Activities.HomeActivity;
import com.ymca.AppManager.DataManager;
import com.ymca.AppManager.SharedPreference;
import com.ymca.Constants.Constant;
import com.ymca.R;
import com.ymca.UserInterFace.RefreshDataListener;
import com.ymca.UserInterFace.Refreshable;
import com.ymca.WebManager.JsonCaller;

import java.util.LinkedHashMap;
import java.util.Map;


/**
 * Created by Soni on 28-Jul-16.
 */
public class AddCardFragment extends Fragment implements View.OnClickListener {

    private View view;
    EditText memberName, memberBarCodeNumber;
    TextView cancelButton, addCardButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.add_my_card_fragment, container, false);

        actionBarUpdate();
        memberName = (EditText) view.findViewById(R.id.memberName);
        memberBarCodeNumber = (EditText) view.findViewById(R.id.memberBarCodeNumber);
        cancelButton = (TextView) view.findViewById(R.id.cancelButton);
        addCardButton = (TextView) view.findViewById(R.id.addCardButton);

        Typeface myTypeface = Typeface.createFromAsset(getActivity().getAssets(), "fonts/cachet-std-bold.ttf");
        memberName.setTypeface(myTypeface);
        memberBarCodeNumber.setTypeface(myTypeface);

        cancelButton.setOnClickListener(this);
        addCardButton.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cancelButton:
                getActivity().onBackPressed();
                break;
            case R.id.addCardButton:

                if (memberName.getText().toString().trim().equals("")) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
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
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
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


                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
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

                    DataManager.getInstance().showProgressMessage(getActivity(), "Progress");
                    DataManager.getInstance().setMemberName(memberName.getText().toString().trim());
                    DataManager.getInstance().setMemberCardNumber(memberBarCodeNumber.getText().toString().trim());
                    DataManager.getInstance().setFlagCardShow(false);

                    getActivity()
                            .getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.content_frame, new CardShowFragment(), Constant.cardShowFragment)
                            .commit();

                }
                break;
        }
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


}
