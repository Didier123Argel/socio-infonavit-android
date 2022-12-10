package com.nextia.socioinfonavit.core.extension

import android.text.format.DateUtils
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.math.abs

fun Date.withFormat(format : String) : String{
    val sdf = SimpleDateFormat(format, Locale.getDefault())
    return sdf.format(this)
}

fun String.toDate(format: String = "yyyy-MM-dd"): Date?{
    val formatter = SimpleDateFormat(format, Locale.getDefault())
    return try {
        formatter.parse(this)
    }catch(e: ParseException){
        null
    }

}

fun String.toDate(format: String = "yyyy-MM-dd", locale: Locale = Locale.getDefault()): Date?{
    val formatter = SimpleDateFormat(format, locale)
    return try {
        formatter.parse(this)
    }catch(e: ParseException){
        null
    }

}

fun String.dateToAnotherFormat(originFormat: String, targetFormat:String): String{
    var mDate = this.toDate(originFormat)
    return mDate?.withFormat(targetFormat) ?: ""

}

fun Date.getRelativeTime(): CharSequence? {
    val now: Long = System.currentTimeMillis()
    return if (abs(now - this.time) > TimeUnit.MINUTES.toMillis(1)) {
        DateUtils.getRelativeTimeSpanString(
            this.time,
            now,
            DateUtils.MINUTE_IN_MILLIS,
            DateUtils.FORMAT_ABBREV_RELATIVE
        )

    } else {
        "just now"
    }
}