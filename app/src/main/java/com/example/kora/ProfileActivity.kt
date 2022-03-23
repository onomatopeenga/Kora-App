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
import com.google.firebase.database.*
import com.google.firebase.auth.FirebaseUser as FirebaseUser

class ProfileActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {
    private lateinit var profileBinding: ActivityProfileBinding
    private var firebaseAuth:FirebaseAuth = FirebaseAuth.getInstance()
    var databaseReference: DatabaseReference? = null
    var database: FirebaseDatabase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        profileBinding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(profileBinding.root)
        setSupportActionBar(profileBinding.appbarNotesTasks)

        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottomNavigationView)
        bottomNavigationView.background = null
        bottomNavigationView.menu.getItem(2).isEnabled = false
        bottomNavigationView.setOnNavigationItemSelectedListener(this)

        val firebaseAuth:FirebaseAuth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        databaseReference = database?.reference!!.child("profile")





        val user = firebaseAuth.currentUser

        if (user != null) {
            loadProfile()

        }



        profileBinding.deleteaccount.setOnClickListener() {
            val dialogBuilder = AlertDialog.Builder(this)
            dialogBuilder.setMessage("Deleting this account will remove your account data and access to the application")
            dialogBuilder.setPositiveButton("Delete", DialogInterface.OnClickListener { dialog, id ->
                user?.delete()?.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val intent = Intent(this, SplashActivity::class.java)
                        startActivity(intent)
                        Toast.makeText(this, "User Deleted", Toast.LENGTH_SHORT).show()
                        finish()
                    }
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
                finish()

            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun loadProfile() {

        val user = firebaseAuth.currentUser
        val userReference = databaseReference?.child(user?.uid!!)
        profileBinding.useremailtv.text = user?.email
        userReference?.addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {

                profileBinding.userusernametv.text = snapshot.child("username").value.toString()

            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
        if (profileBinding.userusernametv.text.isNullOrEmpty()){
            val account: GoogleSignInAccount? = GoogleSignIn.getLastSignedInAccount(this)
            var name = account?.displayName.toString().trim()
            profileBinding.userusernametv.text = name

        }
    }


}