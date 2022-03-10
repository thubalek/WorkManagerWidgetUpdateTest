package com.example.workmanagerwidgets

import android.appwidget.AppWidgetManager
import android.content.Context
import android.widget.RemoteViews


fun updateWidget(
    context: Context,
    appWidgetManager: AppWidgetManager,
    appWidgetIds: IntArray?
) {
    val counter = Counter(context)

    val remoteViews = RemoteViews(context.packageName, R.layout.layout_widget).apply {
        setTextViewText(R.id.count, counter.count.toString())
    }
    counter.increaseCounter()

    appWidgetManager.updateAppWidget(appWidgetIds, remoteViews)
}
