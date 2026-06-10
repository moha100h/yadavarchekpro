package ir.yadavarchek.pro.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ir.yadavarchek.pro.data.local.dao.CheckDao
import ir.yadavarchek.pro.data.local.dao.CustomerDao
import ir.yadavarchek.pro.data.local.dao.NotificationDao
import ir.yadavarchek.pro.data.local.entity.CheckEntity
import ir.yadavarchek.pro.data.local.entity.CheckReminderEntity
import ir.yadavarchek.pro.data.local.entity.CustomerEntity
import ir.yadavarchek.pro.data.local.entity.NotificationEntity

@Database(
    entities = [
        CheckEntity::class,
        CheckReminderEntity::class,
        CustomerEntity::class,
        NotificationEntity::class
    ],
    version = 1,
    exportSchema = true
)
@TypeConverters(Converters::class)
abstract class YadavarChekDatabase : RoomDatabase() {
    abstract fun checkDao(): CheckDao
    abstract fun customerDao(): CustomerDao
    abstract fun notificationDao(): NotificationDao
}
