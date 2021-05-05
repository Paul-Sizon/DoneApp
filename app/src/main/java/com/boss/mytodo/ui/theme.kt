package com.boss.mytodo.ui

import android.app.AlertDialog
import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatDelegate
import com.boss.mytodo.R

/** user changes theme  */
fun showThemeDialog(editor: SharedPreferences.Editor, context: Context) {
    lateinit var dialog: AlertDialog
    val array = arrayOf(context.getString(R.string.light), context.getString(R.string.dark), context.getString(R.string.systemDeafult))
    val builder = AlertDialog.Builder(context)
    builder.setTitle(context.getString(R.string.chooseTheme))
    builder.setSingleChoiceItems(array, -1) { _, which ->
        val choice = array[which]
        if (choice == array[0]) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
        if (choice == array[1]) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }

        if (choice == array[2]) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        }

        editor.putString("mode", choice).apply()
        dialog.dismiss()
    }
    builder.setNeutralButton(context.getString(R.string.cancel)) { _, _ ->
        dialog.cancel()
    }

    dialog = builder.create()
    dialog.show()

}

//todo delete context reference

/** remembers the theme  */
fun setDefaultTheme(sharedPref: SharedPreferences, context: Context) {
    val mode = sharedPref.getString("mode", "")
    if (mode == context.getString(R.string.dark)) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
    }
    if (mode == context.getString(R.string.light)) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }
    if (mode == context.getString(R.string.systemDeafult)) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
    }
}