package com.fatahapps.data.local.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.fatahapps.data.di.DataModule
import com.fatahapps.data.local.PulaDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.testing.HiltTestApplication
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
object TestAppModule {
    @Provides
    fun providedInMemeoryDb(app: Application) =
        Room.inMemoryDatabaseBuilder(app, PulaDatabase::class.java)
            .allowMainThreadQueries()
            .build()
}