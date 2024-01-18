package com.example.timewidget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.PackageManager.NameNotFoundException
import android.widget.RemoteViews
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


/**
 * Implementation of App Widget functionality.
 */
class TimeWidget : AppWidgetProvider() {

    lateinit var packageManager: PackageManager
    lateinit var views: RemoteViews
    var clockIntent = Intent(Intent.ACTION_MAIN).addCategory(Intent.CATEGORY_LAUNCHER)

    var clockImpls = arrayOf(
        arrayOf(
            "HTC Alarm Clock",
            "com.htc.android.worldclock",
            "com.htc.android.worldclock.WorldClockTabControl"
        ),
        arrayOf("Standard Alarm Clock",
            "com.android.deskclock",
            "com.android.deskclock.AlarmClock"),
        arrayOf(
            "Froyo Nexus Alarm Clock",
            "com.google.android.deskclock",
            "com.android.deskclock.DeskClock"
        ),
        arrayOf(
            "Moto Blur Alarm Clock",
            "com.motorola.blur.alarmclock",
            "com.motorola.blur.alarmclock.AlarmClock"
        ),
        arrayOf(
            "Samsung Galaxy Clock",
            "com.sec.android.app.clockpackage",
            "com.sec.android.app.clockpackage.ClockPackage"
        ),
        arrayOf(
            "Sony Ericsson Xperia Z",
            "com.sonyericsson.organizer",
            "com.sonyericsson.organizer.Organizer_WorldClock"
        ),
        arrayOf("ASUS Tablets",
            "com.asus.deskclock",
            "com.asus.deskclock.DeskClock")
    )

    var clockFound= false


    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        packageManager = context.packageManager

        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            views = RemoteViews(context.packageName, R.layout.time_widget)
            updateAppWidget(context,views,  appWidgetManager, appWidgetId)
            openApplication()

            if(clockFound){
                val pendingIntent = PendingIntent.getActivity(context, 0, clockIntent,
                    PendingIntent.FLAG_IMMUTABLE)
                views.setOnClickPendingIntent(R.id.widget_layout, pendingIntent)
            }

            // Instruct the widget manager to update the widget
            appWidgetManager.updateAppWidget(appWidgetId, views)
        }
    }

    private fun openApplication() {

        for (i in clockImpls.indices){
            val vendor = clockImpls[i][0]
            val packageName = clockImpls[i][1]
            val className = clockImpls[i][2]
            try {
                val cn = ComponentName(packageName, className)
                val aInfo = packageManager.getActivityInfo(cn, PackageManager.GET_META_DATA)
                clockIntent.component = cn
                println("Found $vendor --> $packageName/$className")
                clockFound = true
            }catch (e: NameNotFoundException){
                println("$vendor does not exist")
            }
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
    views: RemoteViews,
    appWidgetManager: AppWidgetManager,
    appWidgetId: Int
) {
    val widgetText = context.getString(R.string.appwidget_text)
    // Construct the RemoteViews object
    val currentTime = SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date())
    val currentDate = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(Date())
    val currentDay = SimpleDateFormat("EEE", Locale.getDefault()).format(Date())
    val currentMonth = SimpleDateFormat("MMM", Locale.getDefault()).format(Date())
    views.setTextViewText(R.id.textClockView, currentTime)
    views.setTextViewText(R.id.textViewDate, "$currentDate,")
    views.setTextViewText(R.id.textViewDay, "$currentDay,")
    views.setTextViewText(R.id.textViewMonth, "$currentMonth,")

}