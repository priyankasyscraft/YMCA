package com.ymca.Activities;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Window;
import android.view.WindowManager;

import com.ymca.AppManager.DataManager;
import com.ymca.UserInterFace.RefreshDataListener;
import com.ymca.WebManager.JsonCaller;


public abstract class BaseActivity extends FragmentActivity implements RefreshDataListener {
    final String TAG = "BaseActivity";
    private boolean isCheck = false;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


            requestWindowFeature(Window.FEATURE_NO_TITLE);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 0);
            DataManager.getInstance().setAppCompatActivity(this);

            JsonCaller.getInstance().setRefreshDataListener(this);
    }
}
