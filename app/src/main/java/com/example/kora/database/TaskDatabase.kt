package com.example.kora.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.kora.dao.TaskDao
import com.example.kora.entities.Tasks

@Database(entities = [Tasks::class],version = 1, exportSchema = false)
abstract class TaskDatabase:RoomDatabase() {
    companion object{
        var taskDatabase:TaskDatabase? = null

        @Synchronized
        fun getDatabase(context: Context): TaskDatabase {
            if (taskDatabase == null) {
                taskDatabase = Room.databaseBuilder(
                    context, TaskDatabase::class.java, "tasks.db"
                ).build()
            }
            return taskDatabase!!
        }

    }
    abstract fun taskDao(): TaskDao
}