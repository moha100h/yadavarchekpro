package com.yadavarcheck.tisa.data.repository
import com.yadavarcheck.tisa.data.local.dao.CustomerDao; import com.yadavarcheck.tisa.data.mapper.toDomain; import com.yadavarcheck.tisa.data.mapper.toEntity
import com.yadavarcheck.tisa.domain.model.Customer; import com.yadavarcheck.tisa.domain.repository.CustomerRepository
import kotlinx.coroutines.flow.Flow; import kotlinx.coroutines.flow.map; import javax.inject.Inject; import javax.inject.Singleton
@Singleton
class CustomerRepositoryImpl @Inject constructor(private val dao: CustomerDao) : CustomerRepository {
    override fun getAllCustomers(): Flow<List<Customer>> = dao.getAllCustomers().map { it.map { e -> e.toDomain() } }
    override fun searchCustomers(q: String): Flow<List<Customer>> = dao.searchCustomers(q).map { it.map { e -> e.toDomain() } }
    override suspend fun getCustomerById(id: Long) = dao.getCustomerById(id)?.toDomain()
    override suspend fun insertCustomer(customer: Customer) = dao.insertCustomer(customer.toEntity())
    override suspend fun updateCustomer(customer: Customer) = dao.updateCustomer(customer.toEntity())
    override suspend fun deleteCustomer(id: Long) = dao.deleteCustomer(id)
}
