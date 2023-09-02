package com.trianglz.mimar.modules.notification.presentation.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.trianglz.core.domain.helper.timber
import com.trianglz.mimar.R
import com.trianglz.mimar.modules.home.presentation.HomeActivity
import com.trianglz.mimar.modules.notification.domain.manager.ForegroundNotificationManager
import com.trianglz.mimar.modules.user.data.local.UserLocalDataSource
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MimarMessagingService : FirebaseMessagingService() {

    @Inject
    lateinit var userLocalDataSource: UserLocalDataSource

    companion object {
        const val FOREGROUND_KEY = "foreground_notifications_key"
        const val IN_APP_MESSAGE_CHANNEL_ID = "in_app_message_channel_id"
        const val IN_APP_MESSAGE_NOTIFICATION_ID = 88
        const val IN_APP_UPDATE_CHANNEL_ID = "in_app_update"
        const val IN_APP_UPDATE_TAG = "in_app_update_tag"
        const val IN_APP_UPDATE_NOTIFICATION_ID = 89
    }

    /**
     * Called if InstanceID token is updated. This may occur if the security of
     * the previous token had been compromised. Note that this is called when the InstanceID token
     * is initially generated so this is where you would retrieve the token.
     */
    override fun onNewToken(p0: String) {
        super.onNewToken(p0)
        // TODO: send the refreshed token to backend
//        ForegroundRefreshChannel.broadcastNewFCMToken(p0)
//        Timber.tag("fcmtokendebug").d("new token is $p0")
    }

    override fun onMessageReceived(p0: RemoteMessage) {
        super.onMessageReceived(p0)
        timber { "Notifications: onMessageReceived: ${p0.toIntent()}" }


        val intent = p0.toIntent() ?: Intent(FOREGROUND_KEY)
        intent.action = FOREGROUND_KEY

        createInAppUpdatesNotification(intent)
        if (userLocalDataSource.checkIfUserLoggedIn()) {
            intent.putExtra(FOREGROUND_KEY, true)
            intent.extras?.let {
                val homeIntent = Intent(this, HomeActivity::class.java)
                homeIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                homeIntent.putExtras(it)
                startActivity(homeIntent)
            }
        }
    }

    private fun createInAppUpdatesNotification(intent: Intent): Boolean {
        val mExtras = intent.extras ?: return false
        val message = mExtras.getString("gcm.notification.body", "")
        val title = mExtras.getString("gcm.notification.title", "")
        val pushIntent = Intent(application, HomeActivity::class.java)
//        val pushIntent = Intent(application, if (AppPreferences.isLoggedIn) HomeActivity::class.java else SplashActivity::class.java)
        pushIntent.putExtras(mExtras)
        val pendingIntent =
            PendingIntent.getActivity(
                this, 0, pushIntent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
        val builder = NotificationCompat.Builder(this, IN_APP_UPDATE_CHANNEL_ID)
            .setSmallIcon(R.mipmap.ic_launcher)
//            .setColor(resources.getColor(com.trianglz.core.R.color.transparent, null))
            .setContentTitle(title)
            .setContentIntent(pendingIntent)
            .setContentText(message)
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        val actionId = mExtras.getString(ForegroundNotificationManager.ACTION_ID)
        val clickAction = mExtras.getString(ForegroundNotificationManager.CLICK_ACTION)
        val notificationManager = NotificationManagerCompat.from(this)
//        notificationManager.notify(IN_APP_UPDATE_TAG, IN_APP_UPDATE_NOTIFICATION_ID,builder.build())
        notificationManager.notify(
            "${clickAction}_${actionId}",
            actionId.hashCode(),
            builder.build()
        )
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
        createUpdatesChannel()
    }

    private fun createUpdatesChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name: CharSequence = "In app updates"
            val description = "In app updates"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel =
                NotificationChannel(IN_APP_UPDATE_CHANNEL_ID, name, importance)
            channel.description = description
            val notificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as? NotificationManager
            if (notificationManager?.getNotificationChannel(IN_APP_UPDATE_CHANNEL_ID)?.importance != importance) {
                notificationManager?.deleteNotificationChannel(IN_APP_UPDATE_CHANNEL_ID)
            }
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            notificationManager?.createNotificationChannel(channel)
        }
    }

    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "In App Messaging"
            val description = ""
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(
                IN_APP_MESSAGE_CHANNEL_ID,
                name,
                importance
            )
            channel.description = description
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            val notificationManager: NotificationManager? = getSystemService<NotificationManager>(
                NotificationManager::class.java
            )
            notificationManager?.createNotificationChannel(channel)
        }
    }
}

