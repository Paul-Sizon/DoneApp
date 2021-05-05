package com.boss.mytodo.di

import android.content.Context
import androidx.room.Room
import com.boss.mytodo.data.db.TaskDatabase
import com.boss.mytodo.network.QuoteApi
import com.boss.mytodo.other.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideQuoteApi(): QuoteApi = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(Constants.BASE_URL)
        .build().create(QuoteApi::class.java)


    @Singleton
    @Provides
    fun provideTaskDatabase(
        @ApplicationContext app: Context
    ) = Room.databaseBuilder(
        app,
        TaskDatabase::class.java,
        "task_database"
    ).build()

    @Singleton
    @Provides
    fun provideYourDao(db: TaskDatabase) = db.taskDatabaseDao()

}