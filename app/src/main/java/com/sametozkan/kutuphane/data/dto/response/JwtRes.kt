package com.sametozkan.kutuphane.data.dto.response

import java.time.Instant

data class JwtRes(
    val jwt: String,
    val refreshToken: String,
    val refreshExpiryDate: Long,
    val accountId: Long,
    val accountType: String,
    val email: String
)
