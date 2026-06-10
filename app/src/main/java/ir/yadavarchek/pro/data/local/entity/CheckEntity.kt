package ir.yadavarchek.pro.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import ir.yadavarchek.pro.domain.model.CheckStatus
import ir.yadavarchek.pro.domain.model.CheckType

@Entity(tableName = "checks")
data class CheckEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val checkNumber: String = "",
    val serialNumber: String = "",
    val amount: Long = 0L,
    val issuer: String = "",
    val receiver: String = "",
    val bankName: String = "",
    val branchName: String = "",
    val dueDate: Long = 0L,
    val registrationDate: Long = System.currentTimeMillis(),
    val reminderDate: Long? = null,
    val description: String = "",
    val status: CheckStatus = CheckStatus.PENDING,
    val type: CheckType = CheckType.RECEIVABLE,
    val customerId: Long? = null,
    val imagesJson: String = "[]",
    val attachmentsJson: String = "[]"
)
