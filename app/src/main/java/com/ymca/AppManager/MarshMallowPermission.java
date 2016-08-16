package com.ymca.AppManager;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;


/**
 * Created by on 17-12-2015.
 */
public class MarshMallowPermission {
    public static final int LOCATION_REQUEST_PERMISSION = 1;
    public static final int WRITE_EXTERNAL_STORAGE_PERMISSION_REQUEST_CODE = 2;
    public static final int CALL_PERMISSION = 3;

    Activity activity;

    public MarshMallowPermission(Activity activity) {
        this.activity = activity;
    }


    // TODO: 30-Jul-16 Access Coarse location

    public boolean checkPermissionsLocation() {
        int result = ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }


    // TODO: 30-Jul-16 Request Coarse location
    public void requestPermissionForLocation() {
//        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.ACCESS_COARSE_LOCATION)) {
//            //     Toast.makeText(activity, "Microphone permission needed for recording. Please allow in App Settings for additional functionality.", Toast.LENGTH_LONG).show();
//            showPopUp(DataManager.getInstance().getAppCompatActivity(), "Alert", "This Permission is required because app need your location for accessing app and please don't select never ask again.");
//        } else {
//            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, LOCATION_REQUEST_PERMISSION);
//        }
        ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, LOCATION_REQUEST_PERMISSION);
    }


    // TODO: 30-Jul-16  WRITE_EXTERNAL_STORAGE

    public boolean checkPermissionsExternalStorage() {
        int result = ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }


    // TODO: 30-Jul-16 Request WRITE_EXTERNAL_STORAGE
    public void requestPermissionForExternalStorage() {

//        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE)){
//            //     Toast.makeText(activity, "Microphone permission needed for recording. Please allow in App Settings for additional functionality.", Toast.LENGTH_LONG).show();
//        } else {
//            ActivityCompat.requestPermissions(activity,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},WRITE_EXTERNAL_STORAGE_PERMISSION_REQUEST_CODE);
//        }

        ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, WRITE_EXTERNAL_STORAGE_PERMISSION_REQUEST_CODE);
    }


    // TODO: 12-Aug-16 Check Permission for call
    public boolean checkPermissionsCall() {
        int result = ContextCompat.checkSelfPermission(activity, Manifest.permission.CALL_PHONE);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    public void requestPermissionForCall() {
//        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.CALL_PHONE)){
//            //     Toast.makeText(activity, "Microphone permission needed for recording. Please allow in App Settings for additional functionality.", Toast.LENGTH_LONG).show();
//        } else {
//            ActivityCompat.requestPermissions(activity,new String[]{Manifest.permission.CALL_PHONE},CALL_PERMISSION);
//        }
        ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.CALL_PHONE}, CALL_PERMISSION);
    }

    public void showPopUp(Context context, String title, String msg) {
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle(title);
            builder.setMessage(msg);
            builder.setCancelable(false);
            builder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {

                    dialog.dismiss();

                }
            });
            builder.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
