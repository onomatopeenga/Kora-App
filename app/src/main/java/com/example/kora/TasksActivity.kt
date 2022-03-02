package com.example.kora


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import com.example.kora.databinding.ActivityTasksBinding


class TasksActivity : AppCompatActivity() {
    private lateinit var tasksBinding:ActivityTasksBinding
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        tasksBinding = ActivityTasksBinding.inflate(layoutInflater)
        setContentView(tasksBinding.root)

        tasksBinding.notesHeaderBtn.setOnClickListener {
            val intent = Intent(this, NotesActivity::class.java)
            startActivity(intent)
        }





    }
}