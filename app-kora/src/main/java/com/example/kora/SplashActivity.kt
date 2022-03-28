package com.example.kora

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts

import com.google.firebase.auth.FirebaseAuth
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.GoogleAuthProvider


class SplashActivity : AppCompatActivity() {
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var mGoogleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        val btnLogin = findViewById<Button>(R.id.login_btn)
        val btnSignup = findViewById<Button>(R.id.signup_btn)

        val signInButton : ImageView = findViewById(R.id.google_login_btn)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        val mgoogleSignInClient = GoogleSignIn.getClient(this, gso)

        signInButton.setOnClickListener{ view: View? ->
            val signInIntent = mgoogleSignInClient.signInIntent
            resultLauncher.launch(signInIntent)
        }

        btnLogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
        btnSignup.setOnClickListener {
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
        }

    }

    private var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try{
                val account: GoogleSignInAccount = task.getResult(ApiException::class.java)
                UpdateUI(account)

            } catch (e:ApiException){
                Toast.makeText(this,e.toString(), Toast.LENGTH_SHORT).show()
            }

        }
    }
    var resultLauncher2 = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        Toast.makeText(this, "aaaaaaa", Toast.LENGTH_LONG).show()
        finish()
        super.onBackPressed()

    }

    private fun UpdateUI(account: GoogleSignInAccount) {
        val credential= GoogleAuthProvider.getCredential(account.idToken, null)
        val firebaseAuth:FirebaseAuth = FirebaseAuth.getInstance()
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener {task->
            if(task.isSuccessful) {
                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

    }

    override fun onStart() {
        super.onStart()
        if(GoogleSignIn.getLastSignedInAccount(this)!=null){
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
        }
    }
}
