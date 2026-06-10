package com.yadavarcheck.tisa.data.repository
import com.yadavarcheck.tisa.data.local.dao.NotificationDao; import com.yadavarcheck.tisa.data.mapper.toDomain; import com.yadavarcheck.tisa.data.mapper.toEntity
import com.yadavarcheck.tisa.domain.model.AppNotification; import com.yadavarcheck.tisa.domain.repository.NotificationRepository
import kotlinx.coroutines.flow.Flow; import kotlinx.coroutines.flow.map; import javax.inject.Inject; import javax.inject.Singleton
@Singleton
class NotificationRepositoryImpl @Inject constructor(private val dao: NotificationDao) : NotificationRepository {
    override fun getAllNotifications(): Flow<List<AppNotification>> = dao.getAllNotifications().map { it.map { e -> e.toDomain() } }
    override fun getUnreadCount(): Flow<Int> = dao.getUnreadCount()
    override suspend fun markAsRead(id: Long) = dao.markAsRead(id)
    override suspend fun markAllAsRead() = dao.markAllAsRead()
    override suspend fun insertNotification(notif: AppNotification) = dao.insertNotification(notif.toEntity())
    override suspend fun deleteNotification(id: Long) = dao.deleteNotification(id)
}
