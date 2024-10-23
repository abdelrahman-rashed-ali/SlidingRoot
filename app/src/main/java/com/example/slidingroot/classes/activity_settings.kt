package com.example.slidingroot.classes

import android.content.res.Resources
import android.os.Build
import android.view.View
import android.view.Window
import android.view.WindowInsetsController
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.slidingroot.R

object activity_settings {
    fun apply_size(view : View){
        ViewCompat.setOnApplyWindowInsetsListener(view) { view, insets ->
            val systemBarsInsets = insets.getInsets(WindowInsetsCompat.Type.systemBars())

            view.setPadding(
                systemBarsInsets.left,
                systemBarsInsets.top,
                systemBarsInsets.right,
                systemBarsInsets.bottom
            )
            // Consume the insets
            WindowInsetsCompat.CONSUMED
        }
    }

    fun apply_text(window : Window , res : Resources){
        val window = window

        // Set light status bar to ensure dark icons
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController!!.setSystemBarsAppearance(
                WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS,
                WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
            )
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        } // Change the color to black or any dark color

        window.setNavigationBarColor(res.getColor(R.color.black));
    }
}