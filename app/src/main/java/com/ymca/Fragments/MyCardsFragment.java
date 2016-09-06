package com.ymca.Fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.ymca.Activities.AddCardActivity;
import com.ymca.Activities.CardShowActivity;
import com.ymca.Activities.HomeActivity;
import com.ymca.Adapters.MyCardNewAdapter;
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
 * Created by Soni on 30-Jul-16.
 */
public class MyCardsFragment extends Fragment implements RefreshDataListener {

    private View view;
    private Bitmap BarCodeBitmap;
    private Bitmap QrBitmap;
    MyCardNewAdapter myCardAdapter;
    ListView recyclerCardList;
    TextView addCard;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.my_card_fragment, container, false);

        if (DataManager.getInstance().isFlagCheckIn()) {
            actionBarUpdate();
        } else {
            actionBarUpdateBack();
        }
        DataManager.getInstance().hideProgressMessage();
        DataManager.getInstance().showProgressMessage(getActivity(), "Progress");
        String deviceToken = SharedPreference.getSharedPrefData(getActivity(), Constant.deviceToken);
        Map<String, Object> params = new LinkedHashMap<>();

        params.put("device_type", "1");
        params.put("device_token", deviceToken);
        JsonCaller.getInstance().getAllCard(params);
        recyclerCardList = (ListView) view.findViewById(R.id.recyclerCardList);

        recyclerCardList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                DataManager.getInstance().showProgressMessage(getActivity(), "Progress");
                DataManager.getInstance().setMemberName(DataManager.getInstance().getMyCardModelClasses().get(i).getUserName());
                DataManager.getInstance().setMemberCardNumber(DataManager.getInstance().getMyCardModelClasses().get(i).getUserBarCodeNumber().toString().replace("CARD NUMBER: ", ""));
                DataManager.getInstance().setFlagCardShow(true);

//                getActivity()
//                        .getSupportFragmentManager()
//                        .beginTransaction()
//                        .replace(R.id.content_frame, new CardShowFragment(), Constant.cardShowFragment)
//                        .addToBackStack(getActivity().getSupportFragmentManager().getClass().getName())
//                        .commit();
                Intent intent = new Intent(getActivity(), CardShowActivity.class);
                startActivity(intent);
                getActivity().finish();

            }
        });
        registerForContextMenu(recyclerCardList);
        addCard = (TextView) view.findViewById(R.id.addCard);

        myCardAdapter = new MyCardNewAdapter(getActivity(), DataManager.getInstance().getMyCardModelClasses());
        recyclerCardList.setAdapter(myCardAdapter);
//        DataManager.getInstance().hideProgressMessage();
        addCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                getActivity()
//                        .getSupportFragmentManager()
//                        .beginTransaction()
//                        .replace(R.id.content_frame, new AddCardFragment(), Constant.addMyCardFragment)
//                        .addToBackStack(null)
//                        .commit();
                Intent intent = new Intent(getActivity(), AddCardActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });
        return view;
    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        if (v.getId() == R.id.recyclerCardList) {
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
            menu.add(R.string.delete);
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();


        String deviceToken = SharedPreference.getSharedPrefData(DataManager.getInstance().getAppCompatActivity(), Constant.deviceToken);
        Map<String, Object> params = new LinkedHashMap<>();
        String barcodeno = DataManager.getInstance().getMyCardModelClasses().get(info.position).getUserBarCodeNumber().toString().replace("CARD NUMBER:", "");
        params.put("card_id", barcodeno.trim());
        params.put("device_token", deviceToken);
        params.put("device_type","1");
        JsonCaller.getInstance().getDeleteCard(params);
        DataManager.getInstance().getMyCardModelClasses().remove(info.position);
        myCardAdapter.setReloadData(true);
        DataManager.getInstance().showProgressMessage(DataManager.getInstance().getAppCompatActivity(), "Progress");

        return true;
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

        view.setVisibility(View.GONE);


        actionBar.setCustomView(view, layoutParams);
    }


    private void actionBarUpdateBack() {
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


    public void onRefreshData(Refreshable refreshable, int requestCode) {

//        myCardAdapter.setReloadData(true);
        if (requestCode == JsonCaller.REFRESH_CODE_DELETE_CARDS) {
            DataManager.getInstance().hideProgressMessage();
//            myCardAdapter.setReloadData(true);
        }else if(requestCode == JsonCaller.REFRESH_CODE_ALL_CARDS){
            myCardAdapter.setReloadData(true);
            DataManager.getInstance().hideProgressMessage();
        }
    }
}
