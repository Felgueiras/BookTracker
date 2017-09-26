package com.example.rafae.booktracker.widgets

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import com.example.rafae.booktracker.R


/**
 * Implementation of App Widget functionality.
 */
class StartStopReading : AppWidgetProvider() {


    private var sessionState: Boolean = false


    private val SESSION_STARTSTOP = "SESSION"

    /**
     * React to update action.
     */
    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        // There may be multiple widgets active, so update all of them
        val N = appWidgetIds.size
        for (i in 0 until N) {
            updateAppWidget(context, appWidgetManager, appWidgetIds[i])
        }

        val remoteViews: RemoteViews
        val watchWidget: ComponentName

        remoteViews = RemoteViews(context.packageName, R.layout.start_stop_reading)
        watchWidget = ComponentName(context, StartStopReading::class.java)

        // set the action
        remoteViews.setOnClickPendingIntent(R.id.appwidget_text, getPendingSelfIntent(context, SESSION_STARTSTOP))
        appWidgetManager.updateAppWidget(watchWidget, remoteViews)
    }

    protected fun getPendingSelfIntent(context: Context, action: String): PendingIntent {
        val intent = Intent(context, javaClass)
        intent.action = action
        return PendingIntent.getBroadcast(context, 0, intent, 0)
    }

    /**
     * Respond to pending intent.
     */
    override fun onReceive(context: Context, intent: Intent) {
        super.onReceive(context, intent)

        when (intent.action) {
            SESSION_STARTSTOP -> {
                // switch session state
                sessionState = !sessionState
                val appWidgetManager = AppWidgetManager.getInstance(context)

                val remoteViews: RemoteViews
                val watchWidget: ComponentName

                remoteViews = RemoteViews(context.packageName, R.layout.start_stop_reading)
                watchWidget = ComponentName(context, StartStopReading::class.java!!)

                when (sessionState) {
                    true -> {
                        remoteViews.setTextViewText(R.id.appwidget_text, "STARTED")
                    }
                    false -> {
                        remoteViews.setTextViewText(R.id.appwidget_text, "STOPPED")
                    }
                }




                // set the action
                remoteViews.setOnClickPendingIntent(R.id.appwidget_text, getPendingSelfIntent(context, SESSION_STARTSTOP))
                appWidgetManager.updateAppWidget(watchWidget, remoteViews)
            }
        }
    }


    /**
     * Update the widget
     */
    fun updateAppWidget(context: Context, appWidgetManager: AppWidgetManager,
                        appWidgetId: Int) {

        val widgetText = "Started"
        // Construct the RemoteViews object
        val views = RemoteViews(context.packageName, R.layout.start_stop_reading)
        views.setTextViewText(R.id.appwidget_text, widgetText)

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views)
    }

    override fun onDeleted(context: Context, appWidgetIds: IntArray) {
        // When the user deletes the widget, delete the preference associated with it.
        val N = appWidgetIds.size
        for (i in 0 until N) {
//            MyWidgetConfigureActivity.deleteTitlePref(context, appWidgetIds[i])
        }
    }
}

