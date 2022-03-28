package com.example.kora.dao

import androidx.room.*
import com.example.kora.entities.Notes
import com.example.kora.entities.Tasks

@Dao
interface TaskDao {
    @Query("SELECT * FROM tasks ORDER BY id DESC")
    suspend fun getAllTasks(): List<Tasks>

    @Query("SELECT * FROM tasks WHERE id =:id")
    suspend fun getSpecificTask(id:Int) : Tasks

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTasks(task: Tasks)

    @Update
    suspend fun updateTask(task: Tasks)

    @Delete
    suspend fun deleteTask(task: Tasks)
}