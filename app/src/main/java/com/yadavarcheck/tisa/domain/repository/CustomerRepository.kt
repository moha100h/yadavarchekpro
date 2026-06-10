package com.yadavarcheck.tisa.domain.repository
import com.yadavarcheck.tisa.domain.model.Customer; import kotlinx.coroutines.flow.Flow
interface CustomerRepository {
    fun getAllCustomers(): Flow<List<Customer>>
    fun searchCustomers(q: String): Flow<List<Customer>>
    suspend fun getCustomerById(id: Long): Customer?
    suspend fun insertCustomer(customer: Customer): Long
    suspend fun updateCustomer(customer: Customer)
    suspend fun deleteCustomer(id: Long)
}
