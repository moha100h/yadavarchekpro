package com.yadavarcheck.tisa.domain.repository

import com.yadavarcheck.tisa.domain.model.AppNotification
import kotlinx.coroutines.flow.Flow

interface NotificationRepository {
    fun getAllNotifications(): Flow<List<AppNotification>>
    fun getUnreadCount(): Flow<Int>
    suspend fun insertNotification(notification: AppNotification)
    suspend fun markAsRead(id: Long)
    suspend fun markAllAsRead()
    suspend fun deleteNotification(id: Long)
    suspend fun clearAll()
}
