# YadavarChek Pro — یادآور چک پرو 📋

> Professional check management app for Android — built with Kotlin + Jetpack Compose

[![Android](https://img.shields.io/badge/Android-26%2B-green)](https://developer.android.com)
[![Kotlin](https://img.shields.io/badge/Kotlin-2.0.21-blue)](https://kotlinlang.org)
[![Compose](https://img.shields.io/badge/Compose-BOM%202024.10-purple)](https://developer.android.com/jetpack/compose)
[![License](https://img.shields.io/badge/License-MIT-yellow)](LICENSE)

---

## Features / ویژگی‌ها

| Feature | Description |
|---|---|
| 📋 Check Management | Receivable & payable checks with full CRUD |
| 🔔 Smart Reminders | Multi-step alerts: 30/15/7/3/1 days before + due day |
| 📊 Dashboard | Financial summary with overdue & upcoming checks |
| 📅 Calendar | Jalali (Shamsi) calendar view |
| 📈 Reports | PDF/Excel/CSV export |
| 👥 Customers | Customer management linked to checks |
| 💾 Backup | Local backup & restore |
| 🔒 Security | PIN lock + biometric authentication |
| 🌙 Dark Mode | Dynamic Material 3 theming |
| 🔤 Persian Font | Vazirmatn (5 weights) — full RTL support |

---

## Tech Stack

- **Language:** Kotlin 2.0.21
- **UI:** Jetpack Compose + Material 3
- **Architecture:** MVVM + Clean Architecture (Domain / Data / UI)
- **DI:** Hilt 2.52
- **Database:** Room 2.6.1
- **Async:** Kotlin Coroutines + Flow
- **Background:** WorkManager 2.9.1 (Hilt-integrated)
- **Preferences:** DataStore Preferences
- **Navigation:** Navigation Compose 2.8.4
- **Charts:** Vico 2.0.0-beta.2
- **PDF:** iTextPDF 5.5.13.4
- **Image:** Coil 2.7.0
- **Serialization:** Gson 2.11.0

---

## Project Structure

```
app/src/main/java/com/yadavarcheck/tisa/
├── data/
│   ├── local/
│   │   ├── dao/            # Room DAOs (CheckDao, CustomerDao, NotificationDao)
│   │   ├── entity/         # Room Entities
│   │   ├── Converters.kt   # TypeConverters
│   │   └── YadavarChekDatabase.kt
│   ├── mapper/             # Entity ↔ Domain mappers
│   ├── preferences/        # DataStore PreferencesManager
│   ├── receiver/           # BootReceiver, NotificationActionReceiver
│   ├── repository/         # Repository implementations
│   └── worker/             # ReminderWorker (HiltWorker), ReminderScheduler
├── di/
│   ├── DatabaseModule.kt
│   ├── DataStoreModule.kt
│   └── RepositoryModule.kt
├── domain/
│   ├── model/              # Check, Customer, AppNotification, enums
│   └── repository/         # Repository interfaces
└── ui/
    ├── components/         # Shared Composables (CheckCard, StatusBadge, …)
    ├── navigation/         # Screen routes + NavHost
    ├── screens/
    │   ├── splash/
    │   ├── onboarding/
    │   ├── dashboard/
    │   ├── checks/         # List, Detail, AddEdit
    │   ├── calendar/
    │   ├── reports/
    │   ├── customers/
    │   ├── notifications/
    │   ├── settings/
    │   ├── backup/
    │   └── security/
    └── theme/              # Color.kt, Type.kt (Vazirmatn), Theme.kt
```

---

## Requirements

- Android Studio Hedgehog (2023.1.1) or newer
- JDK 17
- Android SDK 35
- Gradle 8.x

---

## Getting Started

```bash
git clone https://github.com/moha100h/yadavarchekpro.git
cd yadavarchekpro
```

1. Open in Android Studio: `File → Open` → select project folder
2. Wait for Gradle sync to complete
3. Select a device or emulator (API 26+)
4. `Run → Run 'app'`

---

## Build

### Debug APK
```bash
./gradlew assembleDebug
# Output: app/build/outputs/apk/debug/app-debug.apk
```

### Release APK
```bash
./gradlew assembleRelease
# Output: app/build/outputs/apk/release/app-release-unsigned.apk
```

### Release AAB (Play Store)
```bash
./gradlew bundleRelease
# Output: app/build/outputs/bundle/release/app-release.aab
```

---

## Signing Release Build

Create `keystore.properties` in the project root (do **not** commit this file):

```properties
storeFile=path/to/your.jks
storePassword=YOUR_STORE_PASSWORD
keyAlias=YOUR_KEY_ALIAS
keyPassword=YOUR_KEY_PASSWORD
```

Add to `app/build.gradle.kts`:

```kotlin
import java.util.Properties

val keystoreProps = Properties().apply {
    val f = rootProject.file("keystore.properties")
    if (f.exists()) load(f.inputStream())
}

android {
    signingConfigs {
        create("release") {
            storeFile     = file(keystoreProps["storeFile"] as String)
            storePassword = keystoreProps["storePassword"] as String
            keyAlias      = keystoreProps["keyAlias"] as String
            keyPassword   = keystoreProps["keyPassword"] as String
        }
    }
    buildTypes {
        release { signingConfig = signingConfigs.getByName("release") }
    }
}
```

---

## Customization

### App Name
`app/src/main/res/values/strings.xml`:
```xml
<string name="app_name">Your App Name</string>
```

### App Icon
Edit `app/src/main/res/drawable/ic_launcher_foreground.xml`  
or use **Image Asset Studio** in Android Studio (`File → New → Image Asset`).

### Font
Replace TTF files in `app/src/main/res/font/` and update `ui/theme/Type.kt`:
```kotlin
val YourFontFamily = FontFamily(
    Font(R.font.your_font_regular, FontWeight.Normal),
    Font(R.font.your_font_bold,    FontWeight.Bold),
)
```

### Package Name
1. Change `applicationId` in `app/build.gradle.kts`
2. Rename the Java source folder to match the new package
3. Use **Find & Replace** in Android Studio to update all `package` declarations

### Colors
Edit `app/src/main/res/values/colors.xml` and `ui/theme/Color.kt`.

---

## Common Issues

**`Hilt component mismatch` at build time**  
→ Make sure `@HiltAndroidApp` is on your `Application` class (`YadavarChekApp`).

**`WorkManager` not using Hilt factory**  
→ The `InitializationProvider` in `AndroidManifest.xml` removes the default initializer.  
→ `YadavarChekApp` implements `Configuration.Provider` and injects `HiltWorkerFactory`. Do not remove either.

**`Room schema export` warning**  
→ Add to `app/build.gradle.kts` inside `android {}`:
```kotlin
ksp {
    arg("room.schemaLocation", "$projectDir/schemas")
}
```

**Font not found at compile time**  
→ Font file names must contain only lowercase letters, digits, and underscores.

**Notification actions not firing**  
→ `NotificationActionReceiver` must be registered in `AndroidManifest.xml` with the correct `<intent-filter>` actions (already done).

**App crashes on boot after update**  
→ `BootReceiver` handles both `BOOT_COMPLETED` and `MY_PACKAGE_REPLACED` — both are registered in the manifest.

---

## Roadmap

- [x] Core architecture (MVVM + Clean + Hilt + Room)
- [x] Dashboard with financial summary
- [x] Multi-step reminder system (WorkManager)
- [x] Splash + Onboarding
- [ ] Full Check CRUD (AddEdit + Detail screens)
- [ ] Jalali calendar integration
- [ ] PDF/Excel report generation
- [ ] Customer management
- [ ] Biometric lock
- [ ] Local backup/restore
- [ ] Widget

---

## License

```
MIT License

Copyright (c) 2024 moha100h

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
```
