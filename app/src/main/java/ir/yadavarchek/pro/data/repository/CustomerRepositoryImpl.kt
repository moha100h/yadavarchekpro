package ir.yadavarchek.pro.data.repository

import ir.yadavarchek.pro.data.local.dao.CustomerDao
import ir.yadavarchek.pro.data.mapper.toDomain
import ir.yadavarchek.pro.data.mapper.toEntity
import ir.yadavarchek.pro.domain.model.Customer
import ir.yadavarchek.pro.domain.repository.CustomerRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CustomerRepositoryImpl @Inject constructor(
    private val dao: CustomerDao
) : CustomerRepository {

    override fun getAllCustomers(): Flow<List<Customer>> =
        dao.getAllCustomers().map { it.map { e -> e.toDomain() } }

    override fun searchCustomers(query: String): Flow<List<Customer>> =
        dao.searchCustomers(query).map { it.map { e -> e.toDomain() } }

    override suspend fun getCustomerById(id: Long): Customer? =
        dao.getCustomerById(id)?.toDomain()

    override suspend fun insertCustomer(customer: Customer): Long =
        dao.insertCustomer(customer.toEntity())

    override suspend fun updateCustomer(customer: Customer) =
        dao.updateCustomer(customer.toEntity())

    override suspend fun deleteCustomer(id: Long) =
        dao.deleteCustomer(id)
}
