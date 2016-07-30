package com.example.ymca.Activities;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Window;
import android.view.WindowManager;

import com.example.ymca.AppManager.DataManager;


public abstract class BaseActivity extends FragmentActivity  {
    final String TAG = "BaseActivity";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 0);
        DataManager.getInstance().setAppCompatActivity(this);
//        JsonCaller.getInstance().setRefreshDataListener(this);
    }
}
