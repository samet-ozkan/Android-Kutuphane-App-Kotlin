package com.sametozkan.kutuphane.data.dto.response

data class JwtRes(
    val jwt: String,
    val accountId: Long,
    val accountType: String,
    val email: String
)
