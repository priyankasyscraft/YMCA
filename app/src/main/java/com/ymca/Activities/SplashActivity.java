package com.ymca.Activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.ymca.Activities.*;
import com.ymca.Activities.HomeActivity;
import com.ymca.AppManager.DataManager;
import com.ymca.AppManager.MarshMallowPermission;
import com.ymca.R;

import java.text.DateFormat;
import java.util.Date;

/**
 * Created by Soni on 28-Jul-16.
 */
public class SplashActivity extends BaseActivity {

    private long SPLASH_DISPLAY_LENGTH = 2000;

    MarshMallowPermission marshMallowPermission;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        marshMallowPermission = new MarshMallowPermission(this);



        if (marshMallowPermission.checkPermissionsLocation()) {
            if (marshMallowPermission.checkPermissionsExternalStorage()) {
                setUpPane();
            } else {
                marshMallowPermission.requestPermissionForExternalStorage();
            }
        } else {
            marshMallowPermission.requestPermissionForLocation();
        }
    }



    private void setUpPane() {

        if (marshMallowPermission.checkPermissionsLocation()) {
            if (marshMallowPermission.checkPermissionsExternalStorage()) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                        Intent mainIntent = new Intent(SplashActivity.this, HomeActivity.class);
                        startActivity(mainIntent);
                        finish();
                    }
                }, SPLASH_DISPLAY_LENGTH);
            } else {
                marshMallowPermission.requestPermissionForExternalStorage();
            }
        } else {
            marshMallowPermission.requestPermissionForLocation();
        }

    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MarshMallowPermission.LOCATION_REQUEST_PERMISSION:
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    if (marshMallowPermission.checkPermissionsExternalStorage()) {
                        setUpPane();
                    } else {
                        marshMallowPermission.requestPermissionForExternalStorage();
                    }

                } else {
                    if (marshMallowPermission.checkPermissionsLocation()) {
                        if (marshMallowPermission.checkPermissionsExternalStorage()) {
                            setUpPane();
                        } else {
                            marshMallowPermission.requestPermissionForExternalStorage();
                        }
                    } else {
                        marshMallowPermission.requestPermissionForLocation();
                    }
                }
                break;
            case MarshMallowPermission.WRITE_EXTERNAL_STORAGE_PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

//                    if(marshMallowPermission.checkPermissionsExternalStorage()){
                    setUpPane();
//                    }else {
//                        marshMallowPermission.requestPermissionForExternalStorage();
//                    }

                } else {
                    if (marshMallowPermission.checkPermissionsLocation()) {
                        if (marshMallowPermission.checkPermissionsExternalStorage()) {
                            setUpPane();
                        } else {
                            marshMallowPermission.requestPermissionForExternalStorage();
                        }
                    } else {
                        marshMallowPermission.requestPermissionForLocation();
                    }
                }
                break;
        }
    }




}
