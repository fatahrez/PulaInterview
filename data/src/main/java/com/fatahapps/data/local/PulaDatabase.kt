package com.fatahapps.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.fatahapps.data.local.model.EngStringsLocal
import com.fatahapps.data.local.model.QuestionLocal
import com.fatahapps.data.local.model.answer.AnswerLocal

@Database(
    entities = [QuestionLocal::class, EngStringsLocal::class, AnswerLocal::class],
    version = 4,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class PulaDatabase: RoomDatabase() {
    abstract val questionDao: QuestionDao

    abstract val engStringsDao: EngStringsDao

    abstract val answerDao: AnswerDao
}