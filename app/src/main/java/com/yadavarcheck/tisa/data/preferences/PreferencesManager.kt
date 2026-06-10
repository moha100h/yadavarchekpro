package com.yadavarcheck.tisa.data.preferences

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PreferencesManager @Inject constructor(private val dataStore: DataStore<Preferences>) {

    companion object {
        val THEME_MODE       = intPreferencesKey("theme_mode")
        val IS_ONBOARDED     = booleanPreferencesKey("is_onboarded")
        val SECURITY_TYPE    = intPreferencesKey("security_type")
        val PIN_HASH         = stringPreferencesKey("pin_hash")
        val DEFAULT_REMINDER = intPreferencesKey("default_reminder")
        val NOTIF_ENABLED    = booleanPreferencesKey("notif_enabled")
        val LAST_BACKUP      = longPreferencesKey("last_backup")
    }

    private fun <T> flow(key: Preferences.Key<T>, default: T) = dataStore.data
        .catch { if (it is IOException) emit(emptyPreferences()) else throw it }
        .map { it[key] ?: default }

    val themeMode       = flow(THEME_MODE, 0)
    val isOnboarded     = flow(IS_ONBOARDED, false)
    val securityType    = flow(SECURITY_TYPE, 0)
    val pinHash         = flow(PIN_HASH, "")
    val defaultReminder = flow(DEFAULT_REMINDER, 3)
    val notifEnabled    = flow(NOTIF_ENABLED, true)

    suspend fun setThemeMode(v: Int)      = dataStore.edit { it[THEME_MODE] = v }
    suspend fun setOnboarded(v: Boolean)  = dataStore.edit { it[IS_ONBOARDED] = v }
    suspend fun setSecurityType(v: Int)   = dataStore.edit { it[SECURITY_TYPE] = v }
    suspend fun setPinHash(v: String)     = dataStore.edit { it[PIN_HASH] = v }
    suspend fun setDefaultReminder(v: Int)= dataStore.edit { it[DEFAULT_REMINDER] = v }
    suspend fun setNotifEnabled(v: Boolean)=dataStore.edit { it[NOTIF_ENABLED] = v }
    suspend fun setLastBackup(v: Long)    = dataStore.edit { it[LAST_BACKUP] = v }
}
