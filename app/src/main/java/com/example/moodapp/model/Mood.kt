package com.example.moodapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "tbl_mood")
data class Mood(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    var mood: String = "",
    var date: String = ""
)