package ir.yadavarchek.pro.data.preferences

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PreferencesManager @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {
    companion object {
        val THEME_MODE        = intPreferencesKey("theme_mode")        // 0=system 1=light 2=dark
        val IS_ONBOARDED      = booleanPreferencesKey("is_onboarded")
        val SECURITY_TYPE     = intPreferencesKey("security_type")     // 0=none 1=pin 2=biometric
        val PIN_HASH          = stringPreferencesKey("pin_hash")
        val DEFAULT_REMINDER  = intPreferencesKey("default_reminder")  // days before
        val BACKUP_AUTO       = booleanPreferencesKey("backup_auto")
        val BACKUP_INTERVAL   = intPreferencesKey("backup_interval")   // days
        val LAST_BACKUP       = longPreferencesKey("last_backup")
        val NOTIF_ENABLED     = booleanPreferencesKey("notif_enabled")
    }

    val themeMode: Flow<Int> = dataStore.data
        .catch { if (it is IOException) emit(emptyPreferences()) else throw it }
        .map { it[THEME_MODE] ?: 0 }

    val isOnboarded: Flow<Boolean> = dataStore.data
        .catch { if (it is IOException) emit(emptyPreferences()) else throw it }
        .map { it[IS_ONBOARDED] ?: false }

    val securityType: Flow<Int> = dataStore.data
        .catch { if (it is IOException) emit(emptyPreferences()) else throw it }
        .map { it[SECURITY_TYPE] ?: 0 }

    val pinHash: Flow<String> = dataStore.data
        .catch { if (it is IOException) emit(emptyPreferences()) else throw it }
        .map { it[PIN_HASH] ?: "" }

    val defaultReminder: Flow<Int> = dataStore.data
        .catch { if (it is IOException) emit(emptyPreferences()) else throw it }
        .map { it[DEFAULT_REMINDER] ?: 3 }

    val notifEnabled: Flow<Boolean> = dataStore.data
        .catch { if (it is IOException) emit(emptyPreferences()) else throw it }
        .map { it[NOTIF_ENABLED] ?: true }

    suspend fun setThemeMode(mode: Int) = dataStore.edit { it[THEME_MODE] = mode }
    suspend fun setOnboarded(v: Boolean) = dataStore.edit { it[IS_ONBOARDED] = v }
    suspend fun setSecurityType(t: Int) = dataStore.edit { it[SECURITY_TYPE] = t }
    suspend fun setPinHash(h: String) = dataStore.edit { it[PIN_HASH] = h }
    suspend fun setDefaultReminder(d: Int) = dataStore.edit { it[DEFAULT_REMINDER] = d }
    suspend fun setNotifEnabled(v: Boolean) = dataStore.edit { it[NOTIF_ENABLED] = v }
    suspend fun setLastBackup(ts: Long) = dataStore.edit { it[LAST_BACKUP] = ts }
}
