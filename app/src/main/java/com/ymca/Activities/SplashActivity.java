package com.ymca.Activities;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.activate.gcm.CommonUtilities;
import com.google.android.gcm.GCMRegistrar;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.ymca.Activities.*;
import com.ymca.Activities.HomeActivity;
import com.ymca.AppManager.DataManager;
import com.ymca.AppManager.MarshMallowPermission;
import com.ymca.AppManager.SharedPreference;
import com.ymca.Constants.Constant;
import com.ymca.R;
import com.ymca.UserInterFace.Refreshable;
import com.ymca.WebManager.JsonCaller;

import java.text.DateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Created by Soni on 28-Jul-16.
 */
public class SplashActivity extends BaseActivity {

    private long SPLASH_DISPLAY_LENGTH = 2000;

    MarshMallowPermission marshMallowPermission;
    private GoogleCloudMessaging gcm;
    private String regId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 0);
        setContentView(R.layout.activity_splash);
        marshMallowPermission = new MarshMallowPermission(this);


        try {
            GCMRegistrar.checkDevice(SplashActivity.this);
            GCMRegistrar.checkManifest(SplashActivity.this);

            gcm = GoogleCloudMessaging.getInstance(this.getApplicationContext());
            regId = GCMPreference.getRegistrationId(this.getApplicationContext());
            if (regId.isEmpty()) {
                registerGCM();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


        if (marshMallowPermission.checkPermissionsLocation()) {
            if (marshMallowPermission.checkPermissionsExternalStorage()) {
                if (marshMallowPermission.checkPermissionsCall()) {
                    setUpPane();
                } else {
                    marshMallowPermission.requestPermissionForCall();
                }
            } else {
                marshMallowPermission.requestPermissionForExternalStorage();
            }
        } else {
            marshMallowPermission.requestPermissionForLocation();
        }
    }


    private void setUpPane() {

//        if (marshMallowPermission.checkPermissionsLocation()) {
//            if (marshMallowPermission.checkPermissionsExternalStorage()) {
//                if (marshMallowPermission.checkPermissionsCall()) {
        final ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connMgr.getActiveNetworkInfo() != null
                && connMgr.getActiveNetworkInfo().isAvailable()
                && connMgr.getActiveNetworkInfo().isConnected()) {
            DataManager.getInstance().showProgressMessage(this, "Progress");
            Map<String, Object> objectsMap = new LinkedHashMap<>();
            JsonCaller.getInstance().getErrorCode(objectsMap);
        } else {
            try {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Connection Failed");
                builder.setMessage("No Internet Connection, Please try again later.");
                builder.setCancelable(false);
                builder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SplashActivity.this.finish();
                        dialog.dismiss();

                    }
                });
                builder.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
//        boolean isCheck = DataManager.chkStatus(this);
//        if (isCheck) {
//            Map<String, Object> objectsMap = new LinkedHashMap<>();
//            JsonCaller.getInstance().getErrorCode(objectsMap);
//        }
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                /* Create an Intent that will start the Menu-Activity. */
//                Intent mainIntent = new Intent(SplashActivity.this, HomeActivity.class);
//                startActivity(mainIntent);
//                finish();
//            }
//        }, SPLASH_DISPLAY_LENGTH);

//                } else {
//                    marshMallowPermission.requestPermissionForCall();
//                }
//            } else {
//                marshMallowPermission.requestPermissionForExternalStorage();
//            }
//        } else {
//            marshMallowPermission.requestPermissionForLocation();
//        }

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
                        if (marshMallowPermission.checkPermissionsCall()) {
                            setUpPane();
                        } else {
                            marshMallowPermission.requestPermissionForCall();
                        }
                    } else {
                        marshMallowPermission.requestPermissionForExternalStorage();
                    }

                } else if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                    if (marshMallowPermission.checkPermissionsExternalStorage()) {
                        if (marshMallowPermission.checkPermissionsCall()) {
                            setUpPane();
                        } else {
                            marshMallowPermission.requestPermissionForCall();
                        }
                    } else {
                        marshMallowPermission.requestPermissionForExternalStorage();
                    }
                } else {
                    if (marshMallowPermission.checkPermissionsLocation()) {
                        if (marshMallowPermission.checkPermissionsExternalStorage()) {
                            if (marshMallowPermission.checkPermissionsCall()) {
                                setUpPane();
                            } else {
                                marshMallowPermission.requestPermissionForCall();
                            }
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
                    if (marshMallowPermission.checkPermissionsCall()) {
                        setUpPane();
                    } else {
                        marshMallowPermission.requestPermissionForCall();
                    }
//                    }else {
//                        marshMallowPermission.requestPermissionForExternalStorage();
//                    }

                } else if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
//                    if (marshMallowPermission.checkPermissionsExternalStorage()) {
                    if (marshMallowPermission.checkPermissionsCall()) {
                        setUpPane();
                    } else {
                        marshMallowPermission.requestPermissionForCall();
                    }
//                    } else {
//                        marshMallowPermission.requestPermissionForExternalStorage();
//                    }
                } else {
                    if (marshMallowPermission.checkPermissionsLocation()) {
                        if (marshMallowPermission.checkPermissionsExternalStorage()) {
                            if (marshMallowPermission.checkPermissionsCall()) {
                                setUpPane();
                            } else {
                                marshMallowPermission.requestPermissionForCall();
                            }
                        } else {
                            marshMallowPermission.requestPermissionForExternalStorage();
                        }
                    } else {
                        marshMallowPermission.requestPermissionForLocation();
                    }
                }
                break;

            case MarshMallowPermission.CALL_PERMISSION:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    setUpPane();
                } else if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                    setUpPane();
                } else {
                    if (marshMallowPermission.checkPermissionsLocation()) {
                        if (marshMallowPermission.checkPermissionsExternalStorage()) {
                            if (marshMallowPermission.checkPermissionsCall()) {
                                setUpPane();
                            } else {
                                marshMallowPermission.requestPermissionForCall();
                            }
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


    public void registerGCM() {
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                try {
                    // call register and save registration ID to preference.
                    String regId = gcm.register(Constant.SENDER_ID);

                    GCMPreference.setRegistrationId(SplashActivity.this.getApplicationContext(), regId);
                    return null;
                } catch (Exception e) {
                    // Error Handling
                    return null;
                }
            }
        }.execute();
    }

    @Override
    public void onRefreshData(Refreshable refreshable, int requestCode) {
        if (requestCode == JsonCaller.REFRESH_CODE_SERVER_ERROR) {
            SplashActivity.this.finish();
        } else if (requestCode == JsonCaller.REFRESH_CODE_ERROR_CODE) {
            final ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

            if (connMgr.getActiveNetworkInfo() != null
                    && connMgr.getActiveNetworkInfo().isAvailable()
                    && connMgr.getActiveNetworkInfo().isConnected()) {
                Map<String, Object> objectsMap = new LinkedHashMap<>();
                objectsMap.put("meta_key", "_nectar_slider_image");
                JsonCaller.getInstance().getSliderImg(objectsMap);
            } else {
                try {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("Connection Failed");
                    builder.setMessage("No Internet Connection, Please try again later.");
                    builder.setCancelable(false);
                    builder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            SplashActivity.this.finish();
                            dialog.dismiss();

                        }
                    });
                    builder.show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }


        } else if (requestCode == JsonCaller.REFRESH_CODE_SLIDER_IMAGES) {
            DataManager.getInstance().hideProgressMessage();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                    Intent mainIntent = new Intent(SplashActivity.this, HomeActivity.class);
                    startActivity(mainIntent);
                    finish();
                }
            }, SPLASH_DISPLAY_LENGTH);


        } else if (requestCode == JsonCaller.REFRESH_CODE_SLIDER_IMAGES_NULL) {
            try {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Connection Failed");
                builder.setMessage("No Internet Connection, Please try again later.");
                builder.setCancelable(false);
                builder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SplashActivity.this.finish();
                        dialog.dismiss();

                    }
                });
                builder.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static class GCMPreference {

        public static String getRegistrationId(Context context) {

            String registrationId = "";
            return registrationId;
        }

        public static void setRegistrationId(Context context, String regId) {
            SharedPreference.setDataInSharedPreference(context, Constant.deviceToken, regId);
            Log.e("regId = ", "+++++++++++" + regId);
        }
    }
}
