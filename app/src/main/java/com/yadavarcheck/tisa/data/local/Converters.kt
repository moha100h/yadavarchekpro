package com.yadavarcheck.tisa.data.local

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.yadavarcheck.tisa.domain.model.CheckStatus
import com.yadavarcheck.tisa.domain.model.CheckType
import com.yadavarcheck.tisa.domain.model.NotificationType

class Converters {
    private val gson = Gson()

    @TypeConverter fun fromCheckStatus(v: CheckStatus): String = v.name
    @TypeConverter fun toCheckStatus(v: String): CheckStatus = CheckStatus.valueOf(v)
    @TypeConverter fun fromCheckType(v: CheckType): String = v.name
    @TypeConverter fun toCheckType(v: String): CheckType = CheckType.valueOf(v)
    @TypeConverter fun fromNotifType(v: NotificationType): String = v.name
    @TypeConverter fun toNotifType(v: String): NotificationType = NotificationType.valueOf(v)

    @TypeConverter
    fun fromStringList(list: List<String>): String = gson.toJson(list)

    @TypeConverter
    fun toStringList(json: String): List<String> {
        val type = object : TypeToken<List<String>>() {}.type
        return gson.fromJson(json, type) ?: emptyList()
    }
}
