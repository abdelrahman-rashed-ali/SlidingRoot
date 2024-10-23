@file:Suppress("DEPRECATION")

package com.example.slidingroot.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.slidingroot.MainActivity
import com.example.slidingroot.R
import com.example.slidingroot.StartActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth

class LogoutFragment : Fragment() {

    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Initialize Firebase Auth and Google Sign-In Client
        if ((activity as MainActivity).guest){
            val editor = (activity as MainActivity).localStorage.edit()
            editor.putBoolean("guest",false)
            editor.commit()
            val intent = Intent(requireActivity(), StartActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }else{
            mAuth = FirebaseAuth.getInstance()

            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()

            mGoogleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)

            // Sign out and start sign-in activity
            signOutAndStartSignInActivity()
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_logout, container, false)
    }

    private fun signOutAndStartSignInActivity() {
        // Sign out from Firebase
        mAuth.signOut()

        // Sign out from Google
        mGoogleSignInClient.signOut().addOnCompleteListener(requireActivity()) { task ->
            if (task.isSuccessful) {
                val intent = Intent(requireActivity(), StartActivity::class.java)
                startActivity(intent)
                requireActivity().finish()
            }
        }
    }
}
