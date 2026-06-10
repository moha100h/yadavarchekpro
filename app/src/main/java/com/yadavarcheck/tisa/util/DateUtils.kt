package com.yadavarcheck.tisa.util
import java.text.NumberFormat; import java.util.Locale
fun Long.toJalaliDisplay(): String { val j = JalaliCalendar.fromMillis(this); return "${j.day} ${j.monthName()} ${j.year}" }
fun Long.toJalaliShort(): String { val j = JalaliCalendar.fromMillis(this); return "%04d/%02d/%02d".format(j.year, j.month, j.day) }
fun Long.daysUntil(): Int = ((this - System.currentTimeMillis()) / 86_400_000L).toInt()
fun Long.isOverdue(): Boolean = this < System.currentTimeMillis()
fun formatAmount(amount: Long): String = NumberFormat.getNumberInstance(Locale("fa","IR")).format(amount)
