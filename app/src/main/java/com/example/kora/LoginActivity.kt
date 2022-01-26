package com.example.kora

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val username: TextInputEditText = findViewById<TextInputEditText>(R.id.loginEmailEditText)
        val password: TextInputEditText = findViewById<TextInputEditText>(R.id.loginPasswordEditText)
        val btnLogin = findViewById<Button>(R.id.login_btn)

        btnLogin.setOnClickListener {
            if (username.text.toString().equals("user") &&
                password.text.toString().equals("user")
            ) {
                val intent = Intent(this, HomeActivity::class.java)
                val message = intent.putExtra("name", username.text.toString())

                startActivity(intent)
            } else {
                val snackbar = Snackbar.make(
                    it, "incorrect username or password",
                    Snackbar.LENGTH_SHORT
                ).setAction("Action", null)
                snackbar.show()
            }
        }
    }
}