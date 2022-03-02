package com.example.kora

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.kora.databinding.ActivityProfileBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser as FirebaseUser

class ProfileActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {
    private lateinit var profileBinding: ActivityProfileBinding
    private var firebaseAuth:FirebaseAuth = FirebaseAuth.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        profileBinding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(profileBinding.root)
        setSupportActionBar(profileBinding.appbarNotesTasks)

        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottomNavigationView)
        bottomNavigationView.background = null
        bottomNavigationView.menu.getItem(2).isEnabled = false
        bottomNavigationView.setOnNavigationItemSelectedListener(this)



        val user = firebaseAuth.currentUser
        val account: GoogleSignInAccount? = GoogleSignIn.getLastSignedInAccount(this)
        if (account != null) {
            var name = account.displayName.toString().trim()
            var email = account.email.toString().trim()
            profileBinding.useremailtv.text = email
            profileBinding.userusernametv.text = name

        }

        fun deleteUser(user: FirebaseUser) {
            user.delete().addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val intent = Intent(this, SplashActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }

        }

        profileBinding.deleteaccount.setOnClickListener() {
            val dialogBuilder = AlertDialog.Builder(this)
            dialogBuilder.setMessage("Deleting this account will remove your account data and access to the application")
            dialogBuilder.setPositiveButton(
                "Delete",
                DialogInterface.OnClickListener { dialog, i ->
                    if (user != null) {
                        deleteUser(user)

                    }

                })
            dialogBuilder.setNegativeButton(
                "Cancel",
                DialogInterface.OnClickListener { dialog, id ->
                    dialog.cancel()
                })
            val alert = dialogBuilder.create()
            alert.setTitle("Are you sure you want to delete your account?")
            alert.show()
        }



    }
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.home -> {
                Toast.makeText(this, "Home", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
                overridePendingTransition(0,0)


            }

            R.id.profile -> {
                Toast.makeText(this, "Notes and Tasks", Toast.LENGTH_SHORT).show()

                return true
            }



        }
        return false
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.options_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.settings -> {
                Toast.makeText(this, "Settings", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, SettingsActivity::class.java)
                startActivity(intent)
                overridePendingTransition(0,0)
            }
            R.id.support ->{
                Toast.makeText(this, "About us", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, SupportActivity::class.java)
                startActivity(intent)
                overridePendingTransition(0,0)
            }
            R.id.logout -> {
                FirebaseAuth.getInstance().signOut()
                val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(getString(R.string.default_web_client_id))
                    .requestEmail()
                    .build()
                val mgoogleSignInClient = GoogleSignIn.getClient(this, gso)
                mgoogleSignInClient.signOut()
                val intent = Intent(this, SplashScreenActivity::class.java)
                startActivity(intent)
                overridePendingTransition(0,0)
            }
        }
        return super.onOptionsItemSelected(item)
    }


}