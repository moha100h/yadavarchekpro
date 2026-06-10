package com.yadavarcheck.tisa.ui.viewmodel
import androidx.lifecycle.ViewModel; import androidx.lifecycle.viewModelScope
import com.yadavarcheck.tisa.domain.model.AppNotification; import com.yadavarcheck.tisa.domain.repository.NotificationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*; import kotlinx.coroutines.launch; import javax.inject.Inject

data class NotificationUiState(val notifications: List<AppNotification> = emptyList(), val unreadCount: Int = 0)

@HiltViewModel
class NotificationViewModel @Inject constructor(private val repo: NotificationRepository) : ViewModel() {
    private val _uiState = MutableStateFlow(NotificationUiState())
    val uiState: StateFlow<NotificationUiState> = _uiState.asStateFlow()
    init {
        viewModelScope.launch {
            combine(repo.getAllNotifications(), repo.getUnreadCount()) { notifs, count ->
                NotificationUiState(notifications = notifs, unreadCount = count)
            }.collect { _uiState.value = it }
        }
    }
    fun markAsRead(id: Long) = viewModelScope.launch { repo.markAsRead(id) }
    fun markAllAsRead() = viewModelScope.launch { repo.markAllAsRead() }
    fun delete(id: Long) = viewModelScope.launch { repo.deleteNotification(id) }
}
