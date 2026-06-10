package com.yadavarcheck.tisa.domain.model

data class Check(
    val id: Long = 0,
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
    val images: List<String> = emptyList(),
    val attachments: List<String> = emptyList(),
    val reminders: List<CheckReminder> = emptyList()
)

enum class CheckStatus(val label: String) {
    PENDING("در انتظار"),
    PAID("پرداخت شده"),
    DEPOSITED("واریز شده"),
    RETURNED("برگشتی"),
    OVERDUE("معوق"),
    CANCELED("لغو شده")
}

enum class CheckType(val label: String) {
    RECEIVABLE("دریافتنی"),
    PAYABLE("پرداختنی")
}

data class CheckReminder(
    val id: Long = 0,
    val checkId: Long = 0,
    val daysBeforeDue: Int = 1,
    val isEnabled: Boolean = true,
    val workerId: String = ""
)
