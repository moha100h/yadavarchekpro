package ir.yadavarchek.pro.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ir.yadavarchek.pro.data.local.YadavarChekDatabase
import ir.yadavarchek.pro.data.local.dao.CheckDao
import ir.yadavarchek.pro.data.local.dao.CustomerDao
import ir.yadavarchek.pro.data.local.dao.NotificationDao
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): YadavarChekDatabase =
        Room.databaseBuilder(context, YadavarChekDatabase::class.java, "yadavarchek.db")
            .fallbackToDestructiveMigration()
            .build()

    @Provides fun provideCheckDao(db: YadavarChekDatabase): CheckDao = db.checkDao()
    @Provides fun provideCustomerDao(db: YadavarChekDatabase): CustomerDao = db.customerDao()
    @Provides fun provideNotificationDao(db: YadavarChekDatabase): NotificationDao = db.notificationDao()
}
