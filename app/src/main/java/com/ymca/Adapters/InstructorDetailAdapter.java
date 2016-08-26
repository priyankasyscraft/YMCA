package com.ymca.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ymca.Activities.HomeActivity;
import com.ymca.AppManager.DataManager;
import com.ymca.Constants.Constant;
import com.ymca.Fragments.ClassDetailFragment;
import com.ymca.ModelClass.InstructorDetailModel;
import com.ymca.R;
import com.ymca.WebManager.JsonCaller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Soni on 03-Aug-16.
 */
public class InstructorDetailAdapter extends BaseAdapter {
    private ArrayList<InstructorDetailModel> instructorDetailModelArrayList = new ArrayList<>();
    private LayoutInflater inflater;
    Context context;
    ViewHolder viewHolder;

    public InstructorDetailAdapter(Context context, ArrayList<InstructorDetailModel> classesModelClassArrayList) {
        this.instructorDetailModelArrayList = classesModelClassArrayList;
        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    public void setReloadData(boolean shouldReload) {
        instructorDetailModelArrayList = DataManager.getInstance().getInstructorDetailModelArrayList();
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
        return instructorDetailModelArrayList.get(0).getModelClasses().size();
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
            viewHolder.imgButton = (ImageButton) convertView.findViewById(R.id.imgButton);
            viewHolder.className = (TextView) convertView.findViewById(R.id.className);
            viewHolder.colorText = (TextView) convertView.findViewById(R.id.colorText);
            viewHolder.colorText.setVisibility(View.VISIBLE);
            viewHolder.classLayout = (LinearLayout) convertView.findViewById(R.id.classLayout);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.imgButton.setImageResource(R.drawable.arrow_black);
        viewHolder.className.setText(instructorDetailModelArrayList.get(0).getModelClasses().get(position).getClassesName());

        viewHolder.classLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isCheck = DataManager.chkStatus(context);
                if (isCheck) {

                    DataManager.getInstance().showProgressMessage(DataManager.getInstance().getAppCompatActivity(), "Progress");

                    Map<String, Object> objectMap = new LinkedHashMap<String, Object>();
                    objectMap.put("class_id", instructorDetailModelArrayList.get(0).getModelClasses().get(position).getClassesId());

                    JsonCaller.getInstance().getInstructorClassDetail(objectMap);
//                    ((HomeActivity) context)
//                            .getSupportFragmentManager()
//                            .beginTransaction()
//                            .replace(R.id.content_frame, new ClassDetailFragment(), Constant.classDetailFragment)
//                            .addToBackStack(((HomeActivity) context).getSupportFragmentManager().getClass().getName())
//                            .commit();
                }
            }
        });

        return convertView;
    }

    public static class ViewHolder {
        TextView className, colorText;
        LinearLayout classLayout;
        ImageButton imgButton;
    }
}
