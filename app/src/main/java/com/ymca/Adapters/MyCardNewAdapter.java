package com.ymca.Adapters;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ymca.Activities.HomeActivity;
import com.ymca.AppManager.DataManager;
import com.ymca.AppManager.SharedPreference;
import com.ymca.Constants.Constant;
import com.ymca.Fragments.CardShowFragment;
import com.ymca.Fragments.ClassDetailFragment;
import com.ymca.ModelClass.MyCardModelClass;
import com.ymca.R;
import com.ymca.WebManager.JsonCaller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Soni on 03-Aug-16.
 */
public class MyCardNewAdapter extends BaseAdapter implements View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener, View.OnLongClickListener {
    private ArrayList<MyCardModelClass> myCardModelClassArrayList = new ArrayList<MyCardModelClass>();
    private LayoutInflater inflater;
    Context context;
    ViewHolder viewHolder;
    private int pos;

    public MyCardNewAdapter(Context context, ArrayList<MyCardModelClass> myCardModelClassArrayList) {
        this.myCardModelClassArrayList = myCardModelClassArrayList;
        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    public void setReloadData(boolean shouldReload) {
        myCardModelClassArrayList = DataManager.getInstance().getMyCardModelClasses();
        if (shouldReload) {
            DataManager.getInstance().getAppCompatActivity().runOnUiThread(new Runnable() {
                public void run() {
                    notifyDataSetChanged();
                    DataManager.getInstance().hideProgressMessage();
                }
            });
        }
    }

    @Override
    public int getCount() {
        return myCardModelClassArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.my_card_items, null);
            viewHolder.cardBarCodeNo = (TextView) convertView.findViewById(R.id.cardBarCodeNo);
            viewHolder.cardUserName = (TextView) convertView.findViewById(R.id.cardUserName);
            viewHolder.cardLayout = (LinearLayout) convertView.findViewById(R.id.cardLayout);
            viewHolder.customLayout = (LinearLayout) convertView.findViewById(R.id.customLayout);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
//        pos = position;
        viewHolder.cardUserName.setText(myCardModelClassArrayList.get(position).getUserName());
        viewHolder.cardBarCodeNo.setText(myCardModelClassArrayList.get(position).getUserBarCodeNumber());

//        viewHolder.cardLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                DataManager.getInstance().setMemberName(viewHolder.cardUserName.getText().toString());
//                DataManager.getInstance().setMemberCardNumber(viewHolder.cardBarCodeNo.getText().toString().replace("CODE: ", ""));
//                DataManager.getInstance().setFlagCardShow(true);
//                ((HomeActivity) context)
//                        .getSupportFragmentManager()
//                        .beginTransaction()
//                        .replace(R.id.content_frame, new CardShowFragment(), Constant.cardShowFragment)
//                        .addToBackStack(((HomeActivity) context).getSupportFragmentManager().getClass().getName())
//                        .commit();
//            }
//        });

//        viewHolder.customLayout.setTag(position);

//        viewHolder.cardUserName.setOnLongClickListener(this);
//        viewHolder.cardUserName.setOnCreateContextMenuListener(this);
        return convertView;
    }

    @Override
    public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
        MenuItem myActionItem = contextMenu.add("Delete");
        myActionItem.setOnMenuItemClickListener(this);
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {

        DataManager.getInstance().showProgressMessage(DataManager.getInstance().getAppCompatActivity(), "Progress");
        String deviceToken = SharedPreference.getSharedPrefData(DataManager.getInstance().getAppCompatActivity(), Constant.deviceToken);
        Map<String, Object> params = new LinkedHashMap<>();
        params.put("card_id", viewHolder.cardBarCodeNo.getText().toString().replace("CODE:", ""));
        params.put("device_token", deviceToken);
        params.put("device_type","1");
        JsonCaller.getInstance().getDeleteCard(params);
//        pos = (int) viewHolder.cardLayout.getTag();
        myCardModelClassArrayList.remove(pos);
        return true;
    }

    @Override
    public boolean onLongClick(View view) {
        viewHolder.cardUserName.setOnCreateContextMenuListener(this);
        return true;
    }

    public static class ViewHolder {
        LinearLayout cardLayout, customLayout;
        public TextView cardUserName, cardBarCodeNo;
    }
}
