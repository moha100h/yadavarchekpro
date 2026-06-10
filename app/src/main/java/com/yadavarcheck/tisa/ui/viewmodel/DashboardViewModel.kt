package com.yadavarcheck.tisa.ui.viewmodel
import androidx.lifecycle.ViewModel; import androidx.lifecycle.viewModelScope
import com.yadavarcheck.tisa.domain.model.Check; import com.yadavarcheck.tisa.domain.model.CheckType
import com.yadavarcheck.tisa.domain.repository.CheckRepository; import com.yadavarcheck.tisa.domain.repository.NotificationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*; import kotlinx.coroutines.launch; import javax.inject.Inject

data class DashboardUiState(
    val overdueChecks: List<Check> = emptyList(),
    val upcomingChecks: List<Check> = emptyList(),
    val totalReceivable: Long = 0L,
    val totalPayable: Long = 0L,
    val unreadNotifCount: Int = 0,
    val isLoading: Boolean = true
)

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val checkRepo: CheckRepository,
    private val notifRepo: NotificationRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(DashboardUiState())
    val uiState: StateFlow<DashboardUiState> = _uiState.asStateFlow()
    init {
        viewModelScope.launch {
            combine(
                checkRepo.getOverdueChecks(),
                checkRepo.getUpcomingChecks(7),
                checkRepo.getTotalAmount(CheckType.RECEIVABLE),
                checkRepo.getTotalAmount(CheckType.PAYABLE),
                notifRepo.getUnreadCount()
            ) { overdue, upcoming, recv, pay, notif ->
                DashboardUiState(overdueChecks=overdue, upcomingChecks=upcoming, totalReceivable=recv, totalPayable=pay, unreadNotifCount=notif, isLoading=false)
            }.collect { _uiState.value = it }
        }
    }
}
