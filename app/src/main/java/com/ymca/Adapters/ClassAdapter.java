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
import com.ymca.ModelClass.ClassesModelClass;
import com.ymca.R;
import com.ymca.WebManager.JsonCaller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Soni on 03-Aug-16.
 */
public class ClassAdapter extends BaseAdapter {
    private ArrayList<ClassesModelClass> classesModelClassArrayList = new ArrayList<ClassesModelClass>();
    private LayoutInflater inflater;
    Context context;
    ViewHolder viewHolder;

    public ClassAdapter(Context context, ArrayList<ClassesModelClass> classesModelClassArrayList) {
        this.classesModelClassArrayList = classesModelClassArrayList;
        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    public void setReloadData(boolean shouldReload) {
        classesModelClassArrayList = DataManager.getInstance().getClassesModelClassArrayList();
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
    public View getView(final int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.class_item, null);
            viewHolder.className = (TextView) convertView.findViewById(R.id.className);
            viewHolder.classLayout = (LinearLayout) convertView.findViewById(R.id.classLayout);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.className.setText(classesModelClassArrayList.get(position).getClassesName());

        DataManager.getInstance().hideProgressMessage();
        viewHolder.classLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isCheck = DataManager.chkStatus(context);
                if (isCheck) {

                    DataManager.getInstance().setFlagClassList(true);
                    DataManager.getInstance().showProgressMessage(DataManager.getInstance().getAppCompatActivity(), "Progress");

                    Map<String, Object> objectMap = new LinkedHashMap<String, Object>();
                    objectMap.put("class_id", classesModelClassArrayList.get(position).getClassesId());

                    JsonCaller.getInstance().getClassDetail(objectMap);
                }
            }
        });

        return convertView;
    }

    public static class ViewHolder {
        TextView className;
        LinearLayout classLayout;
    }
}
