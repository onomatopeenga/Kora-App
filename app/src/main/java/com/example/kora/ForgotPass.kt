package com.example.kora

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth

class ForgotPass : AppCompatActivity() {
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_pass)
        val btnRecover = findViewById<Button>(R.id.recover_btn)
        val forgotemail: TextInputEditText = findViewById<TextInputEditText>(R.id.forgotEmailEditText)
        val firebaseAuth:FirebaseAuth = FirebaseAuth.getInstance()

        btnRecover.setOnClickListener {
            val getforgotemail: String = forgotemail.text.toString().trim()
            if (getforgotemail.isNullOrBlank()){
                Toast.makeText(this, "Enter your email first", Toast.LENGTH_LONG).show()
            }
            else{
                firebaseAuth.sendPasswordResetEmail(getforgotemail)
                    .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful){
                        Toast.makeText(this, "Password Reset sent to your email", Toast.LENGTH_LONG).show()
                        finish()
                        val intent = Intent(this, HomeActivity::class.java)
                        startActivity(intent)
                    }else{
                        Toast.makeText(this, "Email doesn't exist", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }



    }

}