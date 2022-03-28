package com.example.kora

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class LoginActivity : AppCompatActivity() {
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var firebaseUser: FirebaseUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        FirebaseApp.initializeApp(this);
        val email: TextInputEditText = findViewById<TextInputEditText>(R.id.loginEmailEditText)
        val password: TextInputEditText = findViewById<TextInputEditText>(R.id.loginPasswordEditText)
        val btnLogin = findViewById<Button>(R.id.login_btn)
        val gosignup = findViewById<TextView>(R.id.logintosignup)
        val forgotpass = findViewById<TextView>(R.id.forgot_password)
        val firebaseAuth = FirebaseAuth.getInstance()
        val firebaseUser: FirebaseUser? = firebaseAuth.currentUser


        if (firebaseUser!=null){
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
        }


        gosignup.setOnClickListener {
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
        }
        forgotpass.setOnClickListener{
            val intent = Intent(this, ForgotPass::class.java)
            startActivity(intent)
        }
        btnLogin.setOnClickListener {
            val getuser: String = email.text.toString().trim()
            val getpass: String = password.text.toString().trim()
            if (getuser.isBlank() ||  getpass.isBlank()){
                Toast.makeText(this, "All Fields Required", Toast.LENGTH_LONG).show()
            }else{
                firebaseAuth.signInWithEmailAndPassword(getuser, getpass )
                    .addOnCompleteListener(this) { task ->
                        if(task.isSuccessful){
                            if (firebaseUser?.isEmailVerified == true){

                                Toast.makeText(this, "Logged In Successfully", Toast.LENGTH_LONG).show()
                                val intent = Intent(this,HomeActivity::class.java)
                                startActivity(intent)
                                finish()
                            }else{
                                Toast.makeText(this, "Please verify your email first", Toast.LENGTH_LONG).show()

                            }
                        }else{
                            Toast.makeText(this, "Email or password doesn't exist", Toast.LENGTH_LONG).show()
                        }
                        }
                    }
            }

        }

    var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            val data: Intent? = result.data
            Toast.makeText(this, "Logged In Successfully", Toast.LENGTH_LONG).show()
            finish()
            super.onBackPressed()

    }

}


