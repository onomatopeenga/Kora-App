package com.example.kora
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.View.inflate
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import android.widget.Toolbar
import androidx.cardview.widget.CardView
import androidx.fragment.app.FragmentTransaction
import com.example.kora.databinding.ActivityHomeBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.auth.api.signin.GoogleSignInAccount

class HomeActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener{
    private lateinit var  homeBinding: ActivityHomeBinding



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeBinding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(homeBinding.root)
        setSupportActionBar(homeBinding.appbarHome)

        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottomNavigationView)
        bottomNavigationView.background = null
        bottomNavigationView.menu.getItem(1).isEnabled = false
        bottomNavigationView.setOnNavigationItemSelectedListener(this)


        homeBinding.notesCard.setOnClickListener {
            Toast.makeText(this, "Notes", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, NotesActivity::class.java)
            startActivity(intent)
            overridePendingTransition(0,0)

        }
        homeBinding.tasksCard.setOnClickListener {
            Toast.makeText(this, "Tasks", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, TasksActivity::class.java)
            startActivity(intent)
            overridePendingTransition(0,0)

        }
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
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {


            R.id.profile -> {
                Toast.makeText(this, "Profile", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, ProfileActivity::class.java)
                startActivity(intent)
                overridePendingTransition(0,0)



            }
            R.id.home -> {
                Toast.makeText(this, "Home", Toast.LENGTH_SHORT).show()
                overridePendingTransition(0,0)
                return true



            }



        }
        return false


    }

}