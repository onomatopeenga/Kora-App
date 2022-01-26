package com.example.kora
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {
    lateinit var notesFragment: NotesFragment
    lateinit var calendarFragment: CalendarFragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNavigationView: BottomNavigationView=findViewById(R.id.bottomNavigationView)
        bottomNavigationView.background = null
        bottomNavigationView.menu.getItem(2).isEnabled=false
        bottomNavigationView.setOnNavigationItemSelectedListener(this)
    }
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.notes -> {
                Toast.makeText(this, "Notes and Tasks", Toast.LENGTH_SHORT).show()
                notesFragment = NotesFragment()
                supportFragmentManager.beginTransaction().replace(R.id.container_frame, notesFragment)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .addToBackStack(null).commit()

            }
            R.id.calendar -> {
                Toast.makeText(this, "Calendar and Schedule", Toast.LENGTH_SHORT).show()
                calendarFragment = CalendarFragment()
                supportFragmentManager.beginTransaction().replace(R.id.container_frame, calendarFragment)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .addToBackStack(null).commit()

            }
        }
        return false
    }
}
