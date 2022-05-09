package com.fatahapps.data.local

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.fatahapps.data.local.model.QuestionLocal
import com.fatahapps.data.local.util.GsonParser
import com.google.gson.Gson

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
    public override fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context,
            PulaDatabase::class.java
        ).addTypeConverter(Converters(GsonParser(Gson()))).build()
        questionDao = db.questionDao
    }

    @After
    public override fun tearDown() {
        db.close()
    }

    @Test
    fun testQuestionsDao_insertAndGetQuestion_returnsListContainsQuestion() = runBlocking {
        val question = QuestionLocal(
            "id", "STRING", "STRING",
            "question", listOf(), null
        )
        questionDao.insertQuestions(listOf(question))
        val allQuestions = questionDao.getQuestions()
        assert(allQuestions.contains(question))
    }

    @Test
    fun testQuestionsDao_insertMultipleQuestions_returnsItemsCount() = runBlocking {
        val question = QuestionLocal(
            "id", "STRING", "STRING",
            "question", listOf(), null
        )
        val question2 = QuestionLocal(
            "id2", "STRING", "STRING",
            "question", listOf(), null
        )
        questionDao.insertQuestions(listOf(question, question2))
        val allQuestions = questionDao.getQuestions()
        assert(allQuestions.size  == 2)
    }

    @Test
    fun testQuestionsDao_deletesQuestion_returnsItemCountZero() = runBlocking {
        val question = QuestionLocal(
            "id", "STRING", "STRING",
            "question", listOf(), null
        )
        questionDao.insertQuestions(listOf(question))
        questionDao.deleteQuestions()
        val allQuestions = questionDao.getQuestions()
        assert(!allQuestions.contains(question) && allQuestions.isEmpty())
    }

    @Test
    fun testQuestionsDao_deletesAllQuestions_returnsItemCountZero() = runBlocking {
        val question = QuestionLocal(
            "id", "STRING", "STRING",
            "question", listOf(), null
        )
        val question2 = QuestionLocal(
            "id2", "STRING", "STRING",
            "question", listOf(), null
        )
        questionDao.insertQuestions(listOf(question, question2))
        questionDao.deleteQuestions()
        val allQuestions = questionDao.getQuestions()
        assert(allQuestions.isEmpty())
    }
}