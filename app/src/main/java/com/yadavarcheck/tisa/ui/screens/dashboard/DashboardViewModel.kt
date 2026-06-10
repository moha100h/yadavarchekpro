package com.yadavarcheck.tisa.ui.screens.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import com.yadavarcheck.tisa.domain.model.Check
import com.yadavarcheck.tisa.domain.model.CheckType
import com.yadavarcheck.tisa.domain.repository.CheckRepository
import com.yadavarcheck.tisa.domain.repository.NotificationRepository
import kotlinx.coroutines.flow.*
import javax.inject.Inject

data class DashboardUiState(
    val totalReceivable: Long = 0L,
    val totalPayable: Long = 0L,
    val upcomingChecks: List<Check> = emptyList(),
    val overdueChecks: List<Check> = emptyList(),
    val unreadNotifCount: Int = 0,
    val isLoading: Boolean = true
)

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val checkRepo: CheckRepository,
    private val notifRepo: NotificationRepository
) : ViewModel() {

    val uiState: StateFlow<DashboardUiState> = combine(
        checkRepo.getTotalAmount(CheckType.RECEIVABLE),
        checkRepo.getTotalAmount(CheckType.PAYABLE),
        checkRepo.getUpcomingChecks(7),
        checkRepo.getOverdueChecks(),
        notifRepo.getUnreadCount()
    ) { receivable, payable, upcoming, overdue, unread ->
        DashboardUiState(
            totalReceivable   = receivable,
            totalPayable      = payable,
            upcomingChecks    = upcoming,
            overdueChecks     = overdue,
            unreadNotifCount  = unread,
            isLoading         = false
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), DashboardUiState())
}
