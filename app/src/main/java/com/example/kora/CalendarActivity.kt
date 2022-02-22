package com.example.kora

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView

class CalendarActivity : AppCompatActivity(),  BottomNavigationView.OnNavigationItemSelectedListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendar)

        val bottomNavigationView: BottomNavigationView =findViewById(R.id.bottomNavigationView)
        bottomNavigationView.background = null
        bottomNavigationView.menu.getItem(2).isEnabled=false
        bottomNavigationView.setOnNavigationItemSelectedListener(this)

    }
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.notes -> {
                Toast.makeText(this, "Notes and Tasks", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, NotesActivity::class.java)
                startActivity(intent)
                overridePendingTransition(0,0)
                finish()


            }
            R.id.home -> {
                Toast.makeText(this, "Home", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
                overridePendingTransition(0,0)
                finish()

            }
            R.id.profile -> {
                Toast.makeText(this, "Profile", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, ProfileActivity::class.java)
                startActivity(intent)
                overridePendingTransition(0,0)
                finish()


            }
            R.id.calendar -> {
                Toast.makeText(this, "Calendar and Schedule", Toast.LENGTH_SHORT).show()
                overridePendingTransition(0,0)
                return true

            }


        }
        return false
    }
}