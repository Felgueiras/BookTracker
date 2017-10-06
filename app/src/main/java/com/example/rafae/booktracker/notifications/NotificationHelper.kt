/*
 * Copyright 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.notificationchannels

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.support.annotation.RequiresApi
import com.example.rafae.booktracker.DrawerActivity
import com.example.rafae.booktracker.R

import java.util.Random

/**
 * Helper class to manage notification channels, and create notifications.
 */
@RequiresApi(Build.VERSION_CODES.O)
internal class NotificationHelper (context: Context) : ContextWrapper(context) {

    companion object {
        val ELAPSED_TIME_CHANNEL = "follower"
        val DIRECT_MESSAGE_CHANNEL = "message"
    }

    private val mNotificationManager: NotificationManager by lazy {
        getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }

    /**
     * Registers notification channels, which can be used later by individual notifications.
     */
    init {

       // Create the channel object with the unique ID ELAPSED_TIME_CHANNEL
        val followersChannel = NotificationChannel(
                ELAPSED_TIME_CHANNEL,
                getString(R.string.notification_channel_followers),
                NotificationManager.IMPORTANCE_DEFAULT)

        // Configure the channel's initial settings
        followersChannel.lightColor = Color.GREEN
//        followersChannel.vibrationPattern = longArrayOf(100)

        // Submit the notification channel object to the notification manager
        mNotificationManager.createNotificationChannel(followersChannel)
    }

    /**
     * Get a follow/un-follow notification
     *
     * Provide the builder rather than the notification it's self as useful for making
     * notification changes.

     * @param title the elapsedTime of the notification
     * *
     * @param body  the body text for the notification
     * *
     * @return A Notification.Builder configured with the selected channel and details
     */
    fun getNotificationElapsedTime(title: String, body: String): Notification.Builder {
        return Notification.Builder(applicationContext, ELAPSED_TIME_CHANNEL)
                .setContentTitle(title)
                .setContentText(body)
                .setSmallIcon(smallIcon)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
    }


    /**
     * Create a PendingIntent for opening up the MainActivity when the notification is pressed

     * @return A PendingIntent that opens the MainActivity
     */
    private // The stack builder object will contain an artificial back stack for the
            // started Activity.
            // This ensures that navigating backward from the Activity leads out of
            // your application to the Home screen.
            // Adds the back stack for the Intent (but not the Intent itself)
            // Adds the Intent that starts the Activity to the top of the stack
    val pendingIntent: PendingIntent
        get() {
            val openMainIntent = Intent(this, DrawerActivity::class.java)
            val stackBuilder = TaskStackBuilder.create(this)
            stackBuilder.addParentStack(DrawerActivity::class.java)
            stackBuilder.addNextIntent(openMainIntent)
            return stackBuilder.getPendingIntent(0, PendingIntent.FLAG_ONE_SHOT)
        }

    /**
     * Send a notification.
     *
     * @param id           The ID of the notification
     * *
     * @param notification The notification object
     */
    fun notify(id: Int, notification: Notification.Builder) {
        mNotificationManager.notify(id, notification.build())
    }

    fun cancelNotification(id: Int) {
        mNotificationManager.cancel(id)
    }

    /**
     * Get the small icon for this app

     * @return The small icon resource id
     */
    private val smallIcon: Int
        get() = android.R.drawable.stat_notify_chat


    /**
     * Get a random name string from resources to add personalization to the notification

     * @return A random name
     */
    val randomName: String
        get() {
            var names = applicationContext.resources.getStringArray(R.array.names_array)
            return names[Random().nextInt(names.size)]
        }


}
