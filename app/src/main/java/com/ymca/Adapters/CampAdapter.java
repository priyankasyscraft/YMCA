package com.ymca.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ymca.Activities.HomeActivity;
import com.ymca.AppManager.DataManager;
import com.ymca.Constants.Constant;
import com.ymca.Fragments.CampDetailFragment;
import com.ymca.Fragments.ClassDetailFragment;
import com.ymca.ModelClass.CampModelClass;
import com.ymca.R;

import java.util.ArrayList;

/**
 * Created by Soni on 03-Aug-16.
 */
public class CampAdapter extends BaseAdapter {
    private ArrayList<CampModelClass> campModelClassArrayList = new ArrayList<CampModelClass>();
    private LayoutInflater inflater;
    Context context;
    ViewHolder viewHolder;

    public CampAdapter(Context context, ArrayList<CampModelClass> campModelClasses) {
        this.campModelClassArrayList = campModelClasses;
        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    public void setReloadData(boolean shouldReload) {
        campModelClassArrayList = DataManager.getInstance().getCampModelClassArrayList();
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
        return campModelClassArrayList.size();
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
            convertView = inflater.inflate(R.layout.camp_item, null);
            viewHolder.campCount = (TextView) convertView.findViewById(R.id.campCount);
            viewHolder.campName = (TextView) convertView.findViewById(R.id.campName);
            viewHolder.campAddress = (TextView) convertView.findViewById(R.id.campAddress);
            viewHolder.campLayout = (LinearLayout) convertView.findViewById(R.id.campLayout);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.campCount.setText(campModelClassArrayList.get(position).getCampCounter());
        viewHolder.campName.setText(campModelClassArrayList.get(position).getCampName());
        viewHolder.campAddress.setText(campModelClassArrayList.get(position).getCampAddress());

        viewHolder.campLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ((HomeActivity) context)
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.content_frame, new CampDetailFragment(), Constant.campDetailFramgent)
                        .addToBackStack(((HomeActivity) context).getSupportFragmentManager().getClass().getName())
                        .commit();
            }
        });

        return convertView;
    }

    public static class ViewHolder {
        TextView campCount, campName, campAddress;
        LinearLayout campLayout;
    }
}
