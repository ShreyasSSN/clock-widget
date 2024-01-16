package com.example.timewidget

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.widget.RemoteViews
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * Implementation of App Widget functionality.
 */
class TimeWidget : AppWidgetProvider() {
    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    companion object{
        internal fun updateAppWidget(
            context: Context, appWidgetManager: AppWidgetManager, appWidgetId: Int
        ){
            val views = RemoteViews(context.packageName, R.layout.time_widget)
            val currentTime = SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date())
            val currentDate = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(Date())
            val currentDay = SimpleDateFormat("EEE", Locale.getDefault()).format(Date())
            val currentMonth = SimpleDateFormat("MMM", Locale.getDefault()).format(Date())
            views.setTextViewText(R.id.textClockView, "$currentTime,")
            views.setTextViewText(R.id.textViewDate, "$currentDate,")
            views.setTextViewText(R.id.textViewDay, "$currentDay,")
            views.setTextViewText(R.id.textViewMonth, "$currentMonth,")


            appWidgetManager.updateAppWidget(appWidgetId, views)
        }
    }

    override fun onEnabled(context: Context) {
        // Enter relevant functionality for when the first widget is created
    }

    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

internal fun updateAppWidget(
    context: Context,
    appWidgetManager: AppWidgetManager,
    appWidgetId: Int
) {
    val widgetText = context.getString(R.string.appwidget_text)
    // Construct the RemoteViews object
    val views = RemoteViews(context.packageName, R.layout.time_widget)
    views.setTextViewText(R.id.textClockView, widgetText)

    // Instruct the widget manager to update the widget
    appWidgetManager.updateAppWidget(appWidgetId, views)
}