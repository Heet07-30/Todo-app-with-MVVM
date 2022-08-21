package com.example.todoprectice.di

import android.app.Application
import androidx.room.Room
import com.example.todoprectice.data.Database
import com.example.todoprectice.data.Repository
import com.example.todoprectice.data.RepositoryImp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideTodoDatabase(app:Application):Database{

        return Room.databaseBuilder(app,
            Database::class.java,
            "todo"
            ).build()
    }

    @Provides
    @Singleton
    fun provideTodoRepository(database: Database):Repository{

        return RepositoryImp(database.dao)
    }
}