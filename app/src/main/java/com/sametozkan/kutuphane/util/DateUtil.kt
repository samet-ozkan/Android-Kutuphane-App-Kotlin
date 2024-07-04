package com.sametozkan.kutuphane.util

import android.os.Build
import okhttp3.internal.format
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object DateUtil {

    private val formatter = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        DateTimeFormatter.ofPattern("dd-MM-yyyy")
    } else {
        org.threeten.bp.format.DateTimeFormatter.ofPattern("dd-MM-yyyy")
    }

    private val dateTimeFormatter = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")
    } else {
        org.threeten.bp.format.DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")
    }

    fun stringToLocalDate(dateString: String): Any {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            LocalDate.parse(dateString, formatter as DateTimeFormatter?)
        } else {
            org.threeten.bp.LocalDate.parse(dateString, formatter as org.threeten.bp.format.DateTimeFormatter?)
        }
    }

    fun stringToLocalDateTime(dateString: String): Any {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            LocalDateTime.parse(dateString, dateTimeFormatter as DateTimeFormatter)
        } else {
            org.threeten.bp.LocalDateTime.parse(dateString, dateTimeFormatter as org.threeten.bp.format.DateTimeFormatter?)
        }
    }

    fun localDateToString(localDate: Any): String {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            (localDate as LocalDate).format(formatter as DateTimeFormatter?)
        } else {
            (localDate as org.threeten.bp.LocalDate).format(formatter as org.threeten.bp.format.DateTimeFormatter)
        }
    }
}
