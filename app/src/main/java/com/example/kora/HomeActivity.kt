package com.example.kora
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {
    lateinit var notesFragment: NotesFragment
    lateinit var calendarFragment: CalendarFragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val bottomNavigationView: BottomNavigationView =findViewById(R.id.bottomNavigationView)
        bottomNavigationView.background = null
        bottomNavigationView.menu.getItem(2).isEnabled=false
        bottomNavigationView.setOnNavigationItemSelectedListener(this)

        val textView = findViewById<TextView>(R.id.username_txt)
        val intent = intent
        val message = intent.getStringExtra("name")
        textView.setText(message)

    }
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.notes -> {
                Toast.makeText(this, "Notes and Tasks", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, NotesActivity::class.java)
                startActivity(intent)

            }
            R.id.calendar -> {
                Toast.makeText(this, "Calendar and Schedule", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, CalendarActivity::class.java)
                startActivity(intent)

            }



        }
        return false
    }

}