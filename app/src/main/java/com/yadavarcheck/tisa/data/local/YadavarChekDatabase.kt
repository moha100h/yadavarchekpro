package com.yadavarcheck.tisa.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.yadavarcheck.tisa.data.local.dao.CheckDao
import com.yadavarcheck.tisa.data.local.dao.CustomerDao
import com.yadavarcheck.tisa.data.local.dao.NotificationDao
import com.yadavarcheck.tisa.data.local.entity.CheckEntity
import com.yadavarcheck.tisa.data.local.entity.CheckReminderEntity
import com.yadavarcheck.tisa.data.local.entity.CustomerEntity
import com.yadavarcheck.tisa.data.local.entity.NotificationEntity

@Database(
    entities = [CheckEntity::class, CheckReminderEntity::class, CustomerEntity::class, NotificationEntity::class],
    version = 1,
    exportSchema = true
)
@TypeConverters(Converters::class)
abstract class YadavarChekDatabase : RoomDatabase() {
    abstract fun checkDao(): CheckDao
    abstract fun customerDao(): CustomerDao
    abstract fun notificationDao(): NotificationDao
}
