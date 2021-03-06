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
import com.ymca.Fragments.ClassDetailFragment;
import com.ymca.Fragments.HomeClassDetailFragment;
import com.ymca.ModelClass.HomeClassesModelClass;
import com.ymca.R;

import java.util.ArrayList;

/**
 * Created by Soni on 03-Aug-16.
 */
public class HomeClassAdapter extends BaseAdapter {
    private ArrayList<HomeClassesModelClass> classesModelClassArrayList = new ArrayList<HomeClassesModelClass>();
    private LayoutInflater inflater;
    Context context;
    ViewHolder viewHolder;

    public HomeClassAdapter(Context context, ArrayList<HomeClassesModelClass> classesModelClassArrayList) {
        this.classesModelClassArrayList = classesModelClassArrayList;
        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    public void setReloadData(boolean shouldReload) {
        classesModelClassArrayList = DataManager.getInstance().getHomeClassesModelClassArrayList();
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
        return classesModelClassArrayList.size();
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
            convertView = inflater.inflate(R.layout.home_classes_item, null);
            viewHolder.homeClassName = (TextView) convertView.findViewById(R.id.homeClassName);
            viewHolder.homeClassDayDate = (TextView) convertView.findViewById(R.id.homeClassDayDate);
            viewHolder.homeClassTime = (TextView) convertView.findViewById(R.id.homeClassTime);
            viewHolder.bgLayout = (LinearLayout) convertView.findViewById(R.id.bgLayout);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.homeClassName.setText(classesModelClassArrayList.get(position).getHomeClassesName());
        viewHolder.homeClassDayDate.setText(classesModelClassArrayList.get(position).getHomeClassesDayDate());
        viewHolder.homeClassTime.setText(classesModelClassArrayList.get(position).getHomeClassesTime());

        viewHolder.bgLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((HomeActivity) context)
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.content_frame, new HomeClassDetailFragment(), Constant.homeClassDetailFragment)
                        .addToBackStack(((HomeActivity) context).getSupportFragmentManager().getClass().getName())
                        .commit();
            }
        });

        return convertView;
    }

    public static class ViewHolder {
        TextView homeClassName, homeClassDayDate, homeClassTime;
        LinearLayout bgLayout;
    }
}
