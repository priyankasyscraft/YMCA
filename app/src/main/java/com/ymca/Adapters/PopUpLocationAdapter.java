package com.ymca.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ymca.AppManager.DataManager;
import com.ymca.ModelClass.PopUpLocationModel;
import com.ymca.R;

import java.util.ArrayList;

/**
 * Created by Soni on 03-Aug-16.
 */
public class PopUpLocationAdapter extends BaseAdapter {
    private ArrayList<PopUpLocationModel> popUpLocationModelArrayList = new ArrayList<PopUpLocationModel>();
    private LayoutInflater inflater;
    Context context;
    ViewHolder viewHolder;

    public PopUpLocationAdapter(Context context, ArrayList<PopUpLocationModel> popUpLocationModels) {
        this.popUpLocationModelArrayList = popUpLocationModels;
        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    public void setReloadData(boolean shouldReload) {
        popUpLocationModelArrayList = DataManager.getInstance().getPopUpLocationModelArrayList();
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
        return popUpLocationModelArrayList.size();
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
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.popup_location_item, null);
            viewHolder.popLocationName = (TextView) convertView.findViewById(R.id.popLocationName);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.popLocationName.setText(popUpLocationModelArrayList.get(position).getLocationName());



        return convertView;
    }

    public static class ViewHolder {
        TextView popLocationName;
    }
}
