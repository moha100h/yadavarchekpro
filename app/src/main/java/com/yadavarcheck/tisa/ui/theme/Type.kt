package com.yadavarcheck.tisa.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.yadavarcheck.tisa.R

val VazirmatnFamily = FontFamily(
    Font(R.font.vazirmatn_thin,    FontWeight.Thin),
    Font(R.font.vazirmatn_light,   FontWeight.Light),
    Font(R.font.vazirmatn_regular, FontWeight.Normal),
    Font(R.font.vazirmatn_medium,  FontWeight.Medium),
    Font(R.font.vazirmatn_bold,    FontWeight.Bold)
)

val Typography = Typography(
    displayLarge  = TextStyle(fontFamily = VazirmatnFamily, fontWeight = FontWeight.Bold,   fontSize = 57.sp, lineHeight = 64.sp),
    displayMedium = TextStyle(fontFamily = VazirmatnFamily, fontWeight = FontWeight.Bold,   fontSize = 45.sp, lineHeight = 52.sp),
    displaySmall  = TextStyle(fontFamily = VazirmatnFamily, fontWeight = FontWeight.Normal, fontSize = 36.sp, lineHeight = 44.sp),
    headlineLarge = TextStyle(fontFamily = VazirmatnFamily, fontWeight = FontWeight.Bold,   fontSize = 32.sp, lineHeight = 40.sp),
    headlineMedium= TextStyle(fontFamily = VazirmatnFamily, fontWeight = FontWeight.Bold,   fontSize = 28.sp, lineHeight = 36.sp),
    headlineSmall = TextStyle(fontFamily = VazirmatnFamily, fontWeight = FontWeight.Medium, fontSize = 24.sp, lineHeight = 32.sp),
    titleLarge    = TextStyle(fontFamily = VazirmatnFamily, fontWeight = FontWeight.Bold,   fontSize = 22.sp, lineHeight = 28.sp),
    titleMedium   = TextStyle(fontFamily = VazirmatnFamily, fontWeight = FontWeight.Medium, fontSize = 16.sp, lineHeight = 24.sp),
    titleSmall    = TextStyle(fontFamily = VazirmatnFamily, fontWeight = FontWeight.Medium, fontSize = 14.sp, lineHeight = 20.sp),
    bodyLarge     = TextStyle(fontFamily = VazirmatnFamily, fontWeight = FontWeight.Normal, fontSize = 16.sp, lineHeight = 24.sp),
    bodyMedium    = TextStyle(fontFamily = VazirmatnFamily, fontWeight = FontWeight.Normal, fontSize = 14.sp, lineHeight = 20.sp),
    bodySmall     = TextStyle(fontFamily = VazirmatnFamily, fontWeight = FontWeight.Normal, fontSize = 12.sp, lineHeight = 16.sp),
    labelLarge    = TextStyle(fontFamily = VazirmatnFamily, fontWeight = FontWeight.Medium, fontSize = 14.sp, lineHeight = 20.sp),
    labelMedium   = TextStyle(fontFamily = VazirmatnFamily, fontWeight = FontWeight.Medium, fontSize = 12.sp, lineHeight = 16.sp),
    labelSmall    = TextStyle(fontFamily = VazirmatnFamily, fontWeight = FontWeight.Medium, fontSize = 11.sp, lineHeight = 16.sp)
)
