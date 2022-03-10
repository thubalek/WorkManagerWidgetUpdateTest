package com.example.workmanagerwidgets

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context

class AppWidget : AppWidgetProvider() {
    override fun onUpdate(
        context: Context?,
        appWidgetManager: AppWidgetManager?,
        appWidgetIds: IntArray?
    ) {
        super.onUpdate(context, appWidgetManager, appWidgetIds)

        requireNotNull(context)
        requireNotNull(appWidgetManager)

        // This works as expected
        // updateWidget(context, appWidgetManager, appWidgetIds)

        // This causes update of the widget every 5 or 10 seconds
        appWidgetIds?.let { ids ->
            ids.forEach { appWidgetId ->
                AppWidgetUpdateWorker.updateWidget(context, appWidgetId)
            }
        }
    }
}
