package com.example.slidingroot

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.hardware.camera2.CameraExtensionSession.StillCaptureLatency
import android.os.Bundle
import android.view.Window
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.slidingroot.classes.activity_settings
import com.example.slidingroot.fragments.DetailsShowMovies
import com.example.slidingroot.fragments.ItemsShowMeals
import com.example.slidingroot.fragments.StepsFragment
import com.example.slidingroot.fragments.VideoFragment
import com.example.slidingroot.interfaces.SelectFromShowMealLes


class ShowMeal : AppCompatActivity() , SelectFromShowMealLes {
    private lateinit var localStorage : SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_show_meal)
        activity_settings.apply_text(window , resources)
        activity_settings.apply_size(findViewById(R.id.main))

        localStorage = getSharedPreferences("Guest", Context.MODE_PRIVATE)
    }


    override fun onDetailsSelect() {
        deleteAllFragments()
        val trans = supportFragmentManager.beginTransaction().add(R.id.dynamic_frag_sm,DetailsShowMovies(),"dynamic")
        trans.commit()
    }

    override fun onItemsSelect() {
        deleteAllFragments()
        val trans = supportFragmentManager.beginTransaction().add(R.id.dynamic_frag_sm,ItemsShowMeals(),"dynamic")
        trans.commit()
    }

    override fun onStepsSelect() {
        deleteAllFragments()
        val trans = supportFragmentManager.beginTransaction().add(R.id.dynamic_frag_sm,StepsFragment(),"dynamic")
        trans.commit()
    }

    override fun onVideoSelect() {
        deleteAllFragments()
        val trans = supportFragmentManager.beginTransaction().add(R.id.dynamic_frag_sm,VideoFragment(),"dynamic")
        trans.commit()
    }

    // delete all fragment in dynamic fragment container ( dynamic_frag_sm )
    private fun deleteAllFragments(){
        val trans = supportFragmentManager.beginTransaction()
        val fragmentManager = supportFragmentManager
        val fragmentList = fragmentManager.fragments
        fragmentList.forEach { fragment ->
            if (fragment.id == R.id.dynamic_frag_sm) {
                trans.remove(fragment)
            }
        }
        trans.commit()
    }

    @SuppressLint("CommitPrefEdits")
    fun showCustomDialog(message : String){
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.login_dialog)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val dialogMsg = dialog.findViewById<TextView>(R.id.dialog_errMsg)
        val login = dialog.findViewById<Button>(R.id.dialog_loginBtn)
        val cancel = dialog.findViewById<Button>(R.id.dialog_cancelBtn)

        message.also { dialogMsg.text = it }

        login.setOnClickListener{
            val editor = localStorage.edit()
            editor.putBoolean("guest",false)
            editor.commit()
            startActivity(Intent(this, StartActivity::class.java))
            finish()
        }

        cancel.setOnClickListener{
            dialog.dismiss()
        }

        dialog.show()
    }
}