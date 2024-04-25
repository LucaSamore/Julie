package com.example.data.statistics.implementation

import android.app.Notification
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import android.util.Log
import com.example.data.statistics.NotificationDao
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDate
import java.util.UUID
import javax.inject.Inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NotificationListener : NotificationListenerService() {

    @Inject lateinit var notificationDao: NotificationDao
    private val job = SupervisorJob()
    private val scope = CoroutineScope(Dispatchers.IO + job)

    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "NotificationListenerService created")
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
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

        val notificationReceived =
            Notification(
                id = UUID.randomUUID().toString(),
                packageName = sbn.packageName,
                date = LocalDate.now().toString()
            )

        scope.launch { notificationDao.insert(notificationReceived) }

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
