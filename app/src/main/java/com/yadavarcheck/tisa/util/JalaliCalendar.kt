package com.yadavarcheck.tisa.util
import java.util.Calendar
object JalaliCalendar {
    private val dim = intArrayOf(31,31,31,31,31,31,30,30,30,30,30,29)
    val persianMonths = arrayOf("فروردین","اردیبهشت","خرداد","تیر","مرداد","شهریور","مهر","آبان","آذر","دی","بهمن","اسفند")
    val persianDaysShort = arrayOf("ش","ی","د","س","چ","پ","ج")
    data class JalaliDate(val year: Int, val month: Int, val day: Int) {
        override fun toString() = "%04d/%02d/%02d".format(year, month, day)
        fun monthName() = persianMonths[month - 1]
        fun toMillis(): Long {
            val g = toGregorian(); val cal = Calendar.getInstance()
            cal.set(g[0], g[1]-1, g[2], 0, 0, 0); cal.set(Calendar.MILLISECOND, 0); return cal.timeInMillis
        }
        fun toGregorian(): IntArray {
            var jy = year - 979; val jm = month - 1
            var jdn = 365*jy + (jy/33)*8 + (jy%33+3)/4
            for (i in 0 until jm) jdn += dim[i]; jdn += day - 1
            var gdn = jdn + 79; var gy = 1600 + 400*(gdn/146097); gdn %= 146097
            var leap = true
            if (gdn >= 36525) { gdn--; gy += 100*(gdn/36524); gdn %= 36524; if (gdn >= 365) gdn++ else leap = false }
            gy += 4*(gdn/1461); gdn %= 1461
            if (gdn >= 366) { leap = false; gdn--; gy += gdn/365; gdn %= 365 }
            val gdim = intArrayOf(31, if (leap) 29 else 28, 31,30,31,30,31,31,30,31,30,31)
            var gm = 0; while (gm < 12 && gdn >= gdim[gm]) { gdn -= gdim[gm]; gm++ }
            return intArrayOf(gy, gm+1, gdn+1)
        }
    }
    fun now() = fromMillis(System.currentTimeMillis())
    fun fromMillis(ms: Long): JalaliDate {
        val c = Calendar.getInstance(); c.timeInMillis = ms
        return fromGregorian(c.get(Calendar.YEAR), c.get(Calendar.MONTH)+1, c.get(Calendar.DAY_OF_MONTH))
    }
    fun fromGregorian(gy: Int, gm: Int, gd: Int): JalaliDate {
        val gdn0 = intArrayOf(0,31,59,90,120,151,181,212,243,273,304,334)
        var gdn = 365*(gy-1) + (gy-1)/4 - (gy-1)/100 + (gy-1)/400 + gdn0[gm-1] + gd - 1
        if (gm > 2 && gy%4==0 && (gy%100!=0 || gy%400==0)) gdn++
        var jdn = gdn - 79; val jnp = jdn/12053; jdn %= 12053
        var jy = 979 + 33*jnp + 4*(jdn/1461); jdn %= 1461
        if (jdn >= 366) { jy += (jdn-1)/365; jdn = (jdn-1)%365 }
        var i = 0; while (i < 11 && jdn >= dim[i]) { jdn -= dim[i]; i++ }
        return JalaliDate(jy, i+1, jdn+1)
    }
    fun parse(s: String) = try { val p = s.split("/"); JalaliDate(p[0].toInt(), p[1].toInt(), p[2].toInt()) } catch (e: Exception) { null }
    fun daysInMonth(y: Int, m: Int): Int { if (m == 12) { val r = y%2820+474; return if (((r+38)*682)%2816 < 682) 30 else 29 }; return dim[m-1] }
}
