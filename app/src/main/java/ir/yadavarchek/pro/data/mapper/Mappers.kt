package ir.yadavarchek.pro.data.mapper

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ir.yadavarchek.pro.data.local.entity.CheckEntity
import ir.yadavarchek.pro.data.local.entity.CheckReminderEntity
import ir.yadavarchek.pro.data.local.entity.CustomerEntity
import ir.yadavarchek.pro.data.local.entity.NotificationEntity
import ir.yadavarchek.pro.domain.model.*

private val gson = Gson()
private val listType = object : TypeToken<List<String>>() {}.type

fun CheckEntity.toDomain(reminders: List<CheckReminderEntity> = emptyList()) = Check(
    id = id, checkNumber = checkNumber, serialNumber = serialNumber,
    amount = amount, issuer = issuer, receiver = receiver,
    bankName = bankName, branchName = branchName, dueDate = dueDate,
    registrationDate = registrationDate, reminderDate = reminderDate,
    description = description, status = status, type = type,
    customerId = customerId,
    images = gson.fromJson(imagesJson, listType) ?: emptyList(),
    attachments = gson.fromJson(attachmentsJson, listType) ?: emptyList(),
    reminders = reminders.map { it.toDomain() }
)

fun Check.toEntity() = CheckEntity(
    id = id, checkNumber = checkNumber, serialNumber = serialNumber,
    amount = amount, issuer = issuer, receiver = receiver,
    bankName = bankName, branchName = branchName, dueDate = dueDate,
    registrationDate = registrationDate, reminderDate = reminderDate,
    description = description, status = status, type = type,
    customerId = customerId,
    imagesJson = gson.toJson(images),
    attachmentsJson = gson.toJson(attachments)
)

fun CheckReminderEntity.toDomain() = CheckReminder(
    id = id, checkId = checkId, daysBeforeDue = daysBeforeDue,
    isEnabled = isEnabled, workerId = workerId
)

fun CheckReminder.toEntity() = CheckReminderEntity(
    id = id, checkId = checkId, daysBeforeDue = daysBeforeDue,
    isEnabled = isEnabled, workerId = workerId
)

fun CustomerEntity.toDomain() = Customer(
    id = id, name = name, mobile = mobile, phone = phone,
    address = address, notes = notes, createdAt = createdAt
)

fun Customer.toEntity() = CustomerEntity(
    id = id, name = name, mobile = mobile, phone = phone,
    address = address, notes = notes, createdAt = createdAt
)

fun NotificationEntity.toDomain() = AppNotification(
    id = id, title = title, message = message, checkId = checkId,
    type = type, isRead = isRead, createdAt = createdAt
)

fun AppNotification.toEntity() = NotificationEntity(
    id = id, title = title, message = message, checkId = checkId,
    type = type, isRead = isRead, createdAt = createdAt
)
