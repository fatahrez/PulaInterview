package com.fatahapps.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.fatahapps.data.local.model.QuestionLocal

@Database(
    entities = [QuestionLocal::class],
    version = 1
)
@TypeConverters(Converters::class)
abstract class PulaDatabase: RoomDatabase() {
    abstract val questionDao: QuestionDao
}