package com.boss.mytodo.data

import android.content.Context
import android.content.SharedPreferences

class SharedPrefs(context: Context) {
    val sharedPref: SharedPreferences =   context.getSharedPreferences("SharedPrefs",
        Context.MODE_PRIVATE)!!
    val editor: SharedPreferences.Editor = sharedPref.edit()
}
