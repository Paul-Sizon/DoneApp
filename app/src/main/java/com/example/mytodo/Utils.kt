package com.example.mytodo

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.google.android.material.textfield.TextInputLayout
import java.util.*


class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext

        defaultTheme()
    }

    companion object {
        var appContext: Context? = null
            private set
    }

    /** remembers the theme  */
    fun defaultTheme() {
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
}



fun hideKeyboard(activity: Activity) {
    val inputMethodManager =
        activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

    // Check if no view has focus
    val currentFocusedView = activity.currentFocus
    currentFocusedView?.let {
        inputMethodManager.hideSoftInputFromWindow(
            currentFocusedView.windowToken, InputMethodManager.HIDE_NOT_ALWAYS
        )
    }
}




 fun hasNetworkAvailable(): Boolean {
    val service = Context.CONNECTIVITY_SERVICE
    val manager = MyApplication.appContext?.getSystemService(service) as ConnectivityManager?
    val network = manager?.activeNetworkInfo
    return (network != null)
}



//check that title is not empty
fun checksTitle(title: TextView, materialText: TextInputLayout): Boolean {
    return if (title.text?.isEmpty()!!) {
        materialText.error = "empty title"
        false
    } else {
        materialText.error = null
        materialText.isErrorEnabled = false
        true
    }

}

// changes phrase's language based on the system's language. Only russian and english are available on the server
fun checkLanguage(): String {
    return if (Locale.getDefault().language == "ru") "ru"
    else "en"
}

