package com.ymca.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ymca.ModelClass.DrawerModel;
import com.ymca.R;

import java.util.ArrayList;


/**
 * Created by Syscraft on 9/19/2015.
 */
public class DrawerAdapter extends BaseAdapter {
    private Context context;
    ArrayList<DrawerModel> drawerModels = new ArrayList<>();

    public DrawerAdapter(Context context, ArrayList<DrawerModel> drawerModels) {
        this.context = context;
        this.drawerModels = drawerModels;
    }

    @Override
    public int getViewTypeCount() {

        return getCount();
    }
    @Override
    public int getItemViewType(int position) {

        return position;
    }
    @Override
    public int getCount() {
        return drawerModels.size();
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
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater mInflater = (LayoutInflater) context
                    .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.drawer_list_item, parent, false);

            viewHolder.txtTitle = (TextView) convertView.findViewById(R.id.drawerTitle);
            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.titleIcon);

            if(drawerModels.get(position).getMenuTextBg()!=0) {
                viewHolder.txtTitle.setBackgroundResource(drawerModels.get(position).getMenuTextBg());
            }
            if (position == 0) {
                viewHolder.imageView.setVisibility(View.GONE);
                viewHolder.txtTitle.setWidth(WindowManager.LayoutParams.MATCH_PARENT);
            } else if (position == 6) {
                viewHolder.imageView.setVisibility(View.GONE);
                viewHolder.txtTitle.setWidth(WindowManager.LayoutParams.MATCH_PARENT);
            } else if (position == 13) {
                viewHolder.imageView.setVisibility(View.GONE);
                viewHolder.txtTitle.setWidth(WindowManager.LayoutParams.MATCH_PARENT);
            } else {
                viewHolder.txtTitle.setPadding(8, 8, 8, 8);
                viewHolder.imageView.setPadding(8, 0, 0, 0);
            }
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.txtTitle.setText(drawerModels.get(position).getMenuName());
        viewHolder.txtTitle.setTextColor(drawerModels.get(position).getMenuColor());


        Typeface myTypeface = Typeface.createFromAsset(context.getAssets(), "fonts/cachet-std-bold.ttf");
        viewHolder.txtTitle.setTypeface(myTypeface);

        viewHolder.imageView.setImageResource(drawerModels.get(position).getMenuImg());
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        params.setMargins(0, 0, 7, 0);
//        viewHolder.txtTitle.setLayoutParams(params);




        return convertView;
    }

    public class ViewHolder {
        TextView txtTitle;
        ImageView imageView;
    }

}
