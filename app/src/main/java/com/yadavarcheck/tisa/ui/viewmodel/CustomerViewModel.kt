package com.yadavarcheck.tisa.ui.viewmodel
import androidx.lifecycle.ViewModel; import androidx.lifecycle.viewModelScope
import com.yadavarcheck.tisa.domain.model.Customer; import com.yadavarcheck.tisa.domain.repository.CustomerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*; import kotlinx.coroutines.launch; import javax.inject.Inject

data class CustomerUiState(val customers: List<Customer> = emptyList(), val searchQuery: String = "")

@HiltViewModel
class CustomerViewModel @Inject constructor(private val repo: CustomerRepository) : ViewModel() {
    private val _uiState = MutableStateFlow(CustomerUiState())
    val uiState: StateFlow<CustomerUiState> = _uiState.asStateFlow()
    private val _query = MutableStateFlow("")
    init {
        @OptIn(ExperimentalCoroutinesApi::class)
        _query.flatMapLatest { q -> if (q.isBlank()) repo.getAllCustomers() else repo.searchCustomers(q) }
            .onEach { list -> _uiState.update { it.copy(customers = list) } }.launchIn(viewModelScope)
    }
    fun search(q: String) { _query.value = q; _uiState.update { it.copy(searchQuery = q) } }
    fun add(name: String, mobile: String) = viewModelScope.launch { repo.insertCustomer(Customer(name = name, mobile = mobile)) }
    fun delete(id: Long) = viewModelScope.launch { repo.deleteCustomer(id) }
}
