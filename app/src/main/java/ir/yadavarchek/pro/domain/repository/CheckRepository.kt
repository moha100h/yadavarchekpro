package ir.yadavarchek.pro.domain.repository

import ir.yadavarchek.pro.domain.model.Check
import ir.yadavarchek.pro.domain.model.CheckStatus
import ir.yadavarchek.pro.domain.model.CheckType
import kotlinx.coroutines.flow.Flow

interface CheckRepository {
    fun getAllChecks(): Flow<List<Check>>
    fun getChecksByType(type: CheckType): Flow<List<Check>>
    fun getChecksByStatus(status: CheckStatus): Flow<List<Check>>
    fun getOverdueChecks(): Flow<List<Check>>
    fun getUpcomingChecks(daysAhead: Int): Flow<List<Check>>
    fun searchChecks(query: String): Flow<List<Check>>
    suspend fun getCheckById(id: Long): Check?
    suspend fun insertCheck(check: Check): Long
    suspend fun updateCheck(check: Check)
    suspend fun deleteCheck(id: Long)
    suspend fun updateCheckStatus(id: Long, status: CheckStatus)
    fun getTotalAmount(type: CheckType): Flow<Long>
    fun getMonthlyStats(): Flow<Map<String, Long>>
}
