package com.sametozkan.kutuphane.util

sealed class MyResult<out T> {
    data class Success<out T>(val data: T) : MyResult<T>()
    data class Error(val exception: Exception, val responseCode: Int?) : MyResult<Nothing>()
}
