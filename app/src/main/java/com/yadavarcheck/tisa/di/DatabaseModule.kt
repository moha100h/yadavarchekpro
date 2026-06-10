package com.yadavarcheck.tisa.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import com.yadavarcheck.tisa.data.local.YadavarChekDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides @Singleton
    fun provideDatabase(@ApplicationContext ctx: Context): YadavarChekDatabase =
        Room.databaseBuilder(ctx, YadavarChekDatabase::class.java, "yadavarchek.db")
            .fallbackToDestructiveMigration()
            .build()

    @Provides fun provideCheckDao(db: YadavarChekDatabase) = db.checkDao()
    @Provides fun provideCustomerDao(db: YadavarChekDatabase) = db.customerDao()
    @Provides fun provideNotificationDao(db: YadavarChekDatabase) = db.notificationDao()
}
