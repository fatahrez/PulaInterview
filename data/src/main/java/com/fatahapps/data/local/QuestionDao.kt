package com.fatahapps.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.fatahapps.data.local.model.QuestionLocal

@Dao
interface QuestionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuestions(questions: List<QuestionLocal>)

    @Query("DELETE FROM questionlocal")
    suspend fun deleteQuestions()

    @Query("SELECT * FROM questionlocal")
    suspend fun getQuestions(): List<QuestionLocal>
}