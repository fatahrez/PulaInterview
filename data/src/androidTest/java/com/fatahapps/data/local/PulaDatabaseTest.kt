package com.fatahapps.data.local

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.fatahapps.data.local.model.QuestionLocal

import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class PulaDatabaseTest : TestCase() {

    private lateinit var db: PulaDatabase
    private lateinit var questionDao: QuestionDao

    @Before
    override fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context,
            PulaDatabase::class.java
        ).build()
        questionDao = db.questionDao
    }

    @After
    fun closeDb() {
        db.close()
    }

    @Test
    fun insertQuestion() = runBlocking {
        val question = QuestionLocal(
            "id", "STRING", "STRING",
            "question", listOf(), null
        )
        questionDao.insertQuestions(listOf(question))
        val allQuestions = questionDao.getQuestions()
        assert(allQuestions.contains(question))
    }
}