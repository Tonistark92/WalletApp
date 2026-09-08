package com.iscoding.android.pending

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.iscoding.android.R
import kotlin.jvm.java

class TransferNotificationManager(
    private val context: Context
) {
    companion object {
        const val CHANNEL_ID = "transfer_channel"
        const val CHANNEL_NAME = "Transfer Updates"
        const val CHANNEL_DESCRIPTION = "Notifications for money transfers"
    }

    init {
        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = CHANNEL_DESCRIPTION
                enableVibration(true)
            }

            val notificationManager = context.getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }
    }

    fun showTransferQueued(amount: String, toAccount: String) {
//        showNotification(
//            id = System.currentTimeMillis().toInt(),
//            title = "Transfer Queued",
//            message = "$amount to $toAccount will be sent when you're back online",
////            icon = R.drawable.ic_pending
//            icon = R.drawable.ic_launcher_foreground
//        )
    }

    fun showTransferProcessing(amount: String, toAccount: String) {
//        showNotification(
//            id = System.currentTimeMillis().toInt(),
//            title = "Transfer Processing",
//            message = "Sending $amount to $toAccount...",
//            icon = R.drawable.ic_launcher_foreground,
////            icon = R.drawable.ic_sync,
//            ongoing = true
//        )
    }

    fun showTransferSuccess(amount: String, toAccount: String, transactionId: String) {
//        showNotification(
//            id = transactionId.hashCode(),
//            title = "Transfer Complete",
//            message = "$amount sent to $toAccount",
////            icon = R.drawable.ic_check,
//            icon = R.drawable.ic_launcher_foreground
//
//        )
    }

    fun showTransferFailed(amount: String, toAccount: String, error: String) {
//        showNotification(
//            id = System.currentTimeMillis().toInt(),
//            title = "Transfer Failed",
//            message = "Could not send $amount to $toAccount: $error",
////            icon = R.drawable.ic_error,
//            icon = R.drawable.ic_launcher_foreground
//
//
//        )
    }

    private fun showNotification(
        id: Int,
        title: String,
        message: String,
        icon: Int,
        ongoing: Boolean = false
    ) {
//        val intent = Intent(context, MainActivity::class.java).apply {
//            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
//        }
//
//        val pendingIntent = PendingIntent.getActivity(
//            context,
//            0,
//            intent,
//            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
//        )
//
//        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
//            .setSmallIcon(icon)
//            .setContentTitle(title)
//            .setContentText(message)
//            .setPriority(NotificationCompat.PRIORITY_HIGH)
//            .setAutoCancel(true)
//            .setOngoing(ongoing)
//            .setContentIntent(pendingIntent)
//            .build()
//
//        val notificationManager = context.getSystemService(NotificationManager::class.java)
//        notificationManager.notify(id, notification)
    }
}