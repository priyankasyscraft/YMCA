package com.ymca.Adapters;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ymca.Activities.HomeActivity;
import com.ymca.AppManager.DataManager;
import com.ymca.Constants.Constant;
import com.ymca.Fragments.InstructorDetailFragment;
import com.ymca.R;
import com.ymca.ImageCache.ImageLoader;
import com.ymca.ModelClass.InstructorModelClass;
import com.ymca.WebManager.JsonCaller;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Jack-Sparrow on 7/31/2015.
 */
public class InstructorAdapter extends RecyclerView.Adapter<InstructorAdapter.RecyclerViewHolders> {

    private final ImageLoader imageLoader;
    private List<InstructorModelClass> itemList;
    private Context context;

    public InstructorAdapter(Context context, List<InstructorModelClass> itemList) {
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
        if(!itemList.get(position).getInstructorImg().equals("null") || !itemList.get(position).getInstructorImg().equals("")) {
            Glide.with(context).load(itemList.get(position).getInstructorImg()).into(holder.instructorImg).onLoadCleared(context.getResources().getDrawable(R.mipmap.user_default));
        }
//        imageLoader.DisplayImage(itemList.get(position).getInstructorImg(), holder.traineeImg);
    }

    @Override
    public int getItemCount() {
        return this.itemList.size();
    }

    public class RecyclerViewHolders extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView instructorName;
        public ImageView instructorImg;

        public RecyclerViewHolders(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            instructorName = (TextView) itemView.findViewById(R.id.userName);
            instructorImg = (ImageView) itemView.findViewById(R.id.userImg);
        }

        @Override
        public void onClick(View view) {

            boolean isCheck = DataManager.chkStatus(context);
            if (isCheck) {

                DataManager.getInstance().setInstructorModelClass(itemList.get(getAdapterPosition()));

                DataManager.getInstance().showProgressMessage((HomeActivity) context, "Progress");
                Map<String, Object> objectMap = new LinkedHashMap<>();
                objectMap.put("instructor_id", DataManager.getInstance().getInstructorModelClass().getInstructorId());
                JsonCaller.getInstance().getInstructorDetail(objectMap);
//                ((HomeActivity) context)
//                        .getSupportFragmentManager()
//                        .beginTransaction()
//                        .replace(R.id.content_frame, new InstructorDetailFragment(), Constant.instructorDetailFrag)
//                        .addToBackStack(((HomeActivity) context).getSupportFragmentManager().getClass().getName())
//                        .commit();
            }
        }
    }
}
