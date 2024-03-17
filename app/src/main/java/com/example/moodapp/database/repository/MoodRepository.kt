package com.example.moodapp.database.repository

import android.content.Context
import com.example.moodapp.database.dao.MoodDB
import com.example.moodapp.model.Mood

class MoodRepository(context: Context) {

    private val db = MoodDB.getDatabase(context).moodDao()

    fun salvar(mood: Mood): Long {
        return db.salvar(mood)
    }

    fun listarMoods(): List<Mood> {
        return db.listarMoods()
    }

}