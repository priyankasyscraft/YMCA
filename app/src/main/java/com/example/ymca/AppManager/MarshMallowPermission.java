package com.example.ymca.AppManager;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;


/**
 * Created by on 17-12-2015.
 */
public class MarshMallowPermission {
    public static final int LOCATION_REQUEST_PERMISSION = 1;
    public static final int WRITE_EXTERNAL_STORAGE_PERMISSION_REQUEST_CODE = 2;

    Activity activity;

    public MarshMallowPermission(Activity activity) {
        this.activity = activity;
    }


    // TODO: 30-Jul-16 Access Coarse location

    public boolean checkPermissionsLocation() {
        int result = ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION);
        if (result == PackageManager.PERMISSION_GRANTED ) {
            return true;
        } else {
            return false;
        }
    }


    // TODO: 30-Jul-16 Request Coarse location
    public void requestPermissionForLocation() {
        ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, LOCATION_REQUEST_PERMISSION);
    }


    // TODO: 30-Jul-16  WRITE_EXTERNAL_STORAGE

    public boolean checkPermissionsExternalStorage() {
        int result = ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (result == PackageManager.PERMISSION_GRANTED ) {
            return true;
        } else {
            return false;
        }
    }


    // TODO: 30-Jul-16 Request WRITE_EXTERNAL_STORAGE
    public void requestPermissionForExternalStorage() {
        ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, LOCATION_REQUEST_PERMISSION);
    }
}
