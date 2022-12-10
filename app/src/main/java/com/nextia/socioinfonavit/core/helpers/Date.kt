package com.nextia.socioinfonavit.core.helpers

import com.nextia.socioinfonavit.core.extension.toDate
import java.text.SimpleDateFormat
import java.util.*

fun differenceDay(expiration_date: String): Long {
    return try {

        val cal = Calendar.getInstance().time
        val mTimeFormat = SimpleDateFormat("yyyy-MM-dd").format(cal)
        val mTime = SimpleDateFormat("yyyy-MM-dd").parse(mTimeFormat)

        val days = (expiration_date.toDate()?.time!! - mTime.time) / (1000 * 60 * 60 * 24)
        if (days <= 0) 0 else days
    } catch (e: Exception) {0}
}

/**
 * ChronoUnit.DAYS.between(mTime.toInstant().atZone(ZoneId.systemDefault()).toLocalDate(), expiration_date.toDate()!!.toInstant().atZone(
ZoneId.systemDefault()
).toLocalDate())
 * **/