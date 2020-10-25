package com.example.mytodo

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatDelegate

// TODO: implement functions in Utils
class TodoApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
        defaultTheme()
    }

    /** remembers the theme  */
    private fun defaultTheme() {
        val sharedPref: SharedPreferences = getSharedPreferences(
            "SharedPrefs",
            Context.MODE_PRIVATE
        )
        val mode = sharedPref.getString("mode", "")
        if (mode == "Dark") {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }
        if (mode == "Light") {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
        if (mode == "System default") {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        }
    }

    companion object {
        var appContext: Context? = null
            private set
    }
}