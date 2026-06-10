package ir.yadavarchek.pro.domain.model

data class Customer(
    val id: Long = 0,
    val name: String = "",
    val mobile: String = "",
    val phone: String = "",
    val address: String = "",
    val notes: String = "",
    val createdAt: Long = System.currentTimeMillis()
)
