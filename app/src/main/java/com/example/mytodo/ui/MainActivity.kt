package com.example.mytodo.ui


import android.app.AlertDialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController

import com.example.mytodo.R


class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navController = findNavController(R.id.myNavHostFragment)
        setupActionBarWithNavController(navController)

    }

    /** user changes theme  */
    private fun showDialog() {
        val sharedPref: SharedPreferences =
            getSharedPreferences("SharedPrefs", Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPref.edit()
        // Late initialize an alert dialog object
        lateinit var dialog: AlertDialog
        // Initialize an array of colors
        val array = arrayOf(getString(R.string.light), getString(R.string.dark), getString(R.string.systemDeafult))
        // Initialize a new instance of alert dialog builder object
        val builder = AlertDialog.Builder(this)
        // Set a title for alert dialog
        builder.setTitle(getString(R.string.chooseTheme))
        // Set the single choice items for alert dialog with initial selection
        builder.setSingleChoiceItems(array, -1) { _, which ->
            // Get the dialog selected item
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
        builder.setNeutralButton(getString(R.string.cancel)) { _, _ ->
            // Do something when click the neutral button
            dialog.cancel()
        }
        // Initialize the AlertDialog using builder object
        dialog = builder.create()
        // Finally, display the alert dialog
        dialog.show()

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_etc, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.night_mode) {
            showDialog()
        }
        return NavigationUI.onNavDestinationSelected(
            item,
            navController
        ) || super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
        navController = findNavController(R.id.myNavHostFragment)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}