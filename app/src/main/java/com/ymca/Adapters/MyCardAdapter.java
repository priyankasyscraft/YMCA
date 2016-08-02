package com.ymca.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ymca.ModelClass.MyCardModelClass;
import com.ymca.R;

import java.util.ArrayList;

/**
 * Created by Soni on 02-Aug-16.
 */
public class MyCardAdapter extends RecyclerView.Adapter<MyCardAdapter.MyViewHolder> {

    private ArrayList<MyCardModelClass> myCardModelClasses;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView cardUserName, cardBarCodeNo;

        public MyViewHolder(View view) {
            super(view);
            cardUserName = (TextView) view.findViewById(R.id.cardUserName);
            cardBarCodeNo = (TextView) view.findViewById(R.id.cardBarCodeNo);

        }
    }


    public MyCardAdapter(ArrayList<MyCardModelClass> myCardModelClasses) {
        this.myCardModelClasses = myCardModelClasses;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.my_card_items, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.cardUserName.setText(myCardModelClasses.get(position).getUserName());
        holder.cardBarCodeNo.setText(myCardModelClasses.get(position).getUserBarCodeNumber());
    }

    @Override
    public int getItemCount() {
        return myCardModelClasses.size();
    }
}





        /*extends BaseAdapter {

    private final LayoutInflater inflater;
    private Context context;
    private ArrayList<MyCardModelClass> myCardModelClasses = new ArrayList<>();
    private ViewHolder viewHolder;

    public MyCardAdapter(Context context, ArrayList<MyCardModelClass> cardModelClassArrayList) {
        this.context = context;
        this.myCardModelClasses = cardModelClassArrayList;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return myCardModelClasses.size();
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
    public View getView(int position, View view, ViewGroup viewGroup) {

        if (view == null) {
            viewHolder = new ViewHolder();
            view = inflater.inflate(R.layout.my_card_items, viewGroup, false);
            viewHolder.cardUserName = (TextView) view.findViewById(R.id.cardUserName);
            viewHolder.cardBarCodeNo = (TextView) view.findViewById(R.id.cardBarCodeNo);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.cardUserName.setText(myCardModelClasses.get(position).getUserName());
        viewHolder.cardBarCodeNo.setText(myCardModelClasses.get(position).getUserBarCodeNumber());

        return view;
    }

    public static class ViewHolder {
        TextView cardUserName, cardBarCodeNo;
    }
}*/
