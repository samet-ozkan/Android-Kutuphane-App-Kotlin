package com.sametozkan.kutuphane.data.dto.request

data class LoginReq(
    val email: String,
    val password: String,
    val accountType: String
)
