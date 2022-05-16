package com.fatahapps.pulatest

import android.app.Application
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.*
import com.fatahapps.pulatest.workers.PostAnswerWorker
import dagger.hilt.android.HiltAndroidApp
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltAndroidApp
class PulaApp: Application(), Configuration.Provider {

    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    override fun getWorkManagerConfiguration() =
        Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()

    override fun onCreate() {
        super.onCreate()
        mSelf = this
        val uniqueWorkName = BuildConfig.APPLICATION_ID + ":post-answer-work"
        configureWorkManager(uniqueWorkName)
    }

    private fun configureWorkManager(uniqueWorkName: String) {
        val workManager: WorkManager = WorkManager.getInstance(applicationContext)
        val constraints: Constraints = Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED)
            .build()
        val periodicWorkRequest: PeriodicWorkRequest = PeriodicWorkRequest.Builder(PostAnswerWorker::class.java,
        15, TimeUnit.MINUTES)
            .setConstraints(constraints)
            .setInitialDelay(1, TimeUnit.MINUTES)
            .build()
        workManager.enqueueUniquePeriodicWork(
            uniqueWorkName,
            ExistingPeriodicWorkPolicy.REPLACE,
            periodicWorkRequest
        )

    }

    companion object {
        private var mSelf: PulaApp? = null

        fun self(): PulaApp {
            return mSelf!!
        }
    }
}

