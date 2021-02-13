package com.boss.mytodo

import android.app.Application
import android.content.Context
import com.boss.mytodo.data.SharedPrefs
import com.boss.mytodo.ui.defaultTheme


class TodoApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
        defaultTheme(SharedPrefs(this).sharedPref, this)
    }

    companion object {
        var appContext: Context? = null
            private set
    }
}