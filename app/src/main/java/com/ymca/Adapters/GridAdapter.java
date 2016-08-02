package com.ymca.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ymca.R;
import com.ymca.AppManager.DataManager;
import com.ymca.ImageCache.ImageLoader;
import com.ymca.ModelClass.InstructorModelClass;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.List;

/**
 * Created by Jack-Sparrow on 7/31/2015.
 */
public class GridAdapter extends RecyclerView.Adapter<GridAdapter.RecyclerViewHolders> {

    private final ImageLoader imageLoader;
    private List<InstructorModelClass> itemList;
    private Context context;

    public GridAdapter(Context context, List<InstructorModelClass> itemList) {
        this.itemList = itemList;
        this.context = context;
        imageLoader = new ImageLoader(context);
    }

    @Override
    public RecyclerViewHolders onCreateViewHolder(ViewGroup parent, int viewType) {

        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.instructor_items, null);
        RecyclerViewHolders rcv = new RecyclerViewHolders(layoutView);
        return rcv;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolders holder, int position) {
        holder.instructorName.setText(itemList.get(position).getInstructorName());
        imageLoader.DisplayImage(itemList.get(position).getInstructorImg(), holder.instructorImg);
    }

    @Override
    public int getItemCount() {
        return this.itemList.size();
    }

    public class RecyclerViewHolders extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView instructorName;
        public ImageView instructorImg;

        public RecyclerViewHolders(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            instructorName = (TextView)itemView.findViewById(R.id.userName);
            instructorImg = (ImageView)itemView.findViewById(R.id.userImg);
        }

        @Override
        public void onClick(View view) {
            Toast.makeText(view.getContext(), "Clicked Country Position = " + getPosition(), Toast.LENGTH_SHORT).show();
        }
    }
}
