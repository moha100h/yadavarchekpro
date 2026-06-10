package ir.yadavarchek.pro.data.repository

import ir.yadavarchek.pro.data.local.dao.NotificationDao
import ir.yadavarchek.pro.data.mapper.toDomain
import ir.yadavarchek.pro.data.mapper.toEntity
import ir.yadavarchek.pro.domain.model.AppNotification
import ir.yadavarchek.pro.domain.repository.NotificationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NotificationRepositoryImpl @Inject constructor(
    private val dao: NotificationDao
) : NotificationRepository {

    override fun getAllNotifications(): Flow<List<AppNotification>> =
        dao.getAllNotifications().map { it.map { e -> e.toDomain() } }

    override fun getUnreadCount(): Flow<Int> = dao.getUnreadCount()

    override suspend fun insertNotification(notification: AppNotification) =
        dao.insertNotification(notification.toEntity())

    override suspend fun markAsRead(id: Long) = dao.markAsRead(id)
    override suspend fun markAllAsRead() = dao.markAllAsRead()
    override suspend fun deleteNotification(id: Long) = dao.deleteNotification(id)
    override suspend fun clearAll() = dao.clearAll()
}
