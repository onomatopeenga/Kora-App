package com.example.kora

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class LoginActivity : AppCompatActivity() {
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val email: TextInputEditText = findViewById<TextInputEditText>(R.id.loginEmailEditText)
        val password: TextInputEditText = findViewById<TextInputEditText>(R.id.loginPasswordEditText)
        val btnLogin = findViewById<Button>(R.id.login_btn)
        val btnSignup = findViewById<Button>(R.id.signup_btn)
        val gosignup = findViewById<TextView>(R.id.logintosignup)
        val forgotpass = findViewById<TextView>(R.id.forgot_password)
        val signInButton : ImageView = findViewById<ImageView>(R.id.google_login_btn)
        val firebaseAuth:FirebaseAuth = FirebaseAuth.getInstance()
        val firebaseUser: FirebaseUser? = firebaseAuth.currentUser

        if (firebaseUser!=null){
            finish()
            startActivity(Intent(this, HomeActivity::class.java))
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
            if (getuser.isNullOrBlank() ||  getpass.isNullOrBlank()){
                Toast.makeText(this, "All Fields Required", Toast.LENGTH_LONG).show()
            }else{
                firebaseAuth.signInWithEmailAndPassword(getuser, getpass )
                    .addOnCompleteListener(this) { task ->
                        if(task.isSuccessful){
                            val firebaseUser: FirebaseUser? = firebaseAuth.currentUser
                            if (firebaseUser?.isEmailVerified == true){
                                val intent = Intent(this, HomeActivity::class.java)
                                startActivity(intent)
                                Toast.makeText(this, "Logged In Successfully", Toast.LENGTH_LONG).show()
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

}


