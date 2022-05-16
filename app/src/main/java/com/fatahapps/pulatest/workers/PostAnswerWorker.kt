package com.fatahapps.pulatest.workers

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.fatahapps.data.local.AnswerDao
import com.fatahapps.data.mapper.toDomain
import com.fatahapps.domain.usecases.PostAnswersUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.withContext

@HiltWorker
class PostAnswerWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val postAnswersUseCase: PostAnswersUseCase,
    private val answerDao: AnswerDao
): CoroutineWorker(appContext, workerParams) {
    override suspend  fun doWork(): Result {
        withContext(Dispatchers.IO) {
            Log.i("TAG", "doWork: ${answerDao.getAnswer()}")
            postAnswersUseCase(
                answerDao.getAnswer().toDomain()
            ).catch { e ->
                Log.e("TAG", "doWork: ${answerDao.getAnswer()}", )
                Log.e("TAG", "postAnswer: ", e)
            }.collect{

            }
        }
        return Result.success()
    }
}