package com.boss.mytodo

import android.app.Application
import android.content.Context
import com.boss.mytodo.data.SharedPrefs
import com.boss.mytodo.ui.setDefaultTheme
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class TodoApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
        setDefaultTheme(SharedPrefs(this).sharedPref, this)
    }

    companion object {
        var appContext: Context? = null
            private set
    }
}