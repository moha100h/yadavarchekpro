package com.yadavarcheck.tisa.data.repository

import com.yadavarcheck.tisa.data.local.dao.CheckDao
import com.yadavarcheck.tisa.data.mapper.toDomain
import com.yadavarcheck.tisa.data.mapper.toEntity
import com.yadavarcheck.tisa.domain.model.*
import com.yadavarcheck.tisa.domain.repository.CheckRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CheckRepositoryImpl @Inject constructor(private val dao: CheckDao) : CheckRepository {

    override fun getAllChecks() = dao.getAllChecks().map { it.map { e -> e.toDomain() } }
    override fun getChecksByType(type: CheckType) = dao.getChecksByType(type).map { it.map { e -> e.toDomain() } }
    override fun getChecksByStatus(status: CheckStatus) = dao.getChecksByStatus(status).map { it.map { e -> e.toDomain() } }
    override fun getOverdueChecks() = dao.getOverdueChecks().map { it.map { e -> e.toDomain() } }

    override fun getUpcomingChecks(daysAhead: Int): Flow<List<Check>> {
        val now = System.currentTimeMillis()
        val future = now + daysAhead.toLong() * 86_400_000L
        return dao.getUpcomingChecks(now, future).map { it.map { e -> e.toDomain() } }
    }

    override fun searchChecks(query: String) = dao.searchChecks(query).map { it.map { e -> e.toDomain() } }
    override suspend fun getCheckById(id: Long) = dao.getCheckById(id)?.toDomain()
    override suspend fun insertCheck(check: Check) = dao.insertCheck(check.toEntity())
    override suspend fun updateCheck(check: Check) = dao.updateCheck(check.toEntity())
    override suspend fun deleteCheck(id: Long) = dao.deleteCheck(id)
    override suspend fun updateCheckStatus(id: Long, status: CheckStatus) = dao.updateStatus(id, status)
    override fun getTotalAmount(type: CheckType) = dao.getTotalAmount(type)
    override fun getMonthlyStats(): Flow<Map<String, Long>> = dao.getPaidAmount().map { mapOf("paid" to it) }
}
