package com.example.kora.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "Tasks")
class Tasks:Serializable{
    @PrimaryKey(autoGenerate = true)

    var id:Int? = null

    @ColumnInfo(name = "title")
    var title:String? = null

    @ColumnInfo(name = "date_time")
    var dateTime:String? = null



    override fun toString(): String {

        return "$title : $dateTime"

    }
}