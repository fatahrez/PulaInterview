package com.fatahapps.data.di

import android.app.Application
import androidx.room.Room
import com.fatahapps.data.local.Converters
import com.fatahapps.data.local.PulaDatabase
import com.fatahapps.data.local.util.GsonParser
import com.fatahapps.data.remote.HttpClient
import com.fatahapps.data.remote.HttpLogger
import com.fatahapps.data.remote.PulaApi
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module()
@InstallIn(SingletonComponent::class)
class DataModule {
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

}