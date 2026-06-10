package com.yadavarcheck.tisa.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "check_reminders",
    foreignKeys = [ForeignKey(
        entity = CheckEntity::class,
        parentColumns = ["id"],
        childColumns = ["checkId"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index("checkId")]
)
data class CheckReminderEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val checkId: Long = 0,
    val daysBeforeDue: Int = 1,
    val isEnabled: Boolean = true,
    val workerId: String = ""
)
