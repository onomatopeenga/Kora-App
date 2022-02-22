package com.example.kora

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser as FirebaseUser

class ProfileActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {
    lateinit var deletebutton : Button
    private var firebaseAuth:FirebaseAuth = FirebaseAuth.getInstance()
    private lateinit var username : TextView
    private lateinit var useremail : TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottomNavigationView)
        bottomNavigationView.background = null
        bottomNavigationView.menu.getItem(2).isEnabled = false
        bottomNavigationView.setOnNavigationItemSelectedListener(this)
        deletebutton = findViewById(R.id.deleteaccount)
        useremail = findViewById(R.id.useremailtv)
        username = findViewById(R.id.userusernametv)


        val user = firebaseAuth.currentUser
        val account: GoogleSignInAccount? = GoogleSignIn.getLastSignedInAccount(this)
        if (account != null) {
            var name = account.displayName.toString().trim()
            var email = account.email.toString().trim()
            username.text = name
            useremail.text = email
        }

        fun deleteUser(user: FirebaseUser) {
            user.delete()?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val intent = Intent(this, SplashActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }

        }

        deletebutton.setOnClickListener() {
            val dialogBuilder = AlertDialog.Builder(this)
            dialogBuilder.setMessage("Deleting this account will remove your account data and access to the application")
            dialogBuilder.setPositiveButton(
                "Delete",
                DialogInterface.OnClickListener { dialogInterface, i ->
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
            R.id.calendar -> {
                Toast.makeText(this, "Calendar and Schedule", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, CalendarActivity::class.java)
                startActivity(intent)
                overridePendingTransition(0,0)


            }
            R.id.notes -> {
                Toast.makeText(this, "Notes and Tasks", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, NotesActivity::class.java)
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


}