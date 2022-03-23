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
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth

class HomeActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {
    private lateinit var homeBinding: ActivityHomeBinding
    var isAllFabsVisible = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeBinding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(homeBinding.root)
        setSupportActionBar(homeBinding.appbarHome)

        homeBinding.bottomNavigationView.background = null
        homeBinding.bottomNavigationView.menu.getItem(1).isEnabled = false
        homeBinding.bottomNavigationView.setOnNavigationItemSelectedListener(this)

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
            Toast.makeText(this, "Calendar", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, CalendarActivity::class.java)
            startActivity(intent)
            overridePendingTransition(0, 0)
            finish()
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
            R.id.settings -> {
                Toast.makeText(this, "Settings", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, SettingsActivity::class.java)
                startActivity(intent)
                overridePendingTransition(0, 0)
            }
            R.id.support -> {
                Toast.makeText(this, "About us", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, SupportActivity::class.java)
                startActivity(intent)
                overridePendingTransition(0, 0)
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
            }
            R.id.home -> {
                Toast.makeText(this, "Home", Toast.LENGTH_SHORT).show()
                overridePendingTransition(0, 0)
                return true
            }
        }
        return false
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