package com.ymca.AppManager;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.ymca.ModelClass.EventModelClass;
import com.ymca.ModelClass.InstructorModelClass;
import com.ymca.ModelClass.MyCardModelClass;
import com.ymca.R;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Soni on 28-Jul-16.
 */
public class DataManager {
    private static DataManager ourInstance = new DataManager();
    private Dialog mDialog;

    public static DataManager getInstance() {
        return ourInstance;
    }

    private DataManager() {
    }

    private ImageLoader imageLoader;
    private Activity appCompatActivity;
    private boolean isProgressDialogRunning = false;

    private ArrayList<InstructorModelClass> instructorModelClassArrayList = new ArrayList<>();
    private ArrayList<EventModelClass> eventModelClasses = new ArrayList<>();
    private ArrayList<MyCardModelClass> myCardModelClasses = new ArrayList<>();

    public static boolean chkStatus() {
        // TODO Auto-generated method stub
        final ConnectivityManager connMgr = (ConnectivityManager) getInstance().getAppCompatActivity()
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connMgr.getActiveNetworkInfo() != null
                && connMgr.getActiveNetworkInfo().isAvailable()
                && connMgr.getActiveNetworkInfo().isConnected()) {

            return true;
        } else {
            try {
                AlertDialog.Builder builder = new AlertDialog.Builder(getInstance().getAppCompatActivity());
                builder.setTitle("Alert");
                builder.setMessage("No Internet Connection");
                builder.setCancelable(false);
                builder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getInstance().hideProgressMessage();
                        dialog.dismiss();

                    }
                });
                builder.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return false;
        }
    }

    public Activity getAppCompatActivity() {
        return appCompatActivity;
    }

    public void setAppCompatActivity(Activity appCompatActivity) {
        this.appCompatActivity = appCompatActivity;
    }


    // region progress dialog..
    public void showProgressMessage(Activity dialogActivity, String msg) {
        if (isProgressDialogRunning) {
            hideProgressMessage();
        }
        isProgressDialogRunning = true;
        this.appCompatActivity = dialogActivity;
        mDialog = new Dialog(appCompatActivity, android.R.style.Theme_Translucent_NoTitleBar);
        // mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mDialog.setContentView(R.layout.dialog_loading);

        TextView textView = (TextView) mDialog.findViewById(R.id.textView);
        textView.setText(Html.fromHtml(msg));

        WindowManager.LayoutParams lp = mDialog.getWindow().getAttributes();
        lp.dimAmount = 0.8f;

        mDialog.getWindow().setAttributes(lp);
        mDialog.getWindow()
                .addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);

        mDialog.setCancelable(false);
        mDialog.setCanceledOnTouchOutside(false);
        try {
            mDialog.show();

        } catch (Exception ex) {
            ex.printStackTrace();
        } catch (Error ex) {
            ex.printStackTrace();
        }

    }

    public void hideMessage() {
        if (mDialog != null)
            mDialog.dismiss();
    }


    public void hideProgressMessage() {
        isProgressDialogRunning = true;
        try {
            if (mDialog != null)
                mDialog.dismiss();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void uniImageLoader(Context context, String imgUrl, ImageView imageView) {
        imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(context));
        imageLoader.displayImage(imgUrl, imageView);
    }

    public ArrayList<InstructorModelClass> getInstructorModelClassArrayList() {
        return instructorModelClassArrayList;
    }

    public void setInstructorModelClassArrayList(ArrayList<InstructorModelClass> instructorModelClassArrayList) {
        this.instructorModelClassArrayList = instructorModelClassArrayList;
    }

    public void addInstructorModelClassArrayList(InstructorModelClass instructorModelClassArrayList) {
        this.instructorModelClassArrayList.add(instructorModelClassArrayList);
    }

    public void clearInstructorModelClassArrayList() {
        this.instructorModelClassArrayList.clear();
    }

    public ArrayList<EventModelClass> getEventModelClasses() {
        return eventModelClasses;
    }

    public void setEventModelClasses(ArrayList<EventModelClass> eventModelClasses) {
        this.eventModelClasses = eventModelClasses;
    }

    public void addEventModelClasses(EventModelClass eventModelClasses) {
        this.eventModelClasses.add(eventModelClasses);
    }

    public void clearEventModelClasses() {
        this.eventModelClasses.clear();
    }

    public ArrayList<MyCardModelClass> getMyCardModelClasses() {
        return myCardModelClasses;
    }

    public void setMyCardModelClasses(ArrayList<MyCardModelClass> myCardModelClasses) {
        this.myCardModelClasses = myCardModelClasses;
    }

    public void addMyCardModelClasses(MyCardModelClass myCardModelClasses) {
        this.myCardModelClasses.add(myCardModelClasses);
    }

    public void clearMyCardModelClasses() {
        this.myCardModelClasses.clear();
    }
}
