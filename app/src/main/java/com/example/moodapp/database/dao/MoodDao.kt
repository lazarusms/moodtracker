package com.example.moodapp.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.moodapp.model.Mood

@Dao
interface MoodDao {

    @Insert
    fun salvar(mood: Mood): Long

    @Query("SELECT * FROM tbl_mood ORDER BY mood ASC")
    fun listarMoods(): List<Mood>

}