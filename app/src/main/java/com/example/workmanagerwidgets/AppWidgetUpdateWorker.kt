package com.example.workmanagerwidgets

import android.appwidget.AppWidgetManager
import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.Data
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkerParameters

class AppWidgetUpdateWorker(
    appContext: Context,
    params: WorkerParameters
) : CoroutineWorker(appContext, params) {

    override suspend fun doWork(): Result {

        val widgetId = inputData.getInt(
            INPUT_DATA_WIDGET_ID,
            AppWidgetManager.INVALID_APPWIDGET_ID
        )

        require(widgetId != AppWidgetManager.INVALID_APPWIDGET_ID)

        updateWidget(
            applicationContext,
            AppWidgetManager.getInstance(applicationContext),
            arrayOf(
                widgetId
            ).toIntArray()
        )

        return Result.success()
    }

    companion object {
        private const val INPUT_DATA_WIDGET_ID =
            "com.example.workmanagerwidgets.inputdata.widget_id"

        fun updateWidget(context: Context, widgetId: Int) {

            val data: Data = Data.Builder()
                .putInt(INPUT_DATA_WIDGET_ID, widgetId)
                .build()

            val request = OneTimeWorkRequestBuilder<AppWidgetUpdateWorker>()
                .setInputData(data)
                .build()

            WorkManager.getInstance(context)
                .enqueueUniqueWork(uniqueWorkName(widgetId), ExistingWorkPolicy.REPLACE, request)
        }

        private fun uniqueWorkName(widgetId: Int): String {
            return "widget_update_$widgetId"
        }
    }
}
