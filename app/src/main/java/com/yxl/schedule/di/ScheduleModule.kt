package com.yxl.schedule.di

import android.content.Context
import androidx.room.Room
import com.yxl.schedule.db.ScheduleDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ScheduleModule {

    @Singleton
    @Provides
    fun provideScheduleDatabase(
        @ApplicationContext app: Context
    ): ScheduleDatabase{
        return Room.databaseBuilder(
            app,
            ScheduleDatabase::class.java,
            ScheduleDatabase.DATABASE_NAME
        ).build()
    }

    @Singleton
    @Provides
    fun provideGroupDAO(db: ScheduleDatabase) = db.groupsDAO()

    @Singleton
    @Provides
    fun provideScheduleDAO(db: ScheduleDatabase) = db.scheduleDAO()
}