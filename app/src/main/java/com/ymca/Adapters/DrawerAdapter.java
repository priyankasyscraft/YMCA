package com.ymca.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ymca.R;


/**
 * Created by Syscraft on 9/19/2015.
 */
public class DrawerAdapter extends BaseAdapter {
    TextView txtTitle;
    private Context context;
    private String[] navDrawerItems;
    private int[] navDrawerImg;
    private int[] color;
    private int[] state;
    private ColorStateList colorStateList;
    Typeface face;

    public DrawerAdapter(Context context, String[] navDrawerItems, int[] navDrawerImg, int[] color) {
        this.context = context;
        this.navDrawerItems = navDrawerItems;
        this.navDrawerImg = navDrawerImg;
        this.color = color;
    }

    @Override
    public int getCount() {
        return navDrawerItems.length;
    }

    @Override
    public Object getItem(int position) {
        return navDrawerItems[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater) context
                    .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.drawer_list_item, parent,
                    false);
        }

        ImageView imageView = (ImageView)convertView.findViewById(R.id.titleIcon);
//        LinearLayout drawerLayout = (LinearLayout)convertView.findViewById(R.id.drawerLayout);
        txtTitle = (TextView) convertView.findViewById(R.id.drawerTitle);
        txtTitle.setTypeface(face);
        txtTitle.setText(navDrawerItems[position]);
        txtTitle.setTextColor(color[position]);


        Typeface myTypeface = Typeface.createFromAsset(context.getAssets(), "fonts/cachet-std-bold.ttf");
        txtTitle.setTypeface(myTypeface);

        imageView.setImageResource(navDrawerImg[position]);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        params.setMargins(0,0,7,0);

        if(position==0){
            txtTitle.setBackgroundResource(R.drawable.bg_leftmenu_heading);
            txtTitle.setClickable(false);
            txtTitle.setPadding(10,0,0,0);
            txtTitle.setLayoutParams(params);
            imageView.setVisibility(View.GONE);
            txtTitle.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);

        }else if(position==3){
            txtTitle.setBackgroundResource(R.drawable.bg_leftmenu_heading);
            txtTitle.setClickable(false);
            txtTitle.setPadding(10,0,0,0);
            txtTitle.setLayoutParams(params);
            txtTitle.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
            imageView.setVisibility(View.GONE);
        }else if(position==11){
            txtTitle.setBackgroundResource(R.drawable.bg_leftmenu_heading);
            txtTitle.setClickable(false);
            txtTitle.setLayoutParams(params);
            txtTitle.setPadding(10,0,0,0);

            txtTitle.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
            imageView.setVisibility(View.GONE);
        }else {
            txtTitle.setPadding(8,8,8,8);
            imageView.setPadding(8,0,0,0);
        }
        return convertView;
    }



}
