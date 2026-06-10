package com.yadavarcheck.tisa.data.local.dao

import androidx.room.*
import com.yadavarcheck.tisa.data.local.entity.CheckEntity
import com.yadavarcheck.tisa.data.local.entity.CheckReminderEntity
import com.yadavarcheck.tisa.domain.model.CheckStatus
import com.yadavarcheck.tisa.domain.model.CheckType
import kotlinx.coroutines.flow.Flow

@Dao
interface CheckDao {

    @Query("SELECT * FROM checks ORDER BY dueDate ASC")
    fun getAllChecks(): Flow<List<CheckEntity>>

    @Query("SELECT * FROM checks WHERE type = :type ORDER BY dueDate ASC")
    fun getChecksByType(type: CheckType): Flow<List<CheckEntity>>

    @Query("SELECT * FROM checks WHERE status = :status ORDER BY dueDate ASC")
    fun getChecksByStatus(status: CheckStatus): Flow<List<CheckEntity>>

    @Query("SELECT * FROM checks WHERE status = 'OVERDUE' ORDER BY dueDate ASC")
    fun getOverdueChecks(): Flow<List<CheckEntity>>

    @Query("SELECT * FROM checks WHERE dueDate BETWEEN :now AND :future AND status NOT IN ('PAID','DEPOSITED','CANCELED') ORDER BY dueDate ASC")
    fun getUpcomingChecks(now: Long, future: Long): Flow<List<CheckEntity>>

    @Query("SELECT * FROM checks WHERE checkNumber LIKE '%' || :q || '%' OR issuer LIKE '%' || :q || '%' OR receiver LIKE '%' || :q || '%' OR bankName LIKE '%' || :q || '%' OR description LIKE '%' || :q || '%' ORDER BY dueDate ASC")
    fun searchChecks(q: String): Flow<List<CheckEntity>>

    @Query("SELECT * FROM checks WHERE id = :id")
    suspend fun getCheckById(id: Long): CheckEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCheck(check: CheckEntity): Long

    @Update
    suspend fun updateCheck(check: CheckEntity)

    @Query("DELETE FROM checks WHERE id = :id")
    suspend fun deleteCheck(id: Long)

    @Query("UPDATE checks SET status = :status WHERE id = :id")
    suspend fun updateStatus(id: Long, status: CheckStatus)

    @Query("SELECT COALESCE(SUM(amount),0) FROM checks WHERE type = :type AND status NOT IN ('CANCELED','RETURNED')")
    fun getTotalAmount(type: CheckType): Flow<Long>

    @Query("SELECT COALESCE(SUM(amount),0) FROM checks WHERE status IN ('PAID','DEPOSITED')")
    fun getPaidAmount(): Flow<Long>

    @Query("SELECT COALESCE(SUM(amount),0) FROM checks WHERE status = 'OVERDUE'")
    fun getOverdueAmount(): Flow<Long>

    @Query("SELECT COUNT(*) FROM checks WHERE status = 'PENDING'")
    fun getPendingCount(): Flow<Int>

    @Query("SELECT COUNT(*) FROM checks WHERE status = 'OVERDUE'")
    fun getOverdueCount(): Flow<Int>

    @Query("SELECT * FROM check_reminders WHERE checkId = :checkId")
    fun getRemindersByCheckId(checkId: Long): Flow<List<CheckReminderEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReminder(reminder: CheckReminderEntity): Long

    @Query("DELETE FROM check_reminders WHERE checkId = :checkId")
    suspend fun deleteRemindersByCheckId(checkId: Long)

    @Query("UPDATE check_reminders SET workerId = :workerId WHERE id = :id")
    suspend fun updateReminderWorkerId(id: Long, workerId: String)
}
