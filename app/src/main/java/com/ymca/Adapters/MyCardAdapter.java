package com.ymca.Adapters;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ymca.Activities.HomeActivity;
import com.ymca.AppManager.DataManager;
import com.ymca.Constants.Constant;
import com.ymca.Fragments.CardShowFragment;
import com.ymca.Fragments.InstructorDetailFragment;
import com.ymca.ModelClass.MyCardModelClass;
import com.ymca.R;

import java.util.ArrayList;

/**
 * Created by Soni on 02-Aug-16.
 */
public class MyCardAdapter extends RecyclerView.Adapter<MyCardAdapter.MyViewHolder> {

    private final Context context;
    private ArrayList<MyCardModelClass> myCardModelClasses;

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener {
        public TextView cardUserName, cardBarCodeNo;

        public MyViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);
            view.setOnCreateContextMenuListener(this);
            cardUserName = (TextView) view.findViewById(R.id.cardUserName);
            cardBarCodeNo = (TextView) view.findViewById(R.id.cardBarCodeNo);

        }


        @Override
        public void onClick(View view) {
            DataManager.getInstance().setMemberName(cardUserName.getText().toString());
            DataManager.getInstance().setMemberCardNumber(cardBarCodeNo.getText().toString().replace("CODE: ", ""));
            DataManager.getInstance().setFlagCardShow(true);
            ((HomeActivity) context)
                    .getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.content_frame, new CardShowFragment(), Constant.cardShowFragment)
                    .addToBackStack(((HomeActivity) context).getSupportFragmentManager().getClass().getName())
                    .commit();

        }

        @Override
        public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
            MenuItem myActionItem = contextMenu.add("Delete");
            myActionItem.setOnMenuItemClickListener(this);
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {

            // TODO: 12-Aug-16 remove Item from its position
            myCardModelClasses.remove(getAdapterPosition());
            notifyItemRemoved(getAdapterPosition());
            return true;
        }
    }


    public MyCardAdapter(Context context, ArrayList<MyCardModelClass> myCardModelClasses) {
        this.myCardModelClasses = myCardModelClasses;
        this.context = context;
    }



    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.my_card_items, parent, false);

        return new MyViewHolder(itemView);
    }

    public void setReloadData(boolean shouldReload) {
        myCardModelClasses = DataManager.getInstance().getMyCardModelClasses();
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
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.cardUserName.setText(myCardModelClasses.get(position).getUserName());
        holder.cardBarCodeNo.setText(myCardModelClasses.get(position).getUserBarCodeNumber());
    }

    @Override
    public int getItemCount() {
        return myCardModelClasses.size();
    }
}

