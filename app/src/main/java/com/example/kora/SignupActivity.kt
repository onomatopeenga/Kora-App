package com.example.kora

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.GoogleAuthProvider



class SignupActivity : AppCompatActivity() {
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        val username: TextInputEditText = findViewById<TextInputEditText>(R.id.signupNameEditText)
        val email: TextInputEditText = findViewById<TextInputEditText>(R.id.signupEmailEditText)
        val password: TextInputEditText = findViewById<TextInputEditText>(R.id.signupPasswordEditText)
        val confirmpass: TextInputEditText = findViewById<TextInputEditText>(R.id.signupConfirmPasswordEditText)
        val gologin = findViewById<TextView>(R.id.signuptologin)
        val signup_btn = findViewById<Button>(R.id.signup_btn)
        val firebaseAuth:FirebaseAuth = FirebaseAuth.getInstance()



        gologin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
        signup_btn.setOnClickListener{
            val getuser: String = username.text.toString().trim()
            val getemail: String = email.text.toString().trim()
            val getpass: String = password.text.toString().trim()
            val getcomfpass: String = confirmpass.text.toString().trim()
            if (getuser.isNullOrBlank() || getemail.isNullOrBlank() || getpass.isNullOrBlank()){
                Toast.makeText(this, "All Fields Required", Toast.LENGTH_LONG).show()
            }
            else if (getpass != getcomfpass){
                Toast.makeText(this, "Passwords aren't matching", Toast.LENGTH_LONG).show()

                }
            else {
                firebaseAuth.createUserWithEmailAndPassword(getemail,getpass)
                    .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        firebaseAuth.currentUser?.sendEmailVerification()?.addOnCompleteListener { task ->
                            if(task.isSuccessful){
                                val intent = Intent(this, LoginActivity::class.java)
                                startActivity(intent)
                                Toast.makeText(this, "Sign up Successful, Please verify first and Login Again.", Toast.LENGTH_LONG).show()


                            }
                        }
                    }else{
                        Toast.makeText(this, "Sign in Failed", Toast.LENGTH_LONG).show()
                    }

                    }
            }

        }

        }





}