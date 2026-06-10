package ir.yadavarchek.pro.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import ir.yadavarchek.pro.domain.model.NotificationType

@Entity(tableName = "notifications")
data class NotificationEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val title: String = "",
    val message: String = "",
    val checkId: Long? = null,
    val type: NotificationType = NotificationType.REMINDER,
    val isRead: Boolean = false,
    val createdAt: Long = System.currentTimeMillis()
)
