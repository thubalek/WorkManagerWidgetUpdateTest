package com.example.workmanagerwidgets

import android.content.Context

class Counter(context: Context) {

    private val prefs = context.getSharedPreferences("Counter", Context.MODE_PRIVATE)

    var count: Int
        get() {
            return prefs.getInt(PREF_KEY, 0)
        }
        set(value) {
            prefs.edit().putInt(PREF_KEY, value).apply()
        }

    fun increaseCounter() {
        count = count + 1
    }

    companion object {
        private const val PREF_KEY = "count"
    }
}
