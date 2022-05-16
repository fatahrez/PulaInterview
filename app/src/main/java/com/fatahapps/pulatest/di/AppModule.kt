package com.fatahapps.pulatest.di

import android.app.Application
import androidx.room.Room
import com.fatahapps.data.local.Converters
import com.fatahapps.data.local.PulaDatabase
import com.fatahapps.data.local.util.GsonParser
import com.fatahapps.data.remote.HttpClient
import com.fatahapps.data.remote.HttpLogger
import com.fatahapps.data.remote.PulaApi
import com.fatahapps.data.repository.EngStringRepositoryImpl
import com.fatahapps.data.repository.PulaRepositoryImpl
import com.fatahapps.domain.repository.EngStringRepository
import com.fatahapps.domain.repository.PulaRepository
import com.google.gson.Gson
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module(includes = [AppModule.Binders::class])
@InstallIn(SingletonComponent::class)
class AppModule {

    @Module
    @InstallIn(SingletonComponent::class)
    interface Binders{
        @Binds
        fun bindsRepository(
            pulaRepositoryImpl: PulaRepositoryImpl
        ): PulaRepository

        @Binds
        fun bindsEngStringsRepository(
            engStringRepositoryImpl: EngStringRepositoryImpl
        ): EngStringRepository
    }

    @Provides
    fun providesHttpLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLogger.create()

    @Provides
    fun providesOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return HttpClient.setupOkHttpClient(httpLoggingInterceptor)
    }

    @Singleton
    @Provides
    fun providesPulaAPI(retrofit: Retrofit): PulaApi = retrofit.create(PulaApi::class.java)

    @Provides
    fun providesRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .baseUrl(PulaApi.BASE_URL)
            .build()
    }

    @Provides
    @Singleton
    fun providesPulaDatabase(app: Application): PulaDatabase {
        return Room.databaseBuilder(
            app,
            PulaDatabase::class.java,
            "pula_db"
        ).addTypeConverter(Converters(GsonParser(Gson())))
            .fallbackToDestructiveMigration()
            .build()
    }



    @Provides
    @Singleton
    fun providesQuestionDao(
        pulaDatabase: PulaDatabase
    ) = pulaDatabase.questionDao

    @Provides
    @Singleton
    fun providesEngStringsDao(
        pulaDatabase: PulaDatabase
    ) = pulaDatabase.engStringsDao

    @Provides
    @Singleton
    fun providesAnswerDao(
        pulaDatabase: PulaDatabase
    ) = pulaDatabase.answerDao
}