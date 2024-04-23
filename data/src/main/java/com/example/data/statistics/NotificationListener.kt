package com.example.data.statistics

import android.app.Notification
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import android.util.Log
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class NotificationListener : NotificationListenerService() {

    @Inject lateinit var notificationDao: NotificationDao

    override fun onCreate() {
        super.onCreate()
        // keep this
        Log.d(TAG, "NotificationListenerService created")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "NotificationListenerService destroyed")
    }

    override fun onNotificationPosted(sbn: StatusBarNotification) {
        super.onNotificationPosted(sbn)
        // Some app send a group summary notification alongside other notifications.
        // That’s why for some app you get this method called twice.
        // The related flag is documented here:
        // https://developer.android.com/reference/android/app/Notification.html#FLAG_GROUP_SUMMARY
        // To fix this we can simply ignore any notification with that flag

        if ((sbn.notification.flags and Notification.FLAG_GROUP_SUMMARY) != 0) {
            return
        }

        Log.d(TAG, "Notification posted: ${sbn.packageName} - ${sbn.notification}")
    }

    override fun onNotificationRemoved(sbn: StatusBarNotification) {
        super.onNotificationRemoved(sbn)
        Log.d(TAG, "Notification removed: ${sbn.packageName} - ${sbn.notification}")
    }

    companion object {
        private const val TAG = "NotificationListener"
    }
}
