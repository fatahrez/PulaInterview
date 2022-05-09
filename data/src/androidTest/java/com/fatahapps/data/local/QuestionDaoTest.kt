package com.fatahapps.data.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import com.fatahapps.data.di.DataModule
import com.fatahapps.data.local.model.QuestionLocal
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import javax.inject.Named


@HiltAndroidTest
@UninstallModules(DataModule::class)
class QuestionDaoTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    lateinit var database: PulaDatabase
    private lateinit var dao: QuestionDao

    @Before
    fun setup() {
        hiltRule.inject()
        dao = database.questionDao
    }

    @After
    fun teardown() {
        database.close()
    }

//    @Test
//    fun insertQuestion() = runTest {
//        val question = QuestionLocal("id", "STRING", "STRING",
//            "question", listOf(), null)
//        dao.insertQuestions(listOf(question))
//        val allQuestions = dao.getQuestions()
//        assert(allQuestions.contains(question))
//    }
}