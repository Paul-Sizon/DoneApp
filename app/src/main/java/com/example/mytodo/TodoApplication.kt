package com.example.mytodo

import android.app.Application
import android.content.Context

// TODO: implement functions in Utils
class TodoApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
    }

    companion object {
        var appContext: Context? = null
            private set
    }
}