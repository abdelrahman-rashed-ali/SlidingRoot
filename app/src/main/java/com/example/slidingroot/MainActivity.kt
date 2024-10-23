package com.example.slidingroot

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.ComplexColorCompat.inflate
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.setupWithNavController
import com.bumptech.glide.Glide
import com.example.slidingroot.classes.activity_settings
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.material.navigation.NavigationView
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth


class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    private lateinit var user : FirebaseUser
    private lateinit var userName : String
    private lateinit var menu : Menu
    private var mSlideState = false
    var guest = false
    lateinit var localStorage : SharedPreferences
    private lateinit var drawerLayout : DrawerLayout
    @SuppressLint("MissingInflatedId", "ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        activity_settings.apply_size(findViewById(R.id.constraint))
        activity_settings.apply_text(window , resources)


        navController = Navigation.findNavController(this, R.id.host_fragment)
        val navView = findViewById<NavigationView>(R.id.nav_view)
        navView.setupWithNavController(navController)
        drawerLayout = findViewById(R.id.main)
        menu = navView.menu

        localStorage = getSharedPreferences("Guest", Context.MODE_PRIVATE)
        if (localStorage.getBoolean("guest",false)){
            userName = "Guest"
            guest = true
            menu.findItem(R.id.logout).title = "Log in"
        } else {
            val auth = Firebase.auth
            user = auth.currentUser!!
            userName = user.displayName.toString()
        }




        setupDrawerAnimation()

    }



    private fun setupDrawerAnimation() {

        drawerLayout.setScrimColor(Color.WHITE)
        drawerLayout.setBackgroundColor(Color.WHITE)
        drawerLayout.addDrawerListener(object : DrawerLayout.DrawerListener {
            @SuppressLint("SetTextI18n")
            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
                findViewById<TextView>(R.id.user_text).text = "Welcome, $userName"
                val slideX = drawerView.width * slideOffset
                val constraint = findViewById<ConstraintLayout>(R.id.constraint)
                constraint.translationX = slideX
                constraint.translationY = slideX / 6f

                val headerImage = drawerView.findViewById<ImageView>(R.id.header_nav_image)

                try {
                    headerImage.setImageResource(R.drawable.image_profile)
                } catch (ex: Exception) {
                    TODO()
                }
            }

            override fun onDrawerOpened(drawerView: View) {
                if (!(getSharedPreferences("Guest", Context.MODE_PRIVATE).getBoolean("guest",false))){
                    putImage()
                }

                mSlideState = true
            }

            override fun onDrawerClosed(drawerView: View) {
                mSlideState = false
            }

            override fun onDrawerStateChanged(newState: Int) {}
        })
    }

    @SuppressLint("RtlHardcoded")
    fun clickEventSlide() {
        if (mSlideState) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            drawerLayout.openDrawer(GravityCompat.START)
        }
    }


    private fun putImage(){
        val googleSignInAccount = GoogleSignIn.getLastSignedInAccount(this)
        if (googleSignInAccount != null) {
            val photoUrl = googleSignInAccount.photoUrl
            Glide.with(this)
                .load(photoUrl)
                .into(findViewById(R.id.header_nav_image))
        }
    }


}