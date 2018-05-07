package com.softwareproject.focus.Notification;

/**
 * Created by Amjad on 06/04/18.
 */

import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import java.util.Arrays;
import java.util.HashSet;


public class NotificationService extends NotificationListenerService {

    @Override
    public void onNotificationPosted(StatusBarNotification notification) {

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(NotificationService.this);

       // if (preferences.getBoolean(Utils.PREF_ENABLED, false)) {
           HashSet<String> blocked = new HashSet<>(Arrays.asList(preferences.getString
                   (Utils.PREF_PACKAGES_BLOCKED, "").split(";")));
           if (blocked.contains(notification.getPackageName())) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                cancelNotification(notification.getKey());
                }
           } else {
               cancelNotification(notification.getPackageName(), notification.getTag(), notification.getId());
           }
        //}
    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
    }

}
