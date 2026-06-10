package com.yadavarcheck.tisa.data.repository

import com.yadavarcheck.tisa.data.local.dao.NotificationDao
import com.yadavarcheck.tisa.data.mapper.toDomain
import com.yadavarcheck.tisa.data.mapper.toEntity
import com.yadavarcheck.tisa.domain.model.AppNotification
import com.yadavarcheck.tisa.domain.repository.NotificationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NotificationRepositoryImpl @Inject constructor(
    private val dao: NotificationDao
) : NotificationRepository {
    override fun getAllNotifications(): Flow<List<AppNotification>> =
        dao.getAllNotifications().map { list -> list.map { it.toDomain() } }

    override fun getUnreadCount(): Flow<Int> =
        dao.getUnreadCount()

    override suspend fun insertNotification(notification: AppNotification) =
        dao.insertNotification(notification.toEntity())

    override suspend fun markAsRead(id: Long) =
        dao.markAsRead(id)

    override suspend fun markAllAsRead() =
        dao.markAllAsRead()

    override suspend fun deleteNotification(id: Long) =
        dao.deleteNotification(id)

    override suspend fun clearAll() =
        dao.clearAll()
}
