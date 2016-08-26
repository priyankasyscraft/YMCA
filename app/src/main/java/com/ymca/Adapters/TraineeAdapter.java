package com.ymca.Adapters;

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
import com.ymca.Fragments.TrainerDetailFragment;
import com.ymca.ImageCache.ImageLoader;
import com.ymca.ModelClass.TraineeModelClass;
import com.ymca.R;
import com.ymca.WebManager.JsonCaller;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Jack-Sparrow on 7/31/2015.
 */
public class TraineeAdapter extends RecyclerView.Adapter<TraineeAdapter.RecyclerViewHolders> {

    private final ImageLoader imageLoader;
    private List<TraineeModelClass> traineeModelClasses;
    private Context context;

    public TraineeAdapter(Context context, List<TraineeModelClass> itemList) {
        this.traineeModelClasses = itemList;
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
        holder.traineeName.setText(traineeModelClasses.get(position).getTraineeName());
        Glide.with(context).load(traineeModelClasses.get(position).getTraineeImg()).into(holder.traineeImg).onLoadCleared(context.getResources().getDrawable(R.mipmap.user_default));
        DataManager.getInstance().hideProgressMessage();
//        imageLoader.DisplayImage(traineeModelClasses.get(position).getInstructorImg(), holder.traineeImg);
    }

    @Override
    public int getItemCount() {
        return this.traineeModelClasses.size();
    }

    public class RecyclerViewHolders extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView traineeName;
        public ImageView traineeImg;

        public RecyclerViewHolders(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            traineeName = (TextView) itemView.findViewById(R.id.userName);
            traineeImg = (ImageView) itemView.findViewById(R.id.userImg);
        }

        @Override
        public void onClick(View view) {
            DataManager.getInstance().setTraineeModelClass(traineeModelClasses.get(getAdapterPosition()));

            DataManager.getInstance().showProgressMessage(DataManager.getInstance().getAppCompatActivity(),"Progress");
            Map<String,Object> objectMap = new LinkedHashMap<>();
            objectMap.put("trainer_id",DataManager.getInstance().getTraineeModelClass().getTraineeId());
            JsonCaller.getInstance().getTrainerDetail(objectMap);

        }
    }
}
