# یادآور چک پرو 📋

اپلیکیشن حرفه‌ای مدیریت چک برای اندروید — ساخته‌شده با Kotlin + Jetpack Compose

---

## ویژگی‌ها

- ثبت و مدیریت چک‌های دریافتنی و پرداختنی
- یادآور چندمرحله‌ای (۳۰/۱۵/۷/۳/۱ روز قبل + روز سررسید)
- داشبورد با خلاصه مالی و نمودار
- تقویم شمسی
- گزارش‌های PDF/Excel
- مدیریت مشتریان
- پشتیبان‌گیری و بازیابی محلی
- امنیت با PIN و اثر انگشت
- تم تاریک/روشن/داینامیک
- فونت فارسی Vazirmatn
- پشتیبانی کامل RTL

---

## پیش‌نیازها

- Android Studio Hedgehog یا بالاتر
- JDK 17
- Android SDK 35
- Gradle 8.x

---

## نصب و راه‌اندازی

```bash
git clone https://github.com/moha100h/yadavarchekpro.git
cd yadavarchekpro
```

در Android Studio:
1. `File → Open` → پوشه پروژه را انتخاب کنید
2. منتظر بمانید تا Gradle sync کامل شود
3. دستگاه یا امولاتور را انتخاب کنید
4. `Run → Run 'app'`

---

## بیلد APK

```bash
# Debug APK
./gradlew assembleDebug

# Release APK (نیاز به keystore)
./gradlew assembleRelease
```

خروجی: `app/build/outputs/apk/`

---

## بیلد AAB (برای پلی‌استور)

```bash
./gradlew bundleRelease
```

خروجی: `app/build/outputs/bundle/release/app-release.aab`

---

## امضای ریلیز

1. در Android Studio: `Build → Generate Signed Bundle/APK`
2. یا از طریق `keystore.properties`:

```properties
storeFile=path/to/keystore.jks
storePassword=YOUR_STORE_PASSWORD
keyAlias=YOUR_KEY_ALIAS
keyPassword=YOUR_KEY_PASSWORD
```

در `app/build.gradle.kts` اضافه کنید:

```kotlin
signingConfigs {
    create("release") {
        val props = Properties().apply { load(rootProject.file("keystore.properties").inputStream()) }
        storeFile = file(props["storeFile"] as String)
        storePassword = props["storePassword"] as String
        keyAlias = props["keyAlias"] as String
        keyPassword = props["keyPassword"] as String
    }
}
buildTypes {
    release { signingConfig = signingConfigs.getByName("release") }
}
```

---

## تغییر نام اپ

در `app/src/main/res/values/strings.xml`:
```xml
<string name="app_name">نام دلخواه</string>
```

---

## تغییر پکیج

1. در `app/build.gradle.kts` مقدار `applicationId` را تغییر دهید
2. پوشه `app/src/main/java/com/yadavarcheck/tisa` را به پکیج جدید rename کنید
3. همه `package` declarationها را آپدیت کنید (Find & Replace در Android Studio)

---

## تغییر فونت

فایل‌های TTF را در `app/src/main/res/font/` قرار دهید و `vazirmatn_font_family.xml` را آپدیت کنید.
سپس در `ui/theme/Type.kt` نام FontFamily را تغییر دهید.

---

## تغییر آیکن

فایل `app/src/main/res/drawable/ic_launcher_foreground.xml` را ویرایش کنید
یا از `Image Asset Studio` در Android Studio استفاده کنید.

---

## ساختار پروژه

```
app/src/main/java/com/yadavarcheck/tisa/
├── data/
│   ├── local/          # Room DB, DAOs, Entities, Converters
│   ├── mapper/         # Entity ↔ Domain mappers
│   ├── preferences/    # DataStore
│   ├── receiver/       # BroadcastReceivers
│   ├── repository/     # Repository implementations
│   └── worker/         # WorkManager workers
├── di/                 # Hilt modules
├── domain/
│   ├── model/          # Domain models
│   └── repository/     # Repository interfaces
└── ui/
    ├── components/     # Shared Composables
    ├── navigation/     # NavHost + Routes
    ├── screens/        # Feature screens
    └── theme/          # Color, Type, Theme
```

---

## خطاهای رایج

**`Hilt component mismatch`**
→ مطمئن شوید `@HiltAndroidApp` روی Application class دارید

**`Room schema export`**
→ در `build.gradle.kts` اضافه کنید:
```kotlin
ksp { arg("room.schemaLocation", "$projectDir/schemas") }
```

**`WorkManager initialization`**
→ `InitializationProvider` در مانیفست باید `tools:node="merge"` داشته باشد (موجود است)

**`Font not found`**
→ نام فایل‌های TTF باید فقط حروف کوچک، اعداد و underscore باشد

---

## مجوز

MIT License — آزاد برای استفاده شخصی و تجاری
