package com.example.kora

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.kora.databinding.ActivityHomeBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class HomeActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {
    private lateinit var homeBinding: ActivityHomeBinding
    var isAllFabsVisible = false
    private var firebaseAuth:FirebaseAuth = FirebaseAuth.getInstance()
    var databaseReference: DatabaseReference? = null
    var database: FirebaseDatabase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeBinding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(homeBinding.root)
        setSupportActionBar(homeBinding.appbarHome)

        homeBinding.bottomNavigationView.background = null
        homeBinding.bottomNavigationView.menu.getItem(1).isEnabled = false
        homeBinding.bottomNavigationView.setOnNavigationItemSelectedListener(this)

        val firebaseAuth:FirebaseAuth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        databaseReference = database?.reference!!.child("profile")

        homeBinding.notesCard.setOnClickListener {
            Toast.makeText(this, "Notes", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, NotesActivity::class.java)
            startActivity(intent)
            overridePendingTransition(0, 0)
            finish()

        }
        homeBinding.tasksCard.setOnClickListener {
            Toast.makeText(this, "Tasks", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, TasksActivity::class.java)
            startActivity(intent)
            overridePendingTransition(0, 0)
            finish()
        }

        homeBinding.calendarCard.setOnClickListener {

        }

        homeBinding.fabnote.setOnClickListener {
            Toast.makeText(this, "Notes", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, NotesActivity::class.java)
            startActivity(intent)
            overridePendingTransition(0, 0)
            finish()
        }
        homeBinding.fabtask.setOnClickListener {
            Toast.makeText(this, "Checklist", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, TasksActivity::class.java)
            startActivity(intent)
            overridePendingTransition(0, 0)
            finish()
        }

        val user = firebaseAuth.currentUser

        if (user != null) {
            loadProfile()

        }

        hideSubFabIcons()
        setupPlusFab()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.options_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.support -> {
                Toast.makeText(this, "About us", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, SupportActivity::class.java)
                startActivity(intent)
                overridePendingTransition(0, 0)
                finish()
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
                overridePendingTransition(0, 0)
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.profile -> {
                Toast.makeText(this, "Profile", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, ProfileActivity::class.java)
                startActivity(intent)
                overridePendingTransition(0, 0)
                finish()
            }
            R.id.home -> {
                Toast.makeText(this, "Home", Toast.LENGTH_SHORT).show()
                overridePendingTransition(0, 0)
                return true
            }
        }
        return false
    }

    private fun loadProfile() {

        val user = firebaseAuth.currentUser
        val userReference = databaseReference?.child(user?.uid!!)
        userReference?.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                homeBinding.usernameTxt.text = snapshot.child("username").value.toString()

            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
        if (homeBinding.usernameTxt.text.isNullOrEmpty()){
            val account: GoogleSignInAccount? = GoogleSignIn.getLastSignedInAccount(this)
            var name = account?.givenName.toString().trim()
            homeBinding.usernameTxt.text = name

        }
    }

    private fun hideSubFabIcons() {
        isAllFabsVisible = false
        homeBinding.llEvent.visibility = View.GONE
        homeBinding.llNote.visibility = View.GONE
        homeBinding.llTask.visibility = View.GONE
    }

    private fun showFabIcons() {
        isAllFabsVisible = true
        homeBinding.llEvent.visibility = View.VISIBLE
        homeBinding.llNote.visibility = View.VISIBLE
        homeBinding.llTask.visibility = View.VISIBLE
    }

    private fun setupPlusFab() {
        homeBinding.mainFab.setOnClickListener {
            if (isAllFabsVisible) hideSubFabIcons()
            else showFabIcons()
        }
    }
}