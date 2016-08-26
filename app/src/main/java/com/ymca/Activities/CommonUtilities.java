package com.ymca.Activities;

import android.content.Context;
import android.content.Intent;

public final class CommonUtilities {
    // Tag used on log messages.
    public static String TAG = "Check Address Notification";
    public static String DISPLAY_MESSAGE_ACTION = ".notificationdemo.DISPLAY_MESSAGE";
    public static String EXTRA_MESSAGE = "message";

    /**
     * Notifies UI to display a message.
     * <p/>
     * This method is defined in the common helper because it's used both by the
     * UI and the background service.
     *
     * @param context application's context.
     * @param message message to be displayed.
     */
    public static void displayMessage(Context context, String message) {
        Intent intent = new Intent(context.getPackageName() + DISPLAY_MESSAGE_ACTION);
        intent.putExtra(EXTRA_MESSAGE, message);
        context.sendBroadcast(intent);
    }
}
