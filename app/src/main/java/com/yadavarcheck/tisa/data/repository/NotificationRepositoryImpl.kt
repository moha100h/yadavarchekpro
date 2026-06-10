package com.yadavarcheck.tisa.data.repository

import com.yadavarcheck.tisa.data.local.dao.NotificationDao
import com.yadavarcheck.tisa.data.mapper.toDomain
import com.yadavarcheck.tisa.data.mapper.toEntity
import com.yadavarcheck.tisa.domain.model.AppNotification
import com.yadavarcheck.tisa.domain.repository.NotificationRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NotificationRepositoryImpl @Inject constructor(private val dao: NotificationDao) : NotificationRepository {
    override fun getAllNotifications() = dao.getAllNotifications().map { it.map { e -> e.toDomain() } }
    override fun getUnreadCount() = dao.getUnreadCount()
    override suspend fun insertNotification(notification: AppNotification) = dao.insertNotification(notification.toEntity())
    override suspend fun markAsRead(id: Long) = dao.markAsRead(id)
    override suspend fun markAllAsRead() = dao.markAllAsRead()
    override suspend fun deleteNotification(id: Long) = dao.deleteNotification(id)
    override suspend fun clearAll() = dao.clearAll()
}
