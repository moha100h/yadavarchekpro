package com.yadavarcheck.tisa.ui.viewmodel
import androidx.lifecycle.ViewModel; import androidx.lifecycle.viewModelScope
import com.yadavarcheck.tisa.domain.model.Check; import com.yadavarcheck.tisa.domain.model.CheckStatus; import com.yadavarcheck.tisa.domain.model.CheckType
import com.yadavarcheck.tisa.domain.repository.CheckRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*; import kotlinx.coroutines.launch; import javax.inject.Inject

data class CheckListUiState(
    val checks: List<Check> = emptyList(), val filterType: CheckType? = null,
    val filterStatus: CheckStatus? = null, val searchQuery: String = "",
    val isLoading: Boolean = true, val snackbar: String? = null
)
data class CheckFormState(val check: Check = Check(), val isLoading: Boolean = false, val isSaved: Boolean = false, val error: String? = null)

@HiltViewModel
class CheckViewModel @Inject constructor(private val repo: CheckRepository) : ViewModel() {
    private val _listState = MutableStateFlow(CheckListUiState())
    val listState: StateFlow<CheckListUiState> = _listState.asStateFlow()
    private val _formState = MutableStateFlow(CheckFormState())
    val formState: StateFlow<CheckFormState> = _formState.asStateFlow()
    private val _filterType   = MutableStateFlow<CheckType?>(null)
    private val _filterStatus = MutableStateFlow<CheckStatus?>(null)
    private val _searchQuery  = MutableStateFlow("")
    init { observeChecks() }
    @OptIn(ExperimentalCoroutinesApi::class)
    private fun observeChecks() {
        combine(_filterType, _filterStatus, _searchQuery) { t, s, q -> Triple(t, s, q) }
            .flatMapLatest { (type, status, query) ->
                when { query.isNotBlank() -> repo.searchChecks(query); type != null -> repo.getChecksByType(type); status != null -> repo.getChecksByStatus(status); else -> repo.getAllChecks() }
            }.onEach { checks -> _listState.update { it.copy(checks=checks, isLoading=false) } }.launchIn(viewModelScope)
    }
    fun setFilterType(t: CheckType?) { _filterType.value=t; _listState.update { it.copy(filterType=t) } }
    fun setFilterStatus(s: CheckStatus?) { _filterStatus.value=s; _listState.update { it.copy(filterStatus=s) } }
    fun setSearchQuery(q: String) { _searchQuery.value=q; _listState.update { it.copy(searchQuery=q) } }
    fun loadCheck(id: Long) = viewModelScope.launch {
        _formState.update { it.copy(isLoading=true) }
        val c = if (id > 0) repo.getCheckById(id) ?: Check() else Check()
        _formState.update { it.copy(check=c, isLoading=false) }
    }
    fun saveCheck(check: Check) = viewModelScope.launch {
        _formState.update { it.copy(isLoading=true) }
        try { if (check.id == 0L) repo.insertCheck(check) else repo.updateCheck(check); _formState.update { it.copy(isSaved=true, isLoading=false) }; _listState.update { it.copy(snackbar="چک ذخیره شد") } }
        catch (e: Exception) { _formState.update { it.copy(error=e.message, isLoading=false) } }
    }
    fun deleteCheck(id: Long) = viewModelScope.launch { repo.deleteCheck(id); _listState.update { it.copy(snackbar="چک حذف شد") } }
    fun updateStatus(id: Long, status: CheckStatus) = viewModelScope.launch { repo.updateCheckStatus(id, status); _listState.update { it.copy(snackbar="وضعیت: ${status.label}") } }
    fun clearSnackbar() = _listState.update { it.copy(snackbar=null) }
    fun resetForm() = _formState.update { CheckFormState() }
}
