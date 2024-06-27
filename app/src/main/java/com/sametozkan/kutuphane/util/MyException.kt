package com.sametozkan.kutuphane.util

sealed class MyException(message: String): Exception(message) {
    data class AllFieldsMustBeFilledException(override val message: String) : MyException(message)
    data class InvalidCredentialsException(override val message: String) : MyException(message)
    data class ConflictException(override val message: String) : MyException(message)
}
