package com.example.rafae.booktracker

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews


/**
 * Implementation of App Widget functionality.
 */
class StartStopReading : AppWidgetProvider() {

    private val SYNC_CLICKED = "automaticWidgetSyncButtonClick"

    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        // There may be multiple widgets active, so update all of them
        val N = appWidgetIds.size
        for (i in 0 until N) {
            updateAppWidget(context, appWidgetManager, appWidgetIds[i])
        }

        val remoteViews: RemoteViews
        val watchWidget: ComponentName

        remoteViews = RemoteViews(context.packageName, R.layout.start_stop_reading)
        watchWidget = ComponentName(context, StartStopReading::class.java!!)

        remoteViews.setOnClickPendingIntent(R.id.appwidget_text, getPendingSelfIntent(context, SYNC_CLICKED))
        appWidgetManager.updateAppWidget(watchWidget, remoteViews)
    }

    protected fun getPendingSelfIntent(context: Context, action: String): PendingIntent {
        val intent = Intent(context, javaClass)
        intent.action = action
        return PendingIntent.getBroadcast(context, 0, intent, 0)
    }

    override fun onReceive(context: Context, intent: Intent) {
        // TODO Auto-generated method stub
        super.onReceive(context, intent)

        if (SYNC_CLICKED == intent.action) {

            val appWidgetManager = AppWidgetManager.getInstance(context)

            val remoteViews: RemoteViews
            val watchWidget: ComponentName

            remoteViews = RemoteViews(context.packageName, R.layout.start_stop_reading)
            watchWidget = ComponentName(context, StartStopReading::class.java!!)

            remoteViews.setTextViewText(R.id.appwidget_text, "TESTING")

            appWidgetManager.updateAppWidget(watchWidget, remoteViews)


        }
    }

    override fun onDeleted(context: Context, appWidgetIds: IntArray) {
        // When the user deletes the widget, delete the preference associated with it.
        val N = appWidgetIds.size
        for (i in 0 until N) {
//            MyWidgetConfigureActivity.deleteTitlePref(context, appWidgetIds[i])
        }
    }

    override fun onEnabled(context: Context) {
        // Enter relevant functionality for when the first widget is created
    }

    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    fun updateAppWidget(context: Context, appWidgetManager: AppWidgetManager,
                        appWidgetId: Int) {

//        val widgetText = MyWidgetConfigureActivity.loadTitlePref(context, appWidgetId)
        val widgetText = "1234"
        // Construct the RemoteViews object
        val views = RemoteViews(context.packageName, R.layout.start_stop_reading)
        views.setTextViewText(R.id.appwidget_text, widgetText)

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views)
    }

}

