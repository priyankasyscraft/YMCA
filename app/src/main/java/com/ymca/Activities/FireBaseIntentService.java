package com.ymca.Activities;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.RemoteInput;
import android.util.Log;
import android.widget.RemoteViews;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.ymca.R;

/**
 * Created by Syscraft on 7/19/2016.
 */
public class FireBaseIntentService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";
    private static String REPLY_ACTION;
    private static String CONVERSATION_LABEL;
    private String KEY_TEXT_REPLY;
    private int REPLY_INTENT_ID = 1;
    private String LABEL_REPLY;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        //Displaying data in log
        //It is optional
        Log.d(TAG, "From: " + remoteMessage.getFrom());
        Log.d(TAG, "Notification Message Body: " + remoteMessage.getNotification().getBody());

        //Calling method to generate notification

        sendNotification(remoteMessage.getNotification().getBody());
    }

    //This method is only generating push notification
    //It is same as we did in earlier posts

    private void sendNotification(String messageBody) {

        final String KEY_TEXT_REPLY = "key_text_reply";


        Intent intent = new Intent(this, HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent replyIntent = PendingIntent.getActivity(this,
                REPLY_INTENT_ID,
                getDirectReplyIntent(this, LABEL_REPLY),
                PendingIntent.FLAG_UPDATE_CURRENT);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);

//        NotificationCompat.Action action =
//                new NotificationCompat.Action.Builder(R.mipmap.ic_launcher,
//                        getString(R.string.label), replyIntent)
//                        .addRemoteInput(remoteInput)
//                        .build();
//        getMessageText(intent);

//        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.notif_custom_view);
//        remoteViews.setImageViewResource(R.id.image_icon, R.mipmap.ic_launcher);
//        remoteViews.setTextViewText(R.id.text_title, "title");
//        remoteViews.setTextViewText(R.id.text_message, "message");
//        remoteViews.setImageViewResource(R.id.image_end, R.drawable.custom_marker);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Firebase Push Notification")
                .setContentText(messageBody)
                .setStyle(new NotificationCompat.BigTextStyle().setSummaryText("Messages content"))
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(replyIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0, notificationBuilder.build());
    }

    private CharSequence getMessageText(Intent intent) {
        Bundle remoteInput = RemoteInput.getResultsFromIntent(intent);
        if (remoteInput != null) {
            return remoteInput.getCharSequence(KEY_TEXT_REPLY);
        }
        return null;
    }
    private Intent getDirectReplyIntent(Context context, String label) {
        Intent intent = new Intent(context,FireBaseIntentService.class);
        intent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES)
                .setAction(REPLY_ACTION)
                .putExtra(CONVERSATION_LABEL, label);
        return intent;

    }

}
