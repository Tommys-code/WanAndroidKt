package com.tommy.shen.module_common.util

import java.text.SimpleDateFormat
import java.util.*

const val FORMAT_DATE_NORMAL = "yyyy-MM-dd hh:mm:ss"

fun getDateFormat(
    time: Long,
    format: String = FORMAT_DATE_NORMAL
): String {
    return SimpleDateFormat(format, Locale.getDefault()).format(time)
}
