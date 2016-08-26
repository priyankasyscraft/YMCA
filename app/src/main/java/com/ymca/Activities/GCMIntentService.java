package com.ymca.Activities;


import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gcm.GCMBaseIntentService;
import com.ymca.Constants.Constant;
import com.ymca.R;


public class GCMIntentService extends GCMBaseIntentService {

    public static final int NOTIFICATION_ID = 1;
    String message = "";
    private NotificationManager mNotificationManager;
    String type = "";
    //Intent notificationIntent;
    private static final String TAG = "GCMIntentService";
    boolean background = false;
    String app_id, list_type, title;
    boolean sent = false;
    int icon;
    long when;

    public GCMIntentService() {
        super(Constant.SENDER_ID);
    }

    /**
     * Method called on device registered
     */
    @Override
    protected void onRegistered(Context context, String registrationId) {

        // Constants.resIdNotification = registrationId;
        CommonUtilities.displayMessage(context,
                "Your device registred with GCM");
        /*SharedPrefrnceThings2Do.setDataInSharedPrefrence(context,
                Constants.DEVICE_TOKEN, registrationId);*/

    }

    /**
     * Method called on device un registred
     */
    @Override
    protected void onUnregistered(Context context, String registrationId) {

        CommonUtilities.displayMessage(context,
                getString(R.string.gcm_unregistered));

    }

    /**
     * Method called on Receiving a new message
     */
    @Override
    protected void onMessage(Context context, Intent intent) {
        Log.e("NotificationResponse", "Received message");
        Bundle bn = intent.getExtras();
        message = bn.getString("alert");
        type = bn.getString("notification_type");

//        if (type.equals("Appointment")) {
//            app_id = bn.getString("app_id");
//            list_type = bn.getString("list_type");
//            if (list_type.equalsIgnoreCase("received")) {
//                sent = false;
//            } else {
//                sent = true;
//            }
//        } else if (type.equalsIgnoreCase("Release")) {
//            app_id = bn.getString("app_id");
//        }
        // }
        WakeLocker.acquire(context);
        WakeLocker.release();
        CommonUtilities.displayMessage(context, message);
        generateNotification(context, message);
    }

    @Override
    protected void onDeletedMessages(Context context, int total) {

        String message = getString(R.string.gcm_deleted, total);
        CommonUtilities.displayMessage(context, message);
    }

    @Override
    public void onError(Context context, String errorId) {

        CommonUtilities.displayMessage(context,
                getString(R.string.gcm_error, errorId));
    }

    @Override
    protected boolean onRecoverableError(Context context, String errorId) {
        // log message

        CommonUtilities.displayMessage(context,
                getString(R.string.gcm_recoverable_error, errorId));
        return super.onRecoverableError(context, errorId);
    }

    @SuppressWarnings("deprecation")
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @SuppressLint("NewApi")
    private void generateNotification(Context context, String message) {

        icon = R.mipmap.app_icon;
        when = System.currentTimeMillis();
//        SharedPreferences sharedPreferences = getSharedPreferences(Constant.TEMP_PREF_NAME, Activity.MODE_PRIVATE);
//        int flagValue = sharedPreferences.getInt(Constant.appNotification, 2);
//        if (flagValue != 0) {
            sendNotification(message);
//        } else {
//            Toast.makeText(getApplicationContext(), "App Notification off", Toast.LENGTH_SHORT).show();
//        }
    }

    private void sendNotification(String msg) {



//        String cons = sharedPreferences1.getString(Constant.CONS_ID, "");

//        PackageManager pm = getPackageManager();
        Intent launchIntent = new Intent(getApplicationContext(), SplashActivity.class);
//        launchIntent.putExtra("switch_notif_frag", "notify_frag");
//        Intent launchIntent = pm.getLaunchIntentForPackage(getPackageName());
        Log.d(">>>>>>>>", "" + getPackageName());

//        Intent intent  = new Intent(DataManager.getInstance().getActivity(),MainActivity.class);

        launchIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
//        intent.setFlags(Intent.FLAG_CLEAR_TOP);
        PendingIntent contentIntent = PendingIntent.getActivity(getApplicationContext(), 0, launchIntent, PendingIntent.FLAG_UPDATE_CURRENT);


        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(icon)
                .setContentTitle("Dealici")
                .setVibrate(new long[]{10000, 10000})
                .setStyle(new NotificationCompat.BigTextStyle().bigText(message))
                .setContentText(msg)
                .setWhen(when)
                .setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE)
                .setContentIntent(contentIntent)
                .setAutoCancel(true);
//        JsonCaller.getInstance().getNotificationBadgeData(cons);
//        SharedPreferences sharedPreferences1 = getSharedPreferences(Constant.TEMP_PREF_NAME, Activity.MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPreferences1.edit();
//        editor.putString(Constant.NOTIFICATION_VALUE, "1");
//        editor.apply();
        mNotificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
    }
}
