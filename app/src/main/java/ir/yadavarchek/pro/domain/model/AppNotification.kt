package ir.yadavarchek.pro.domain.model

data class AppNotification(
    val id: Long = 0,
    val title: String = "",
    val message: String = "",
    val checkId: Long? = null,
    val type: NotificationType = NotificationType.REMINDER,
    val isRead: Boolean = false,
    val createdAt: Long = System.currentTimeMillis()
)

enum class NotificationType(val label: String) {
    REMINDER("یادآور"),
    OVERDUE("معوق"),
    PAYMENT("پرداخت")
}
