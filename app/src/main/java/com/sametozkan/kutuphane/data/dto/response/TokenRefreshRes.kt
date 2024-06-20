package com.sametozkan.kutuphane.data.dto.response

import java.time.Instant

data class TokenRefreshRes(
    val jwt: String,
    val refreshToken: String,
    val refreshExpiryDate: Long
)
