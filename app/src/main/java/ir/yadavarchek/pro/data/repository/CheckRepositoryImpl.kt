package ir.yadavarchek.pro.data.repository

import ir.yadavarchek.pro.data.local.dao.CheckDao
import ir.yadavarchek.pro.data.mapper.toDomain
import ir.yadavarchek.pro.data.mapper.toEntity
import ir.yadavarchek.pro.domain.model.Check
import ir.yadavarchek.pro.domain.model.CheckStatus
import ir.yadavarchek.pro.domain.model.CheckType
import ir.yadavarchek.pro.domain.repository.CheckRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CheckRepositoryImpl @Inject constructor(
    private val dao: CheckDao
) : CheckRepository {

    override fun getAllChecks(): Flow<List<Check>> =
        dao.getAllChecks().map { list -> list.map { it.toDomain() } }

    override fun getChecksByType(type: CheckType): Flow<List<Check>> =
        dao.getChecksByType(type).map { list -> list.map { it.toDomain() } }

    override fun getChecksByStatus(status: CheckStatus): Flow<List<Check>> =
        dao.getChecksByStatus(status).map { list -> list.map { it.toDomain() } }

    override fun getOverdueChecks(): Flow<List<Check>> =
        dao.getOverdueChecks().map { list -> list.map { it.toDomain() } }

    override fun getUpcomingChecks(daysAhead: Int): Flow<List<Check>> {
        val now = System.currentTimeMillis()
        val future = now + daysAhead.toLong() * 24 * 60 * 60 * 1000
        return dao.getUpcomingChecks(now, future).map { list -> list.map { it.toDomain() } }
    }

    override fun searchChecks(query: String): Flow<List<Check>> =
        dao.searchChecks(query).map { list -> list.map { it.toDomain() } }

    override suspend fun getCheckById(id: Long): Check? =
        dao.getCheckById(id)?.toDomain()

    override suspend fun insertCheck(check: Check): Long =
        dao.insertCheck(check.toEntity())

    override suspend fun updateCheck(check: Check) =
        dao.updateCheck(check.toEntity())

    override suspend fun deleteCheck(id: Long) =
        dao.deleteCheck(id)

    override suspend fun updateCheckStatus(id: Long, status: CheckStatus) =
        dao.updateStatus(id, status)

    override fun getTotalAmount(type: CheckType): Flow<Long> =
        dao.getTotalAmount(type)

    override fun getMonthlyStats(): Flow<Map<String, Long>> =
        dao.getPaidAmount().map { mapOf("paid" to it) }
}
