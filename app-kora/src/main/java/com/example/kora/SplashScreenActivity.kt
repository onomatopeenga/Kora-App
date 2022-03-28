package com.example.kora

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class SplashScreenActivity : AppCompatActivity() {
    private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        val firebaseAuth:FirebaseAuth = FirebaseAuth.getInstance()
        val firebaseUser: FirebaseUser? = firebaseAuth.currentUser

        Handler().postDelayed({
            val intent = Intent(this,SplashActivity::class.java)
            startActivity(intent)
            finish()
                              }, 3000)

    }

}