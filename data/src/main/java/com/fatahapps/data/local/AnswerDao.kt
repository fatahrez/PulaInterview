package com.fatahapps.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.fatahapps.data.local.model.answer.AnswerLocal

@Dao
interface AnswerDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAnswer(answerLocal: AnswerLocal)

    @Query("DELETE FROM answerlocal")
    suspend fun deleteAnswer()

    @Query("SELECT * FROM answerlocal")
    suspend fun getAnswer(): List<AnswerLocal>

}